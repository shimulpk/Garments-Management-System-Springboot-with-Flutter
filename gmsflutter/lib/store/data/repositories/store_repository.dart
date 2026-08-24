import 'package:dio/dio.dart';
import 'package:gmsflutter/store/data/models/item_request.dart';
import 'package:gmsflutter/store/data/models/item_response.dart';
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

// inventory item
Future<List<ItemResponse>> getAllItems() async{
    final response = await _dio.get(
      ApiConstants.items,
    );

    final List data= response.data as List;

   return data.map(
       (item)=> ItemResponse.fromJson(
         item as Map<String,dynamic>,
       ),
   ).toList();
}

  Future<ItemResponse> getItemById(int id) async {
    final response = await _dio.get(
      ApiConstants.itemById(id),
    );

    return ItemResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  Future<ItemResponse> createItem(ItemRequest request) async {
    final response = await _dio.post(
      ApiConstants.items,
      data: request.toJson(),
    );

    return ItemResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  Future<ItemResponse> updateItem(
      int id,
      ItemRequest request,
      ) async {
    final response = await _dio.put(
      ApiConstants.itemById(id),
      data: request.toJson(),
    );

    return ItemResponse.fromJson(
      response.data as Map<String, dynamic>,
    );
  }

  Future<void> deleteItem(int id) async {
    await _dio.delete(
      ApiConstants.itemById(id),
    );
  }


}