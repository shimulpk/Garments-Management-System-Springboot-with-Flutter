import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../models/store_requisition_item_request.dart';
import '../models/store_requisition_request.dart';
import '../provider/store_requisition_provider.dart';
import '../provider/store_provider.dart';

class EditStoreRequisitionScreen extends ConsumerStatefulWidget {
  const EditStoreRequisitionScreen({
    super.key,
    required this.requisitionId,
  });

  final int requisitionId;

  @override
  ConsumerState<EditStoreRequisitionScreen> createState() =>
      _EditStoreRequisitionScreenState();
}

class _EditStoreRequisitionScreenState
    extends ConsumerState<EditStoreRequisitionScreen> {
  final _formKey = GlobalKey<FormState>();

  final _requestedByController = TextEditingController();
  final _departmentController = TextEditingController();
  final _remarksController = TextEditingController();

  final Map<int, TextEditingController> _quantityControllers = {};
  final Set<int> _selectedItemIds = {};

  bool _initialized = false;
  bool _saving = false;

  @override
  Widget build(BuildContext context) {
    final requisitionAsync =
    ref.watch(storeRequisitionByIdProvider(widget.requisitionId));

    final itemsAsync = ref.watch(allItemsProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Edit Requisition'),
      ),
      body: requisitionAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),
        error: (error, stack) => Center(
          child: Text('Failed to load requisition\n$error'),
        ),
        data: (requisition) {
          if (requisition.status != 'PENDING') {
            return Center(
              child: Text(
                'This requisition cannot be edited.\n'
                    'Current status: ${requisition.status}',
                textAlign: TextAlign.center,
              ),
            );
          }

          if (!_initialized) {
            _requestedByController.text =
                requisition.requestedBy ?? '';

            _departmentController.text =
                requisition.department ?? '';

            _remarksController.text =
                requisition.remarks ?? '';

            for (final item in requisition.items) {
              if (item.itemId == null) continue;

              _selectedItemIds.add(item.itemId!);

              _quantityControllers[item.itemId!] =
                  TextEditingController(
                    text: '${item.quantity ?? ''}',
                  );
            }

            _initialized = true;
          }

          return itemsAsync.when(
            loading: () => const Center(
              child: CircularProgressIndicator(),
            ),
            error: (error, stack) => Center(
              child: Text('Failed to load items\n$error'),
            ),
            data: (items) {
              return Form(
                key: _formKey,
                child: ListView(
                  padding: const EdgeInsets.all(16),
                  children: [
                    TextFormField(
                      controller: _requestedByController,
                      decoration: const InputDecoration(
                        labelText: 'Requested By',
                        prefixIcon: Icon(Icons.person_outline),
                        border: OutlineInputBorder(),
                      ),
                      validator: (value) {
                        if (value == null ||
                            value.trim().isEmpty) {
                          return 'Requested by is required';
                        }
                        return null;
                      },
                    ),

                    const SizedBox(height: 16),

                    TextFormField(
                      controller: _departmentController,
                      decoration: const InputDecoration(
                        labelText: 'Department',
                        prefixIcon:
                        Icon(Icons.business_outlined),
                        border: OutlineInputBorder(),
                      ),
                      validator: (value) {
                        if (value == null ||
                            value.trim().isEmpty) {
                          return 'Department is required';
                        }
                        return null;
                      },
                    ),

                    const SizedBox(height: 16),

                    TextFormField(
                      controller: _remarksController,
                      maxLines: 3,
                      decoration: const InputDecoration(
                        labelText: 'Remarks',
                        prefixIcon: Icon(Icons.notes_outlined),
                        border: OutlineInputBorder(),
                      ),
                    ),

                    const SizedBox(height: 24),

                    const Text(
                      'Requested Items',
                      style: TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),

                    const SizedBox(height: 10),

                    ...items.map((item) {
                      final id = item.id;

                      if (id == null) {
                        return const SizedBox.shrink();
                      }

                      final selected =
                      _selectedItemIds.contains(id);

                      _quantityControllers.putIfAbsent(
                        id,
                            () => TextEditingController(),
                      );

                      return Card(
                        margin:
                        const EdgeInsets.only(bottom: 10),
                        child: Padding(
                          padding: const EdgeInsets.all(10),
                          child: Column(
                            children: [
                              CheckboxListTile(
                                value: selected,
                                contentPadding:
                                EdgeInsets.zero,
                                title: Text(
                                  item.itemName ?? '-',
                                  style: const TextStyle(
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                subtitle: Text(
                                  '${item.category ?? '-'} • '
                                      '${item.unit ?? '-'}',
                                ),
                                onChanged: (value) {
                                  setState(() {
                                    if (value == true) {
                                      _selectedItemIds.add(id);
                                    } else {
                                      _selectedItemIds.remove(id);
                                      _quantityControllers[id]
                                          ?.clear();
                                    }
                                  });
                                },
                              ),

                              if (selected)
                                TextFormField(
                                  controller:
                                  _quantityControllers[id],
                                  keyboardType:
                                  const TextInputType
                                      .numberWithOptions(
                                    decimal: true,
                                  ),
                                  decoration: InputDecoration(
                                    labelText:
                                    'Quantity (${item.unit ?? ''})',
                                    prefixIcon:
                                    const Icon(Icons.numbers),
                                    border:
                                    const OutlineInputBorder(),
                                  ),
                                  validator: (value) {
                                    if (!selected) return null;

                                    final quantity =
                                    double.tryParse(
                                      value ?? '',
                                    );

                                    if (quantity == null ||
                                        quantity <= 0) {
                                      return 'Enter valid quantity';
                                    }

                                    return null;
                                  },
                                ),
                            ],
                          ),
                        ),
                      );
                    }),

                    const SizedBox(height: 20),

                    SizedBox(
                      height: 50,
                      child: ElevatedButton.icon(
                        onPressed:
                        _saving ? null : _updateRequisition,
                        icon: _saving
                            ? const SizedBox(
                          width: 20,
                          height: 20,
                          child:
                          CircularProgressIndicator(
                            strokeWidth: 2,
                          ),
                        )
                            : const Icon(Icons.update),
                        label: Text(
                          _saving
                              ? 'Updating...'
                              : 'Update Requisition',
                        ),
                      ),
                    ),
                  ],
                ),
              );
            },
          );
        },
      ),
    );
  }

  Future<void> _updateRequisition() async {
    if (!_formKey.currentState!.validate()) return;

    if (_selectedItemIds.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Select at least one item'),
        ),
      );
      return;
    }

    setState(() {
      _saving = true;
    });

    final requestItems = _selectedItemIds.map((id) {
      return StoreRequisitionItemRequest(
        itemId: id,
        quantity: double.parse(
          _quantityControllers[id]!.text.trim(),
        ),
      );
    }).toList();

    final request = StoreRequisitionRequest(
      requisitionDate:
      DateTime.now().toIso8601String().split('T')[0],
      requestedBy: _requestedByController.text.trim(),
      department: _departmentController.text.trim(),
      remarks: _remarksController.text.trim(),
      items: requestItems,
    );

    try {
      await ref
          .read(storeRequisitionRepositoryProvider)
          .update(
        widget.requisitionId,
        request,
      );

      ref.invalidate(
        storeRequisitionByIdProvider(widget.requisitionId),
      );
      ref.invalidate(allStoreRequisitionsProvider);
      ref.invalidate(pendingStoreRequisitionsProvider);
      ref.invalidate(storeDashboardProvider);

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content:
          Text('Requisition updated successfully!'),
        ),
      );

      Navigator.of(context).pop();
    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Update failed: $e'),
        ),
      );
    } finally {
      if (mounted) {
        setState(() {
          _saving = false;
        });
      }
    }
  }

  @override
  void dispose() {
    _requestedByController.dispose();
    _departmentController.dispose();
    _remarksController.dispose();

    for (final controller in _quantityControllers.values) {
      controller.dispose();
    }

    super.dispose();
  }
}