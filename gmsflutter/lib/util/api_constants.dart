import 'package:flutter/foundation.dart';

class ApiConstants {
  ApiConstants._();

  // ── Host & Base URLs ──────────────────────────────────
  static String get host {
    if (kIsWeb) {
      return 'localhost';
    }

    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return '10.0.2.2';
      case TargetPlatform.iOS:
        return 'localhost';
      default:
        return 'localhost';
    }
  }

  static String get baseUrl => 'http://$host:8085/api/';
  static String get imageUrl => 'http://$host:8085/images/';

  // ── Auth ──────────────────────────────────────────────
  static const String login = 'auth/login';

  // ── Dashboards (Role Based) ───────────────────────────
  static const String dashboard = 'dashboard';
  static const String merchandiserDashboard = 'dashboard/merchandiser';
  static const String purchaseDashboard = 'dashboard/purchase';
  static const String storeDashboard = '/dashboard/store';
  static const String cuttingDashboard = 'dashboard/cutting';
  static const String sewingDashboard = 'dashboard/sewing';
  static const String finishingDashboard = 'dashboard/finishing';
  static const String packingDashboard = 'dashboard/packing';
  static const String shipmentDashboard = 'dashboard/shipment';

  // ── Merchandising ─────────────────────────────────────
  static const String buyers = 'buyers';
  static const String styles = 'styles';
  static const String orders = 'orders';
  static const String uoms = 'uoms';
  static const String bom = 'bom-views';

  // ── Procurement ───────────────────────────────────────
  static const String vendors = 'vendors';
  static const String storeRequisitions = 'store-requisitions';
  static const String purchaseOrders = 'purchase-orders';
  static const String grns = 'grns';

  // ── Inventory ─────────────────────────────────────────
  static const String items = '/items';
  static String itemById(int id) => '/items/$id';
  static const String stocks = 'stocks';
  static const String materialIssues = 'material-issues';

  // ── Production - Cutting ──────────────────────────────
  static const String cuttingPlans = 'cutting-plans';
  static String cuttingPlanById(int id) => 'cutting-plans/$id';
  static const String pendingCuttingPlans = 'cutting-plans/pending';
  static const String completedCuttingPlans = 'cutting-plans/completed';
  static const String availableForSewingPlan =
      'cutting-plans/available-for-sewing-plan';

  static const String dayWiseCuttingProduction =
      'day-wise-cutting-production';
  static String dayWiseCuttingProductionById(int id) =>
      'day-wise-cutting-production/$id';
  static String cuttingProgress(int cuttingPlanId) =>
      'day-wise-cutting-production/progress/$cuttingPlanId';
  static String dayWiseCuttingByCuttingPlan(int cuttingPlanId) =>
      'day-wise-cutting-production/cutting-plan/$cuttingPlanId';

  // Cutting History
  static const String cuttingHistory =
      'day-wise-cutting-production/history';
  static String cuttingHistoryByDate(String date) =>
      'day-wise-cutting-production/history?date=$date';
  static String cuttingHistoryDetails(int cuttingPlanId, String date) =>
      'day-wise-cutting-production/history/details?cuttingPlanId=$cuttingPlanId&date=$date';
  static String cuttingHistorySummary(int cuttingPlanId, String date) =>
      'day-wise-cutting-production/history-details-summary?cuttingPlanId=$cuttingPlanId&date=$date';








}