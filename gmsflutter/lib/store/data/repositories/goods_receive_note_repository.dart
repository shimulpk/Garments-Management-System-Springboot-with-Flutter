import 'package:dio/dio.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/util/api_constants.dart';

import '../models/goods_receive_note_request.dart';
import '../models/goods_receive_note_response.dart';

class GoodsReceiveNoteRepository {
  GoodsReceiveNoteRepository(this._apiClient);

  final ApiClient _apiClient;

  Dio get _dio => _apiClient.dio;

  // Create GRN
  Future<GoodsReceiveNoteResponse> create(
      GoodsReceiveNoteRequest request,
      ) async {
    final response = await _dio.post(
      ApiConstants.grns,
      data: request.toJson(),
    );

    return GoodsReceiveNoteResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  // Get All GRNs
  Future<List<GoodsReceiveNoteResponse>> getAll() async {
    final response = await _dio.get(
      ApiConstants.grns,
    );

    final List data = response.data as List;

    return data
        .map(
          (item) => GoodsReceiveNoteResponse.fromJson(
        item as Map<String, dynamic>,
      ),
    )
        .toList();
  }

  // Get GRN By ID
  Future<GoodsReceiveNoteResponse> getById(
      int id,
      ) async {
    final response = await _dio.get(
      ApiConstants.grnById(id),
    );

    return GoodsReceiveNoteResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }
}