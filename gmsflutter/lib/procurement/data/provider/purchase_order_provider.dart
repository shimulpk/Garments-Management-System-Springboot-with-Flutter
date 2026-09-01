import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';
import 'package:gmsflutter/procurement/data/repository/purchase_order_repository.dart';

import '../models/purchase_order_response.dart';


// ======================================================
// PURCHASE ORDER REPOSITORY PROVIDER
// ======================================================

final purchaseOrderRepositoryProvider =
Provider<PurchaseOrderRepository>((ref) {
  return PurchaseOrderRepository(
    ref.watch(apiClientProvider),
  );
});


// ======================================================
// ALL PURCHASE ORDERS PROVIDER
// ======================================================

final allPurchaseOrdersProvider =
FutureProvider.autoDispose<
    List<PurchaseOrderResponse>>((ref) async {
  return ref
      .watch(purchaseOrderRepositoryProvider)
      .getAll();
});


// ======================================================
// PURCHASE ORDER BY ID PROVIDER
// ======================================================

final purchaseOrderByIdProvider =
FutureProvider.autoDispose
    .family<PurchaseOrderResponse, int>((ref, id) async {
  return ref
      .watch(purchaseOrderRepositoryProvider)
      .getById(id);
});


// ======================================================
// PENDING PURCHASE ORDERS PROVIDER
// ======================================================

final pendingPurchaseOrdersProvider =
FutureProvider.autoDispose<
    List<PurchaseOrderResponse>>((ref) async {
  return ref
      .watch(purchaseOrderRepositoryProvider)
      .getPending();
});