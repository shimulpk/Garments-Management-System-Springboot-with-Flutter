import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/procurement/data/provider/purchase_order_provider.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';


import '../models/goods_receive_note_request.dart';
import '../provider/goods_receive_note_provider.dart';

class CreateGoodsReceiveNoteScreen extends ConsumerStatefulWidget {
  const CreateGoodsReceiveNoteScreen({super.key});

  @override
  ConsumerState<CreateGoodsReceiveNoteScreen> createState() =>
      _CreateGoodsReceiveNoteScreenState();
}

class _CreateGoodsReceiveNoteScreenState
    extends ConsumerState<CreateGoodsReceiveNoteScreen> {

  final _formKey = GlobalKey<FormState>();

  final _challanNoController = TextEditingController();
  final _remarksController = TextEditingController();

  int? _selectedPurchaseOrderId;

  bool _saving = false;

  String _grnDate =
  DateTime.now().toIso8601String().split('T')[0];

  @override
  Widget build(BuildContext context) {

    final pendingPurchaseOrdersAsync =
    ref.watch(pendingPurchaseOrdersProvider);

    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Create GRN'),
      ),

      body: pendingPurchaseOrdersAsync.when(

        // =====================================
        // LOADING
        // =====================================

        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        // =====================================
        // ERROR
        // =====================================

        error: (error, stack) => Center(
          child: Text(
            'Failed to load purchase orders\n$error',
            textAlign: TextAlign.center,
          ),
        ),

        // =====================================
        // DATA
        // =====================================

        data: (purchaseOrders) {

          final selectedPO =
          _selectedPurchaseOrderId == null
              ? null
              : purchaseOrders
              .where(
                (po) =>
            po.id ==
                _selectedPurchaseOrderId,
          )
              .firstOrNull;

          return Form(
            key: _formKey,

            child: ListView(
              padding: const EdgeInsets.all(16),

              children: [

                // =====================================
                // GRN DATE
                // =====================================

                TextFormField(
                  readOnly: true,

                  decoration: const InputDecoration(
                    labelText: 'GRN Date',
                    prefixIcon:
                    Icon(Icons.calendar_today_outlined),
                    border: OutlineInputBorder(),
                  ),

                  controller:
                  TextEditingController(
                    text: _grnDate,
                  ),
                ),

                const SizedBox(height: 16),

                // =====================================
                // PURCHASE ORDER DROPDOWN
                // =====================================

                DropdownButtonFormField<int>(
                  value: _selectedPurchaseOrderId,

                  decoration: const InputDecoration(
                    labelText: 'Purchase Order',
                    prefixIcon:
                    Icon(Icons.shopping_cart_outlined),
                    border: OutlineInputBorder(),
                  ),

                  hint: const Text(
                    'Select Purchase Order',
                  ),

                  items: purchaseOrders.map(
                        (po) {
                      return DropdownMenuItem<int>(
                        value: po.id,

                        child: Text(
                          '${po.poNo ?? 'PO'}'
                              ' - '
                              '${po.vendorName ?? '-'}',
                        ),
                      );
                    },
                  ).toList(),

                  onChanged: (value) {

                    setState(() {
                      _selectedPurchaseOrderId =
                          value;
                    });
                  },

                  validator: (value) {
                    if (value == null) {
                      return 'Please select purchase order';
                    }

                    return null;
                  },
                ),

                const SizedBox(height: 20),

                // =====================================
                // SELECTED PO DETAILS
                // =====================================

                if (selectedPO != null)
                  Card(
                    elevation: 2,

                    child: Padding(
                      padding:
                      const EdgeInsets.all(16),

                      child: Column(
                        crossAxisAlignment:
                        CrossAxisAlignment.start,

                        children: [

                          const Text(
                            'Purchase Order Details',
                            style: TextStyle(
                              fontSize: 18,
                              fontWeight:
                              FontWeight.bold,
                            ),
                          ),

                          const SizedBox(height: 14),

                          _InfoRow(
                            title: 'PO No',
                            value:
                            selectedPO.poNo ?? '-',
                          ),

                          _InfoRow(
                            title: 'Vendor',
                            value:
                            selectedPO.vendorName ?? '-',
                          ),

                          _InfoRow(
                            title: 'PO Date',
                            value:
                            selectedPO.poDate ?? '-',
                          ),

                          _InfoRow(
                            title: 'Requisition',
                            value:
                            selectedPO.requisitionNo ??
                                '-',
                          ),

                          _InfoRow(
                            title: 'Grand Total',
                            value:
                            '${selectedPO.grandTotal ?? 0}',
                          ),
                        ],
                      ),
                    ),
                  ),

                const SizedBox(height: 20),

                // =====================================
                // PO ITEMS
                // =====================================

                if (selectedPO != null) ...[

                  const Text(
                    'Purchase Order Items',
                    style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),

                  const SizedBox(height: 10),

                  ...selectedPO.items.map(
                        (item) => Card(
                      margin:
                      const EdgeInsets.only(
                        bottom: 10,
                      ),

                      child: ListTile(
                        leading:
                        const CircleAvatar(
                          child: Icon(
                            Icons.inventory_2_outlined,
                          ),
                        ),

                        title: Text(
                          item.itemName ?? '-',
                          style: const TextStyle(
                            fontWeight:
                            FontWeight.bold,
                          ),
                        ),

                        subtitle: Text(
                          'Unit: ${item.unit ?? '-'}\n'
                              'Unit Price: '
                              '${item.unitPrice ?? 0}',
                        ),

                        trailing: Text(
                          '${item.quantity ?? 0}',
                          style: const TextStyle(
                            fontSize: 17,
                            fontWeight:
                            FontWeight.bold,
                          ),
                        ),
                      ),
                    ),
                  ),

                  const SizedBox(height: 10),
                ],

                // =====================================
                // CHALLAN NO
                // =====================================

                TextFormField(
                  controller:
                  _challanNoController,

                  decoration: const InputDecoration(
                    labelText: 'Challan No',
                    prefixIcon:
                    Icon(Icons.receipt_long_outlined),
                    border: OutlineInputBorder(),
                  ),

                  validator: (value) {
                    if (value == null ||
                        value.trim().isEmpty) {
                      return 'Challan No is required';
                    }

                    return null;
                  },
                ),

                const SizedBox(height: 16),

                // =====================================
                // REMARKS
                // =====================================

                TextFormField(
                  controller:
                  _remarksController,

                  maxLines: 3,

                  decoration: const InputDecoration(
                    labelText: 'Remarks',
                    prefixIcon:
                    Icon(Icons.notes_outlined),
                    border: OutlineInputBorder(),
                    alignLabelWithHint: true,
                  ),
                ),

                const SizedBox(height: 28),

                // =====================================
                // RECEIVE BUTTON
                // =====================================

                SizedBox(
                  height: 50,

                  child: ElevatedButton.icon(

                    onPressed:
                    _saving ? null : _submit,

                    icon: _saving
                        ? const SizedBox(
                      width: 20,
                      height: 20,
                      child:
                      CircularProgressIndicator(
                        strokeWidth: 2,
                      ),
                    )
                        : const Icon(
                      Icons.inventory_rounded,
                    ),

                    label: Text(
                      _saving
                          ? 'Receiving...'
                          : 'Receive Goods',
                    ),
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }


  // ==========================================
  // SUBMIT GRN
  // ==========================================

  Future<void> _submit() async {

    if (!_formKey.currentState!.validate()) {
      return;
    }

    if (_selectedPurchaseOrderId == null) {
      return;
    }

    setState(() {
      _saving = true;
    });

    final request =
    GoodsReceiveNoteRequest(
      grnDate: _grnDate,

      purchaseOrderId:
      _selectedPurchaseOrderId!,

      challanNo:
      _challanNoController.text.trim(),

      remarks:
      _remarksController.text.trim(),
    );

    try {

      await ref
          .read(
        goodsReceiveNoteRepositoryProvider,
      )
          .create(request);

      // Refresh GRN list
      ref.invalidate(
        allGrnsProvider,
      );

      // Refresh pending PO list
      ref.invalidate(
        pendingPurchaseOrdersProvider,
      );

      // Refresh all PO list
      ref.invalidate(
        allPurchaseOrdersProvider,
      );

      if (!mounted) return;

      ScaffoldMessenger.of(context)
          .showSnackBar(
        const SnackBar(
          content: Text(
            'Goods received successfully!',
          ),
        ),
      );

      Navigator.of(context).pop();

    } catch (e) {

      if (!mounted) return;

      ScaffoldMessenger.of(context)
          .showSnackBar(
        SnackBar(
          content: Text(
            'GRN creation failed: $e',
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


  @override
  void dispose() {

    _challanNoController.dispose();

    _remarksController.dispose();

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
            width: 105,

            child: Text(
              title,
              style: const TextStyle(
                fontWeight:
                FontWeight.w600,
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