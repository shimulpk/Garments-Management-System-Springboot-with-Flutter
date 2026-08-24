import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';


import '../models/store_requisition_response.dart';
import '../repositories/store_requisition_repository.dart';

final storeRequisitionRepositoryProvider =
Provider<StoreRequisitionRepository>((ref) {
  return StoreRequisitionRepository(
    ref.watch(apiClientProvider),
  );
});

final allStoreRequisitionsProvider =
FutureProvider.autoDispose<List<StoreRequisitionResponse>>((ref) async {
  return ref
      .watch(storeRequisitionRepositoryProvider)
      .getAll();
});

final pendingStoreRequisitionsProvider =
FutureProvider.autoDispose<List<StoreRequisitionResponse>>((ref) async {
  return ref
      .watch(storeRequisitionRepositoryProvider)
      .getPending();
});

final approvedStoreRequisitionsProvider =
FutureProvider.autoDispose<List<StoreRequisitionResponse>>((ref) async {
  return ref
      .watch(storeRequisitionRepositoryProvider)
      .getApproved();
});

final storeRequisitionByIdProvider =
FutureProvider.autoDispose
    .family<StoreRequisitionResponse, int>((ref, id) async {
  return ref
      .watch(storeRequisitionRepositoryProvider)
      .getById(id);
});