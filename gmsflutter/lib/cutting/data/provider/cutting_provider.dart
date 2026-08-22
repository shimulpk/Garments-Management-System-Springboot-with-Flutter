import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';

import 'package:gmsflutter/cutting/data/models/cutting_dashboard_response.dart';
import 'package:gmsflutter/cutting/data/models/cutting_plan_progress_response.dart';
import 'package:gmsflutter/cutting/data/models/cutting_plan_response.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_history_details_response.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_history_response.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_history_summary_response.dart';

import 'package:gmsflutter/cutting/data/repositories/cutting_repository.dart';

// Repository Provider
final cuttingRepositoryProvider = Provider<CuttingRepository>((ref) {
  return CuttingRepository(ref.watch(apiClientProvider));
});

// Dashboard Statistics Provider
final cuttingDashboardProvider =
FutureProvider.autoDispose<CuttingDashboardResponse>((ref) async {
  return ref.watch(cuttingRepositoryProvider).getDashboard();
});

// All Cutting Plans Provider
final allCuttingPlansProvider =
FutureProvider.autoDispose<List<CuttingPlanResponse>>((ref) async {
  return ref.watch(cuttingRepositoryProvider).getAllCuttingPlans();
});

// Pending Cutting Plans Provider
final pendingCuttingPlansProvider =
FutureProvider.autoDispose<List<CuttingPlanResponse>>((ref) async {
  return ref.watch(cuttingRepositoryProvider).getPendingCuttingPlans();
});

// Plan Progress Provider (Family)
final cuttingPlanProgressProvider = FutureProvider.autoDispose
    .family<CuttingPlanProgressResponse, int>((ref, planId) async {
  return ref.watch(cuttingRepositoryProvider).getCuttingProgress(planId);
});

// Cutting History Provider
final cuttingHistoryProvider = FutureProvider.autoDispose
    .family<List<DayWiseCuttingHistoryResponse>, String?>((ref, date) async {
  return ref.watch(cuttingRepositoryProvider).getHistory(date: date);
});

// History Details
final cuttingHistoryDetailsProvider = FutureProvider.autoDispose
    .family<
    List<DayWiseCuttingHistoryDetailsResponse>,
    ({int cuttingPlanId, String date})>((ref, params) async {
  return ref
      .watch(cuttingRepositoryProvider)
      .getHistoryDetails(
    params.cuttingPlanId,
    params.date,
  );
});


// History Summary
final cuttingHistorySummaryProvider = FutureProvider.autoDispose
    .family<
    DayWiseCuttingHistorySummaryResponse,
    ({int cuttingPlanId, String date})>((ref, params) async {
  return ref
      .watch(cuttingRepositoryProvider)
      .getHistorySummary(
    params.cuttingPlanId,
    params.date,
  );
});