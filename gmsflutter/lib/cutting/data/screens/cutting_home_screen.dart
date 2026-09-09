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

      // ==================================================
      // SIDEBAR
      // ==================================================

      drawer: const AppSidebar(),

      // ==================================================
      // APP BAR
      // ==================================================

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

      // ==================================================
      // BODY
      // ==================================================

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
                // SECTION TITLE
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
                // ROW 1
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
                // ROW 2
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
                // ROW 3
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
                // INFORMATION CARD
                // ==================================================

                Card(
                  elevation: 2,

                  shape: RoundedRectangleBorder(
                    borderRadius:
                    BorderRadius.circular(15),
                  ),

                  child: Padding(
                    padding:
                    const EdgeInsets.all(16),

                    child: Row(
                      children: [

                        Icon(
                          Icons.info_outline,

                          color:
                          Theme.of(context)
                              .colorScheme
                              .primary,
                        ),

                        const SizedBox(width: 12),

                        const Expanded(
                          child: Text(
                            'Use the sidebar to access '
                                'Day Wise Entry, Cutting Plans '
                                'and Production History.',
                          ),
                        ),
                      ],
                    ),
                  ),
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
        borderRadius:
        BorderRadius.circular(18),
      ),

      child: Container(
        height: 145,

        padding:
        const EdgeInsets.symmetric(
          horizontal: 16,
          vertical: 12,
        ),

        child: Column(
          mainAxisAlignment:
          MainAxisAlignment.center,

          children: [

            // ICON
            Icon(
              icon,
              size: 28,
              color: iconColor,
            ),

            const SizedBox(height: 6),

            // TITLE
            Text(
              title,

              textAlign: TextAlign.center,

              maxLines: 1,

              overflow:
              TextOverflow.ellipsis,

              style: const TextStyle(
                fontSize: 14,
                fontWeight: FontWeight.w600,
              ),
            ),

            const SizedBox(height: 5),

            // VALUE
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