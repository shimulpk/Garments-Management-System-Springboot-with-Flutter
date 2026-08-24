import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../models/item_request.dart';
import '../provider/store_provider.dart';

class EditItemScreen extends ConsumerStatefulWidget {
  const EditItemScreen({
    super.key,
    required this.itemId,
  });

  final int itemId;

  @override
  ConsumerState<EditItemScreen> createState() => _EditItemScreenState();
}

class _EditItemScreenState extends ConsumerState<EditItemScreen> {
  final _formKey = GlobalKey<FormState>();

  final _itemNameController = TextEditingController();
  final _categoryController = TextEditingController();
  final _unitController = TextEditingController();

  bool _saving = false;
  bool _loaded = false;

  @override
  Widget build(BuildContext context) {
    final itemAsync = ref.watch(itemByIdProvider(widget.itemId));

    return Scaffold(
      appBar: AppBar(
        title: const Text('Edit Item'),
      ),
      body: itemAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),
        error: (error, stack) => Center(
          child: Text('Failed to load item\n$error'),
        ),
        data: (item) {
          if (!_loaded) {
            _itemNameController.text = item.itemName ?? '';
            _categoryController.text = item.category ?? '';
            _unitController.text = item.unit ?? '';
            _loaded = true;
          }

          return SingleChildScrollView(
            padding: const EdgeInsets.all(20),
            child: Form(
              key: _formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  TextFormField(
                    controller: _itemNameController,
                    decoration: const InputDecoration(
                      labelText: 'Item Name',
                      prefixIcon: Icon(Icons.inventory_2_outlined),
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.trim().isEmpty) {
                        return 'Item name is required';
                      }
                      return null;
                    },
                  ),

                  const SizedBox(height: 16),

                  TextFormField(
                    controller: _categoryController,
                    decoration: const InputDecoration(
                      labelText: 'Category',
                      prefixIcon: Icon(Icons.category_outlined),
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.trim().isEmpty) {
                        return 'Category is required';
                      }
                      return null;
                    },
                  ),

                  const SizedBox(height: 16),

                  TextFormField(
                    controller: _unitController,
                    decoration: const InputDecoration(
                      labelText: 'Unit',
                      prefixIcon: Icon(Icons.straighten_outlined),
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.trim().isEmpty) {
                        return 'Unit is required';
                      }
                      return null;
                    },
                  ),

                  const SizedBox(height: 28),

                  SizedBox(
                    height: 50,
                    child: ElevatedButton.icon(
                      onPressed: _saving ? null : _updateItem,
                      icon: _saving
                          ? const SizedBox(
                        height: 20,
                        width: 20,
                        child: CircularProgressIndicator(
                          strokeWidth: 2,
                        ),
                      )
                          : const Icon(Icons.update),
                      label: Text(
                        _saving ? 'Updating...' : 'Update Item',
                      ),
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  Future<void> _updateItem() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() {
      _saving = true;
    });

    final request = ItemRequest(
      itemName: _itemNameController.text.trim(),
      category: _categoryController.text.trim(),
      unit: _unitController.text.trim(),
    );

    try {
      await ref
          .read(storeRepositoryProvider)
          .updateItem(widget.itemId, request);

      ref.invalidate(allItemsProvider);
      ref.invalidate(itemByIdProvider(widget.itemId));
      ref.invalidate(storeDashboardProvider);

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Item updated successfully!'),
        ),
      );

      Navigator.of(context).pop();
    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Update failed: $e'),
        ),
      );
    } finally {
      if (mounted) {
        setState(() {
          _saving = false;
        });
      }
    }
  }

  @override
  void dispose() {
    _itemNameController.dispose();
    _categoryController.dispose();
    _unitController.dispose();
    super.dispose();
  }
}