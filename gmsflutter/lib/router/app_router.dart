import 'package:gmsflutter/auth/screens/login_screen.dart';

import 'package:gmsflutter/cutting/data/screens/add_day_wise_production_screen.dart';
import 'package:gmsflutter/cutting/data/screens/cutting_home_screen.dart';
import 'package:gmsflutter/cutting/data/screens/cutting_plans_screen.dart';
import 'package:gmsflutter/cutting/data/screens/production_history_screen.dart';
import 'package:gmsflutter/procurement/data/screens/purchase_dashboard_screen.dart';

import 'package:gmsflutter/store/data/screens/add_item_screen.dart';
import 'package:gmsflutter/store/data/screens/add_store_requisition_screen.dart';
import 'package:gmsflutter/store/data/screens/create_material_issue_screen.dart';
import 'package:gmsflutter/store/data/screens/current_stock_screen.dart';
import 'package:gmsflutter/store/data/screens/edit_item_screen.dart';
import 'package:gmsflutter/store/data/screens/edit_store_requisition_screen.dart';
import 'package:gmsflutter/store/data/screens/grn_details_screen.dart';
import 'package:gmsflutter/store/data/screens/grn_list_screen.dart';
import 'package:gmsflutter/store/data/screens/item_details_screen.dart';
import 'package:gmsflutter/store/data/screens/item_list_screen.dart';
import 'package:gmsflutter/store/data/screens/material_issue_details_screen.dart';
import 'package:gmsflutter/store/data/screens/material_issue_list_screen.dart';
import 'package:gmsflutter/store/data/screens/store_dashboard_screen.dart';
import 'package:gmsflutter/store/data/screens/store_requisition_details_screen.dart';
import 'package:gmsflutter/store/data/screens/store_requisition_list_screen.dart';

import 'package:go_router/go_router.dart';

final GoRouter appRouter = GoRouter(
  initialLocation: '/login',

  routes: [
    // ==================================================
    // LOGIN
    // ==================================================

    GoRoute(
      path: '/login',
      builder: (context, state) {
        return const LoginScreen();
      },
    ),

    // ==================================================
    // CUTTING
    // ==================================================

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

    // ==================================================
    // STORE DASHBOARD
    // ==================================================

    GoRoute(
      path: '/store/dashboard',
      builder: (context, state) {
        return const StoreDashboardScreen();
      },
    ),

    // ==================================================
    // ITEMS
    // ==================================================

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
        final id = int.parse(
          state.pathParameters['id']!,
        );

        return EditItemScreen(
          itemId: id,
        );
      },
    ),

    GoRoute(
      path: '/store/items/details/:id',
      builder: (context, state) {
        final id = int.parse(
          state.pathParameters['id']!,
        );

        return ItemDetailsScreen(
          itemId: id,
        );
      },
    ),

    // ==================================================
    // STORE REQUISITIONS
    // ==================================================

    GoRoute(
      path: '/store/requisitions',
      builder: (context, state) {
        return const StoreRequisitionListScreen();
      },
    ),

    GoRoute(
      path: '/store/requisitions/add',
      builder: (context, state) {
        return const AddStoreRequisitionScreen();
      },
    ),

    GoRoute(
      path: '/store/requisitions/details/:id',
      builder: (context, state) {
        final id = int.parse(
          state.pathParameters['id']!,
        );

        return StoreRequisitionDetailsScreen(
          requisitionId: id,
        );
      },
    ),

    GoRoute(
      path: '/store/requisitions/edit/:id',
      builder: (context, state) {
        final id = int.parse(
          state.pathParameters['id']!,
        );

        return EditStoreRequisitionScreen(
          requisitionId: id,
        );
      },
    ),

    // ==================================================
    // GRN
    // ==================================================

    GoRoute(
      path: '/store/grn',
      builder: (context, state) {
        return const GrnListScreen();
      },
    ),

    GoRoute(
      path: '/store/grn/details/:id',
      builder: (context, state) {
        final id = int.parse(
          state.pathParameters['id']!,
        );

        return GrnDetailsScreen(
          grnId: id,
        );
      },
    ),

    // Create GRN পরে হবে

    // ==================================================
    // MATERIAL ISSUES
    // ==================================================

    GoRoute(
      path: '/store/material-issues',
      builder: (context, state) {
        return const MaterialIssueListScreen();
      },
    ),

    GoRoute(
      path: '/store/material-issues/add',
      builder: (context, state) {
        return const CreateMaterialIssueScreen();
      },
    ),

    GoRoute(
      path: '/store/material-issues/details/:id',
      builder: (context, state) {
        final id = int.parse(
          state.pathParameters['id']!,
        );

        return MaterialIssueDetailsScreen(
          issueId: id,
        );
      },
    ),

    // ==================================================
    // STOCK
    // ==================================================

    GoRoute(
      path: '/store/stock',
      builder: (context, state) {
        return const CurrentStockScreen();
      },
    ),

    // procurement
    GoRoute(
      path: '/procurement/dashboard',
      builder: (context, state) {
        return const PurchaseDashboardScreen();
      },
    ),
  ],
);