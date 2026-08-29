import 'package:flutter/material.dart';
import 'package:gmsflutter/service/storage_service.dart';
import 'package:go_router/go_router.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class AppSidebar extends StatelessWidget {
  const AppSidebar({super.key});

  // ==========================================
  // STORAGE
  // ==========================================

  Future<String?> _getRole() async {
    const storage = FlutterSecureStorage();

    final storageService = StorageService(storage);

    return storageService.getRole();
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: SafeArea(
        child: FutureBuilder<String?>(
          future: _getRole(),

          builder: (context, snapshot) {
            // ==========================================
            // LOADING
            // ==========================================

            if (snapshot.connectionState ==
                ConnectionState.waiting) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            }

            // ==========================================
            // ROLE
            // ==========================================

            final role = snapshot.data;

            return Column(
              children: [
                // ==========================================
                // HEADER
                // ==========================================

                DrawerHeader(
                  child: Column(
                    mainAxisAlignment:
                    MainAxisAlignment.center,
                    children: [
                      const CircleAvatar(
                        radius: 30,
                        child: Icon(
                          Icons.business,
                          size: 32,
                        ),
                      ),

                      const SizedBox(height: 10),

                      const Text(
                        'Garments ERP',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                      ),

                      const SizedBox(height: 4),

                      Text(
                        _getRoleTitle(role),
                        style: TextStyle(
                          color: Theme.of(context)
                              .colorScheme
                              .primary,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ],
                  ),
                ),

                // ==========================================
                // MENU
                // ==========================================

                Expanded(
                  child: ListView(
                    padding: EdgeInsets.zero,
                    children: [
                      // ==========================================
                      // STORE MANAGER
                      // ==========================================

                      if (role == 'STORE_MANAGER')
                        ..._storeMenu(context),

                      // ==========================================
                      // PURCHASE MANAGER
                      // ==========================================

                      if (role == 'PURCHASE_MANAGER')
                        ..._purchaseMenu(context),

                      // ==========================================
                      // CUTTING MANAGER
                      // ==========================================

                      if (role == 'CUTTING_MANAGER')
                        ..._cuttingMenu(context),

                      // ==========================================
                      // UNKNOWN ROLE
                      // ==========================================

                      if (role == null ||
                          !_isKnownRole(role))
                        const ListTile(
                          leading: Icon(
                            Icons.warning_amber_outlined,
                          ),
                          title: Text(
                            'No menu available',
                          ),
                        ),
                    ],
                  ),
                ),

                // ==========================================
                // LOGOUT
                // ==========================================

                const Divider(),

                ListTile(
                  leading: const Icon(
                    Icons.logout,
                  ),
                  title: const Text(
                    'Logout',
                  ),
                  onTap: () async {
                    const storage =
                    FlutterSecureStorage();

                    final storageService =
                    StorageService(storage);

                    await storageService.clearSession();

                    if (!context.mounted) return;

                    context.go('/login');
                  },
                ),
              ],
            );
          },
        ),
      ),
    );
  }

  // ======================================================
  // STORE MENU
  // ======================================================

  List<Widget> _storeMenu(
      BuildContext context) {
    return [
      _menuItem(
        context,
        icon: Icons.dashboard_outlined,
        title: 'Dashboard',
        route: '/store/dashboard',
      ),

      _menuItem(
        context,
        icon: Icons.add_box_outlined,
        title: 'Add Item',
        route: '/store/items/add',
      ),

      _menuItem(
        context,
        icon: Icons.inventory_2_outlined,
        title: 'View Items',
        route: '/store/items',
      ),

      const Divider(),

      _menuItem(
        context,
        icon: Icons.post_add_outlined,
        title: 'Create Store Requisition',
        route: '/store/requisitions/add',
      ),

      _menuItem(
        context,
        icon: Icons.list_alt_outlined,
        title: 'View Store Requisitions',
        route: '/store/requisitions',
      ),

      const Divider(),

      _menuItem(
        context,
        icon: Icons.add_task_outlined,
        title: 'Create GRN',
        route: '/store/grn/add',
      ),

      _menuItem(
        context,
        icon: Icons.receipt_long_outlined,
        title: 'View GRN',
        route: '/store/grn',
      ),

      const Divider(),

      _menuItem(
        context,
        icon: Icons.warehouse_outlined,
        title: 'Current Stock',
        route: '/store/stock',
      ),

      const Divider(),

      _menuItem(
        context,
        icon: Icons.outbox_outlined,
        title: 'Create Material Issue',
        route: '/store/material-issues/add',
      ),

      _menuItem(
        context,
        icon: Icons.assignment_outlined,
        title: 'View Material Issues',
        route: '/store/material-issues',
      ),
    ];
  }

  // ======================================================
  // PROCUREMENT MENU
  // ======================================================

  List<Widget> _purchaseMenu(
      BuildContext context) {
    return [
      _menuItem(
        context,
        icon: Icons.dashboard_outlined,
        title: 'Dashboard',
        route: '/procurement/dashboard',
      ),

      _menuItem(
        context,
        icon: Icons.person_add_alt_1_outlined,
        title: 'Add Vendor',
        route: '/procurement/vendors/add',
      ),

      _menuItem(
        context,
        icon: Icons.business_outlined,
        title: 'View Vendors',
        route: '/procurement/vendors',
      ),

      const Divider(),

      _menuItem(
        context,
        icon: Icons.pending_actions,
        title: 'Pending Requisitions',
        route: '/procurement/requisitions/pending',
      ),

      _menuItem(
        context,
        icon: Icons.add_shopping_cart_outlined,
        title: 'Create Purchase Order',
        route: '/procurement/purchase-orders/add',
      ),

      _menuItem(
        context,
        icon: Icons.receipt_long_outlined,
        title: 'View Purchase Orders',
        route: '/procurement/purchase-orders',
      ),
    ];
  }

  // ======================================================
  // CUTTING MENU
  // ======================================================

  List<Widget> _cuttingMenu(
      BuildContext context) {
    return [
      _menuItem(
        context,
        icon: Icons.dashboard_outlined,
        title: 'Dashboard',
        route: '/cutting',
      ),

      _menuItem(
        context,
        icon: Icons.edit_note_outlined,
        title: 'Day Wise Entry',
        route: '/cutting/day-wise-entry',
      ),

      _menuItem(
        context,
        icon: Icons.content_cut_outlined,
        title: 'Cutting Plans',
        route: '/cutting/plans',
      ),

      _menuItem(
        context,
        icon: Icons.history,
        title: 'Production History',
        route: '/cutting/history',
      ),
    ];
  }

  // ======================================================
  // MENU ITEM
  // ======================================================

  Widget _menuItem(
      BuildContext context, {
        required IconData icon,
        required String title,
        required String route,
      }) {
    final currentLocation =
    GoRouterState.of(context).uri.toString();

    final isActive =
        currentLocation == route ||
            currentLocation.startsWith('$route/');

    return ListTile(
      leading: Icon(
        icon,
        color: isActive
            ? Theme.of(context)
            .colorScheme
            .primary
            : null,
      ),

      title: Text(
        title,
        style: TextStyle(
          fontWeight: isActive
              ? FontWeight.bold
              : FontWeight.normal,
          color: isActive
              ? Theme.of(context)
              .colorScheme
              .primary
              : null,
        ),
      ),

      selected: isActive,

      onTap: () {
        Navigator.pop(context);

        if (currentLocation != route) {
          context.go(route);
        }
      },
    );
  }

  // ======================================================
  // ROLE TITLE
  // ======================================================

  String _getRoleTitle(String? role) {
    switch (role) {
      case 'STORE_MANAGER':
        return 'Store Management';

      case 'PURCHASE_MANAGER':
        return 'Procurement Management';

      case 'CUTTING_MANAGER':
        return 'Cutting Management';

      default:
        return 'Garments ERP';
    }
  }

  // ======================================================
  // KNOWN ROLE
  // ======================================================

  bool _isKnownRole(String role) {
    return role == 'STORE_MANAGER' ||
        role == 'PURCHASE_MANAGER' ||
        role == 'CUTTING_MANAGER';
  }
}