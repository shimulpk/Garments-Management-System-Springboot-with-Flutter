import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

import '../provider/goods_receive_note_provider.dart';

class GrnDetailsScreen extends ConsumerWidget {
  const GrnDetailsScreen({
    super.key,
    required this.grnId,
  });

  final int grnId;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final grnAsync = ref.watch(grnByIdProvider(grnId));

    return Scaffold(
      drawer: const AppSidebar(),
      appBar: AppBar(
        title: const Text('GRN Details'),
      ),

      body: grnAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Padding(
            padding: const EdgeInsets.all(20),
            child: Text(
              'Failed to load GRN\n$error',
              textAlign: TextAlign.center,
            ),
          ),
        ),

        data: (grn) {
          return SingleChildScrollView(
            padding: const EdgeInsets.all(16),

            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [

                // =========================
                // GRN INFORMATION
                // =========================

                Card(
                  elevation: 2,
                  child: Padding(
                    padding: const EdgeInsets.all(18),

                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [

                        Row(
                          mainAxisAlignment:
                          MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              grn.grnNo ?? 'GRN',
                              style: const TextStyle(
                                fontSize: 22,
                                fontWeight: FontWeight.bold,
                              ),
                            ),

                            Text(
                              '${grn.grandTotal ?? 0}',
                              style: const TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ],
                        ),

                        const Divider(height: 28),

                        _InfoRow(
                          title: 'GRN Date',
                          value: grn.grnDate ?? '-',
                        ),

                        _InfoRow(
                          title: 'PO No',
                          value: grn.poNo ?? '-',
                        ),

                        _InfoRow(
                          title: 'Challan No',
                          value: grn.challanNo ?? '-',
                        ),

                        _InfoRow(
                          title: 'Remarks',
                          value: grn.remarks?.isNotEmpty == true
                              ? grn.remarks!
                              : '-',
                        ),
                      ],
                    ),
                  ),
                ),

                const SizedBox(height: 24),

                // =========================
                // ITEMS
                // =========================

                const Text(
                  'Received Items',
                  style: TextStyle(
                    fontSize: 19,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 10),

                if (grn.items.isEmpty)
                  const Card(
                    child: Padding(
                      padding: EdgeInsets.all(20),
                      child: Center(
                        child: Text(
                          'No items found.',
                        ),
                      ),
                    ),
                  )
                else
                  ...grn.items.map(
                        (item) => Card(
                      margin: const EdgeInsets.only(
                        bottom: 10,
                      ),

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

                            const SizedBox(height: 8),

                            Row(
                              mainAxisAlignment:
                              MainAxisAlignment.spaceBetween,
                              children: [

                                Text(
                                  'Quantity: '
                                      '${item.quantity ?? 0} '
                                      '${item.unit ?? ''}',
                                ),

                                Text(
                                  'Unit Price: '
                                      '${item.unitPrice ?? 0}',
                                ),
                              ],
                            ),

                            const SizedBox(height: 6),

                            Align(
                              alignment: Alignment.centerRight,
                              child: Text(
                                'Line Total: '
                                    '${item.lineTotal ?? 0}',
                                style: const TextStyle(
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),

                const SizedBox(height: 20),

                // =========================
                // GRAND TOTAL
                // =========================

                Card(
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
                          '${grn.grandTotal ?? 0}',
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
          );
        },
      ),
    );
  }
}

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
      padding: const EdgeInsets.only(bottom: 10),

      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
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