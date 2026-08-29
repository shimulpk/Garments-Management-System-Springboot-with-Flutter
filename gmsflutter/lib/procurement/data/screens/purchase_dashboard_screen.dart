import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

import '../provider/purchase_dashboard_provider.dart';

class PurchaseDashboardScreen extends ConsumerWidget {
  const PurchaseDashboardScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final dashboardAsync = ref.watch(purchaseDashboardProvider);

    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Procurement Dashboard'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(purchaseDashboardProvider);
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
              ref.invalidate(purchaseDashboardProvider);

              await ref.read(
                purchaseDashboardProvider.future,
              );
            },

            child: ListView(
              padding: const EdgeInsets.all(16),

              children: [
                const Text(
                  'Procurement Overview',
                  style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 16),

                // =====================================================
                // SUMMARY CARDS
                // =====================================================

                GridView.count(
                  crossAxisCount: 2,
                  shrinkWrap: true,
                  physics:
                  const NeverScrollableScrollPhysics(),
                  crossAxisSpacing: 12,
                  mainAxisSpacing: 12,
                  childAspectRatio: 1.5,

                  children: [
                    _StatCard(
                      title: 'Total Vendors',
                      value:
                      '${dashboard.totalVendors ?? 0}',
                      icon: Icons.business_outlined,
                    ),

                    _StatCard(
                      title: 'Pending Requisitions',
                      value:
                      '${dashboard.pendingRequisitions ?? 0}',
                      icon: Icons.pending_actions,
                    ),

                    _StatCard(
                      title: 'Pending Purchase Orders',
                      value:
                      '${dashboard.pendingPurchaseOrders ?? 0}',
                      icon: Icons.shopping_cart_outlined,
                    ),

                    _StatCard(
                      title: 'Today Purchase Orders',
                      value:
                      '${dashboard.todayPurchaseOrders ?? 0}',
                      icon: Icons.receipt_long_outlined,
                    ),

                    _StatCard(
                      title: 'Today GRN',
                      value:
                      '${dashboard.todayGoodsReceive ?? 0}',
                      icon: Icons.move_to_inbox_outlined,
                    ),
                  ],
                ),

                const SizedBox(height: 24),

                // =====================================================
                // PENDING REQUISITIONS
                // =====================================================

                _SectionTitle(
                  title: 'Pending Requisitions',
                  icon: Icons.pending_actions,
                ),

                const SizedBox(height: 10),

                if (dashboard.pendingRequisitionsList.isEmpty)
                  const _EmptyCard(
                    message: 'No pending requisitions',
                  )
                else
                  ...dashboard.pendingRequisitionsList.map(
                        (req) => Card(
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
                      ),
                    ),
                  ),

                const SizedBox(height: 24),

                // =====================================================
                // RECENT PURCHASE ORDERS
                // =====================================================

                _SectionTitle(
                  title: 'Recent Purchase Orders',
                  icon: Icons.shopping_cart_outlined,
                ),

                const SizedBox(height: 10),

                if (dashboard.recentPurchaseOrders.isEmpty)
                  const _EmptyCard(
                    message:
                    'No recent purchase orders',
                  )
                else
                  ...dashboard.recentPurchaseOrders.map(
                        (po) => Card(
                      margin: const EdgeInsets.only(
                        bottom: 10,
                      ),

                      child: ListTile(
                        contentPadding:
                        const EdgeInsets.symmetric(
                          horizontal: 12,
                          vertical: 6,
                        ),

                        leading: const CircleAvatar(
                          child: Icon(
                            Icons.receipt_long_outlined,
                          ),
                        ),

                        title: Text(
                          po.poNo ?? 'PO',
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),

                        subtitle: Padding(
                          padding:
                          const EdgeInsets.only(
                            top: 4,
                          ),
                          child: Text(
                            '${po.vendorName ?? '-'}\n'
                                '${po.poDate ?? '-'}',
                          ),
                        ),

                        isThreeLine: true,

                        // ==============================
                        // FIXED STATUS SECTION
                        // ==============================

                        trailing: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.end,
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Text(
                              '${po.grandTotal ?? 0}',
                              style: const TextStyle(
                                fontWeight: FontWeight.bold,
                              ),
                            ),

                            const SizedBox(height: 3),

                            Container(
                              padding: const EdgeInsets.symmetric(
                                horizontal: 8,
                                vertical: 3,
                              ),
                              decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(12),
                                color: Theme.of(context)
                                    .colorScheme
                                    .primaryContainer,
                              ),
                              child: Text(
                                po.status ?? '-',
                                style: TextStyle(
                                  fontSize: 11,
                                  fontWeight: FontWeight.w600,
                                  color: Theme.of(context)
                                      .colorScheme
                                      .onPrimaryContainer,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),

                const SizedBox(height: 24),

                // =====================================================
                // RECENT GOODS RECEIVE
                // =====================================================

                _SectionTitle(
                  title: 'Recent Goods Receive',
                  icon: Icons.move_to_inbox_outlined,
                ),

                const SizedBox(height: 10),

                if (dashboard.recentGoodsReceives.isEmpty)
                  const _EmptyCard(
                    message:
                    'No recent goods receive',
                  )
                else
                  ...dashboard.recentGoodsReceives.map(
                        (grn) => Card(
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
                          grn.grnNo ?? 'GRN',
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),

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
              ],
            ),
          );
        },
      ),
    );
  }
}

// ================================================================
// STAT CARD
// ================================================================

class _StatCard extends StatelessWidget {
  const _StatCard({
    required this.title,
    required this.value,
    required this.icon,
  });

  final String title;
  final String value;
  final IconData icon;

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 2,

      child: Padding(
        padding: const EdgeInsets.all(14),

        child: Row(
          children: [
            CircleAvatar(
              child: Icon(icon),
            ),

            const SizedBox(width: 12),

            Expanded(
              child: Column(
                mainAxisAlignment:
                MainAxisAlignment.center,
                crossAxisAlignment:
                CrossAxisAlignment.start,

                children: [
                  Text(
                    value,
                    style: const TextStyle(
                      fontSize: 22,
                      fontWeight: FontWeight.bold,
                    ),
                  ),

                  const SizedBox(height: 4),

                  Text(
                    title,
                    maxLines: 2,
                    overflow:
                    TextOverflow.ellipsis,
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

// ================================================================
// STATUS BADGE
// ================================================================

class _StatusBadge extends StatelessWidget {
  const _StatusBadge({
    required this.status,
  });

  final String status;

  @override
  Widget build(BuildContext context) {
    return Container(
      constraints: const BoxConstraints(
        maxWidth: 110,
      ),

      padding: const EdgeInsets.symmetric(
        horizontal: 8,
        vertical: 4,
      ),

      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(20),
        color: Colors.grey.withValues(
          alpha: 0.15,
        ),
      ),

      child: Text(
        status,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,

        style: const TextStyle(
          fontSize: 11,
          fontWeight: FontWeight.w600,
        ),
      ),
    );
  }
}

// ================================================================
// SECTION TITLE
// ================================================================

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

        Expanded(
          child: Text(
            title,
            style: const TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
      ],
    );
  }
}

// ================================================================
// EMPTY CARD
// ================================================================

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