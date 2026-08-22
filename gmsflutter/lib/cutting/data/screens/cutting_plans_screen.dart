import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../models/cutting_plan_response.dart';
import '../provider/cutting_provider.dart';
import 'add_day_wise_production_screen.dart';

class CuttingPlansScreen extends ConsumerStatefulWidget {
  const CuttingPlansScreen({super.key});

  @override
  ConsumerState<CuttingPlansScreen> createState() =>
      _CuttingPlansScreenState();
}

class _CuttingPlansScreenState
    extends ConsumerState<CuttingPlansScreen> {

  final TextEditingController _searchController =
  TextEditingController();

  String _searchText = '';

  @override
  void initState() {
    super.initState();

    _searchController.addListener(() {
      setState(() {
        _searchText =
            _searchController.text.trim().toLowerCase();
      });
    });
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  List<CuttingPlanResponse> _filterPlans(
      List<CuttingPlanResponse> plans,
      ) {
    if (_searchText.isEmpty) {
      return plans;
    }

    return plans.where((plan) {
      final planId =
      (plan.cuttingPlanId ?? '').toLowerCase();

      final poNumber =
      (plan.poNumber ?? '').toLowerCase();

      final buyer =
      (plan.buyerName ?? '').toLowerCase();

      final style =
      (plan.styleNo ?? '').toLowerCase();

      return planId.contains(_searchText) ||
          poNumber.contains(_searchText) ||
          buyer.contains(_searchText) ||
          style.contains(_searchText);
    }).toList();
  }

  @override
  Widget build(BuildContext context) {
    final plansAsync = ref.watch(allCuttingPlansProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Cutting Plans'),

        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              ref.invalidate(allCuttingPlansProvider);
            },
          ),
        ],
      ),

      body: plansAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Icon(
                Icons.error_outline,
                size: 50,
              ),

              const SizedBox(height: 12),

              const Text(
                'Failed to load cutting plans',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 16),

              ElevatedButton(
                onPressed: () {
                  ref.invalidate(
                    allCuttingPlansProvider,
                  );
                },
                child: const Text('Retry'),
              ),
            ],
          ),
        ),

        data: (plans) {
          final filteredPlans =
          _filterPlans(plans);

          return RefreshIndicator(
            onRefresh: () async {
              ref.invalidate(
                allCuttingPlansProvider,
              );

              await ref.read(
                allCuttingPlansProvider.future,
              );
            },

            child: ListView(
              padding: const EdgeInsets.all(16),

              children: [

                // ─────────────────────────────
                // Search Box
                // ─────────────────────────────

                TextField(
                  controller: _searchController,

                  decoration: InputDecoration(
                    hintText:
                    'Search by buyer, style, plan or PO',

                    prefixIcon:
                    const Icon(Icons.search),

                    suffixIcon:
                    _searchText.isNotEmpty
                        ? IconButton(
                      icon:
                      const Icon(Icons.clear),
                      onPressed: () {
                        _searchController.clear();
                      },
                    )
                        : null,

                    border: OutlineInputBorder(
                      borderRadius:
                      BorderRadius.circular(12),
                    ),

                    filled: true,
                  ),
                ),

                const SizedBox(height: 16),

                // Search result count

                Text(
                  '${filteredPlans.length} plan(s) found',

                  style: TextStyle(
                    color: Colors.grey.shade600,
                    fontWeight: FontWeight.w500,
                  ),
                ),

                const SizedBox(height: 12),

                if (filteredPlans.isEmpty)
                  const Padding(
                    padding: EdgeInsets.only(top: 80),

                    child: Center(
                      child: Column(
                        children: [
                          Icon(
                            Icons.search_off,
                            size: 55,
                          ),

                          SizedBox(height: 12),

                          Text(
                            'No matching cutting plans found.',
                            style: TextStyle(
                              fontSize: 16,
                            ),
                          ),
                        ],
                      ),
                    ),
                  )

                else

                  ...filteredPlans.map(
                        (plan) => _CuttingPlanCard(
                      plan: plan,

                      onAddProduction: () {
                        Navigator.of(context).push(
                          MaterialPageRoute(
                            builder: (_) =>
                            const AddDayWiseProductionScreen(),
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


// ─────────────────────────────────────────────
// Cutting Plan Card
// ─────────────────────────────────────────────

class _CuttingPlanCard extends StatelessWidget {
  const _CuttingPlanCard({
    required this.plan,
    required this.onAddProduction,
  });

  final CuttingPlanResponse plan;
  final VoidCallback onAddProduction;

  @override
  Widget build(BuildContext context) {
    final progress =
    ((plan.progress ?? 0) / 100).clamp(0.0, 1.0);

    return Card(
      margin: const EdgeInsets.only(bottom: 16),

      elevation: 3,

      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(16),
      ),

      child: Padding(
        padding: const EdgeInsets.all(16),

        child: Column(
          crossAxisAlignment:
          CrossAxisAlignment.start,

          children: [

            // ─────────────────────────
            // Plan + Status
            // ─────────────────────────

            Row(
              mainAxisAlignment:
              MainAxisAlignment.spaceBetween,

              children: [
                Expanded(
                  child: Text(
                    'Plan: ${plan.cuttingPlanId ?? 'N/A'}',

                    style: const TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),

                _StatusChip(
                  status:
                  plan.status ?? 'PENDING',
                ),
              ],
            ),

            const SizedBox(height: 14),

            _InfoRow(
              icon: Icons.style_outlined,
              label: 'Style',
              value:
              plan.styleNo ?? 'N/A',
            ),

            const SizedBox(height: 8),

            _InfoRow(
              icon: Icons.person_outline,
              label: 'Buyer',
              value:
              plan.buyerName ?? 'N/A',
            ),

            const SizedBox(height: 8),

            _InfoRow(
              icon:
              Icons.receipt_long_outlined,
              label: 'PO',
              value:
              plan.poNumber ?? 'N/A',
            ),

            const SizedBox(height: 18),

            const Text(
              'Production Progress',

              style: TextStyle(
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 8),

            LinearProgressIndicator(
              value: progress,
              minHeight: 8,

              borderRadius:
              BorderRadius.circular(10),
            ),

            const SizedBox(height: 14),

            Row(
              children: [

                Expanded(
                  child: _ProductionValue(
                    title: 'Planned',
                    value:
                    '${plan.plannedPieces ?? 0}',
                  ),
                ),

                Expanded(
                  child: _ProductionValue(
                    title: 'Cut',
                    value:
                    '${plan.actualCutPieces ?? 0}',
                  ),
                ),

                Expanded(
                  child: _ProductionValue(
                    title: 'Reject',
                    value:
                    '${plan.rejectedPieces ?? 0}',
                  ),
                ),

                Expanded(
                  child: _ProductionValue(
                    title: 'Remaining',
                    value:
                    '${plan.remainingPieces ?? 0}',
                  ),
                ),
              ],
            ),

            const SizedBox(height: 16),

            const Divider(),

            Align(
              alignment:
              Alignment.centerRight,

              child: OutlinedButton.icon(
                onPressed:
                onAddProduction,

                icon: const Icon(
                  Icons.add,
                ),

                label: const Text(
                  'Add Production',
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}


// ─────────────────────────────────────────────
// Info Row
// ─────────────────────────────────────────────

class _InfoRow extends StatelessWidget {
  const _InfoRow({
    required this.icon,
    required this.label,
    required this.value,
  });

  final IconData icon;
  final String label;
  final String value;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [

        Icon(
          icon,
          size: 20,
          color: Colors.grey.shade700,
        ),

        const SizedBox(width: 10),

        SizedBox(
          width: 65,

          child: Text(
            label,

            style: TextStyle(
              color: Colors.grey.shade600,
            ),
          ),
        ),

        Expanded(
          child: Text(
            value,

            style: const TextStyle(
              fontWeight: FontWeight.w600,
            ),
          ),
        ),
      ],
    );
  }
}


// ─────────────────────────────────────────────
// Production Value
// ─────────────────────────────────────────────

class _ProductionValue extends StatelessWidget {
  const _ProductionValue({
    required this.title,
    required this.value,
  });

  final String title;
  final String value;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [

        Text(
          value,

          style: const TextStyle(
            fontSize: 19,
            fontWeight: FontWeight.bold,
          ),
        ),

        const SizedBox(height: 4),

        Text(
          title,

          textAlign: TextAlign.center,

          style: TextStyle(
            fontSize: 11,
            color: Colors.grey.shade600,
          ),
        ),
      ],
    );
  }
}


// ─────────────────────────────────────────────
// Status Chip
// ─────────────────────────────────────────────

class _StatusChip extends StatelessWidget {
  const _StatusChip({
    required this.status,
  });

  final String status;

  @override
  Widget build(BuildContext context) {
    final isCompleted =
        status.toUpperCase() == 'COMPLETED';

    return Chip(
      label: Text(
        status,

        style: TextStyle(
          fontSize: 12,
          fontWeight: FontWeight.bold,

          color: isCompleted
              ? Colors.green.shade800
              : Colors.orange.shade800,
        ),
      ),

      backgroundColor: isCompleted
          ? Colors.green.shade50
          : Colors.orange.shade50,

      side: BorderSide.none,
    );
  }
}