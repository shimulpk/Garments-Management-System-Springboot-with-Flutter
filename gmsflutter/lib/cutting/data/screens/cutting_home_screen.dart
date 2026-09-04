import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../provider/cutting_provider.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

class CuttingHomeScreen extends ConsumerWidget {
  const CuttingHomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final dashboardAsync = ref.watch(cuttingDashboardProvider);

    return Scaffold(
      backgroundColor: const Color(0xFFF5F7FA),

      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text(
          'Cutting Dashboard',
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(cuttingDashboardProvider);
            },
          ),
        ],
      ),

      body: dashboardAsync.when(
        // ==================================================
        // LOADING
        // ==================================================

        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        // ==================================================
        // ERROR
        // ==================================================

        error: (error, stack) => Center(
          child: Padding(
            padding: const EdgeInsets.all(20),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Icon(
                  Icons.error_outline,
                  size: 55,
                ),

                const SizedBox(height: 12),

                const Text(
                  'Dashboard load failed',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 8),

                Text(
                  '$error',
                  textAlign: TextAlign.center,
                ),

                const SizedBox(height: 18),

                ElevatedButton.icon(
                  onPressed: () {
                    ref.invalidate(
                      cuttingDashboardProvider,
                    );
                  },
                  icon: const Icon(Icons.refresh),
                  label: const Text('Retry'),
                ),
              ],
            ),
          ),
        ),

        // ==================================================
        // DATA
        // ==================================================

        data: (dashboard) {
          return RefreshIndicator(
            onRefresh: () async {
              ref.invalidate(
                cuttingDashboardProvider,
              );

              await ref.read(
                cuttingDashboardProvider.future,
              );
            },

            child: ListView(
              padding: const EdgeInsets.all(20),

              children: [
                // ==================================================
                // HEADER
                // ==================================================

                const Text(
                  'Welcome, User',
                  style: TextStyle(
                    fontSize: 26,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 5),

                Text(
                  _currentDate(),
                  style: TextStyle(
                    fontSize: 15,
                    color: Colors.grey.shade600,
                  ),
                ),

                const SizedBox(height: 28),

                // ==================================================
                // SUMMARY TITLE
                // ==================================================

                const Text(
                  'Production Overview',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 15),

                // ==================================================
                // SUMMARY CARDS ROW 1
                // ==================================================

                Row(
                  children: [
                    Expanded(
                      child: _DashboardCard(
                        title: 'Today Cutting',
                        value:
                        '${dashboard.todayCutting ?? 0}',
                        icon: Icons.content_cut,
                        iconColor: Colors.blue,
                      ),
                    ),

                    const SizedBox(width: 12),

                    Expanded(
                      child: _DashboardCard(
                        title: 'Today Reject',
                        value:
                        '${dashboard.todayReject ?? 0}',
                        icon: Icons.cancel_outlined,
                        iconColor: Colors.red,
                      ),
                    ),
                  ],
                ),

                const SizedBox(height: 12),

                // ==================================================
                // SUMMARY CARDS ROW 2
                // ==================================================

                Row(
                  children: [
                    Expanded(
                      child: _DashboardCard(
                        title: 'Total Plans',
                        value:
                        '${dashboard.totalPlans ?? 0}',
                        icon: Icons.assignment_outlined,
                        iconColor: Colors.indigo,
                      ),
                    ),

                    const SizedBox(width: 12),

                    Expanded(
                      child: _DashboardCard(
                        title: 'Pending Plans',
                        value:
                        '${dashboard.pendingPlans ?? 0}',
                        icon: Icons.pending_actions,
                        iconColor: Colors.orange,
                      ),
                    ),
                  ],
                ),

                const SizedBox(height: 12),

                // ==================================================
                // SUMMARY CARDS ROW 3
                // ==================================================

                _DashboardCard(
                  title: 'Completed Plans',
                  value:
                  '${dashboard.completedPlans ?? 0}',
                  icon: Icons.check_circle_outline,
                  iconColor: Colors.green,
                ),

                const SizedBox(height: 30),

                // ==================================================
                // TODAY'S CUTTING
                // ==================================================

                _SectionHeader(
                  title: "Today's Cutting",
                  icon: Icons.content_cut,
                ),

                const SizedBox(height: 12),

                if (dashboard.todayCuttings == null ||
                    dashboard.todayCuttings!.isEmpty)

                  _EmptyCard(
                    icon: Icons.content_cut_outlined,
                    message:
                    'No cutting production recorded today.',
                  )

                else

                  ...dashboard.todayCuttings!.map(
                        (production) {
                      return _TodayCuttingCard(
                        styleNo:
                        production.styleNo ?? '-',
                        cuttingMaster:
                        production.cuttingMaster ?? '-',
                        cutPieces:
                        production.actualCutPieces ?? 0,
                        rejectPieces:
                        production.rejectPieces ?? 0,
                      );
                    },
                  ),

                const SizedBox(height: 30),

                // ==================================================
                // RECENT CUTTING PLANS
                // ==================================================

                _SectionHeader(
                  title: 'Recent Cutting Plans',
                  icon: Icons.assignment_outlined,
                ),

                const SizedBox(height: 12),

                if (dashboard.recentPlans == null ||
                    dashboard.recentPlans!.isEmpty)

                  _EmptyCard(
                    icon: Icons.assignment_outlined,
                    message: 'No cutting plans found.',
                  )

                else

                  ...dashboard.recentPlans!.map(
                        (plan) {
                      return _RecentPlanCard(
                        planId:
                        plan.cuttingPlanId,
                        styleNo:
                        plan.styleNo ?? '-',
                        orderId:
                        plan.orderId,
                        plannedPieces:
                        plan.plannedPieces ?? 0,
                        status:
                        plan.status?.toString() ?? '-',
                      );
                    },
                  ),

                const SizedBox(height: 20),
              ],
            ),
          );
        },
      ),
    );
  }

  // ======================================================
  // CURRENT DATE
  // ======================================================

  String _currentDate() {
    final now = DateTime.now();

    const months = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December',
    ];

    return '${now.day} '
        '${months[now.month - 1]} '
        '${now.year}';
  }
}


