import 'package:dio/dio.dart';
import 'package:gmsflutter/procurement/data/models/vendor_request.dart';
import 'package:gmsflutter/procurement/data/models/vendor_response.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/util/api_constants.dart';

class VendorRepository {
  VendorRepository(this._apiClient);

  final ApiClient _apiClient;

  Dio get _dio => _apiClient.dio;

  // ==========================================
  // CREATE VENDOR
  // ==========================================

  Future<VendorResponse> create(
      VendorRequest request,
      ) async {
    final response = await _dio.post(
      ApiConstants.vendors,
      data: request.toJson(),
    );

    return VendorResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // ==========================================
  // GET ALL VENDORS
  // ==========================================

  Future<List<VendorResponse>> getAll() async {
    final response = await _dio.get(
      ApiConstants.vendors,
    );

    final List data = response.data as List;

    return data
        .map(
          (vendor) => VendorResponse.fromJson(
        vendor as Map<String, dynamic>,
      ),
    )
        .toList();
  }

  // ==========================================
  // GET VENDOR BY ID
  // ==========================================

  Future<VendorResponse> getById(
      int id,
      ) async {
    final response = await _dio.get(
      '${ApiConstants.vendors}/$id',
    );

    return VendorResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // ==========================================
  // UPDATE VENDOR
  // ==========================================

  Future<VendorResponse> update(
      int id,
      VendorRequest request,
      ) async {
    final response = await _dio.put(
      '${ApiConstants.vendors}/$id',
      data: request.toJson(),
    );

    return VendorResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // ==========================================
  // DELETE VENDOR
  // ==========================================

  Future<void> delete(
      int id,
      ) async {
    await _dio.delete(
      '${ApiConstants.vendors}/$id',
    );
  }
}