
import 'package:gmsflutter/util/api_client.dart';

import '../models/stock_response.dart';

class StockRepository {
  final ApiClient apiClient;

  StockRepository(this.apiClient);

  Future<List<StockResponse>> getAllStocks() async {
    final response = await apiClient.dio.get('/stocks');

    final List data = response.data;

    return data
        .map(
          (json) => StockResponse.fromJson(json),
    )
        .toList();
  }

  Future<List<StockResponse>> getAvailableStocks() async {
    final response = await apiClient.dio.get('/stocks/available');

    final List data = response.data;

    return data
        .map(
          (json) => StockResponse.fromJson(json),
    )
        .toList();
  }

  Future<StockResponse> getStockById(int id) async {
    final response = await apiClient.dio.get('/stocks/$id');

    return StockResponse.fromJson(response.data);
  }
}