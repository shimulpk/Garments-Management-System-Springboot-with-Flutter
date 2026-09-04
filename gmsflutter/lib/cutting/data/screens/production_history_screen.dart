import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/cutting/data/screens/production_history_details_screen.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

import '../provider/cutting_provider.dart';

class ProductionHistoryScreen extends ConsumerStatefulWidget {
  const ProductionHistoryScreen({super.key});

  @override
  ConsumerState<ProductionHistoryScreen> createState() =>
      _ProductionHistoryScreenState();
}

class _ProductionHistoryScreenState
    extends ConsumerState<ProductionHistoryScreen> {

  String? _selectedDate;

  Future<void> _selectDate() async {
    final pickedDate = await showDatePicker(
      context: context,
      initialDate: _selectedDate != null
          ? DateTime.parse(_selectedDate!)
          : DateTime.now(),
      firstDate: DateTime(2020),
      lastDate: DateTime.now(),
    );

    if (pickedDate == null) return;

    final formattedDate =
        '${pickedDate.year.toString().padLeft(4, '0')}-'
        '${pickedDate.month.toString().padLeft(2, '0')}-'
        '${pickedDate.day.toString().padLeft(2, '0')}';

    setState(() {
      _selectedDate = formattedDate;
    });
  }

  void _clearDate() {
    setState(() {
      _selectedDate = null;
    });
  }

  @override
  Widget build(BuildContext context) {
    final historyAsync =
    ref.watch(cuttingHistoryProvider(_selectedDate));

    return Scaffold(
      drawer: const AppSidebar(),
      appBar: AppBar(
        title: const Text('Production History'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(
                cuttingHistoryProvider(_selectedDate),
              );
            },
          ),
        ],
      ),

      body: Column(
        children: [

          // ─────────────────────────────
          // Date Filter
          // ─────────────────────────────

          Padding(
            padding: const EdgeInsets.fromLTRB(
              16,
              16,
              16,
              8,
            ),
            child: Row(
              children: [

                Expanded(
                  child: OutlinedButton.icon(
                    onPressed: _selectDate,
                    icon: const Icon(
                      Icons.calendar_today,
                    ),
                    label: Text(
                      _selectedDate == null
                          ? 'Filter by Date'
                          : _selectedDate!,
                    ),
                    style: OutlinedButton.styleFrom(
                      padding: const EdgeInsets.symmetric(
                        vertical: 14,
                      ),
                      shape: RoundedRectangleBorder(
                        borderRadius:
                        BorderRadius.circular(12),
                      ),
                    ),
                  ),
                ),

                if (_selectedDate != null) ...[
                  const SizedBox(width: 8),

                  IconButton(
                    onPressed: _clearDate,
                    tooltip: 'Clear date filter',
                    icon: const Icon(
                      Icons.clear,
                    ),
                  ),
                ],
              ],
            ),
          ),

          // ─────────────────────────────
          // History
          // ─────────────────────────────

          Expanded(
            child: historyAsync.when(
              loading: () => const Center(
                child: CircularProgressIndicator(),
              ),

              error: (error, stack) => Center(
                child: Column(
                  mainAxisAlignment:
                  MainAxisAlignment.center,
                  children: [

                    const Icon(
                      Icons.error_outline,
                      size: 50,
                    ),

                    const SizedBox(height: 12),

                    const Text(
                      'Failed to load production history',
                      style: TextStyle(
                        fontSize: 17,
                        fontWeight: FontWeight.bold,
                      ),
                    ),

                    const SizedBox(height: 16),

                    ElevatedButton(
                      onPressed: () {
                        ref.invalidate(
                          cuttingHistoryProvider(
                            _selectedDate,
                          ),
                        );
                      },
                      child: const Text('Retry'),
                    ),
                  ],
                ),
              ),

              data: (history) {

                if (history.isEmpty) {
                  return Center(
                    child: Text(
                      _selectedDate == null
                          ? 'No production history found.'
                          : 'No production history found for $_selectedDate.',
                      textAlign: TextAlign.center,
                      style: const TextStyle(
                        fontSize: 16,
                      ),
                    ),
                  );
                }

                return RefreshIndicator(
                  onRefresh: () async {
                    ref.invalidate(
                      cuttingHistoryProvider(
                        _selectedDate,
                      ),
                    );

                    await ref.read(
                      cuttingHistoryProvider(
                        _selectedDate,
                      ).future,
                    );
                  },

                  child: ListView.builder(
                    padding: const EdgeInsets.all(16),
                    itemCount: history.length,

                    itemBuilder: (context, index) {
                      final item = history[index];

                      return Card(
                        margin:
                        const EdgeInsets.only(bottom: 14),
                        elevation: 3,

                        shape: RoundedRectangleBorder(
                          borderRadius:
                          BorderRadius.circular(16),
                        ),

                        child: InkWell(
                          borderRadius:
                          BorderRadius.circular(16),

                          onTap: () {
                            if (item.cuttingPlanId == null ||
                                item.date == null) {
                              return;
                            }

                            Navigator.of(context).push(
                              MaterialPageRoute(
                                builder: (_) => ProductionHistoryDetailsScreen(
                                  cuttingPlanId: item.cuttingPlanId!,
                                  cuttingPlanCode:
                                  item.cuttingPlanCode ?? 'N/A',
                                  styleNo:
                                  item.styleNo ?? 'N/A',
                                  date: item.date!,
                                ),
                              ),
                            );
                          },

                          child: Padding(
                            padding:
                            const EdgeInsets.all(16),

                            child: Column(
                              crossAxisAlignment:
                              CrossAxisAlignment.start,

                              children: [

                                // Plan + Date
                                Row(
                                  mainAxisAlignment:
                                  MainAxisAlignment
                                      .spaceBetween,

                                  children: [
                                    Expanded(
                                      child: Text(
                                        'Plan: ${item.cuttingPlanCode ?? 'N/A'}',

                                        style:
                                        const TextStyle(
                                          fontSize: 17,
                                          fontWeight:
                                          FontWeight.bold,
                                        ),
                                      ),
                                    ),

                                    Text(
                                      item.date ?? 'N/A',

                                      style: TextStyle(
                                        color: Colors
                                            .grey
                                            .shade600,
                                        fontWeight:
                                        FontWeight.w600,
                                      ),
                                    ),
                                  ],
                                ),

                                const SizedBox(height: 10),

                                // Style
                                Row(
                                  children: [
                                    const Icon(
                                      Icons.style_outlined,
                                      size: 20,
                                    ),

                                    const SizedBox(width: 8),

                                    const Text('Style: '),

                                    Expanded(
                                      child: Text(
                                        item.styleNo ?? 'N/A',

                                        style:
                                        const TextStyle(
                                          fontWeight:
                                          FontWeight.w600,
                                        ),
                                      ),
                                    ),
                                  ],
                                ),

                                const SizedBox(height: 14),

                                // Summary
                                Row(
                                  children: [

                                    Expanded(
                                      child: _HistoryValue(
                                        title: 'Cut Pieces',
                                        value:
                                        '${item.totalCutPieces ?? 0}',
                                        icon:
                                        Icons.content_cut,
                                      ),
                                    ),

                                    Expanded(
                                      child: _HistoryValue(
                                        title: 'Reject',
                                        value:
                                        '${item.totalRejectPieces ?? 0}',
                                        icon:
                                        Icons.cancel_outlined,
                                      ),
                                    ),

                                    Expanded(
                                      child: _HistoryValue(
                                        title: 'Entries',
                                        value:
                                        '${item.totalEntries ?? 0}',
                                        icon:
                                        Icons.list_alt,
                                      ),
                                    ),
                                  ],
                                ),

                                const SizedBox(height: 12),

                                const Divider(),

                                const Align(
                                  alignment:
                                  Alignment.centerRight,

                                  child: Row(
                                    mainAxisSize:
                                    MainAxisSize.min,

                                    children: [
                                      Text(
                                        'View Details',
                                        style: TextStyle(
                                          fontWeight:
                                          FontWeight.w600,
                                        ),
                                      ),

                                      SizedBox(width: 4),

                                      Icon(
                                        Icons
                                            .arrow_forward_ios,
                                        size: 15,
                                      ),
                                    ],
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      );
                    },
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}


// ─────────────────────────────────────────────
// History Value
// ─────────────────────────────────────────────

class _HistoryValue extends StatelessWidget {
  const _HistoryValue({
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
          size: 21,
          color: Colors.grey.shade700,
        ),

        const SizedBox(height: 5),

        Text(
          value,
          style: const TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
        ),

        const SizedBox(height: 3),

        Text(
          title,
          style: TextStyle(
            fontSize: 11,
            color: Colors.grey.shade600,
          ),
        ),
      ],
    );
  }
}