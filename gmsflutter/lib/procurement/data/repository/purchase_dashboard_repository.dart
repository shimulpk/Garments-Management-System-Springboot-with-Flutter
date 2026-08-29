import 'package:dio/dio.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/util/api_constants.dart';

import '../models/purchase_dashboard_response.dart';

class PurchaseDashboardRepository {
  PurchaseDashboardRepository(this._apiClient);

  final ApiClient _apiClient;

  Dio get _dio => _apiClient.dio;

  // ==========================================
  // Purchase Dashboard
  // ==========================================

  Future<PurchaseDashboardResponse> getDashboard() async {
    final response = await _dio.get(
      ApiConstants.purchaseDashboard,
    );

    return PurchaseDashboardResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }
}