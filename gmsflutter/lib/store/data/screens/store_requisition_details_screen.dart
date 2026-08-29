import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
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
    ref.watch(storeRequisitionByIdProvider(requisitionId));

    return requisitionAsync.when(
      loading: () => const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      ),

      error: (error, stack) => Scaffold(
        appBar: AppBar(
          title: const Text('Requisition Details'),
        ),
        body: Center(
          child: Text(
            'Failed to load requisition\n$error',
            textAlign: TextAlign.center,
          ),
        ),
      ),

      data: (requisition) {
        final status = requisition.status ?? 'PENDING';

        return Scaffold(
          drawer: const AppSidebar(),
          appBar: AppBar(
            title: const Text('Requisition Details'),

            actions: [
              // only pending e edit icon show
              if (status == 'PENDING')
                IconButton(
                  icon: const Icon(Icons.edit),
                  tooltip: 'Edit Requisition',
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
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [

                // =========================
                // Requisition Information
                // =========================

                Card(
                  elevation: 3,
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
                              requisition.prNo ?? 'PR',
                              style: const TextStyle(
                                fontSize: 22,
                                fontWeight: FontWeight.bold,
                              ),
                            ),

                            Chip(
                              label: Text(status),
                            ),
                          ],
                        ),

                        const Divider(height: 28),

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
                          title: 'Remarks',
                          value:
                          requisition.remarks?.isNotEmpty == true
                              ? requisition.remarks!
                              : '-',
                        ),
                      ],
                    ),
                  ),
                ),

                const SizedBox(height: 20),

                // =========================
                // Items
                // =========================

                const Text(
                  'Requested Items',
                  style: TextStyle(
                    fontSize: 19,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 10),

                ...requisition.items.map(
                      (item) => Card(
                    margin:
                    const EdgeInsets.only(bottom: 10),

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
                        'Unit: ${item.unit ?? '-'}',
                      ),

                      trailing: Text(
                        '${item.quantity ?? 0}',
                        style: const TextStyle(
                          fontSize: 17,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
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

  Future<void> _approve(
      BuildContext context,
      WidgetRef ref,
      ) async {
    try {
      await ref
          .read(storeRequisitionRepositoryProvider)
          .approve(requisitionId);

      ref.invalidate(
        storeRequisitionByIdProvider(requisitionId),
      );
      ref.invalidate(allStoreRequisitionsProvider);
      ref.invalidate(pendingStoreRequisitionsProvider);
      ref.invalidate(approvedStoreRequisitionsProvider);
      ref.invalidate(storeDashboardProvider);

      if (!context.mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Requisition approved successfully!'),
        ),
      );
    } catch (e) {
      if (!context.mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Approve failed: $e'),
        ),
      );
    }
  }

  Future<void> _reject(
      BuildContext context,
      WidgetRef ref,
      ) async {
    try {
      await ref
          .read(storeRequisitionRepositoryProvider)
          .reject(requisitionId);

      ref.invalidate(
        storeRequisitionByIdProvider(requisitionId),
      );
      ref.invalidate(allStoreRequisitionsProvider);
      ref.invalidate(pendingStoreRequisitionsProvider);
      ref.invalidate(approvedStoreRequisitionsProvider);
      ref.invalidate(storeDashboardProvider);

      if (!context.mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Requisition rejected successfully!'),
        ),
      );
    } catch (e) {
      if (!context.mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Reject failed: $e'),
        ),
      );
    }
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
      padding: const EdgeInsets.only(bottom: 12),
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