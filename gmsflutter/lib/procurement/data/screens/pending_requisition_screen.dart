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
          child: Text(
            'Failed to load pending requisitions\n$error',
            textAlign: TextAlign.center,
          ),
        ),

        data: (requisitions) {
          if (requisitions.isEmpty) {
            return const Center(
              child: Text(
                'No pending requisitions found.',
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
                    bottom: 10,
                  ),

                  child: ListTile(
                    leading: const CircleAvatar(
                      child: Icon(
                        Icons.description_outlined,
                      ),
                    ),

                    title: Text(
                      requisition.prNo ?? 'PR',
                      style: const TextStyle(
                        fontWeight: FontWeight.bold,
                      ),
                    ),

                    subtitle: Text(
                      '${requisition.department ?? '-'} • '
                          '${requisition.requestedBy ?? '-'}\n'
                          'Date: '
                          '${requisition.requisitionDate ?? '-'}',
                    ),

                    isThreeLine: true,

                    trailing: const Icon(
                      Icons.arrow_forward_ios,
                      size: 18,
                    ),

                    onTap: () {
                      context.push(
                        '/procurement/requisitions/details/${requisition.id}',
                      );
                    },
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