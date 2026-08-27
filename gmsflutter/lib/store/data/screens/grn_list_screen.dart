import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/store/data/provider/goods_receive_note_provider.dart';
import 'package:go_router/go_router.dart';

class GrnListScreen extends ConsumerWidget {
  const GrnListScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final grnsAsync = ref.watch(allGrnsProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Goods Receive Notes'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(allGrnsProvider);
            },
          ),
        ],
      ),

      body: grnsAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Padding(
            padding: const EdgeInsets.all(20),
            child: Text(
              'Failed to load GRNs\n$error',
              textAlign: TextAlign.center,
            ),
          ),
        ),

        data: (grns) {
          if (grns.isEmpty) {
            return const Center(
              child: Text('No goods receive notes found.'),
            );
          }

          return RefreshIndicator(
            onRefresh: () async {
              ref.invalidate(allGrnsProvider);
              await ref.read(allGrnsProvider.future);
            },

            child: ListView.builder(
              padding: const EdgeInsets.all(12),
              itemCount: grns.length,

              itemBuilder: (context, index) {
                final grn = grns[index];

                return Card(
                  margin: const EdgeInsets.only(bottom: 12),

                  child: ListTile(
                    onTap: () {
                      if (grn.id != null) {
                        context.push(
                          '/store/grn/details/${grn.id}',
                        );
                      }
                    },

                    leading: const CircleAvatar(
                      child: Icon(
                        Icons.receipt_long_outlined,
                      ),
                    ),

                    title: Text(
                      grn.grnNo ?? 'GRN',
                      style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    ),

                    subtitle: Padding(
                      padding: const EdgeInsets.only(top: 6),
                      child: Text(
                        'PO: ${grn.poNo ?? '-'}\n'
                            'Date: ${grn.grnDate ?? '-'}\n'
                            'Challan: ${grn.challanNo ?? '-'}',
                      ),
                    ),

                    isThreeLine: true,

                    trailing: Text(
                      '${grn.grandTotal ?? 0}',
                      style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 15,
                      ),
                    ),
                  ),
                );
              },
            ),
          );
        },
      ),

      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
          context.push('/store/grn/add');
        },
        icon: const Icon(Icons.add),
        label: const Text('Receive Goods'),
      ),
    );
  }
}