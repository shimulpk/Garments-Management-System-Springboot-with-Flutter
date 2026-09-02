import 'package:dio/dio.dart';
import 'package:gmsflutter/util/api_client.dart';
import 'package:gmsflutter/util/api_constants.dart';

import '../models/goods_receive_note_response.dart';

class GoodsReceiveNoteRepository {
  GoodsReceiveNoteRepository(this._apiClient);

  final ApiClient _apiClient;

  Dio get _dio => _apiClient.dio;


  // ======================================================
  // CREATE GRN
  // ======================================================

  Future<GoodsReceiveNoteResponse> create(
      Map<String, dynamic> request) async {
    final response = await _dio.post(
      ApiConstants.grns,
      data: request,
    );

    return GoodsReceiveNoteResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }


  // ======================================================
  // GET ALL GRNs
  // ======================================================

  Future<List<GoodsReceiveNoteResponse>> getAll() async {
    final response = await _dio.get(
      ApiConstants.grns,
    );

    final List data = response.data as List;

    return data
        .map(
          (e) => GoodsReceiveNoteResponse.fromJson(
        e as Map<String, dynamic>,
      ),
    )
        .toList();
  }


  // ======================================================
  // GET GRN BY ID
  // ======================================================

  Future<GoodsReceiveNoteResponse> getById(
      int id) async {
    final response = await _dio.get(
      '${ApiConstants.grns}/$id',
    );

    return GoodsReceiveNoteResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }
}