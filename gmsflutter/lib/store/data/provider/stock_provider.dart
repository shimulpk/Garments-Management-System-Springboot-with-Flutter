import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';
import 'package:gmsflutter/store/data/repositories/stock_repository.dart';


import '../models/stock_response.dart';


final stockRepositoryProvider = Provider<StockRepository>((ref) {
  final apiClient = ref.read(apiClientProvider);

  return StockRepository(apiClient);
});

final allStocksProvider =
FutureProvider<List<StockResponse>>((ref) async {
  final repository = ref.read(stockRepositoryProvider);

  return repository.getAllStocks();
});

final availableStocksProvider =
FutureProvider<List<StockResponse>>((ref) async {
  final repository = ref.read(stockRepositoryProvider);

  return repository.getAvailableStocks();
});