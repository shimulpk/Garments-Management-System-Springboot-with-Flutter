import 'package:gmsflutter/auth/screens/login_screen.dart';
import 'package:gmsflutter/cutting/data/screens/add_day_wise_production_screen.dart';
import 'package:gmsflutter/cutting/data/screens/cutting_home_screen.dart';
import 'package:gmsflutter/cutting/data/screens/cutting_plans_screen.dart';
import 'package:gmsflutter/cutting/data/screens/production_history_screen.dart';
import 'package:gmsflutter/store/data/screens/add_item_screen.dart';
import 'package:gmsflutter/store/data/screens/add_store_requisition_screen.dart';
import 'package:gmsflutter/store/data/screens/edit_item_screen.dart';
import 'package:gmsflutter/store/data/screens/edit_store_requisition_screen.dart';
import 'package:gmsflutter/store/data/screens/item_details_screen.dart';
import 'package:gmsflutter/store/data/screens/item_list_screen.dart';
import 'package:gmsflutter/store/data/screens/store_dashboard_screen.dart';
import 'package:gmsflutter/store/data/screens/store_requisition_details_screen.dart';
import 'package:gmsflutter/store/data/screens/store_requisition_list_screen.dart';
import 'package:go_router/go_router.dart';

final GoRouter appRouter = GoRouter(
  initialLocation: '/login',

  routes: [
    GoRoute(
      path: '/login',
      builder: (context, state) {
        return const LoginScreen();
      },
    ),

    GoRoute(
      path: '/cutting',
      builder: (context, state) {
        return const CuttingHomeScreen();
      },
    ),

    GoRoute(
      path: '/cutting/day-wise-entry',
      builder: (context, state) {
        return const AddDayWiseProductionScreen();
      },
    ),

    GoRoute(
      path: '/cutting/plans',
      builder: (context, state) {
        return const CuttingPlansScreen();
      },
    ),

    GoRoute(
      path: '/cutting/history',
      builder: (context, state) {
        return const ProductionHistoryScreen();
      },
    ),

    GoRoute(
      path: '/store-dashboard',
      builder: (context, state) {
        return const StoreDashboardScreen();
      },
    ),

    GoRoute(
      path: '/store/items',
      builder: (context, state) {
        return const ItemListScreen();
      },
    ),

    GoRoute(
      path: '/store/items/add',
      builder: (context, state) {
        return const AddItemScreen();
      },
    ),

    GoRoute(
      path: '/store/items/edit/:id',
      builder: (context, state) {
        final id = int.parse(state.pathParameters['id']!);

        return EditItemScreen(itemId: id);
      },
    ),

    GoRoute(
      path: '/store/items/details/:id',
      builder: (context, state) {
        final id = int.parse(state.pathParameters['id']!);

        return ItemDetailsScreen(itemId: id);
      },
    ),

    GoRoute(
      path: '/store/requisitions',
      builder: (context, state) =>
      const StoreRequisitionListScreen(),
    ),

    GoRoute(
      path: '/store/requisitions/add',
      builder: (context, state) =>
      const AddStoreRequisitionScreen(),
    ),

    GoRoute(
      path: '/store/requisitions/details/:id',
      builder: (context, state) {
        final id = int.parse(state.pathParameters['id']!);

        return StoreRequisitionDetailsScreen(
          requisitionId: id,
        );
      },
    ),

    GoRoute(
      path: '/store/requisitions/edit/:id',
      builder: (context, state) {
        final id = int.parse(state.pathParameters['id']!);

        return EditStoreRequisitionScreen(
          requisitionId: id,
        );
      },
    ),
  ],
);