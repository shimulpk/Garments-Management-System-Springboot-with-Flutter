import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../provider/stock_provider.dart';

class CurrentStockScreen extends ConsumerStatefulWidget {
  const CurrentStockScreen({super.key});

  @override
  ConsumerState<CurrentStockScreen> createState() =>
      _CurrentStockScreenState();
}

class _CurrentStockScreenState
    extends ConsumerState<CurrentStockScreen> {
  String _search = '';

  @override
  Widget build(BuildContext context) {
    final stocksAsync = ref.watch(allStocksProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Current Stock'),
        actions: [
          IconButton(
            onPressed: () {
              ref.invalidate(allStocksProvider);
            },
            icon: const Icon(Icons.refresh),
          ),
        ],
      ),

      body: stocksAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Padding(
            padding: const EdgeInsets.all(20),
            child: Text(
              'Failed to load stock\n$error',
              textAlign: TextAlign.center,
            ),
          ),
        ),

        data: (stocks) {
          final filteredStocks = stocks.where((stock) {
            final search = _search.toLowerCase();

            return (stock.itemName ?? '')
                .toLowerCase()
                .contains(search) ||
                (stock.category ?? '')
                    .toLowerCase()
                    .contains(search);
          }).toList();

          return RefreshIndicator(
            onRefresh: () async {
              ref.invalidate(allStocksProvider);
              await ref.read(allStocksProvider.future);
            },

            child: Column(
              children: [

                // ==========================
                // SEARCH
                // ==========================

                Padding(
                  padding: const EdgeInsets.all(12),
                  child: TextField(
                    onChanged: (value) {
                      setState(() {
                        _search = value;
                      });
                    },
                    decoration: const InputDecoration(
                      hintText: 'Search item or category...',
                      prefixIcon: Icon(Icons.search),
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),

                // ==========================
                // STOCK COUNT
                // ==========================

                Padding(
                  padding: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 4,
                  ),
                  child: Row(
                    children: [
                      const Icon(
                        Icons.inventory_2_outlined,
                        size: 20,
                      ),
                      const SizedBox(width: 8),
                      Text(
                        '${filteredStocks.length} items',
                        style: const TextStyle(
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ],
                  ),
                ),

                const SizedBox(height: 8),

                // ==========================
                // STOCK LIST
                // ==========================

                Expanded(
                  child: filteredStocks.isEmpty
                      ? ListView(
                    children: const [
                      SizedBox(height: 150),
                      Center(
                        child: Text(
                          'No stock found.',
                        ),
                      ),
                    ],
                  )
                      : ListView.builder(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 12,
                    ),
                    itemCount: filteredStocks.length,
                    itemBuilder: (context, index) {
                      final stock =
                      filteredStocks[index];

                      final quantity =
                          stock.availableQuantity ?? 0;

                      final isLowStock =
                          quantity <= 10;

                      return Card(
                        margin: const EdgeInsets.only(
                          bottom: 10,
                        ),

                        child: ListTile(
                          leading: CircleAvatar(
                            child: const Icon(
                              Icons.inventory_2_outlined,
                            ),
                          ),

                          title: Text(
                            stock.itemName ?? 'N/A',
                            style: const TextStyle(
                              fontWeight:
                              FontWeight.bold,
                            ),
                          ),

                          subtitle: Text(
                            '${stock.category ?? '-'} • '
                                '${stock.unit ?? '-'}',
                          ),

                          trailing: Column(
                            mainAxisAlignment:
                            MainAxisAlignment.center,
                            crossAxisAlignment:
                            CrossAxisAlignment.end,
                            children: [
                              Text(
                                '$quantity',
                                style: TextStyle(
                                  fontSize: 18,
                                  fontWeight:
                                  FontWeight.bold,
                                  color: isLowStock
                                      ? Colors.orange
                                      : Colors.green,
                                ),
                              ),

                              Text(
                                stock.unit ?? '',
                                style: const TextStyle(
                                  fontSize: 12,
                                ),
                              ),
                            ],
                          ),

                          // onTap: () {
                          //   if (stock.id != null) {
                          //     context.push(
                          //       '/store/stock/details/${stock.id}',
                          //     );
                          //   }
                          // },
                        ),
                      );
                    },
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}