import 'dart:convert';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:gmsflutter/auth/data/models/login_response.dart';

class StorageKeys {
  StorageKeys._();


  // Authentication


  static const String token = 'gms_token';
  static const String user = 'gms_user';
}


class StorageService {

  StorageService(this._storage);

  final FlutterSecureStorage _storage;



  // Write


  Future<void> saveSession(LoginResponse data) async {

    await _storage.write(
      key: StorageKeys.token,
      value: data.token,
    );

    await _storage.write(
      key: StorageKeys.user,
      value: jsonEncode(data.toJson()),
    );
  }



  // Read

  Future<String?> getToken() {
    return _storage.read(
      key: StorageKeys.token,
    );
  }


  Future<LoginResponse?> getUser() async {

    final raw = await _storage.read(
      key: StorageKeys.user,
    );

    if (raw == null) {
      return null;
    }

    try {

      return LoginResponse.fromJson(
        jsonDecode(raw) as Map<String, dynamic>,
      );

    } catch (_) {

      return null;

    }
  }


  Future<String?> getRole() async {

    return (await getUser())?.role;
  }


  Future<bool> isLoggedIn() async {

    final token = await getToken();

    return token != null && token.isNotEmpty;
  }



  // Clear Session

  Future<void> clearSession() async {

    await _storage.delete(
      key: StorageKeys.token,
    );

    await _storage.delete(
      key: StorageKeys.user,
    );
  }



  // Generic Data

  Future<void> saveData(
      String key,
      Map<String, dynamic> data,
      ) {

    return _storage.write(
      key: key,
      value: jsonEncode(data),
    );
  }


  Future<Map<String, dynamic>?> getData(
      String key,
      ) async {

    final raw = await _storage.read(
      key: key,
    );

    if (raw == null) {
      return null;
    }

    try {

      return jsonDecode(raw) as Map<String, dynamic>;

    } catch (_) {

      return null;

    }
  }


  Future<void> removeData(String key) {

    return _storage.delete(
      key: key,
    );
  }
}