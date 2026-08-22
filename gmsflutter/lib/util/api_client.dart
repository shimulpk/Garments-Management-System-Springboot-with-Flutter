import 'package:dio/dio.dart';
import 'package:gmsflutter/service/storage_service.dart';

import 'api_constants.dart';


class ApiClient {

  final StorageService _storageService;

  late final Dio dio;

  ApiClient(this._storageService) {

    dio = Dio(
      BaseOptions(
        baseUrl: ApiConstants.baseUrl,
        connectTimeout: const Duration(seconds: 20),
        receiveTimeout: const Duration(seconds: 20),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
      ),
    );

    dio.interceptors.add(
      InterceptorsWrapper(

        onRequest: (options, handler) async {

          final token = await _storageService.getToken();

          if (token != null && token.isNotEmpty) {

            options.headers['Authorization'] = 'Bearer $token';
          }

          handler.next(options);
        },

      ),
    );
  }
}


// ─────────────────────────────────────────────
// API Error Message
// ─────────────────────────────────────────────

String apiErrorMessage(Object error) {

  if (error is DioException) {

    final status = error.response?.statusCode;
    final data = error.response?.data;


    // Backend returned plain String
    if (data is String && data.isNotEmpty) {
      return data;
    }


    // Backend returned JSON message
    if (data is Map && data['message'] is String) {
      return data['message'] as String;
    }


    // HTTP Status
    switch (status) {

      case 400:
        return 'Invalid request.';

      case 401:
        return 'Invalid email or password.';

      case 403:
        return 'You do not have permission to access this resource.';

      case 404:
        return 'Requested resource was not found.';

      case 409:
        return 'This data already exists.';

      case 422:
        return 'Invalid data provided.';

      case 500:
        return 'Server error. Please try again later.';

      case null:
        return 'Could not reach the server. Check your connection / API URL.';

      default:
        return 'Something went wrong. Please try again.';
    }
  }

  return 'Something went wrong. Please try again.';
}