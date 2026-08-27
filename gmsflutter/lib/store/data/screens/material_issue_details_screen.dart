import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../provider/material_issue_provider.dart';

class MaterialIssueDetailsScreen extends ConsumerWidget {
  const MaterialIssueDetailsScreen({
    super.key,
    required this.issueId,
  });

  final int issueId;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final issueAsync = ref.watch(
      materialIssueByIdProvider(issueId),
    );

    return Scaffold(
      appBar: AppBar(
        title: const Text('Material Issue Details'),
      ),

      body: issueAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Padding(
            padding: const EdgeInsets.all(20),
            child: Text(
              'Failed to load material issue\n$error',
              textAlign: TextAlign.center,
            ),
          ),
        ),

        data: (issue) {
          return SingleChildScrollView(
            padding: const EdgeInsets.all(16),

            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [

                // =====================================
                // ISSUE INFORMATION
                // =====================================

                Card(
                  elevation: 2,
                  child: Padding(
                    padding: const EdgeInsets.all(18),

                    child: Column(
                      crossAxisAlignment:
                      CrossAxisAlignment.start,
                      children: [

                        Row(
                          mainAxisAlignment:
                          MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              issue.issueNo ?? 'Issue',
                              style: const TextStyle(
                                fontSize: 22,
                                fontWeight: FontWeight.bold,
                              ),
                            ),

                            Chip(
                              label: Text(
                                issue.status ?? 'PENDING',
                              ),
                            ),
                          ],
                        ),

                        const Divider(height: 28),

                        _InfoRow(
                          title: 'Issue Date',
                          value: issue.issueDate ?? '-',
                        ),

                        _InfoRow(
                          title: 'Department',
                          value: issue.department ?? '-',
                        ),

                        _InfoRow(
                          title: 'Requested By',
                          value: issue.requestedBy ?? '-',
                        ),

                        _InfoRow(
                          title: 'Remarks',
                          value: issue.remarks?.isNotEmpty == true
                              ? issue.remarks!
                              : '-',
                        ),
                      ],
                    ),
                  ),
                ),

                const SizedBox(height: 24),

                // =====================================
                // ISSUED ITEMS
                // =====================================

                const Text(
                  'Issued Items',
                  style: TextStyle(
                    fontSize: 19,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 10),

                if (issue.items.isEmpty)
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
                  ...issue.items.map(
                        (item) => Card(
                      margin: const EdgeInsets.only(
                        bottom: 10,
                      ),

                      child: ListTile(
                        leading: const CircleAvatar(
                          child: Icon(
                            Icons.inventory_2_outlined,
                          ),
                        ),

                        title: Text(
                          item.itemName ?? '-',
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),

                        subtitle: Text(
                          'Quantity: '
                              '${item.quantity ?? 0} '
                              '${item.unit ?? ''}',
                        ),
                      ),
                    ),
                  ),

                const SizedBox(height: 20),

                // =====================================
                // TOTAL ITEMS
                // =====================================

                Card(
                  child: Padding(
                    padding: const EdgeInsets.all(18),

                    child: Row(
                      mainAxisAlignment:
                      MainAxisAlignment.spaceBetween,
                      children: [
                        const Text(
                          'Total Items',
                          style: TextStyle(
                            fontSize: 17,
                            fontWeight: FontWeight.bold,
                          ),
                        ),

                        Text(
                          '${issue.items.length}',
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