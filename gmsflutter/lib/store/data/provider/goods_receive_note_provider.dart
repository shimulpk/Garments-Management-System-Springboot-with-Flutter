import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';
import 'package:gmsflutter/store/data/repositories/goods_receive_note_repository.dart';

import '../models/goods_receive_note_response.dart';



// ======================================================
// GRN REPOSITORY PROVIDER
// ======================================================

final goodsReceiveNoteRepositoryProvider =
Provider<GoodsReceiveNoteRepository>((ref) {
  return GoodsReceiveNoteRepository(
    ref.watch(apiClientProvider),
  );
});


// ======================================================
// ALL GRNs
// ======================================================

final allGrnsProvider =
FutureProvider.autoDispose<List<GoodsReceiveNoteResponse>>(
      (ref) async {
    return ref
        .watch(goodsReceiveNoteRepositoryProvider)
        .getAll();
  },
);


// ======================================================
// GRN BY ID
// ======================================================

final grnByIdProvider =
FutureProvider.autoDispose
    .family<GoodsReceiveNoteResponse, int>((ref, id) async {
  return ref
      .watch(goodsReceiveNoteRepositoryProvider)
      .getById(id);
});