// ==========================================================
// DASHBOARD CARD
// ==========================================================

class _DashboardCard extends StatelessWidget {
  const _DashboardCard({
    required this.title,
    required this.value,
    required this.icon,
    required this.iconColor,
  });

  final String title;
  final String value;
  final IconData icon;
  final Color iconColor;

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 3,

      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(18),
      ),

      child: Container(
        height: 145,

        padding: const EdgeInsets.symmetric(
          horizontal: 16,
          vertical: 12,
        ),

        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,

          children: [
            Icon(
              icon,
              size: 28,
              color: iconColor,
            ),

            const SizedBox(height: 6),

            Text(
              title,
              textAlign: TextAlign.center,
              maxLines: 1,
              overflow: TextOverflow.ellipsis,

              style: const TextStyle(
                fontSize: 14,
                fontWeight: FontWeight.w600,
              ),
            ),

            const SizedBox(height: 5),

            Text(
              value,

              style: TextStyle(
                fontSize: 26,
                fontWeight: FontWeight.bold,
                color: iconColor,
              ),
            ),
          ],
        ),
      ),
    );
  }
}


// ==========================================================
// SECTION HEADER
// ==========================================================

class _SectionHeader extends StatelessWidget {
  const _SectionHeader({
    required this.title,
    required this.icon,
  });

  final String title;
  final IconData icon;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Icon(
          icon,
          color: Theme.of(context)
              .colorScheme
              .primary,
        ),

        const SizedBox(width: 8),

        Text(
          title,
          style: const TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.bold,
          ),
        ),
      ],
    );
  }
}


// ==========================================================
// TODAY CUTTING CARD
// ==========================================================

class _TodayCuttingCard extends StatelessWidget {
  const _TodayCuttingCard({
    required this.styleNo,
    required this.cuttingMaster,
    required this.cutPieces,
    required this.rejectPieces,
  });

  final String styleNo;
  final String cuttingMaster;
  final num cutPieces;
  final num rejectPieces;

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.only(bottom: 10),

