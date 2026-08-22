import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../auth/providers/auth_provider.dart';

class DashboardScreen extends ConsumerWidget {
  const DashboardScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {

    final authState = ref.watch(authControllerProvider);
    final user = authState.value;

    final role = user?.role ?? '';

    return Scaffold(

      appBar: AppBar(
        title: const Text('Dashboard'),

        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () async {

              await ref
                  .read(authControllerProvider.notifier)
                  .logout();

              if (context.mounted) {
                context.go('/login');
              }
            },
          ),
        ],
      ),

      body: Padding(
        padding: const EdgeInsets.all(20),

        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,

          children: [

            Text(
              'Welcome ${user?.name ?? ''}',
              style: const TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 8),

            Text(
              'Role: $role',
              style: const TextStyle(
                fontSize: 16,
              ),
            ),

            const SizedBox(height: 30),

            Expanded(
              child: _buildDashboardContent(role),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildDashboardContent(String role) {

    switch (role) {

      case 'ADMIN':
        return _dashboardCard(
          'Admin Dashboard',
          Icons.admin_panel_settings,
        );

      case 'MERCHANDISER':
        return _dashboardCard(
          'Merchandising Dashboard',
          Icons.business_center,
        );

      case 'PURCHASE_MANAGER':
        return _dashboardCard(
          'Purchase Dashboard',
          Icons.shopping_cart,
        );

      case 'STORE_MANAGER':
        return _dashboardCard(
          'Store Dashboard',
          Icons.inventory,
        );

      case 'PRODUCTION_MANAGER':
        return _dashboardCard(
          'Production Dashboard',
          Icons.factory,
        );

      case 'CUTTING_MANAGER':
        return _dashboardCard(
          'Cutting Dashboard',
          Icons.content_cut,
        );

      case 'SEWING_MANAGER':
        return _dashboardCard(
          'Sewing Dashboard',
          Icons.checkroom,
        );

      case 'FINISHING_MANAGER':
        return _dashboardCard(
          'Finishing Dashboard',
          Icons.auto_awesome,
        );

      case 'PACKING_MANAGER':
        return _dashboardCard(
          'Packing Dashboard',
          Icons.inventory_2,
        );

      case 'SHIPMENT_EXECUTIVE':
        return _dashboardCard(
          'Shipment Dashboard',
          Icons.local_shipping,
        );

      default:
        return _dashboardCard(
          'Dashboard',
          Icons.dashboard,
        );
    }
  }

  Widget _dashboardCard(String title, IconData icon) {

    return Card(
      child: Padding(
        padding: const EdgeInsets.all(24),

        child: Row(
          children: [

            Icon(
              icon,
              size: 40,
            ),

            const SizedBox(width: 20),

            Text(
              title,
              style: const TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
          ],
        ),
      ),
    );
  }
}