import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';
import 'package:gmsflutter/store/data/models/goods_receive_note_response.dart';
import 'package:gmsflutter/store/data/repositories/goods_receive_note_repository.dart';

final goodsReceiveNoteRepositoryProvider =
Provider<GoodsReceiveNoteRepository>((ref) {
  return GoodsReceiveNoteRepository(
    ref.read(apiClientProvider),
  );
});

// All GRNs
final allGrnsProvider =
FutureProvider<List<GoodsReceiveNoteResponse>>((ref) async {
  return ref
      .read(goodsReceiveNoteRepositoryProvider)
      .getAll();
});

// GRN by ID
final grnByIdProvider = FutureProvider.family<
    GoodsReceiveNoteResponse, int>((ref, id) async {
  return ref
      .read(goodsReceiveNoteRepositoryProvider)
      .getById(id);
});