      elevation: 2,

      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15),
      ),

      child: Padding(
        padding: const EdgeInsets.all(15),

        child: Column(
          crossAxisAlignment:
          CrossAxisAlignment.start,

          children: [
            Row(
              children: [
                const CircleAvatar(
                  child: Icon(
                    Icons.content_cut,
                  ),
                ),

                const SizedBox(width: 12),

                Expanded(
                  child: Column(
                    crossAxisAlignment:
                    CrossAxisAlignment.start,

                    children: [
                      const Text(
                        'Style No',
                        style: TextStyle(
                          fontSize: 12,
                          color: Colors.grey,
                        ),
                      ),

                      Text(
                        styleNo,
                        style: const TextStyle(
                          fontSize: 17,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                ),

                Container(
                  padding:
                  const EdgeInsets.symmetric(
                    horizontal: 10,
                    vertical: 6,
                  ),

                  decoration: BoxDecoration(
                    borderRadius:
                    BorderRadius.circular(20),

                    color:
                    Colors.blue.withOpacity(0.1),
                  ),

                  child: Text(
                    '$cutPieces pcs',
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.blue,
                    ),
                  ),
                ),
              ],
            ),

            const Divider(height: 24),

            Row(
              children: [
                Expanded(
                  child: _InfoItem(
                    icon: Icons.person_outline,
                    title: 'Cutting Master',
                    value: cuttingMaster,
                  ),
                ),

                Expanded(
                  child: _InfoItem(
                    icon: Icons.content_cut,
                    title: 'Cut Pieces',
                    value: '$cutPieces',
                  ),
                ),

                Expanded(
                  child: _InfoItem(
                    icon: Icons.cancel_outlined,
                    title: 'Reject',
                    value: '$rejectPieces',
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}


// ==========================================================
// RECENT PLAN CARD
// ==========================================================

class _RecentPlanCard extends StatelessWidget {
  const _RecentPlanCard({
    required this.planId,
    required this.styleNo,
    required this.orderId,
    required this.plannedPieces,
    required this.status,
  });

  final dynamic planId;
  final String styleNo;
  final dynamic orderId;
  final num plannedPieces;
  final String status;

  @override
  Widget build(BuildContext context) {
    final normalizedStatus =
    status.toUpperCase();

    final isCompleted =
    normalizedStatus.contains('COMPLETED');

    return Card(
      margin: const EdgeInsets.only(bottom: 10),

      elevation: 2,

      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15),
      ),

      child: Padding(
        padding: const EdgeInsets.all(15),

        child: Column(
          children: [
            Row(
              children: [
                const CircleAvatar(
                  child: Icon(
                    Icons.assignment_outlined,
                  ),
                ),

                const SizedBox(width: 12),

                Expanded(
                  child: Column(
                    crossAxisAlignment:
                    CrossAxisAlignment.start,

                    children: [
                      Text(
                        'Plan #$planId',
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 16,
                        ),
                      ),

                      const SizedBox(height: 3),

                      Text(
                        'Style: $styleNo',
                        style: TextStyle(
                          color: Colors.grey.shade700,
                        ),
                      ),
                    ],
                  ),
                ),

                Container(
                  padding:
                  const EdgeInsets.symmetric(
                    horizontal: 10,
                    vertical: 6,
                  ),

                  decoration: BoxDecoration(
                    borderRadius:
                    BorderRadius.circular(20),

                    color: isCompleted
                        ? Colors.green.withOpacity(0.1)
                        : Colors.orange.withOpacity(0.1),
                  ),

                  child: Text(
                    status,
                    style: TextStyle(
                      fontSize: 12,
                      fontWeight: FontWeight.bold,
                      color: isCompleted
                          ? Colors.green
                          : Colors.orange,
                    ),
                  ),
                ),
              ],
            ),

            const Divider(height: 24),

            Row(
              children: [
                Expanded(
                  child: _InfoItem(
                    icon: Icons.shopping_bag_outlined,
                    title: 'Order ID',
                    value: '$orderId',
                  ),
                ),

                Expanded(
                  child: _InfoItem(
                    icon: Icons.inventory_2_outlined,
                    title: 'Planned Pieces',
                    value: '$plannedPieces',
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}


// ==========================================================
// INFO ITEM
// ==========================================================

class _InfoItem extends StatelessWidget {
  const _InfoItem({
    required this.icon,
    required this.title,
    required this.value,
  });

  final IconData icon;
  final String title;
  final String value;

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment:
      CrossAxisAlignment.start,

      children: [
        Icon(
          icon,
          size: 19,
          color: Theme.of(context)
              .colorScheme
              .primary,
        ),

        const SizedBox(width: 7),

        Expanded(
          child: Column(
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

              const SizedBox(height: 2),

              Text(
                value,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,

                style: const TextStyle(
                  fontSize: 13,
                  fontWeight: FontWeight.w600,
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}


// ==========================================================
// EMPTY CARD
// ==========================================================

class _EmptyCard extends StatelessWidget {
  const _EmptyCard({
    required this.icon,
    required this.message,
  });

  final IconData icon;
  final String message;

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(25),

        child: Column(
          children: [
            Icon(
              icon,
              size: 40,
              color: Colors.grey,
            ),

            const SizedBox(height: 10),

            Text(
              message,
              textAlign: TextAlign.center,

              style: TextStyle(
                color: Colors.grey.shade600,
              ),
            ),
          ],
        ),
      ),
    );
  }
}