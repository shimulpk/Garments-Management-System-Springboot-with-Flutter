import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

import '../provider/purchase_order_provider.dart';

class PurchaseOrderDetailsScreen extends ConsumerWidget {
  const PurchaseOrderDetailsScreen({
    super.key,
    required this.purchaseOrderId,
  });

  final int purchaseOrderId;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final purchaseOrderAsync =
    ref.watch(purchaseOrderByIdProvider(purchaseOrderId));

    return purchaseOrderAsync.when(
      loading: () => const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      ),

      error: (error, stack) => Scaffold(
        drawer: const AppSidebar(),
        appBar: AppBar(
          title: const Text('Purchase Order Details'),
        ),
        body: Center(
          child: Text(
            'Failed to load purchase order\n$error',
            textAlign: TextAlign.center,
          ),
        ),
      ),

      data: (po) {
        final status = po.status ?? '-';

        return Scaffold(
          drawer: const AppSidebar(),

          appBar: AppBar(
            title: const Text('Purchase Order Details'),
          ),

          body: SingleChildScrollView(
            padding: const EdgeInsets.all(16),

            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [

                // =====================================
                // PO INFORMATION
                // =====================================

                Card(
                  elevation: 3,
                  child: Padding(
                    padding: const EdgeInsets.all(18),

                    child: Column(
                      crossAxisAlignment:
                      CrossAxisAlignment.start,

                      children: [

                        Row(
                          crossAxisAlignment:
                          CrossAxisAlignment.start,

                          children: [

                            Expanded(
                              child: Text(
                                po.poNo ?? 'Purchase Order',
                                style: const TextStyle(
                                  fontSize: 22,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ),

                            const SizedBox(width: 10),

                            Chip(
                              label: Text(status),
                            ),
                          ],
                        ),

                        const Divider(height: 28),

                        _InfoRow(
                          title: 'PO Date',
                          value: po.poDate ?? '-',
                          icon: Icons.calendar_today_outlined,
                        ),

                        _InfoRow(
                          title: 'Vendor',
                          value: po.vendorName ?? '-',
                          icon: Icons.business_outlined,
                        ),

                        _InfoRow(
                          title: 'Requisition',
                          value: po.requisitionNo ?? '-',
                          icon: Icons.receipt_long_outlined,
                        ),

                        _InfoRow(
                          title: 'Grand Total',
                          value: '${po.grandTotal ?? 0}',
                          icon: Icons.payments_outlined,
                        ),

                        _InfoRow(
                          title: 'Remarks',
                          value: po.remarks?.isNotEmpty == true
                              ? po.remarks!
                              : '-',
                          icon: Icons.notes_outlined,
                        ),
                      ],
                    ),
                  ),
                ),

                const SizedBox(height: 20),

                // =====================================
                // ITEMS
                // =====================================

                const Text(
                  'Purchase Order Items',
                  style: TextStyle(
                    fontSize: 19,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 10),

                if (po.items.isEmpty)
                  const Card(
                    child: Padding(
                      padding: EdgeInsets.all(16),
                      child: Text(
                        'No items found.',
                        textAlign: TextAlign.center,
                      ),
                    ),
                  )
                else
                  ...po.items.map(
                        (item) => Card(
                      margin: const EdgeInsets.only(bottom: 10),

                      child: Padding(
                        padding: const EdgeInsets.all(14),

                        child: Column(
                          crossAxisAlignment:
                          CrossAxisAlignment.start,

                          children: [

                            Row(
                              children: [

                                const CircleAvatar(
                                  child: Icon(
                                    Icons.inventory_2_outlined,
                                  ),
                                ),

                                const SizedBox(width: 12),

                                Expanded(
                                  child: Text(
                                    item.itemName ?? '-',
                                    style: const TextStyle(
                                      fontSize: 17,
                                      fontWeight:
                                      FontWeight.bold,
                                    ),
                                  ),
                                ),
                              ],
                            ),

                            const SizedBox(height: 14),

                            _ItemInfoRow(
                              title: 'Unit',
                              value: item.unit ?? '-',
                            ),

                            _ItemInfoRow(
                              title: 'Quantity',
                              value: '${item.quantity ?? 0}',
                            ),

                            _ItemInfoRow(
                              title: 'Unit Price',
                              value: '${item.unitPrice ?? 0}',
                            ),

                            const Divider(),

                            _ItemInfoRow(
                              title: 'Line Total',
                              value: '${item.lineTotal ?? 0}',
                              bold: true,
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),

                const SizedBox(height: 10),

                // =====================================
                // GRAND TOTAL
                // =====================================

                Card(
                  elevation: 2,
                  child: Padding(
                    padding: const EdgeInsets.all(18),

                    child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.spaceBetween,

                      children: [

                        const Text(
                          'Grand Total',
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),

                        Text(
                          '${po.grandTotal ?? 0}',
                          style: const TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}


// =====================================
// PO INFO ROW
// =====================================

class _InfoRow extends StatelessWidget {
  const _InfoRow({
    required this.title,
    required this.value,
    required this.icon,
  });

  final String title;
  final String value;
  final IconData icon;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),

      child: Row(
        crossAxisAlignment:
        CrossAxisAlignment.start,

        children: [

          Icon(
            icon,
            size: 20,
          ),

          const SizedBox(width: 10),

          SizedBox(
            width: 105,
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


// =====================================
// ITEM INFO ROW
// =====================================

class _ItemInfoRow extends StatelessWidget {
  const _ItemInfoRow({
    required this.title,
    required this.value,
    this.bold = false,
  });

  final String title;
  final String value;
  final bool bold;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8),

      child: Row(
        mainAxisAlignment:
        MainAxisAlignment.spaceBetween,

        children: [

          Text(
            title,
            style: const TextStyle(
              fontWeight: FontWeight.w500,
            ),
          ),

          const SizedBox(width: 10),

          Flexible(
            child: Text(
              value,
              textAlign: TextAlign.end,
              style: TextStyle(
                fontWeight: bold
                    ? FontWeight.bold
                    : FontWeight.normal,
              ),
            ),
          ),
        ],
      ),
    );
  }
}