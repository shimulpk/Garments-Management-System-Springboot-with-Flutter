import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/store/data/provider/store_provider.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';


class StoreDashboardScreen extends ConsumerWidget {
  const StoreDashboardScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final dashboardAsync = ref.watch(storeDashboardProvider);

    return Scaffold(
      drawer: const AppSidebar(),
      appBar: AppBar(
        title: const Text('Store Dashboard'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(storeDashboardProvider);
            },
          ),
        ],
      ),
      body: dashboardAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),
        error: (error, stack) => Center(
          child: Padding(
            padding: const EdgeInsets.all(20),
            child: Text(
              'Failed to load dashboard\n$error',
              textAlign: TextAlign.center,
            ),
          ),
        ),
        data: (dashboard) {
          return RefreshIndicator(
            onRefresh: () async {
              ref.invalidate(storeDashboardProvider);
              await ref.read(storeDashboardProvider.future);
            },
            child: ListView(
              padding: const EdgeInsets.all(16),
              children: [
                const Text(
                  'Store Overview',
                  style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 16),

                GridView.count(
                  crossAxisCount: 2,
                  shrinkWrap: true,
                  physics: const NeverScrollableScrollPhysics(),
                  crossAxisSpacing: 12,
                  mainAxisSpacing: 12,
                  childAspectRatio: 1.5,
                  children: [
                    _StatCard(
                      title: 'Total Items',
                      value: '${dashboard.totalItems ?? 0}',
                      icon: Icons.inventory_2_outlined,
                      color: Colors.blue,
                    ),
                    _StatCard(
                      title: 'Current Stock',
                      value: '${dashboard.currentStockItems ?? 0}',
                      icon: Icons.warehouse_outlined,
                      color: Colors.green,
                    ),
                    _StatCard(
                      title: 'Low Stock',
                      value: '${dashboard.lowStockItems ?? 0}',
                      icon: Icons.warning_amber_outlined,
                      color: Colors.orange,
                    ),
                    _StatCard(
                      title: 'Pending Requisitions',
                      value:
                      '${dashboard.pendingStoreRequisitions ?? 0}',
                      icon: Icons.pending_actions,
                      color: Colors.purple,
                    ),
                    _StatCard(
                      title: 'Today GRN',
                      value: '${dashboard.todayGoodsReceive ?? 0}',
                      icon: Icons.move_to_inbox_outlined,
                      color: Colors.teal,
                    ),
                    _StatCard(
                      title: 'Today Issue',
                      value: '${dashboard.todayMaterialIssue ?? 0}',
                      icon: Icons.outbox_outlined,
                      color: Colors.indigo,
                    ),
                  ],
                ),

                const SizedBox(height: 16),



                const SizedBox(height: 24),

                _SectionTitle(
                  title: 'Low Stock Items',
                  icon: Icons.warning_amber_outlined,
                ),

                const SizedBox(height: 10),

                if (dashboard.lowStockList.isEmpty)
                  const _EmptyCard(
                    message: 'No low stock items',
                  )
                else
                  ...dashboard.lowStockList.map(
                        (item) => Card(
                      child: ListTile(
                        leading: const CircleAvatar(
                          child: Icon(Icons.inventory_2_outlined),
                        ),
                        title: Text(item.itemName ?? 'N/A'),
                        subtitle: Text(
                          '${item.category ?? '-'} • ${item.unit ?? '-'}',
                        ),
                        trailing: Text(
                          '${item.availableQuantity ?? 0}',
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ),
                  ),

                const SizedBox(height: 24),

                _SectionTitle(
                  title: 'Recent Goods Receive',
                  icon: Icons.move_to_inbox_outlined,
                ),

                const SizedBox(height: 10),

                if (dashboard.recentGoodsReceives.isEmpty)
                  const _EmptyCard(
                    message: 'No recent goods receive',
                  )
                else
                  ...dashboard.recentGoodsReceives.map(
                        (grn) => Card(
                      child: ListTile(
                        leading: const CircleAvatar(
                          child: Icon(Icons.receipt_long_outlined),
                        ),
                        title: Text(grn.grnNo ?? 'N/A'),
                        subtitle: Text(
                          '${grn.supplier ?? '-'}\n'
                              '${grn.grnDate ?? '-'}',
                        ),
                        isThreeLine: true,
                        trailing: Text(
                          '${grn.grandTotal ?? 0}',
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ),
                  ),

                const SizedBox(height: 24),

                _SectionTitle(
                  title: 'Recent Material Issues',
                  icon: Icons.outbox_outlined,
                ),

                const SizedBox(height: 10),

                if (dashboard.recentMaterialIssues.isEmpty)
                  const _EmptyCard(
                    message: 'No recent material issues',
                  )
                else
                  ...dashboard.recentMaterialIssues.map(
                        (issue) => Card(
                      child: ListTile(
                        leading: const CircleAvatar(
                          child: Icon(Icons.assignment_outlined),
                        ),
                        title: Text(issue.issueNo ?? 'N/A'),
                        subtitle: Text(
                          '${issue.department ?? '-'} • '
                              '${issue.requestedBy ?? '-'}\n'
                              '${issue.issueDate ?? '-'}',
                        ),
                        isThreeLine: true,
                        trailing: Chip(
                          label: Text(issue.status ?? 'N/A'),
                        ),
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

class _StatCard extends StatelessWidget {
  const _StatCard({
    required this.title,
    required this.value,
    required this.icon,
    required this.color,
  });

  final String title;
  final String value;
  final IconData icon;
  final Color color;

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 2,
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(12),
          gradient: LinearGradient(
            colors: [
              color.withValues(alpha: 0.12),
              color.withValues(alpha: 0.03),
            ],
          ),
        ),
        padding: const EdgeInsets.all(14),
        child: Row(
          children: [
            CircleAvatar(
              backgroundColor: color.withValues(alpha: 0.15),
              child: Icon(
                icon,
                color: color,
              ),
            ),

            const SizedBox(width: 12),

            Expanded(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    value,
                    style: TextStyle(
                      fontSize: 22,
                      fontWeight: FontWeight.bold,
                      color: color,
                    ),
                  ),

                  const SizedBox(height: 4),

                  Text(
                    title,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _SectionTitle extends StatelessWidget {
  const _SectionTitle({
    required this.title,
    required this.icon,
  });

  final String title;
  final IconData icon;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Icon(icon),
        const SizedBox(width: 8),
        Text(
          title,
          style: const TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
        ),
      ],
    );
  }
}

class _EmptyCard extends StatelessWidget {
  const _EmptyCard({
    required this.message,
  });

  final String message;

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Center(
          child: Text(message),
        ),
      ),
    );
  }
}