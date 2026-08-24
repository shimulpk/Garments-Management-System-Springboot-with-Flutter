import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';
import 'package:gmsflutter/store/data/models/item_response.dart';
import 'package:gmsflutter/store/data/models/store_dashboard_response.dart';
import 'package:gmsflutter/store/data/repositories/store_repository.dart';

final storeRepositoryProvider = Provider<StoreRepository>((ref) {
  return StoreRepository(ref.watch(apiClientProvider));
});

final storeDashboardProvider =
FutureProvider.autoDispose<StoreDashboardResponse>((ref) async {
  return ref.watch(storeRepositoryProvider).getDashboard();
});


// Item Providers
final allItemsProvider =
FutureProvider.autoDispose<List<ItemResponse>>((ref) async {
  return ref.watch(storeRepositoryProvider).getAllItems();
});

final itemByIdProvider = FutureProvider.autoDispose
    .family<ItemResponse, int>((ref, id) async {
  return ref.watch(storeRepositoryProvider).getItemById(id);
});