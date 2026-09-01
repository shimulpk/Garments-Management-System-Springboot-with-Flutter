import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';

import '../provider/vendor_provider.dart';

class VendorDetailsScreen extends ConsumerWidget {
  const VendorDetailsScreen({
    super.key,
    required this.vendorId,
  });

  final int vendorId;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final vendorAsync =
    ref.watch(vendorByIdProvider(vendorId));

    return Scaffold(
      drawer: const AppSidebar(),

      appBar: AppBar(
        title: const Text('Vendor Details'),
      ),

      body: vendorAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Text(
            'Failed to load vendor\n$error',
          ),
        ),

        data: (vendor) {
          return SingleChildScrollView(
            padding: const EdgeInsets.all(16),

            child: Column(
              children: [
                Card(
                  elevation: 3,

                  child: Padding(
                    padding: const EdgeInsets.all(20),

                    child: Column(
                      children: [
                        // =====================================
                        // COMPANY AVATAR
                        // =====================================

                        CircleAvatar(
                          radius: 35,

                          child: Text(
                            vendor.companyName
                                ?.isNotEmpty ==
                                true
                                ? vendor.companyName![0]
                                .toUpperCase()
                                : '?',

                            style: const TextStyle(
                              fontSize: 26,
                              fontWeight:
                              FontWeight.bold,
                            ),
                          ),
                        ),

                        const SizedBox(height: 16),

                        // =====================================
                        // COMPANY NAME
                        // =====================================

                        Text(
                          vendor.companyName ?? '-',

                          textAlign: TextAlign.center,

                          style: const TextStyle(
                            fontSize: 22,
                            fontWeight:
                            FontWeight.bold,
                          ),
                        ),

                        const SizedBox(height: 20),

                        // =====================================
                        // CONTACT PERSON
                        // =====================================

                        _InfoRow(
                          title: 'Contact Person',
                          value:
                          vendor.contactPerson ?? '-',
                          icon:
                          Icons.person_outline,
                        ),

                        // =====================================
                        // PHONE
                        // =====================================

                        _InfoRow(
                          title: 'Phone',
                          value:
                          vendor.phone ?? '-',
                          icon:
                          Icons.phone_outlined,
                        ),

                        // =====================================
                        // ADDRESS
                        // =====================================

                        _InfoRow(
                          title: 'Address',
                          value:
                          vendor.address ?? '-',
                          icon:
                          Icons.location_on_outlined,
                        ),

                        // =====================================
                        // STATUS
                        // =====================================

                        _InfoRow(
                          title: 'Status',
                          value:
                          vendor.active == true
                              ? 'Active'
                              : 'Inactive',

                          icon:
                          vendor.active == true
                              ? Icons.check_circle
                              : Icons.cancel,
                        ),
                      ],
                    ),
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


// ======================================================
// INFO ROW
// ======================================================

class _InfoRow extends StatelessWidget {
  const _InfoRow({
    required this.title,
    required this.value,
    required this.icon,
  });

  final String title;
  final String value;
  final IconData icon;

  @override
  Widget build(BuildContext context) {
    return Container(
      margin:
      const EdgeInsets.only(bottom: 12),

      padding:
      const EdgeInsets.all(12),

      decoration: BoxDecoration(
        borderRadius:
        BorderRadius.circular(10),

        color:
        Colors.grey.shade100,
      ),

      child: Row(
        children: [
          Icon(icon),

          const SizedBox(width: 12),

          Expanded(
            child: Text(
              title,

              style: const TextStyle(
                fontWeight:
                FontWeight.w600,
              ),
            ),
          ),

          Flexible(
            child: Text(
              value,
              textAlign: TextAlign.end,
            ),
          ),
        ],
      ),
    );
  }
}