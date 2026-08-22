import 'package:dio/dio.dart';
import 'package:gmsflutter/service/storage_service.dart';
import 'package:gmsflutter/util/api_constants.dart';


import '../../../util/api_client.dart';

import '../models/login_request.dart';
import '../models/login_response.dart';

class AuthRepository {

  AuthRepository(
      this._apiClient,
      this._storageService,
      );

  final ApiClient _apiClient;
  final StorageService _storageService;

  Dio get _dio => _apiClient.dio;

  Future<LoginResponse> login(LoginRequest data) async {

    final response = await _dio.post(
      ApiConstants.login,
      data: data.toJson(),
    );

    final loginResponse = LoginResponse.fromJson(
      response.data as Map<String, dynamic>,
    );

    await _storageService.saveSession(loginResponse);

    return loginResponse;
  }

  Future<void> logout() async {

    await _storageService.clearSession();
  }

  Future<LoginResponse?> getUser() async {

    return await _storageService.getUser();
  }
}