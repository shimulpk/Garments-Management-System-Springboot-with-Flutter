import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

import '../models/vendor_request.dart';
import '../provider/vendor_provider.dart';

class AddVendorScreen extends ConsumerStatefulWidget {
  const AddVendorScreen({super.key});

  @override
  ConsumerState<AddVendorScreen> createState() =>
      _AddVendorScreenState();
}

class _AddVendorScreenState
    extends ConsumerState<AddVendorScreen> {
  final _formKey = GlobalKey<FormState>();

  final _companyNameController = TextEditingController();
  final _contactPersonController = TextEditingController();
  final _phoneController = TextEditingController();
  final _addressController = TextEditingController();

  bool _saving = false;

  Future<void> _saveVendor() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() {
      _saving = true;
    });

    final request = VendorRequest(
      companyName: _companyNameController.text.trim(),
      contactPerson: _contactPersonController.text.trim(),
      phone: _phoneController.text.trim(),
      address: _addressController.text.trim(),
    );

    try {
      await ref
          .read(vendorRepositoryProvider)
          .create(request);

      ref.invalidate(allVendorsProvider);

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Vendor created successfully!'),
        ),
      );

      Navigator.of(context).pop();
    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Failed to create vendor: $e',
          ),
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
    _companyNameController.dispose();
    _contactPersonController.dispose();
    _phoneController.dispose();
    _addressController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Add Vendor'),
      ),

      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),

        child: Form(
          key: _formKey,

          child: Column(
            crossAxisAlignment:
            CrossAxisAlignment.stretch,

            children: [

              // Company Name

              TextFormField(
                controller: _companyNameController,

                decoration: const InputDecoration(
                  labelText: 'Company Name',
                  prefixIcon:
                  Icon(Icons.business_outlined),
                  border: OutlineInputBorder(),
                ),

                validator: (value) {
                  if (value == null ||
                      value.trim().isEmpty) {
                    return 'Company name is required';
                  }

                  return null;
                },
              ),

              const SizedBox(height: 16),

              // Contact Person

              TextFormField(
                controller:
                _contactPersonController,

                decoration: const InputDecoration(
                  labelText: 'Contact Person',
                  prefixIcon:
                  Icon(Icons.person_outline),
                  border: OutlineInputBorder(),
                ),

                validator: (value) {
                  if (value == null ||
                      value.trim().isEmpty) {
                    return 'Contact person is required';
                  }

                  return null;
                },
              ),

              const SizedBox(height: 16),

              // Phone

              TextFormField(
                controller: _phoneController,

                keyboardType:
                TextInputType.phone,

                decoration: const InputDecoration(
                  labelText: 'Phone',
                  prefixIcon:
                  Icon(Icons.phone_outlined),
                  border: OutlineInputBorder(),
                ),

                validator: (value) {
                  if (value == null ||
                      value.trim().isEmpty) {
                    return 'Phone is required';
                  }

                  return null;
                },
              ),

              const SizedBox(height: 16),

              // Address

              TextFormField(
                controller: _addressController,

                maxLines: 3,

                decoration: const InputDecoration(
                  labelText: 'Address',
                  prefixIcon:
                  Icon(Icons.location_on_outlined),
                  border: OutlineInputBorder(),
                  alignLabelWithHint: true,
                ),

                validator: (value) {
                  if (value == null ||
                      value.trim().isEmpty) {
                    return 'Address is required';
                  }

                  return null;
                },
              ),

              const SizedBox(height: 28),

              // Save Button

              SizedBox(
                height: 50,

                child: ElevatedButton.icon(
                  onPressed:
                  _saving ? null : _saveVendor,

                  icon: _saving
                      ? const SizedBox(
                    height: 20,
                    width: 20,
                    child:
                    CircularProgressIndicator(
                      strokeWidth: 2,
                    ),
                  )
                      : const Icon(Icons.save),

                  label: Text(
                    _saving
                        ? 'Saving...'
                        : 'Save Vendor',
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