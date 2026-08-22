import 'package:dio/dio.dart';
import 'package:gmsflutter/util/api_constants.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/cutting/data/models/cutting_dashboard_response.dart';
import 'package:gmsflutter/cutting/data/models/cutting_plan_progress_response.dart';
import 'package:gmsflutter/cutting/data/models/cutting_plan_response.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_history_details_response.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_history_response.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_history_summary_response.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_production_request.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_production_response.dart';
import 'package:gmsflutter/cutting/data/models/day_wise_cutting_production_update_request.dart';

class CuttingRepository {
  CuttingRepository(this._apiClient);

  final ApiClient _apiClient;
  Dio get _dio => _apiClient.dio;

  // ─────────────────────────────────────────────
  // 1. Dashboard API (/api/dashboard/cutting)
  // ─────────────────────────────────────────────

  Future<CuttingDashboardResponse> getDashboard() async {
    final response = await _dio.get(ApiConstants.cuttingDashboard);
    return CuttingDashboardResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // ─────────────────────────────────────────────
  // 2. Cutting Plan APIs (/api/cutting-plans)
  // ─────────────────────────────────────────────

  Future<List<CuttingPlanResponse>> getAllCuttingPlans() async {
    final response = await _dio.get(ApiConstants.cuttingPlans);
    final List data = response.data as List;
    return data
        .map((item) => CuttingPlanResponse.fromJson(item as Map<String, dynamic>))
        .toList();
  }

  Future<CuttingPlanResponse> getCuttingPlanById(int id) async {
    final response = await _dio.get(ApiConstants.cuttingPlanById(id));
    return CuttingPlanResponse.fromJson(response.data as Map<String, dynamic>);
  }

  Future<List<CuttingPlanResponse>> getPendingCuttingPlans() async {
    final response = await _dio.get(ApiConstants.pendingCuttingPlans);
    final List data = response.data as List;
    return data
        .map((item) => CuttingPlanResponse.fromJson(item as Map<String, dynamic>))
        .toList();
  }

  Future<List<CuttingPlanResponse>> getCompletedCuttingPlans() async {
    final response = await _dio.get(ApiConstants.completedCuttingPlans);
    final List data = response.data as List;
    return data
        .map((item) => CuttingPlanResponse.fromJson(item as Map<String, dynamic>))
        .toList();
  }

  Future<List<CuttingPlanResponse>> getAvailableForSewingPlan() async {
    final response = await _dio.get(ApiConstants.availableForSewingPlan);
    final List data = response.data as List;
    return data
        .map((item) => CuttingPlanResponse.fromJson(item as Map<String, dynamic>))
        .toList();
  }

  // ─────────────────────────────────────────────
  // 3. Day Wise Production APIs (/api/day-wise-cutting-production)
  // ─────────────────────────────────────────────

  Future<DayWiseCuttingProductionResponse> saveDayWiseCuttingProduction(
      DayWiseCuttingProductionRequest request) async {
    final response = await _dio.post(
      ApiConstants.dayWiseCuttingProduction,
      data: request.toJson(),
    );
    return DayWiseCuttingProductionResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  Future<DayWiseCuttingProductionResponse> updateDayWiseCuttingProduction(
      int id, DayWiseCuttingProductionUpdateRequest request) async {
    final response = await _dio.put(
      ApiConstants.dayWiseCuttingProductionById(id),
      data: request.toJson(),
    );
    return DayWiseCuttingProductionResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  Future<DayWiseCuttingProductionResponse> getDayWiseCuttingProductionById(
      int id) async {
    final response = await _dio.get(
      ApiConstants.dayWiseCuttingProductionById(id),
    );
    return DayWiseCuttingProductionResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  Future<List<DayWiseCuttingProductionResponse>> getAllDayWiseCuttingProduction() async {
    final response = await _dio.get(ApiConstants.dayWiseCuttingProduction);
    final List data = response.data as List;
    return data
        .map((item) =>
        DayWiseCuttingProductionResponse.fromJson(item as Map<String, dynamic>))
        .toList();
  }

  Future<void> deleteDayWiseCuttingProduction(int id) async {
    await _dio.delete(ApiConstants.dayWiseCuttingProductionById(id));
  }

  Future<CuttingPlanProgressResponse> getCuttingProgress(int cuttingPlanId) async {
    final response = await _dio.get(ApiConstants.cuttingProgress(cuttingPlanId));
    return CuttingPlanProgressResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  Future<List<DayWiseCuttingProductionResponse>> getByCuttingPlan(
      int cuttingPlanId) async {
    final response = await _dio.get(
      ApiConstants.dayWiseCuttingByCuttingPlan(cuttingPlanId),
    );
    final List data = response.data as List;
    return data
        .map((item) =>
        DayWiseCuttingProductionResponse.fromJson(item as Map<String, dynamic>))
        .toList();
  }

  // ─────────────────────────────────────────────
  // 4. History APIs
  // ─────────────────────────────────────────────

  Future<List<DayWiseCuttingHistoryResponse>> getHistory({String? date}) async {
    final String url = date != null
        ? ApiConstants.cuttingHistoryByDate(date)
        : ApiConstants.cuttingHistory;

    final response = await _dio.get(url);
    final List data = response.data as List;
    return data
        .map((item) =>
        DayWiseCuttingHistoryResponse.fromJson(item as Map<String, dynamic>))
        .toList();
  }

  Future<List<DayWiseCuttingHistoryDetailsResponse>> getHistoryDetails(
      int cuttingPlanId, String date) async {
    final response = await _dio.get(
      ApiConstants.cuttingHistoryDetails(cuttingPlanId, date),
    );
    final List data = response.data as List;
    return data
        .map((item) => DayWiseCuttingHistoryDetailsResponse.fromJson(
        item as Map<String, dynamic>))
        .toList();
  }

  Future<DayWiseCuttingHistorySummaryResponse> getHistorySummary(
      int cuttingPlanId, String date) async {
    final response = await _dio.get(
      ApiConstants.cuttingHistorySummary(cuttingPlanId, date),
    );
    return DayWiseCuttingHistorySummaryResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }
}