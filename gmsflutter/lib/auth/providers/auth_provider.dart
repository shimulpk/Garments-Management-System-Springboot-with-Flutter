import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_riverpod/legacy.dart';
import 'package:gmsflutter/auth/data/models/login_request.dart';
import 'package:gmsflutter/auth/providers/providers.dart';

import '../data/models/login_response.dart';
import '../data/repositories/auth_repository.dart';


final authRepositoryProvider = Provider<AuthRepository>((ref) {
  return AuthRepository(
    ref.watch(apiClientProvider),
    ref.watch(storageServiceProvider),
  );
});


final authControllerProvider =
StateNotifierProvider<AuthController, AsyncValue<LoginResponse?>>(
      (ref) {
    return AuthController(
      ref.watch(authRepositoryProvider),
    );
  },
);


class AuthController
    extends StateNotifier<AsyncValue<LoginResponse?>> {

  AuthController(this._repository)
      : super(const AsyncValue.data(null)) {
    _restoreSession();
  }

  final AuthRepository _repository;


  Future<void> _restoreSession() async {

    try {

      final user = await _repository.getUser();

      if (user != null) {

        state = AsyncValue.data(user);

      } else {

        state = const AsyncValue.data(null);
      }

    } catch (e, st) {

      state = AsyncValue.error(e, st);
    }
  }


  Future<void> login({
    required String email,
    required String password,
  }) async {

    state = const AsyncValue.loading();

    try {

      final response = await _repository.login(
        LoginRequest(
          email: email,
          password: password,
        ),
      );

      state = AsyncValue.data(response);

    } catch (e, st) {

      state = AsyncValue.error(e, st);
    }
  }


  Future<void> logout() async {

    await _repository.logout();

    state = const AsyncValue.data(null);
  }
}