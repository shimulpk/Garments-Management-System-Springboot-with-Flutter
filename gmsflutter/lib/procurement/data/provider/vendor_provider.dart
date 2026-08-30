import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';
import 'package:gmsflutter/procurement/data/repository/vendor_repository.dart';

import '../models/vendor_response.dart';



// ======================================================
// VENDOR REPOSITORY PROVIDER
// ======================================================

final vendorRepositoryProvider =
Provider<VendorRepository>((ref) {
  return VendorRepository(
    ref.watch(apiClientProvider),
  );
});


// ======================================================
// ALL VENDORS PROVIDER
// ======================================================

final allVendorsProvider =
FutureProvider.autoDispose<List<VendorResponse>>((ref) async {
  return ref
      .watch(vendorRepositoryProvider)
      .getAll();
});


// ======================================================
// VENDOR BY ID PROVIDER
// ======================================================

final vendorByIdProvider =
FutureProvider.autoDispose
    .family<VendorResponse, int>((ref, id) async {
  return ref
      .watch(vendorRepositoryProvider)
      .getById(id);
});