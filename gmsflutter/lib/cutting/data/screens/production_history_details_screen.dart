import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/cutting/data/provider/cutting_provider.dart';
import 'package:gmsflutter/cutting/data/screens/edit_day_wise_production_screen.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

class ProductionHistoryDetailsScreen extends ConsumerWidget {
  const ProductionHistoryDetailsScreen({
    super.key,
    required this.cuttingPlanId,
    required this.cuttingPlanCode,
    required this.styleNo,
    required this.date,
  });

  final int cuttingPlanId;
  final String cuttingPlanCode;
  final String styleNo;
  final String date;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final params = (
    cuttingPlanId: cuttingPlanId,
    date: date,
    );

    final detailsAsync =
    ref.watch(cuttingHistoryDetailsProvider(params));

    final summaryAsync =
    ref.watch(cuttingHistorySummaryProvider(params));

    return Scaffold(
      drawer: const AppSidebar(),
      appBar: AppBar(
        title: const Text('Production Details'),
      ),

      body: RefreshIndicator(
        onRefresh: () async {
          ref.invalidate(
            cuttingHistoryDetailsProvider(params),
          );

          ref.invalidate(
            cuttingHistorySummaryProvider(params),
          );

          await ref.read(
            cuttingHistoryDetailsProvider(params).future,
          );

          await ref.read(
            cuttingHistorySummaryProvider(params).future,
          );
        },

        child: ListView(
          padding: const EdgeInsets.all(16),
          children: [

            // ─────────────────────────
            // Plan Information
            // ─────────────────────────

            Card(
              elevation: 3,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(16),
              ),

              child: Padding(
                padding: const EdgeInsets.all(16),

                child: Column(
                  crossAxisAlignment:
                  CrossAxisAlignment.start,

                  children: [

                    const Text(
                      'Production Information',
                      style: TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),

                    const SizedBox(height: 16),

                    _InfoRow(
                      label: 'Cutting Plan',
                      value: cuttingPlanCode,
                    ),

                    const SizedBox(height: 10),

                    _InfoRow(
                      label: 'Style',
                      value: styleNo,
                    ),

                    const SizedBox(height: 10),

                    _InfoRow(
                      label: 'Date',
                      value: date,
                    ),
                  ],
                ),
              ),
            ),

            const SizedBox(height: 16),

            // ─────────────────────────
            // Production Summary
            // ─────────────────────────

            summaryAsync.when(
              loading: () => const Card(
                child: Padding(
                  padding: EdgeInsets.all(24),
                  child: Center(
                    child: CircularProgressIndicator(),
                  ),
                ),
              ),

              error: (error, stack) => Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Text(
                    'Failed to load summary',
                    style: TextStyle(
                      color: Colors.red.shade700,
                    ),
                  ),
                ),
              ),

              data: (summary) {
                return Card(
                  elevation: 3,

                  shape: RoundedRectangleBorder(
                    borderRadius:
                    BorderRadius.circular(16),
                  ),

                  child: Padding(
                    padding: const EdgeInsets.all(16),

                    child: Column(
                      crossAxisAlignment:
                      CrossAxisAlignment.start,

                      children: [

                        const Text(
                          'Production Summary',
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),

                        const SizedBox(height: 16),

                        Row(
                          children: [

                            Expanded(
                              child: _SummaryItem(
                                title: 'Cut Pieces',
                                value:
                                '${summary.totalCutPieces ?? 0}',
                                icon:
                                Icons.content_cut,
                              ),
                            ),

                            Expanded(
                              child: _SummaryItem(
                                title: 'Reject',
                                value:
                                '${summary.totalRejectPieces ?? 0}',
                                icon:
                                Icons.cancel_outlined,
                              ),
                            ),

                            Expanded(
                              child: _SummaryItem(
                                title: 'Entries',
                                value:
                                '${summary.totalEntries ?? 0}',
                                icon:
                                Icons.list_alt,
                              ),
                            ),
                          ],
                        ),

                        if (summary.lastUpdated != null) ...[
                          const SizedBox(height: 16),
                          Text(
                            'Last Updated: ${formatTime(summary.lastUpdated)}',
                            style: TextStyle(
                              fontSize: 12,
                              color: Colors.grey.shade600,
                            ),
                          ),
                        ],
                      ],
                    ),
                  ),
                );
              },
            ),

            const SizedBox(height: 16),

            // ─────────────────────────
            // Entry List
            // ─────────────────────────

            const Text(
              'Production Entries',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 10),

            detailsAsync.when(
              loading: () => const Padding(
                padding: EdgeInsets.all(30),
                child: Center(
                  child: CircularProgressIndicator(),
                ),
              ),

              error: (error, stack) => Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Text(
                    'Failed to load production entries',
                    style: TextStyle(
                      color: Colors.red.shade700,
                    ),
                  ),
                ),
              ),

