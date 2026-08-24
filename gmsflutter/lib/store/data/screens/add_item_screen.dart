import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../models/item_request.dart';
import '../provider/store_provider.dart';

class AddItemScreen extends ConsumerStatefulWidget {
  const AddItemScreen({super.key});

  @override
  ConsumerState<AddItemScreen> createState() => _AddItemScreenState();
}

class _AddItemScreenState extends ConsumerState<AddItemScreen> {
  final _formKey = GlobalKey<FormState>();

  final _itemNameController = TextEditingController();
  final _categoryController = TextEditingController();
  final _unitController = TextEditingController();

  bool _saving = false;

  Future<void> _saveItem() async {
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
      await ref.read(storeRepositoryProvider).createItem(request);

      ref.invalidate(allItemsProvider);
      ref.invalidate(storeDashboardProvider);

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Item created successfully!'),
        ),
      );

      Navigator.of(context).pop();
    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Failed to create item: $e'),
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Add Item'),
      ),
      body: SingleChildScrollView(
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
                  hintText: 'e.g. PCS, KG, METER',
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
                  onPressed: _saving ? null : _saveItem,
                  icon: _saving
                      ? const SizedBox(
                    height: 20,
                    width: 20,
                    child: CircularProgressIndicator(
                      strokeWidth: 2,
                    ),
                  )
                      : const Icon(Icons.save),
                  label: Text(
                    _saving ? 'Saving...' : 'Save Item',
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}