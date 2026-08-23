import 'package:dio/dio.dart';
import 'package:gmsflutter/store/data/models/store_dashboard_response.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/util/api_constants.dart';

class StoreRepository {
  StoreRepository(this._apiClient);

  final ApiClient _apiClient;

  Dio get _dio => _apiClient.dio;

  // Store Dashboard
  Future<StoreDashboardResponse> getDashboard() async {
    final response = await _dio.get(
      ApiConstants.storeDashboard,
    );

    return StoreDashboardResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }
}