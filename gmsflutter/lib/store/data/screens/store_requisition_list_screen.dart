import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/store/data/provider/store_provider.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';
import 'package:go_router/go_router.dart';

import '../provider/store_requisition_provider.dart';

class StoreRequisitionListScreen extends ConsumerStatefulWidget {
  const StoreRequisitionListScreen({super.key});

  @override
  ConsumerState<StoreRequisitionListScreen> createState() =>
      _StoreRequisitionListScreenState();
}

class _StoreRequisitionListScreenState
    extends ConsumerState<StoreRequisitionListScreen> {
  String _search = '';

  @override
  Widget build(BuildContext context) {
    final requisitionsAsync =
    ref.watch(allStoreRequisitionsProvider);

    return Scaffold(
      drawer: const AppSidebar(),
      appBar: AppBar(
        title: const Text('Store Requisitions'),
        actions: [
          IconButton(
            onPressed: () {
              ref.invalidate(allStoreRequisitionsProvider);
            },
            icon: const Icon(Icons.refresh),
          ),
        ],
      ),

      body: requisitionsAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Text(
            'Failed to load requisitions\n$error',
          ),
        ),

        data: (requisitions) {
          final filtered = requisitions.where((req) {
            final search = _search.toLowerCase();

            return (req.prNo ?? '')
                .toLowerCase()
                .contains(search) ||
                (req.requestedBy ?? '')
                    .toLowerCase()
                    .contains(search) ||
                (req.department ?? '')
                    .toLowerCase()
                    .contains(search) ||
                (req.status ?? '')
                    .toLowerCase()
                    .contains(search);
          }).toList();

          return Column(
            children: [
              Padding(
                padding: const EdgeInsets.all(12),
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      _search = value;
                    });
                  },
                  decoration: const InputDecoration(
                    hintText:
                    'Search PR No, requester, department...',
                    prefixIcon: Icon(Icons.search),
                    border: OutlineInputBorder(),
                  ),
                ),
              ),

              Expanded(
                child: filtered.isEmpty
                    ? const Center(
                  child: Text(
                    'No requisitions found.',
                  ),
                )
                    : ListView.builder(
                  padding: const EdgeInsets.symmetric(
                    horizontal: 12,
                  ),
                  itemCount: filtered.length,
                  itemBuilder: (context, index) {
                    final req = filtered[index];

                    return Card(
                      margin: const EdgeInsets.only(
                        bottom: 10,
                      ),
                      child: ListTile(
                        onTap: () {
                          context.push(
                            '/store/requisitions/details/${req.id}',
                          );
                        },

                        leading: CircleAvatar(
                          child: Text(
                            req.prNo?.isNotEmpty == true
                                ? req.prNo![0]
                                : 'P',
                          ),
                        ),

                        title: Text(
                          req.prNo ?? 'PR',
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),

                        subtitle: Text(
                          '${req.department ?? '-'} • '
                              '${req.requestedBy ?? '-'}\n'
                              'Date: '
                              '${req.requisitionDate ?? '-'}',
                        ),

                        isThreeLine: true,

                        trailing: Row(
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            _statusChip(req.status),

                            if (req.status == 'PENDING')
                              IconButton(
                                icon: const Icon(
                                  Icons.delete_outline,
                                ),
                                onPressed: () {
                                  _deleteRequisition(
                                    context,
                                    ref,
                                    req.id!,
                                  );
                                },
                              ),
                          ],
                        ),
                      ),
                    );
                  },
                ),
              ),
            ],
          );
        },
      ),

      floatingActionButton:
      FloatingActionButton.extended(
        onPressed: () {
          context.push(
            '/store/requisitions/add',
          );
        },
        icon: const Icon(Icons.add),
        label: const Text('New Requisition'),
      ),
    );
  }

  Widget _statusChip(String? status) {
    return Chip(
      label: Text(status ?? 'PENDING'),
    );
  }

  // ==========================================
  // DELETE REQUISITION
  // ==========================================

  Future<void> _deleteRequisition(
      BuildContext context,
      WidgetRef ref,
      int id,
      ) async {
    final confirm = await showDialog<bool>(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text(
            'Delete Requisition',
          ),
          content: const Text(
            'Are you sure you want to delete this requisition?',
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context, false);
              },
              child: const Text('Cancel'),
            ),

            ElevatedButton(
              onPressed: () {
                Navigator.pop(context, true);
              },
              child: const Text('Delete'),
            ),
          ],
        );
      },
    );

    if (confirm != true) return;

    try {
      await ref
          .read(storeRequisitionRepositoryProvider)
          .delete(id);

      ref.invalidate(allStoreRequisitionsProvider);
      ref.invalidate(
        pendingStoreRequisitionsProvider,
      );
      ref.invalidate(storeDashboardProvider);

      if (!context.mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            'Requisition deleted successfully!',
          ),
        ),
      );
    } catch (e) {
      if (!context.mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Delete failed: $e',
          ),
        ),
      );
    }
  }
}