import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/auth_provider.dart';
import 'package:gmsflutter/store/data/provider/store_provider.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';
import 'package:go_router/go_router.dart';

import '../provider/store_requisition_provider.dart';

class StoreRequisitionDetailsScreen extends ConsumerWidget {
  const StoreRequisitionDetailsScreen({
    super.key,
    required this.requisitionId,
  });

  final int requisitionId;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final requisitionAsync =
    ref.watch(
      storeRequisitionByIdProvider(
        requisitionId,
      ),
    );

    final authState =
    ref.watch(authControllerProvider);

    final role = authState.when(
      data: (user) => user?.role,
      loading: () => null,
      error: (_, __) => null,
    );

    return requisitionAsync.when(
      loading: () => const Scaffold(
        drawer: AppSidebar(),
        body: Center(
          child: CircularProgressIndicator(),
        ),
      ),

      error: (error, stack) => Scaffold(
        drawer: const AppSidebar(),

        appBar: AppBar(
          title: const Text(
            'Requisition Details',
          ),
        ),

        body: Center(
          child: Text(
            'Failed to load requisition\n$error',
            textAlign: TextAlign.center,
          ),
        ),
      ),

      data: (requisition) {
        final status =
            requisition.status
                ?.toString()
                .split('.')
                .last ??
                'PENDING';

        final isStoreManager =
            role == 'STORE_MANAGER';

        final isPurchaseManager =
            role == 'PURCHASE_MANAGER';

        final isPending =
            status == 'PENDING';

        final isApproved =
            status == 'APPROVED';

        return Scaffold(
          drawer: const AppSidebar(),

          appBar: AppBar(
            title: const Text(
              'Requisition Details',
            ),

            actions: [
              // ==========================================
              // STORE MANAGER
              // EDIT PENDING REQUISITION
              // ==========================================

              if (isStoreManager &&
                  isPending)
                IconButton(
                  icon: const Icon(
                    Icons.edit,
                  ),

                  tooltip:
                  'Edit Requisition',

                  onPressed: () {
                    context.push(
                      '/store/requisitions/edit/$requisitionId',
                    );
                  },
                ),
            ],
          ),

          body: SingleChildScrollView(
            padding: const EdgeInsets.all(16),

            child: Column(
              crossAxisAlignment:
              CrossAxisAlignment.stretch,

              children: [
                // ==========================================
                // REQUISITION INFORMATION
                // ==========================================

                Card(
                  elevation: 3,

                  child: Padding(
                    padding:
                    const EdgeInsets.all(18),

                    child: Column(
                      crossAxisAlignment:
                      CrossAxisAlignment.start,

                      children: [
                        Row(
                          mainAxisAlignment:
                          MainAxisAlignment
                              .spaceBetween,

                          children: [
                            Expanded(
                              child: Text(
                                requisition.prNo ??
                                    'PR',

                                style:
                                const TextStyle(
                                  fontSize: 22,
                                  fontWeight:
                                  FontWeight.bold,
                                ),
                              ),
                            ),

                            const SizedBox(
                              width: 8,
                            ),

                            Chip(
                              label: Text(
                                status,
                              ),
                            ),
                          ],
                        ),

                        const Divider(
                          height: 28,
                        ),

                        _InfoRow(
                          title: 'Date',
                          value:
                          requisition
                              .requisitionDate ??
                              '-',
                        ),

                        _InfoRow(
                          title: 'Requested By',
                          value:
                          requisition
                              .requestedBy ??
                              '-',
                        ),

                        _InfoRow(
                          title: 'Department',
                          value:
                          requisition
                              .department ??
                              '-',
                        ),

                        _InfoRow(
                          title: 'Remarks',
                          value: requisition
                              .remarks
                              ?.isNotEmpty ==
                              true
                              ? requisition.remarks!
                              : '-',
                        ),
                      ],
                    ),
                  ),
                ),

                const SizedBox(
                  height: 20,
                ),

                // ==========================================
                // REQUESTED ITEMS
                // ==========================================

                const Text(
                  'Requested Items',

                  style: TextStyle(
                    fontSize: 19,
                    fontWeight:
                    FontWeight.bold,
                  ),
                ),

                const SizedBox(
                  height: 10,
                ),

                ...requisition.items.map(
                      (item) => Card(
                    margin:
                    const EdgeInsets.only(
                      bottom: 10,
                    ),

                    child: ListTile(
                      leading:
                      const CircleAvatar(
                        child: Icon(
                          Icons
                              .inventory_2_outlined,
                        ),
                      ),

                      title: Text(
                        item.itemName ?? '-',

                        style:
                        const TextStyle(
                          fontWeight:
                          FontWeight.bold,
                        ),
                      ),

                      subtitle: Text(
                        'Unit: ${item.unit ?? '-'}',
                      ),

                      trailing: Text(
                        '${item.quantity ?? 0}',

                        style:
                        const TextStyle(
                          fontSize: 17,
                          fontWeight:
                          FontWeight.bold,
                        ),
                      ),
                    ),
                  ),
                ),

                // ==========================================
                // PROCUREMENT ACTION
                // ==========================================

                if (isPurchaseManager &&
                    isPending) ...[
                  const SizedBox(
                    height: 20,
                  ),

                  const Divider(),

                  const SizedBox(
                    height: 12,
                  ),

                  const Text(
                    'Procurement Action',

                    style: TextStyle(
                      fontSize: 18,
                      fontWeight:
                      FontWeight.bold,
                    ),
                  ),

                  const SizedBox(
                    height: 12,
                  ),

                  Row(
                    children: [
                      // ==============================
                      // REJECT
                      // ==============================

                      Expanded(
                        child:
                        OutlinedButton.icon(
                          onPressed: () {
                            _reject(
                              context,
                              ref,
                            );
                          },

                          icon: const Icon(
                            Icons.close,
                          ),

                          label: const Text(
                            'Reject',
                          ),

                          style:
                          OutlinedButton
                              .styleFrom(
                            minimumSize:
                            const Size(
                              0,
                              48,
                            ),
                          ),
                        ),
                      ),

                      const SizedBox(
                        width: 12,
                      ),

                      // ==============================
                      // APPROVE
                      // ==============================

                      Expanded(
                        child:
                        ElevatedButton.icon(
                          onPressed: () {
                            _approve(
                              context,
                              ref,
                            );
                          },

                          icon: const Icon(
                            Icons.check,
                          ),

                          label: const Text(
                            'Approve',
                          ),

                          style:
                          ElevatedButton
                              .styleFrom(
                            minimumSize:
                            const Size(
                              0,
                              48,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ],

                // ==========================================
                // APPROVED → CREATE PO
                // ==========================================

                if (isPurchaseManager &&
                    isApproved) ...[
                  const SizedBox(
                    height: 20,
                  ),

                  SizedBox(
                    height: 50,

                    child:
                    ElevatedButton.icon(
                      onPressed: () {
                        context.push(
                          '/procurement/purchase-orders/add?requisitionId=$requisitionId',
                        );
                      },

                      icon: const Icon(
                        Icons
                            .shopping_cart_outlined,
                      ),

                      label: const Text(
                        'Create Purchase Order',
                      ),
                    ),
                  ),
                ],
              ],
            ),
          ),
        );
      },
    );
  }

  // ======================================================
  // APPROVE
  // ======================================================

  Future<void> _approve(
      BuildContext context,
      WidgetRef ref,
      ) async {
    try {
      await ref
          .read(
        storeRequisitionRepositoryProvider,
      )
          .approve(
        requisitionId,
      );

      ref.invalidate(
        storeRequisitionByIdProvider(
          requisitionId,
        ),
      );

      ref.invalidate(
        allStoreRequisitionsProvider,
      );

      ref.invalidate(
        pendingStoreRequisitionsProvider,
      );

      ref.invalidate(
        approvedStoreRequisitionsProvider,
      );

      ref.invalidate(
        storeDashboardProvider,
      );

      if (!context.mounted) return;

      ScaffoldMessenger.of(context)
          .showSnackBar(
        const SnackBar(
          content: Text(
            'Requisition approved successfully!',
          ),
        ),
      );
    } catch (e) {
      if (!context.mounted) return;

      ScaffoldMessenger.of(context)
          .showSnackBar(
        SnackBar(
          content: Text(
            'Approve failed: $e',
          ),
        ),
      );
    }
  }

  // ======================================================
  // REJECT
  // ======================================================

  Future<void> _reject(
      BuildContext context,
      WidgetRef ref,
      ) async {
    try {
      await ref
          .read(
        storeRequisitionRepositoryProvider,
      )
          .reject(
        requisitionId,
      );

      ref.invalidate(
        storeRequisitionByIdProvider(
          requisitionId,
        ),
      );

      ref.invalidate(
        allStoreRequisitionsProvider,
      );

      ref.invalidate(
        pendingStoreRequisitionsProvider,
      );

      ref.invalidate(
        approvedStoreRequisitionsProvider,
      );

      ref.invalidate(
        storeDashboardProvider,
      );

      if (!context.mounted) return;

      ScaffoldMessenger.of(context)
          .showSnackBar(
        const SnackBar(
          content: Text(
            'Requisition rejected successfully!',
          ),
        ),
      );
    } catch (e) {
      if (!context.mounted) return;

      ScaffoldMessenger.of(context)
          .showSnackBar(
        SnackBar(
          content: Text(
            'Reject failed: $e',
          ),
        ),
      );
    }
  }
}

// ======================================================
// INFO ROW
// ======================================================

class _InfoRow extends StatelessWidget {
  const _InfoRow({
    required this.title,
    required this.value,
  });

  final String title;
  final String value;

  @override
  Widget build(
      BuildContext context,
      ) {
    return Padding(
      padding:
      const EdgeInsets.only(
        bottom: 12,
      ),

      child: Row(
        crossAxisAlignment:
        CrossAxisAlignment.start,

        children: [
          SizedBox(
            width: 110,

            child: Text(
              title,

              style:
              const TextStyle(
                fontWeight:
                FontWeight.w600,
              ),
            ),
          ),

          Expanded(
            child: Text(
              value,
            ),
          ),
        ],
      ),
    );
  }
}