              data: (entries) {
                if (entries.isEmpty) {
                  return const Card(
                    child: Padding(
                      padding: EdgeInsets.all(20),
                      child: Center(
                        child: Text(
                          'No production entries found.',
                        ),
                      ),
                    ),
                  );
                }

                return Column(
                  children: entries.map((entry) {
                    return Card(
                      margin:
                      const EdgeInsets.only(bottom: 12),

                      elevation: 2,

                      shape: RoundedRectangleBorder(
                        borderRadius:
                        BorderRadius.circular(14),
                      ),

                      child: Padding(
                        padding:
                        const EdgeInsets.all(14),

                        child: Row(
                          children: [

                            Expanded(
                              child: Column(
                                crossAxisAlignment:
                                CrossAxisAlignment.start,

                                children: [

                                  Text(
                                    'Production Entry',

                                    style:
                                    const TextStyle(
                                      fontWeight:
                                      FontWeight.bold,
                                      fontSize: 16,
                                    ),
                                  ),

                                  const SizedBox(height: 8),

                                  Text(
                                    'Time: ${formatTime(entry.createdAt)}',
                                    style: TextStyle(
                                      color: Colors.grey.shade600,
                                      fontSize: 12,
                                    ),
                                  ),

                                  const SizedBox(height: 12),

                                  Row(
                                    children: [

                                      Expanded(
                                        child: _EntryValue(
                                          title:
                                          'Cut Pieces',
                                          value:
                                          '${entry.actualCutPieces ?? 0}',
                                          icon:
                                          Icons.content_cut,
                                        ),
                                      ),

                                      Expanded(
                                        child: _EntryValue(
                                          title:
                                          'Reject',
                                          value:
                                          '${entry.rejectPieces ?? 0}',
                                          icon:
                                          Icons
                                              .cancel_outlined,
                                        ),
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                            ),

                            // Edit icon
                            IconButton(
                              onPressed: () async {
                                if (entry.id == null) return;

                                final updated = await Navigator.of(context).push<bool>(
                                  MaterialPageRoute(
                                    builder: (_) => EditDayWiseProductionScreen(
                                      entryId: entry.id!,
                                      actualCutPieces: entry.actualCutPieces ?? 0,
                                      rejectPieces: entry.rejectPieces ?? 0,
                                    ),
                                  ),
                                );

                                if (updated == true) {
                                  ref.invalidate(
                                    cuttingHistoryDetailsProvider(params),
                                  );

                                  ref.invalidate(
                                    cuttingHistorySummaryProvider(params),
                                  );
                                  ref.invalidate(cuttingDashboardProvider);
                                  ref.invalidate(allCuttingPlansProvider);
                                  ref.invalidate(pendingCuttingPlansProvider);
                                }
                              },

                              icon: const Icon(
                                Icons.edit_outlined,
                              ),

                              tooltip:
                              'Edit Production',
                            ),
                          ],
                        ),
                      ),
                    );
                  }).toList(),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}


// ─────────────────────────────────────────────
// Info Row
// ─────────────────────────────────────────────

class _InfoRow extends StatelessWidget {
  const _InfoRow({
    required this.label,
    required this.value,
  });

  final String label;
  final String value;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [

        SizedBox(
          width: 110,

          child: Text(
            label,
            style: TextStyle(
              color: Colors.grey.shade600,
            ),
          ),
        ),

        Expanded(
          child: Text(
            value,
            style: const TextStyle(
              fontWeight: FontWeight.w600,
            ),
          ),
        ),
      ],
    );
  }
}


// ─────────────────────────────────────────────
// Summary Item
// ─────────────────────────────────────────────

class _SummaryItem extends StatelessWidget {
  const _SummaryItem({
    required this.title,
    required this.value,
    required this.icon,
  });

  final String title;
  final String value;
  final IconData icon;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [

        Icon(
          icon,
          size: 22,
          color: Colors.grey.shade700,
        ),

        const SizedBox(height: 6),

        Text(
          value,
          style: const TextStyle(
            fontSize: 19,
            fontWeight: FontWeight.bold,
          ),
        ),

        const SizedBox(height: 3),

        Text(
          title,
          textAlign: TextAlign.center,
          style: TextStyle(
            fontSize: 11,
            color: Colors.grey.shade600,
          ),
        ),
      ],
    );
  }
}



String formatTime(String? dateTime) {
  if (dateTime == null || dateTime.isEmpty) {
    return 'N/A';
  }

  try {
    final parsed = DateTime.parse(dateTime).toLocal();

    final hour = parsed.hour.toString().padLeft(2, '0');
    final minute = parsed.minute.toString().padLeft(2, '0');

    return '$hour:$minute';
  } catch (_) {
    return dateTime;
  }
}

// ─────────────────────────────────────────────
// Entry Value
// ─────────────────────────────────────────────

class _EntryValue extends StatelessWidget {
  const _EntryValue({
    required this.title,
    required this.value,
    required this.icon,
  });

  final String title;
  final String value;
  final IconData icon;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [

        Icon(
          icon,
          size: 18,
          color: Colors.grey.shade700,
        ),

        const SizedBox(width: 6),

        Column(
          crossAxisAlignment:
          CrossAxisAlignment.start,

          children: [
            Text(
              title,
              style: TextStyle(
                fontSize: 11,
                color: Colors.grey.shade600,
              ),
            ),

            Text(
              value,
              style: const TextStyle(
                fontWeight: FontWeight.bold,
              ),
            ),
          ],
        ),
      ],
    );
  }
}