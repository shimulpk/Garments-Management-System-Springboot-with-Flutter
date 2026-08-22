import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../provider/cutting_provider.dart';

class CuttingHomeScreen extends ConsumerWidget {
  const CuttingHomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final dashboardAsync = ref.watch(cuttingDashboardProvider);

    return Scaffold(
      backgroundColor: const Color(0xFFF5F7FA),

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
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Icon(
                Icons.error_outline,
                size: 50,
              ),
              const SizedBox(height: 12),
              const Text(
                'Dashboard load failed',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: () {
                  ref.invalidate(cuttingDashboardProvider);
                },
                child: const Text('Retry'),
              ),
            ],
          ),
        ),

        data: (dashboard) {
          return RefreshIndicator(
            onRefresh: () async {
              ref.invalidate(cuttingDashboardProvider);
              await ref.read(cuttingDashboardProvider.future);
            },

            child: ListView(
              padding: const EdgeInsets.all(20),
              children: [

                // ─────────────────────────────
                // Welcome
                // ─────────────────────────────

                const Text(
                  'Welcome, User',
                  style: TextStyle(
                    fontSize: 26,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 4),

                Text(
                  _currentDate(),
                  style: TextStyle(
                    fontSize: 15,
                    color: Colors.grey.shade600,
                  ),
                ),

                const SizedBox(height: 24),

                const Text(
                  'Production Dashboard',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 16),

                // ─────────────────────────────
                // Dashboard Cards - Row 1
                // ─────────────────────────────

                Row(
                  children: [
                    Expanded(
                      child: _DashboardCard(
                        title: 'Today Cutting',
                        value: '${dashboard.todayCutting ?? 0}',
                        icon: Icons.content_cut,
                        iconColor: Colors.blue,
                      ),
                    ),

                    const SizedBox(width: 12),

                    Expanded(
                      child: _DashboardCard(
                        title: 'Today Reject',
                        value: '${dashboard.todayReject ?? 0}',
                        icon: Icons.cancel_outlined,
                        iconColor: Colors.red,
                      ),
                    ),
                  ],
                ),

                const SizedBox(height: 12),

                // ─────────────────────────────
                // Dashboard Cards - Row 2
                // ─────────────────────────────

                Row(
                  children: [
                    Expanded(
                      child: _DashboardCard(
                        title: 'Pending Plans',
                        value: '${dashboard.pendingPlans ?? 0}',
                        icon: Icons.pending_actions,
                        iconColor: Colors.orange,
                      ),
                    ),

                    const SizedBox(width: 12),

                    Expanded(
                      child: _DashboardCard(
                        title: 'Completed Plans',
                        value: '${dashboard.completedPlans ?? 0}',
                        icon: Icons.check_circle_outline,
                        iconColor: Colors.green,
                      ),
                    ),
                  ],
                ),

                const SizedBox(height: 28),

                // ─────────────────────────────
                // Buttons
                // ─────────────────────────────

                _HomeButton(
                  title: 'Day Wise Entry',
                  icon: Icons.add_circle_outline,
                  onPressed: () {
                    context.push('/cutting/day-wise-entry');
                  },
                ),

                const SizedBox(height: 12),

                _HomeButton(
                  title: 'View Cutting Plans',
                  icon: Icons.list_alt,
                  onPressed: () {
                    context.push('/cutting/plans');
                  },
                ),

                const SizedBox(height: 12),

                _HomeButton(
                  title: 'Production History',
                  icon: Icons.history,
                  onPressed: () {
                    context.push('/cutting/history');
                  },
                ),

                const SizedBox(height: 12),

                _HomeButton(
                  title: 'Logout',
                  icon: Icons.logout,
                  isLogout: true,
                  onPressed: () {
                    _logout(context, ref);
                  },
                ),
              ],
            ),
          );
        },
      ),
    );
  }

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

    return '${now.day} ${months[now.month - 1]} ${now.year}';
  }

  Future<void> _logout(
      BuildContext context,
      WidgetRef ref,
      ) async {
    // Logout logic will be connected with StorageService.
    context.go('/login');
  }
}


// ─────────────────────────────────────────────
// Dashboard Card
// ─────────────────────────────────────────────

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
      elevation: 5,
      shadowColor: Colors.black12,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20),
      ),

      child: Container(
        height: 140,
        padding: const EdgeInsets.all(16),

        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,

          children: [
            Icon(
              icon,
              size: 28,
              color: iconColor,
            ),

            const SizedBox(height: 8),

            Text(
              title,
              textAlign: TextAlign.center,
              style: const TextStyle(
                fontSize: 14,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 8),

            Text(
              value,
              style: TextStyle(
                fontSize: 28,
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


// ─────────────────────────────────────────────
// Home Button
// ─────────────────────────────────────────────

class _HomeButton extends StatelessWidget {
  const _HomeButton({
    required this.title,
    required this.icon,
    required this.onPressed,
    this.isLogout = false,
  });

  final String title;
  final IconData icon;
  final VoidCallback onPressed;
  final bool isLogout;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 54,
      width: double.infinity,

      child: ElevatedButton.icon(
        onPressed: onPressed,

        icon: Icon(icon),

        label: Text(
          title,
          style: const TextStyle(
            fontSize: 17,
            fontWeight: FontWeight.bold,
          ),
        ),

        style: ElevatedButton.styleFrom(
          backgroundColor:
          isLogout ? Colors.red : Colors.blue,

          foregroundColor: Colors.white,

          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(14),
          ),

          elevation: 2,
        ),
      ),
    );
  }
}