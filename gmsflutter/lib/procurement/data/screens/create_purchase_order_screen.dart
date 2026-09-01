import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/procurement/data/models/purchase_order_item_request.dart';
import 'package:gmsflutter/procurement/data/models/purchase_order_request.dart';
import 'package:gmsflutter/procurement/data/provider/purchase_order_provider.dart';
import 'package:gmsflutter/store/data/provider/store_provider.dart';
import 'package:gmsflutter/store/data/provider/store_requisition_provider.dart';
import 'package:gmsflutter/store/data/models/store_requisition_response.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

import '../provider/vendor_provider.dart';

class CreatePurchaseOrderScreen extends ConsumerStatefulWidget {
  const CreatePurchaseOrderScreen({super.key});

  @override
  ConsumerState<CreatePurchaseOrderScreen> createState() =>
      _CreatePurchaseOrderScreenState();
}

class _CreatePurchaseOrderScreenState
    extends ConsumerState<CreatePurchaseOrderScreen> {
  final _formKey = GlobalKey<FormState>();

  final _remarksController = TextEditingController();

  StoreRequisitionResponse? _selectedRequisition;

  int? _selectedVendorId;

  bool _saving = false;

  final Map<int, TextEditingController> _priceControllers = {};

  @override
  Widget build(BuildContext context) {
    final requisitionsAsync =
    ref.watch(approvedStoreRequisitionsProvider);

    final vendorsAsync =
    ref.watch(allVendorsProvider);

    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Create Purchase Order'),
      ),

      body: requisitionsAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Text(
            'Failed to load approved requisitions\n$error',
            textAlign: TextAlign.center,
          ),
        ),

        data: (requisitions) {
          return vendorsAsync.when(
            loading: () => const Center(
              child: CircularProgressIndicator(),
            ),

            error: (error, stack) => Center(
              child: Text(
                'Failed to load vendors\n$error',
                textAlign: TextAlign.center,
              ),
            ),

            data: (vendors) {
              return Form(
                key: _formKey,

                child: ListView(
                  padding: const EdgeInsets.all(16),

                  children: [
                    // =====================================
                    // PO DATE
                    // =====================================

                    TextFormField(
                      initialValue:
                      DateTime.now()
                          .toIso8601String()
                          .split('T')[0],
                      readOnly: true,

                      decoration: const InputDecoration(
                        labelText: 'PO Date',
                        prefixIcon:
                        Icon(Icons.calendar_today_outlined),
                        border: OutlineInputBorder(),
                      ),
                    ),

                    const SizedBox(height: 16),

                    // =====================================
                    // APPROVED REQUISITION
                    // =====================================

                    DropdownButtonFormField<int>(
                      value: _selectedRequisition?.id,

                      decoration: const InputDecoration(
                        labelText: 'Approved Store Requisition',
                        prefixIcon:
                        Icon(Icons.description_outlined),
                        border: OutlineInputBorder(),
                      ),

                      hint: const Text(
                        'Select approved requisition',
                      ),

                      items: requisitions
                          .map(
                            (requisition) =>
                            DropdownMenuItem<int>(
                              value: requisition.id,
                              child: Text(
                                '${requisition.prNo ?? 'PR'}'
                                    ' • '
                                    '${requisition.department ?? '-'}',
                              ),
                            ),
                      )
                          .toList(),

                      onChanged: (id) {
                        if (id == null) return;

                        final selected =
                        requisitions.firstWhere(
                              (req) => req.id == id,
                        );

                        setState(() {
                          _selectedRequisition =
                              selected;

                          _clearPriceControllers();

                          for (final item
                          in selected.items) {
                            if (item.itemId != null) {
                              _priceControllers[
                              item.itemId!] =
                                  TextEditingController();
                            }
                          }
                        });
                      },

                      validator: (value) {
                        if (value == null) {
                          return 'Please select requisition';
                        }

                        return null;
                      },
                    ),

                    const SizedBox(height: 16),

                    // =====================================
                    // VENDOR
                    // =====================================

                    DropdownButtonFormField<int>(
                      value: _selectedVendorId,

                      decoration: const InputDecoration(
                        labelText: 'Vendor',
                        prefixIcon:
                        Icon(Icons.business_outlined),
                        border: OutlineInputBorder(),
                      ),

                      hint: const Text(
                        'Select vendor',
                      ),

                      items: vendors
                          .where(
                            (vendor) => vendor.id != null,
                      )
                          .map(
                            (vendor) =>
                            DropdownMenuItem<int>(
                              value: vendor.id,
                              child: Text(
                                vendor.companyName ??
                                    'Unnamed Vendor',
                              ),
                            ),
                      )
                          .toList(),

                      onChanged: (value) {
                        setState(() {
                          _selectedVendorId = value;
                        });
                      },

                      validator: (value) {
                        if (value == null) {
                          return 'Please select vendor';
                        }

                        return null;
                      },
                    ),

                    const SizedBox(height: 24),

                    // =====================================
                    // REQUISITION INFORMATION
                    // =====================================

                    if (_selectedRequisition != null)
                      _buildRequisitionInfo(
                        _selectedRequisition!,
                      ),

                    if (_selectedRequisition != null)
                      const SizedBox(height: 24),

                    // =====================================
                    // ITEMS
                    // =====================================

                    if (_selectedRequisition != null)
                      const Text(
                        'Purchase Order Items',
                        style: TextStyle(
                          fontSize: 19,
                          fontWeight: FontWeight.bold,
                        ),
                      ),

                    if (_selectedRequisition != null)
                      const SizedBox(height: 10),

                    if (_selectedRequisition != null)
                      ..._selectedRequisition!.items.map(
                            (item) => _buildItemCard(item),
                      ),

                    if (_selectedRequisition != null)
                      const SizedBox(height: 20),

                    // =====================================
                    // REMARKS
                    // =====================================

                    TextFormField(
                      controller: _remarksController,
                      maxLines: 3,

                      decoration: const InputDecoration(
                        labelText: 'Remarks',
                        prefixIcon:
                        Icon(Icons.notes_outlined),
                        border: OutlineInputBorder(),
                      ),
                    ),

                    const SizedBox(height: 28),

                    // =====================================
                    // CREATE BUTTON
                    // =====================================

                    SizedBox(
                      height: 50,

                      child: ElevatedButton.icon(
                        onPressed:
                        _saving ? null : _createPurchaseOrder,

                        icon: _saving
                            ? const SizedBox(
                          width: 20,
                          height: 20,
                          child:
                          CircularProgressIndicator(
                            strokeWidth: 2,
                          ),
                        )
                            : const Icon(Icons.save),

                        label: Text(
                          _saving
                              ? 'Creating...'
                              : 'Create Purchase Order',
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

  // ==========================================
  // REQUISITION INFO
  // ==========================================

  Widget _buildRequisitionInfo(
      StoreRequisitionResponse requisition,
      ) {
    return Card(
      elevation: 2,

      child: Padding(
        padding: const EdgeInsets.all(16),

        child: Column(
          crossAxisAlignment:
          CrossAxisAlignment.start,

          children: [
            const Text(
              'Requisition Information',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),

            const Divider(height: 24),

            _InfoRow(
              title: 'PR No',
              value: requisition.prNo ?? '-',
            ),

            _InfoRow(
              title: 'Date',
              value:
              requisition.requisitionDate ?? '-',
            ),

            _InfoRow(
              title: 'Requested By',
              value:
              requisition.requestedBy ?? '-',
            ),

            _InfoRow(
              title: 'Department',
              value:
              requisition.department ?? '-',
            ),

            _InfoRow(
              title: 'Status',
              value:
              requisition.status ?? '-',
            ),
          ],
        ),
      ),
    );
  }

  // ==========================================
  // ITEM CARD
  // ==========================================

  Widget _buildItemCard(
      StoreRequisitionItemResponse item,
      ) {
    if (item.itemId == null) {
      return const SizedBox.shrink();
    }

    final controller =
    _priceControllers[item.itemId!];

    if (controller == null) {
      return const SizedBox.shrink();
    }

    return Card(
      margin: const EdgeInsets.only(bottom: 10),

      child: Padding(
        padding: const EdgeInsets.all(14),

        child: Column(
          crossAxisAlignment:
          CrossAxisAlignment.start,

          children: [
            Text(
              item.itemName ?? '-',
              style: const TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 6),

            Text(
              'Unit: ${item.unit ?? '-'}',
            ),

            const SizedBox(height: 4),

            Text(
              'Quantity: ${item.quantity ?? 0}',
              style: const TextStyle(
                fontWeight: FontWeight.w600,
              ),
            ),

            const SizedBox(height: 12),

            TextFormField(
              controller: controller,

              keyboardType:
              const TextInputType.numberWithOptions(
                decimal: true,
              ),

              decoration: const InputDecoration(
                labelText: 'Unit Price',
                prefixIcon:
                Icon(Icons.price_change_outlined),
                border: OutlineInputBorder(),
              ),

              validator: (value) {
                final price =
                double.tryParse(
                  value?.trim() ?? '',
                );

                if (price == null || price < 0) {
                  return 'Enter valid unit price';
                }

                return null;
              },
            ),
          ],
        ),
      ),
    );
  }

  // ==========================================
  // CREATE PO
  // ==========================================

  Future<void> _createPurchaseOrder() async {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    if (_selectedRequisition == null) {
      return;
    }

    if (_selectedVendorId == null) {
      return;
    }

    setState(() {
      _saving = true;
    });

    try {
      final requisition =
      _selectedRequisition!;

      final items =
      requisition.items.map((item) {
        final controller =
        _priceControllers[item.itemId];

        final unitPrice =
        double.parse(
          controller!.text.trim(),
        );

        return PurchaseOrderItemRequest(
          itemId: item.itemId!,
          unitPrice: unitPrice,
        );
      }).toList();

      final request = PurchaseOrderRequest(
        poDate: DateTime.now()
            .toIso8601String()
            .split('T')[0],

        vendorId: _selectedVendorId!,

        storeRequisitionId:
        requisition.id!,

        remarks:
        _remarksController.text.trim(),

        items: items,
      );

      await ref
          .read(
        purchaseOrderRepositoryProvider,
      )
          .create(request);

      // Refresh PO data
      ref.invalidate(allPurchaseOrdersProvider);
      ref.invalidate(
        pendingPurchaseOrdersProvider,
      );

      // Important:
      // Approved requisition list refresh
      ref.invalidate(
        approvedStoreRequisitionsProvider,
      );

      // Store dashboard refresh
      ref.invalidate(storeDashboardProvider);

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            'Purchase Order created successfully!',
          ),
        ),
      );

      // Form reset
      setState(() {
        _selectedRequisition = null;
        _selectedVendorId = null;
        _remarksController.clear();
        _clearPriceControllers();
      });
    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Create Purchase Order failed: $e',
          ),
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

  // ==========================================
  // CLEAR PRICE CONTROLLERS
  // ==========================================

  void _clearPriceControllers() {
    for (final controller
    in _priceControllers.values) {
      controller.dispose();
    }

    _priceControllers.clear();
  }

  @override
  void dispose() {
    _remarksController.dispose();
    _clearPriceControllers();
    super.dispose();
  }
}

// ==========================================
// INFO ROW
// ==========================================

class _InfoRow extends StatelessWidget {
  const _InfoRow({
    required this.title,
    required this.value,
  });

  final String title;
  final String value;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding:
      const EdgeInsets.only(bottom: 10),

      child: Row(
        crossAxisAlignment:
        CrossAxisAlignment.start,

        children: [
          SizedBox(
            width: 110,

            child: Text(
              title,
              style: const TextStyle(
                fontWeight: FontWeight.w600,
              ),
            ),
          ),

          Expanded(
            child: Text(value),
          ),
        ],
      ),
    );
  }
}