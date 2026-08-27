import 'package:dio/dio.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/util/api_constants.dart';

import '../models/material_issue_request.dart';
import '../models/material_issue_response.dart';

class MaterialIssueRepository {
  MaterialIssueRepository(this._apiClient);

  final ApiClient _apiClient;

  Dio get _dio => _apiClient.dio;

  // Create Material Issue
  Future<MaterialIssueResponse> create(
      MaterialIssueRequest request,
      ) async {
    final response = await _dio.post(
      ApiConstants.materialIssues,
      data: request.toJson(),
    );

    return MaterialIssueResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // Get All Material Issues
  Future<List<MaterialIssueResponse>> getAll() async {
    final response = await _dio.get(
      ApiConstants.materialIssues,
    );

    final List data = response.data as List;

    return data
        .map(
          (item) => MaterialIssueResponse.fromJson(
        item as Map<String, dynamic>,
      ),
    )
        .toList();
  }

  // Get Material Issue By ID
  Future<MaterialIssueResponse> getById(int id) async {
    final response = await _dio.get(
      ApiConstants.materialIssueById(id),
    );

    return MaterialIssueResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }
}