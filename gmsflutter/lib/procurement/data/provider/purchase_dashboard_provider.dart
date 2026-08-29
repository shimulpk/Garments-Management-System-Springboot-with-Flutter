import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';


import '../models/purchase_dashboard_response.dart';
import '../repository/purchase_dashboard_repository.dart';

final purchaseDashboardRepositoryProvider =
Provider<PurchaseDashboardRepository>((ref) {
  final apiClient = ref.read(apiClientProvider);

  return PurchaseDashboardRepository(apiClient);
});

final purchaseDashboardProvider =
FutureProvider<PurchaseDashboardResponse>((ref) async {
  final repository =
  ref.read(purchaseDashboardRepositoryProvider);

  return repository.getDashboard();
});