import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/store/data/provider/store_requisition_provider.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';
import 'package:go_router/go_router.dart';



class PendingRequisitionScreen extends ConsumerWidget {
  const PendingRequisitionScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final requisitionsAsync =
    ref.watch(pendingStoreRequisitionsProvider);

    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Pending Requisitions'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(
                pendingStoreRequisitionsProvider,
              );
            },
          ),
        ],
      ),

      body: requisitionsAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Padding(
            padding: const EdgeInsets.all(20),
            child: Text(
              'Failed to load pending requisitions\n$error',
              textAlign: TextAlign.center,
            ),
          ),
        ),

        data: (requisitions) {
          if (requisitions.isEmpty) {
            return const Center(
              child: Text(
                'No pending requisitions.',
              ),
            );
          }

          return RefreshIndicator(
            onRefresh: () async {
              ref.invalidate(
                pendingStoreRequisitionsProvider,
              );

              await ref.read(
                pendingStoreRequisitionsProvider.future,
              );
            },

            child: ListView.builder(
              padding: const EdgeInsets.all(12),

              itemCount: requisitions.length,

              itemBuilder: (context, index) {
                final requisition =
                requisitions[index];

                return Card(
                  margin: const EdgeInsets.only(
                    bottom: 12,
                  ),

                  child: InkWell(
                    borderRadius: BorderRadius.circular(12),

                    onTap: () {
                      context.push(
                        '/procurement/requisitions/details/${requisition.id}',
                      );
                    },

                    child: Padding(
                      padding: const EdgeInsets.all(14),

                      child: Column(
                        crossAxisAlignment:
                        CrossAxisAlignment.start,

                        children: [
                          // ==============================
                          // HEADER
                          // ==============================

                          Row(
                            children: [
                              const CircleAvatar(
                                child: Icon(
                                  Icons.description_outlined,
                                ),
                              ),

                              const SizedBox(width: 12),

                              Expanded(
                                child: Text(
                                  requisition.prNo ?? 'PR',
                                  style:
                                  const TextStyle(
                                    fontSize: 17,
                                    fontWeight:
                                    FontWeight.bold,
                                  ),
                                ),
                              ),

                              Chip(
                                label: Text(
                                  requisition.status
                                      ?.toString()
                                      .split('.')
                                      .last ??
                                      '-',
                                ),
                              ),
                            ],
                          ),

                          const SizedBox(height: 12),

                          // ==============================
                          // BASIC INFO
                          // ==============================

                          _InfoRow(
                            icon: Icons.calendar_today_outlined,
                            title: 'Date',
                            value:
                            '${requisition.requisitionDate ?? '-'}',
                          ),

                          _InfoRow(
                            icon: Icons.person_outline,
                            title: 'Requested By',
                            value:
                            requisition.requestedBy ??
                                '-',
                          ),

                          _InfoRow(
                            icon: Icons.business_outlined,
                            title: 'Department',
                            value:
                            requisition.department ??
                                '-',
                          ),

                          const SizedBox(height: 8),

                          const Divider(),

                          const SizedBox(height: 8),

                          // ==============================
                          // ITEMS
                          // ==============================

                          const Text(
                            'Items',
                            style: TextStyle(
                              fontWeight:
                              FontWeight.bold,
                              fontSize: 16,
                            ),
                          ),

                          const SizedBox(height: 8),

                          ...requisition.items.map(
                                (item) => Padding(
                              padding:
                              const EdgeInsets.only(
                                bottom: 6,
                              ),

                              child: Row(
                                children: [
                                  const Icon(
                                    Icons
                                        .inventory_2_outlined,
                                    size: 18,
                                  ),

                                  const SizedBox(width: 8),

                                  Expanded(
                                    child: Text(
                                      item.itemName ?? '-',
                                    ),
                                  ),

                                  Text(
                                    '${item.quantity ?? 0} ${item.unit ?? ''}',
                                    style:
                                    const TextStyle(
                                      fontWeight:
                                      FontWeight.w600,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),

                          const SizedBox(height: 8),

                          Align(
                            alignment:
                            Alignment.centerRight,

                            child: TextButton.icon(
                              onPressed: () {
                                context.push(
                                  '/procurement/requisitions/details/${requisition.id}',
                                );
                              },

                              icon: const Icon(
                                Icons.arrow_forward,
                              ),

                              label: const Text(
                                'View Details',
                              ),
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
    );
  }
}

// ======================================================
// INFO ROW
// ======================================================

class _InfoRow extends StatelessWidget {
  const _InfoRow({
    required this.icon,
    required this.title,
    required this.value,
  });

  final IconData icon;
  final String title;
  final String value;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 7),

      child: Row(
        children: [
          Icon(
            icon,
            size: 18,
          ),

          const SizedBox(width: 8),

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