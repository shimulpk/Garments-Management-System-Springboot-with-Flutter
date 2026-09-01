import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';
import 'package:go_router/go_router.dart';

import '../provider/purchase_order_provider.dart';

class PurchaseOrderListScreen extends ConsumerStatefulWidget {
  const PurchaseOrderListScreen({super.key});

  @override
  ConsumerState<PurchaseOrderListScreen> createState() =>
      _PurchaseOrderListScreenState();
}

class _PurchaseOrderListScreenState
    extends ConsumerState<PurchaseOrderListScreen> {
  final _searchController = TextEditingController();

  String _searchText = '';

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final purchaseOrdersAsync =
    ref.watch(allPurchaseOrdersProvider);

    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Purchase Orders'),

        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(
                allPurchaseOrdersProvider,
              );
            },
          ),
        ],
      ),

      body: purchaseOrdersAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Text(
            'Failed to load purchase orders\n$error',
            textAlign: TextAlign.center,
          ),
        ),

        data: (purchaseOrders) {
          final filteredOrders =
          purchaseOrders.where((po) {
            final search =
            _searchText.toLowerCase();

            return (po.poNo ?? '')
                .toLowerCase()
                .contains(search) ||
                (po.vendorName ?? '')
                    .toLowerCase()
                    .contains(search) ||
                (po.requisitionNo ?? '')
                    .toLowerCase()
                    .contains(search) ||
                (po.status ?? '')
                    .toLowerCase()
                    .contains(search);
          }).toList();

          return Column(
            children: [
              // =====================================
              // SEARCH
              // =====================================

              Padding(
                padding: const EdgeInsets.all(12),

                child: TextField(
                  controller: _searchController,

                  onChanged: (value) {
                    setState(() {
                      _searchText = value;
                    });
                  },

                  decoration: InputDecoration(
                    hintText:
                    'Search PO, vendor, requisition or status...',

                    prefixIcon:
                    const Icon(Icons.search),

                    suffixIcon:
                    _searchText.isNotEmpty
                        ? IconButton(
                      icon:
                      const Icon(Icons.clear),

                      onPressed: () {
                        _searchController
                            .clear();

                        setState(() {
                          _searchText = '';
                        });
                      },
                    )
                        : null,

                    border:
                    const OutlineInputBorder(),
                  ),
                ),
              ),

              // =====================================
              // LIST
              // =====================================

              Expanded(
                child: filteredOrders.isEmpty
                    ? const Center(
                  child: Text(
                    'No purchase orders found.',
                  ),
                )
                    : ListView.builder(
                  padding:
                  const EdgeInsets.symmetric(
                    horizontal: 12,
                  ),

                  itemCount:
                  filteredOrders.length,

                  itemBuilder:
                      (context, index) {
                    final po =
                    filteredOrders[index];

                    return Card(
                      margin:
                      const EdgeInsets.only(
                        bottom: 10,
                      ),

                      child: ListTile(
                        leading:
                        const CircleAvatar(
                          child: Icon(
                            Icons
                                .shopping_cart_outlined,
                          ),
                        ),

                        title: Text(
                          po.poNo ?? 'PO',
                          style:
                          const TextStyle(
                            fontWeight:
                            FontWeight.bold,
                          ),
                        ),

                        subtitle: Text(
                          '${po.vendorName ?? '-'}\n'
                              'Requisition: '
                              '${po.requisitionNo ?? '-'}\n'
                              'Date: '
                              '${po.poDate ?? '-'}',
                        ),

                        isThreeLine: true,

                        trailing: Column(
                          mainAxisSize: MainAxisSize.min,
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            Text(
                              '${po.grandTotal ?? 0}',
                              style: const TextStyle(
                                fontWeight: FontWeight.bold,
                              ),
                            ),

                            const SizedBox(height: 2),

                            Chip(
                              label: Text(
                                po.status ?? '-',
                                style: const TextStyle(
                                  fontSize: 12,
                                ),
                              ),
                              padding: const EdgeInsets.symmetric(
                                horizontal: 6,
                                vertical: 0,
                              ),
                              visualDensity: VisualDensity.compact,
                              materialTapTargetSize:
                              MaterialTapTargetSize.shrinkWrap,
                            ),
                          ],
                        ),

                        onTap: () {
                          if (po.id == null) {
                            return;
                          }

                          context.push(
                            '/procurement/purchase-orders/details/${po.id}',
                          );
                        },
                      ),
                    );
                  },
                ),
              ),
            ],
          );
        },
      ),

      floatingActionButton:
      FloatingActionButton.extended(
        onPressed: () {
          context.push(
            '/procurement/purchase-orders/add',
          );
        },

        icon: const Icon(
          Icons.add_shopping_cart_outlined,
        ),

        label: const Text(
          'Create PO',
        ),
      ),
    );
  }
}