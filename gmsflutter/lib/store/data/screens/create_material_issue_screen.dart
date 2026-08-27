import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/store/data/models/stock_response.dart';
import 'package:gmsflutter/store/data/provider/stock_provider.dart';

import '../models/material_issue_request.dart';
import '../provider/material_issue_provider.dart';

class CreateMaterialIssueScreen extends ConsumerStatefulWidget {
  const CreateMaterialIssueScreen({super.key});

  @override
  ConsumerState<CreateMaterialIssueScreen> createState() =>
      _CreateMaterialIssueScreenState();
}

class _CreateMaterialIssueScreenState
    extends ConsumerState<CreateMaterialIssueScreen> {
  final _formKey = GlobalKey<FormState>();

  final _departmentController = TextEditingController();
  final _requestedByController = TextEditingController();
  final _remarksController = TextEditingController();

  DateTime _issueDate = DateTime.now();

  bool _saving = false;

  final List<_SelectedIssueItem> _selectedItems = [];

  @override
  void dispose() {
    _departmentController.dispose();
    _requestedByController.dispose();
    _remarksController.dispose();

    super.dispose();
  }

  // ======================================================
  // DATE
  // ======================================================

  Future<void> _selectDate() async {
    final selected = await showDatePicker(
      context: context,
      initialDate: _issueDate,
      firstDate: DateTime(2020),
      lastDate: DateTime(2100),
    );

    if (selected != null) {
      setState(() {
        _issueDate = selected;
      });
    }
  }

  String _formatDate(DateTime date) {
    return '${date.year}-'
        '${date.month.toString().padLeft(2, '0')}-'
        '${date.day.toString().padLeft(2, '0')}';
  }

  // ======================================================
  // ADD ITEM
  // ======================================================

  Future<void> _addItem(List<StockResponse> stocks) async {
    if (stocks.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('No available stock found.'),
        ),
      );

      return;
    }

    final selectedId = await showDialog<int>(
      context: context,
      builder: (dialogContext) {
        int? tempSelectedId;

        return StatefulBuilder(
          builder: (context, setDialogState) {
            return AlertDialog(
              title: const Text('Select Item'),

              content: DropdownButtonFormField<int>(
                initialValue: tempSelectedId,
                isExpanded: true,

                decoration: const InputDecoration(
                  labelText: 'Item',
                  border: OutlineInputBorder(),
                ),

                items: stocks.map((stock) {
                  return DropdownMenuItem<int>(
                    value: stock.itemId,

                    child: Text(
                      '${stock.itemName ?? '-'} '
                          '(${stock.availableQuantity ?? 0} '
                          '${stock.unit ?? '-'})',

                      overflow: TextOverflow.ellipsis,
                    ),
                  );
                }).toList(),

                onChanged: (value) {
                  setDialogState(() {
                    tempSelectedId = value;
                  });
                },
              ),

              actions: [
                TextButton(
                  onPressed: () {
                    Navigator.pop(dialogContext);
                  },
                  child: const Text('Cancel'),
                ),

                ElevatedButton(
                  onPressed: tempSelectedId == null
                      ? null
                      : () {
                    Navigator.pop(
                      dialogContext,
                      tempSelectedId,
                    );
                  },
                  child: const Text('Add'),
                ),
              ],
            );
          },
        );
      },
    );

    if (selectedId == null) {
      return;
    }

    // Find selected stock using itemId
    final stock = stocks.firstWhere(
          (element) => element.itemId == selectedId,
    );

    // Check duplicate item
    final alreadyAdded = _selectedItems.any(
          (element) => element.itemId == selectedId,
    );

    if (alreadyAdded) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Item already added.'),
        ),
      );

      return;
    }

    setState(() {
      _selectedItems.add(
        _SelectedIssueItem(
          itemId: stock.itemId!,
          itemName: stock.itemName ?? '-',
          unit: stock.unit ?? '-',
          availableQuantity: stock.availableQuantity ?? 0,
        ),
      );
    });
  }

  // ======================================================
  // SAVE
  // ======================================================

  Future<void> _saveIssue() async {
    // Form validation
    if (!_formKey.currentState!.validate()) {
      return;
    }

    // Item validation
    if (_selectedItems.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Please add at least one item.'),
        ),
      );

      return;
    }

    // Quantity validation
    for (final item in _selectedItems) {
      final quantity = item.quantity;

      if (quantity == null || quantity <= 0) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(
              'Enter quantity for ${item.itemName}.',
            ),
          ),
        );

        return;
      }

      // Available stock validation
      if (quantity > item.availableQuantity) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(
              '${item.itemName}: Issue quantity '
                  'cannot exceed available stock '
                  '(${item.availableQuantity} ${item.unit}).',
            ),
          ),
        );

        return;
      }
    }

    setState(() {
      _saving = true;
    });

    try {
      final request = MaterialIssueRequest(
        issueDate: _formatDate(_issueDate),

        department: _departmentController.text.trim(),

        requestedBy: _requestedByController.text.trim(),

        remarks: _remarksController.text.trim(),

        items: _selectedItems.map((item) {
          return MaterialIssueItemRequest(
            itemId: item.itemId,
            quantity: item.quantity!,
          );
        }).toList(),
      );

      await ref
          .read(materialIssueRepositoryProvider)
          .create(request);

      // Refresh material issue list
      ref.invalidate(allMaterialIssuesProvider);

      // Refresh stock data
      ref.invalidate(availableStocksProvider);
      ref.invalidate(allStocksProvider);

      if (!mounted) {
        return;
      }

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            'Material issue created successfully!',
          ),
        ),
      );

      Navigator.pop(context);
    } catch (e) {
      if (!mounted) {
        return;
      }

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Failed to create material issue\n$e',
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

  // ======================================================
  // BUILD
  // ======================================================

  @override
  Widget build(BuildContext context) {
    final stocksAsync = ref.watch(availableStocksProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('New Material Issue'),
      ),

      body: stocksAsync.when(
        // ==================================================
        // LOADING
        // ==================================================

        loading: () {
          return const Center(
            child: CircularProgressIndicator(),
          );
        },

        // ==================================================
        // ERROR
        // ==================================================

        error: (error, stack) {
          return Center(
            child: Padding(
              padding: const EdgeInsets.all(20),

              child: Text(
                'Failed to load available stock\n$error',
                textAlign: TextAlign.center,
              ),
            ),
          );
        },

        // ==================================================
        // DATA
        // ==================================================

        data: (stocks) {
          return SingleChildScrollView(
            padding: const EdgeInsets.all(16),

            child: Form(
              key: _formKey,

              child: Column(
                crossAxisAlignment:
                CrossAxisAlignment.stretch,

                children: [
                  // ==================================================
                  // ISSUE DATE
                  // ==================================================

                  TextFormField(
                    readOnly: true,

                    controller: TextEditingController(
                      text: _formatDate(_issueDate),
                    ),

                    decoration: InputDecoration(
                      labelText: 'Issue Date',

                      prefixIcon: const Icon(
                        Icons.calendar_today,
                      ),

                      border: const OutlineInputBorder(),

                      suffixIcon: IconButton(
                        onPressed: _selectDate,

                        icon: const Icon(
                          Icons.date_range,
                        ),
                      ),
                    ),

                    onTap: _selectDate,
                  ),

                  const SizedBox(height: 16),

                  // ==================================================
                  // DEPARTMENT
                  // ==================================================

                  TextFormField(
                    controller: _departmentController,

                    decoration: const InputDecoration(
                      labelText: 'Department',

                      prefixIcon: Icon(
                        Icons.business_outlined,
                      ),

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

                  // ==================================================
                  // REQUESTED BY
                  // ==================================================

                  TextFormField(
                    controller: _requestedByController,

                    decoration: const InputDecoration(
                      labelText: 'Requested By',

                      prefixIcon: Icon(
                        Icons.person_outline,
                      ),

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

                  // ==================================================
                  // REMARKS
                  // ==================================================

                  TextFormField(
                    controller: _remarksController,

                    maxLines: 3,

                    decoration: const InputDecoration(
                      labelText: 'Remarks',

                      prefixIcon: Icon(
                        Icons.notes_outlined,
                      ),

                      border: OutlineInputBorder(),
                    ),
                  ),

                  const SizedBox(height: 24),

                  // ==================================================
                  // ITEMS HEADER
                  // ==================================================

                  Row(
                    mainAxisAlignment:
                    MainAxisAlignment.spaceBetween,

                    children: [
                      const Text(
                        'Items',

                        style: TextStyle(
                          fontSize: 19,
                          fontWeight: FontWeight.bold,
                        ),
                      ),

                      ElevatedButton.icon(
                        onPressed: _saving
                            ? null
                            : () => _addItem(stocks),

                        icon: const Icon(
                          Icons.add,
                        ),

                        label: const Text(
                          'Add Item',
                        ),
                      ),
                    ],
                  ),

                  const SizedBox(height: 10),

                  // ==================================================
                  // NO ITEMS
                  // ==================================================

                  if (_selectedItems.isEmpty)
                    const Card(
                      child: Padding(
                        padding: EdgeInsets.all(20),

                        child: Center(
                          child: Text(
                            'No items added.',
                          ),
                        ),
                      ),
                    )

                  // ==================================================
                  // SELECTED ITEMS
                  // ==================================================

                  else
                    ..._selectedItems.map(
                          (item) {
                        final exceedsStock =
                            item.quantity != null &&
                                item.quantity! >
                                    item.availableQuantity;

                        return Card(
                          margin: const EdgeInsets.only(
                            bottom: 10,
                          ),

                          child: Padding(
                            padding:
                            const EdgeInsets.all(12),

                            child: Row(
                              crossAxisAlignment:
                              CrossAxisAlignment.center,

                              children: [
                                // ==================================================
                                // ICON
                                // ==================================================

                                const CircleAvatar(
                                  child: Icon(
                                    Icons
                                        .inventory_2_outlined,
                                  ),
                                ),

                                const SizedBox(width: 12),

                                // ==================================================
                                // ITEM INFO
                                // ==================================================

                                Expanded(
                                  child: Column(
                                    crossAxisAlignment:
                                    CrossAxisAlignment.start,

                                    children: [
                                      Text(
                                        item.itemName,

                                        style:
                                        const TextStyle(
                                          fontWeight:
                                          FontWeight.bold,
                                        ),
                                      ),

                                      const SizedBox(
                                        height: 4,
                                      ),

                                      Text(
                                        'Unit: ${item.unit}',
                                      ),

                                      const SizedBox(
                                        height: 2,
                                      ),

                                      Text(
                                        'Available: '
                                            '${item.availableQuantity} '
                                            '${item.unit}',

                                        style:
                                        const TextStyle(
                                          color:
                                          Colors.green,

                                          fontWeight:
                                          FontWeight.w600,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),

                                // ==================================================
                                // QUANTITY
                                // ==================================================

                                SizedBox(
                                  width: 90,

                                  child: TextField(
                                    keyboardType:
                                    const TextInputType
                                        .numberWithOptions(
                                      decimal: true,
                                    ),

                                    decoration:
                                    InputDecoration(
                                      labelText: 'Qty',

                                      border:
                                      const OutlineInputBorder(),

                                      errorText:
                                      exceedsStock
                                          ? 'Exceeds stock'
                                          : null,
                                    ),

                                    onChanged: (value) {
                                      setState(() {
                                        item.quantity =
                                            double.tryParse(
                                              value,
                                            );
                                      });
                                    },
                                  ),
                                ),

                                // ==================================================
                                // DELETE
                                // ==================================================

                                IconButton(
                                  onPressed: _saving
                                      ? null
                                      : () {
                                    setState(() {
                                      _selectedItems
                                          .remove(item);
                                    });
                                  },

                                  icon: const Icon(
                                    Icons.delete_outline,
                                  ),
                                ),
                              ],
                            ),
                          ),
                        );
                      },
                    ),

                  const SizedBox(height: 28),

                  // ==================================================
                  // SAVE BUTTON
                  // ==================================================

                  SizedBox(
                    height: 50,

                    child: ElevatedButton.icon(
                      onPressed:
                      _saving ? null : _saveIssue,

                      icon: _saving
                          ? const SizedBox(
                        height: 20,
                        width: 20,

                        child:
                        CircularProgressIndicator(
                          strokeWidth: 2,
                        ),
                      )
                          : const Icon(
                        Icons.save_outlined,
                      ),

                      label: Text(
                        _saving
                            ? 'Saving...'
                            : 'Save Material Issue',
                      ),
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}

// ======================================================
// SELECTED ISSUE ITEM
// ======================================================

class _SelectedIssueItem {
  final int itemId;
  final String itemName;
  final String unit;
  final double availableQuantity;

  double? quantity;

  _SelectedIssueItem({
    required this.itemId,
    required this.itemName,
    required this.unit,
    required this.availableQuantity,
  });
}