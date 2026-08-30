import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';
import 'package:go_router/go_router.dart';

import '../provider/vendor_provider.dart';

class VendorListScreen extends ConsumerStatefulWidget {
  const VendorListScreen({super.key});

  @override
  ConsumerState<VendorListScreen> createState() =>
      _VendorListScreenState();
}

class _VendorListScreenState
    extends ConsumerState<VendorListScreen> {
  final _searchController = TextEditingController();
  String _searchText = '';

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final vendorsAsync = ref.watch(allVendorsProvider);

    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Vendors'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(allVendorsProvider);
            },
          ),
        ],
      ),

      body: vendorsAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Text(
            'Failed to load vendors\n$error',
          ),
        ),

        data: (vendors) {
          final filteredVendors = vendors.where((vendor) {
            final search = _searchText.toLowerCase();

            return (vendor.companyName ?? '')
                .toLowerCase()
                .contains(search) ||
                (vendor.contactPerson ?? '')
                    .toLowerCase()
                    .contains(search) ||
                (vendor.phone ?? '')
                    .toLowerCase()
                    .contains(search) ||
                (vendor.address ?? '')
                    .toLowerCase()
                    .contains(search);
          }).toList();

          return Column(
            children: [
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
                    'Search company, contact, phone or address...',
                    prefixIcon:
                    const Icon(Icons.search),

                    suffixIcon:
                    _searchText.isNotEmpty
                        ? IconButton(
                      icon:
                      const Icon(Icons.clear),
                      onPressed: () {
                        _searchController.clear();

                        setState(() {
                          _searchText = '';
                        });
                      },
                    )
                        : null,

                    border: const OutlineInputBorder(),
                  ),
                ),
              ),

              Expanded(
                child: filteredVendors.isEmpty
                    ? const Center(
                  child: Text(
                    'No vendors found.',
                  ),
                )
                    : ListView.builder(
                  padding:
                  const EdgeInsets.symmetric(
                    horizontal: 12,
                  ),

                  itemCount:
                  filteredVendors.length,

                  itemBuilder:
                      (context, index) {
                    final vendor =
                    filteredVendors[index];

                    return Card(
                      margin:
                      const EdgeInsets.only(
                        bottom: 10,
                      ),

                      child: ListTile(
                        leading: CircleAvatar(
                          child: Text(
                            vendor.companyName
                                ?.isNotEmpty ==
                                true
                                ? vendor.companyName![0]
                                .toUpperCase()
                                : '?',
                          ),
                        ),

                        title: Text(
                          vendor.companyName ??
                              'Unnamed Vendor',
                          style:
                          const TextStyle(
                            fontWeight:
                            FontWeight.bold,
                          ),
                        ),

                        subtitle: Text(
                          '${vendor.contactPerson ?? '-'} • '
                              '${vendor.phone ?? '-'}',
                        ),

                        trailing:
                        PopupMenuButton<String>(
                          onSelected:
                              (value) async {
                            if (value ==
                                'edit') {
                              context.push(
                                '/procurement/vendors/edit/${vendor.id}',
                              );
                            }

                            if (value ==
                                'delete') {
                              _deleteVendor(
                                vendor.id!,
                              );
                            }
                          },

                          itemBuilder:
                              (context) =>
                          const [
                            PopupMenuItem(
                              value: 'edit',
                              child:
                              Text('Edit'),
                            ),
                            PopupMenuItem(
                              value: 'delete',
                              child:
                              Text('Delete'),
                            ),
                          ],
                        ),

                        onTap: () {
                          context.push(
                            '/procurement/vendors/details/${vendor.id}',
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
            '/procurement/vendors/add',
          );
        },

        icon: const Icon(Icons.add),

        label: const Text('Add Vendor'),
      ),
    );
  }

  Future<void> _deleteVendor(int id) async {
    final confirm = await showDialog<bool>(
      context: context,

      builder: (context) {
        return AlertDialog(
          title: const Text('Delete Vendor'),

          content: const Text(
            'Are you sure you want to delete this vendor?',
          ),

          actions: [
            TextButton(
              onPressed: () =>
                  Navigator.pop(context, false),

              child: const Text('Cancel'),
            ),

            ElevatedButton(
              onPressed: () =>
                  Navigator.pop(context, true),

              child: const Text('Delete'),
            ),
          ],
        );
      },
    );

    if (confirm != true) return;

    try {
      await ref
          .read(vendorRepositoryProvider)
          .delete(id);

      ref.invalidate(allVendorsProvider);

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            'Vendor deleted successfully',
          ),
        ),
      );
    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Delete failed: $e',
          ),
        ),
      );
    }
  }
}