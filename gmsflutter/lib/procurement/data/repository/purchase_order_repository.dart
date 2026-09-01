import 'package:dio/dio.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/util/api_constants.dart';

import '../models/purchase_order_request.dart';
import '../models/purchase_order_response.dart';

class PurchaseOrderRepository {
  PurchaseOrderRepository(this._apiClient);

  final ApiClient _apiClient;

  Dio get _dio => _apiClient.dio;

  // ==========================================
  // CREATE PURCHASE ORDER
  // ==========================================

  Future<PurchaseOrderResponse> create(
      PurchaseOrderRequest request,
      ) async {
    final response = await _dio.post(
      ApiConstants.purchaseOrders,
      data: request.toJson(),
    );

    return PurchaseOrderResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // ==========================================
  // GET ALL PURCHASE ORDERS
  // ==========================================

  Future<List<PurchaseOrderResponse>> getAll() async {
    final response = await _dio.get(
      ApiConstants.purchaseOrders,
    );

    final List data = response.data as List;

    return data
        .map(
          (po) => PurchaseOrderResponse.fromJson(
        po as Map<String, dynamic>,
      ),
    )
        .toList();
  }

  // ==========================================
  // GET PURCHASE ORDER BY ID
  // ==========================================

  Future<PurchaseOrderResponse> getById(
      int id,
      ) async {
    final response = await _dio.get(
      '${ApiConstants.purchaseOrders}/$id',
    );

    return PurchaseOrderResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // ==========================================
  // UPDATE PURCHASE ORDER
  // ==========================================

  Future<PurchaseOrderResponse> update(
      int id,
      PurchaseOrderRequest request,
      ) async {
    final response = await _dio.put(
      '${ApiConstants.purchaseOrders}/$id',
      data: request.toJson(),
    );

    return PurchaseOrderResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // ==========================================
  // DELETE PURCHASE ORDER
  // ==========================================

  Future<void> delete(int id) async {
    await _dio.delete(
      '${ApiConstants.purchaseOrders}/$id',
    );
  }

  // ==========================================
  // PENDING PURCHASE ORDERS
  // ==========================================

  Future<List<PurchaseOrderResponse>>
  getPending() async {
    final response = await _dio.get(
      '${ApiConstants.purchaseOrders}/pending',
    );

    final List data = response.data as List;

    return data
        .map(
          (po) => PurchaseOrderResponse.fromJson(
        po as Map<String, dynamic>,
      ),
    )
        .toList();
  }
}