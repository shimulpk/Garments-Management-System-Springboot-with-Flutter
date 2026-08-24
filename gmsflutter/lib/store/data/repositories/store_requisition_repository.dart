import 'package:dio/dio.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/util/api_constants.dart';

import '../models/store_requisition_request.dart';
import '../models/store_requisition_response.dart';

class StoreRequisitionRepository {
  StoreRequisitionRepository(this._apiClient);

  final ApiClient _apiClient;

  Dio get _dio => _apiClient.dio;

  // Create
  Future<StoreRequisitionResponse> create(
      StoreRequisitionRequest request,
      ) async {
    final response = await _dio.post(
      ApiConstants.storeRequisitions,
      data: request.toJson(),
    );

    return StoreRequisitionResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // Get All
  Future<List<StoreRequisitionResponse>> getAll() async {
    final response = await _dio.get(
      ApiConstants.storeRequisitions,
    );

    final List data = response.data as List;

    return data
        .map(
          (item) => StoreRequisitionResponse.fromJson(
        item as Map<String, dynamic>,
      ),
    )
        .toList();
  }

  // Get By ID
  Future<StoreRequisitionResponse> getById(int id) async {
    final response = await _dio.get(
      ApiConstants.storeRequisitionById(id),
    );

    return StoreRequisitionResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // Pending
  Future<List<StoreRequisitionResponse>> getPending() async {
    final response = await _dio.get(
      ApiConstants.pendingStoreRequisitions,
    );

    final List data = response.data as List;

    return data
        .map(
          (item) => StoreRequisitionResponse.fromJson(
        item as Map<String, dynamic>,
      ),
    )
        .toList();
  }

  // Approved
  Future<List<StoreRequisitionResponse>> getApproved() async {
    final response = await _dio.get(
      ApiConstants.approvedStoreRequisitions,
    );

    final List data = response.data as List;

    return data
        .map(
          (item) => StoreRequisitionResponse.fromJson(
        item as Map<String, dynamic>,
      ),
    )
        .toList();
  }

  // Update
  Future<StoreRequisitionResponse> update(
      int id,
      StoreRequisitionRequest request,
      ) async {
    final response = await _dio.put(
      ApiConstants.storeRequisitionById(id),
      data: request.toJson(),
    );

    return StoreRequisitionResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // Delete
  Future<void> delete(int id) async {
    await _dio.delete(
      ApiConstants.storeRequisitionById(id),
    );
  }

  // Approve
  Future<StoreRequisitionResponse> approve(int id) async {
    final response = await _dio.put(
      ApiConstants.approveStoreRequisition(id),
    );

    return StoreRequisitionResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // Reject
  Future<StoreRequisitionResponse> reject(int id) async {
    final response = await _dio.put(
      ApiConstants.rejectStoreRequisition(id),
    );

    return StoreRequisitionResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }
}