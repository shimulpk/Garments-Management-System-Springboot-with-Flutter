import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';
import 'package:go_router/go_router.dart';

import '../models/vendor_request.dart';
import '../provider/vendor_provider.dart';

class EditVendorScreen extends ConsumerStatefulWidget {
  const EditVendorScreen({
    super.key,
    required this.vendorId,
  });

  final int vendorId;

  @override
  ConsumerState<EditVendorScreen> createState() =>
      _EditVendorScreenState();
}

class _EditVendorScreenState
    extends ConsumerState<EditVendorScreen> {
  final _formKey = GlobalKey<FormState>();

  final _companyNameController = TextEditingController();
  final _contactPersonController = TextEditingController();
  final _phoneController = TextEditingController();
  final _addressController = TextEditingController();

  bool _saving = false;
  bool _loading = true;

  @override
  void initState() {
    super.initState();
    _loadVendor();
  }

  // ==========================================
  // LOAD VENDOR
  // ==========================================

  Future<void> _loadVendor() async {
    try {
      final vendor = await ref.read(
        vendorByIdProvider(widget.vendorId).future,
      );

      _companyNameController.text =
          vendor.companyName ?? '';

      _contactPersonController.text =
          vendor.contactPerson ?? '';

      _phoneController.text =
          vendor.phone ?? '';

      _addressController.text =
          vendor.address ?? '';

      if (mounted) {
        setState(() {
          _loading = false;
        });
      }
    } catch (e) {
      if (!mounted) return;

      setState(() {
        _loading = false;
      });

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Failed to load vendor: $e',
          ),
        ),
      );
    }
  }

  // ==========================================
  // UPDATE VENDOR
  // ==========================================

  Future<void> _updateVendor() async {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      _saving = true;
    });

    final request = VendorRequest(
      companyName:
      _companyNameController.text.trim(),
      contactPerson:
      _contactPersonController.text.trim(),
      phone:
      _phoneController.text.trim(),
      address:
      _addressController.text.trim(),
    );

    try {
      await ref
          .read(vendorRepositoryProvider)
          .update(
        widget.vendorId,
        request,
      );

      ref.invalidate(allVendorsProvider);

      ref.invalidate(
        vendorByIdProvider(widget.vendorId),
      );

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text(
            'Vendor updated successfully!',
          ),
        ),
      );

      context.go('/procurement/vendors');
    } catch (e) {
      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Failed to update vendor: $e',
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

  // ==========================================
  // BUILD
  // ==========================================

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Edit Vendor'),
      ),

      body: _loading
          ? const Center(
        child: CircularProgressIndicator(),
      )
          : SingleChildScrollView(
        padding: const EdgeInsets.all(20),

        child: Form(
          key: _formKey,

          child: Column(
            crossAxisAlignment:
            CrossAxisAlignment.stretch,

            children: [
              // ==================================
              // COMPANY NAME
              // ==================================

              TextFormField(
                controller:
                _companyNameController,

                decoration:
                const InputDecoration(
                  labelText: 'Company Name',
                  prefixIcon: Icon(
                    Icons.business_outlined,
                  ),
                  border:
                  OutlineInputBorder(),
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

              // ==================================
              // CONTACT PERSON
              // ==================================

              TextFormField(
                controller:
                _contactPersonController,

                decoration:
                const InputDecoration(
                  labelText: 'Contact Person',
                  prefixIcon: Icon(
                    Icons.person_outline,
                  ),
                  border:
                  OutlineInputBorder(),
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

              // ==================================
              // PHONE
              // ==================================

              TextFormField(
                controller: _phoneController,

                keyboardType:
                TextInputType.phone,

                decoration:
                const InputDecoration(
                  labelText: 'Phone',
                  prefixIcon: Icon(
                    Icons.phone_outlined,
                  ),
                  border:
                  OutlineInputBorder(),
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

              // ==================================
              // ADDRESS
              // ==================================

              TextFormField(
                controller:
                _addressController,

                maxLines: 3,

                decoration:
                const InputDecoration(
                  labelText: 'Address',
                  prefixIcon: Icon(
                    Icons.location_on_outlined,
                  ),
                  border:
                  OutlineInputBorder(),
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

              // ==================================
              // UPDATE BUTTON
              // ==================================

              SizedBox(
                height: 50,

                child:
                ElevatedButton.icon(
                  onPressed: _saving
                      ? null
                      : _updateVendor,

                  icon: _saving
                      ? const SizedBox(
                    height: 20,
                    width: 20,
                    child:
                    CircularProgressIndicator(
                      strokeWidth: 2,
                    ),
                  )
                      : const Icon(
                    Icons.save,
                  ),

                  label: Text(
                    _saving
                        ? 'Updating...'
                        : 'Update Vendor',
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