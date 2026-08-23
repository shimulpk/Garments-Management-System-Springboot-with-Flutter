import 'package:gmsflutter/auth/screens/login_screen.dart';
import 'package:gmsflutter/cutting/data/screens/add_day_wise_production_screen.dart';
import 'package:gmsflutter/cutting/data/screens/cutting_home_screen.dart';
import 'package:gmsflutter/cutting/data/screens/cutting_plans_screen.dart';
import 'package:gmsflutter/cutting/data/screens/production_history_screen.dart';
import 'package:gmsflutter/store/data/screens/store_dashboard_screen.dart';
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
  ],
);