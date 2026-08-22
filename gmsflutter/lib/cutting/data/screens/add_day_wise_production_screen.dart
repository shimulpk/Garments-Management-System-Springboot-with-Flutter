import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../models/day_wise_cutting_production_request.dart';
import '../provider/cutting_provider.dart';
import '../../../util/api_client.dart';

class AddDayWiseProductionScreen extends ConsumerStatefulWidget {
  const AddDayWiseProductionScreen({super.key});

  @override
  ConsumerState<AddDayWiseProductionScreen> createState() =>
      _AddDayWiseProductionScreenState();
}

class _AddDayWiseProductionScreenState
    extends ConsumerState<AddDayWiseProductionScreen> {

  final _formKey = GlobalKey<FormState>();

  final _dateCtrl = TextEditingController(
    text: DateTime.now().toIso8601String().split('T')[0],
  );

  final _actualCutCtrl = TextEditingController();
  final _rejectCtrl = TextEditingController(text: '0');

  int? _selectedPlanId;
  bool _submitting = false;
  String? _error;

  @override
  void dispose() {
    _dateCtrl.dispose();
    _actualCutCtrl.dispose();
    _rejectCtrl.dispose();
    super.dispose();
  }

  Future<void> _selectDate() async {
    final currentDate =
        DateTime.tryParse(_dateCtrl.text) ?? DateTime.now();

    final picked = await showDatePicker(
      context: context,
      initialDate: currentDate,
      firstDate: DateTime(2020),
      lastDate: DateTime.now(),
    );

    if (picked != null) {
      setState(() {
        _dateCtrl.text =
        picked.toIso8601String().split('T')[0];
      });
    }
  }

  Future<void> _submit() async {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    if (_selectedPlanId == null) {
      setState(() {
        _error = 'Please select a cutting plan.';
      });
      return;
    }

    setState(() {
      _submitting = true;
      _error = null;
    });

    final request = DayWiseCuttingProductionRequest(
      cuttingPlanId: _selectedPlanId!,
      date: _dateCtrl.text.trim(),
      actualCutPieces:
      int.tryParse(_actualCutCtrl.text.trim()) ?? 0,
      rejectPieces:
      int.tryParse(_rejectCtrl.text.trim()) ?? 0,
    );

    try {
      await ref
          .read(cuttingRepositoryProvider)
          .saveDayWiseCuttingProduction(request);

      ref.invalidate(allCuttingPlansProvider);
      ref.invalidate(pendingCuttingPlansProvider);
      ref.invalidate(cuttingDashboardProvider);

      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text(
              'Production saved successfully!',
            ),
          ),
        );

        Navigator.of(context).pop();
      }
    } catch (e) {
      if (mounted) {
        setState(() {
          _error = apiErrorMessage(e);
        });
      }
    } finally {
      if (mounted) {
        setState(() {
          _submitting = false;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final plansAsync =
    ref.watch(pendingCuttingPlansProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Day Wise Entry'),
      ),

      body: Form(
        key: _formKey,

        child: ListView(
          padding: const EdgeInsets.all(20),

          children: [

            // ─────────────────────────────
            // Error
            // ─────────────────────────────

            if (_error != null)
              Container(
                padding: const EdgeInsets.all(12),
                margin: const EdgeInsets.only(bottom: 16),
                decoration: BoxDecoration(
                  color: Colors.red.shade50,
                  borderRadius:
                  BorderRadius.circular(10),
                  border: Border.all(
                    color: Colors.red.shade200,
                  ),
                ),
                child: Text(
                  _error!,
                  style: TextStyle(
                    color: Colors.red.shade900,
                  ),
                ),
              ),

            // ─────────────────────────────
            // Cutting Plan
            // ─────────────────────────────

            const Text(
              'Select Cutting Plan',
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),

            const SizedBox(height: 8),

            plansAsync.when(
              loading: () => const Center(
                child: CircularProgressIndicator(),
              ),

              error: (error, stack) => Text(
                'Failed to load cutting plans',
                style: TextStyle(
                  color: Colors.red.shade700,
                ),
              ),

              data: (plans) {
                if (plans.isEmpty) {
                  return const Text(
                    'No pending cutting plans available.',
                  );
                }

                return DropdownButtonFormField<int>(
                  value: _selectedPlanId,

                  decoration: const InputDecoration(
                    border: OutlineInputBorder(),
                    prefixIcon:
                    Icon(Icons.assignment_outlined),
                    hintText: 'Select cutting plan',
                  ),

                  items: plans.map((plan) {
                    return DropdownMenuItem<int>(
                      value: plan.id,
                      child: Text(
                        '${plan.styleNo ?? 'N/A'}'
                            ' - '
                            '${plan.poNumber ?? 'N/A'}',
                      ),
                    );
                  }).toList(),

                  onChanged: (value) {
                    setState(() {
                      _selectedPlanId = value;
                    });
                  },

                  validator: (value) {
                    if (value == null) {
                      return 'Please select a cutting plan';
                    }

                    return null;
                  },
                );
              },
            ),

            const SizedBox(height: 20),

            // ─────────────────────────────
            // Production Progress
            // ─────────────────────────────

            if (_selectedPlanId != null)
              _ProductionProgress(
                planId: _selectedPlanId!,
              ),

            if (_selectedPlanId != null)
              const SizedBox(height: 20),

            // ─────────────────────────────
            // Date
            // ─────────────────────────────

            TextFormField(
              controller: _dateCtrl,
              readOnly: true,

              decoration: const InputDecoration(
                labelText: 'Production Date',
                border: OutlineInputBorder(),
                prefixIcon:
                Icon(Icons.calendar_today),
              ),

              onTap: _selectDate,

              validator: (value) {
                if (value == null ||
                    value.trim().isEmpty) {
                  return 'Date is required';
                }

                return null;
              },
            ),

            const SizedBox(height: 20),

            // ─────────────────────────────
            // Actual Cut
            // ─────────────────────────────

            TextFormField(
              controller: _actualCutCtrl,

              keyboardType: TextInputType.number,

              decoration: const InputDecoration(
                labelText: 'Actual Cut Pieces',
                border: OutlineInputBorder(),
                prefixIcon:
                Icon(Icons.content_cut),
              ),

              validator: (value) {
                final number =
                int.tryParse(value ?? '');

                if (number == null ||
                    number <= 0) {
                  return 'Enter valid cut pieces';
                }

                return null;
              },
            ),

            const SizedBox(height: 20),

            // ─────────────────────────────
            // Reject
            // ─────────────────────────────

            TextFormField(
              controller: _rejectCtrl,

              keyboardType: TextInputType.number,

              decoration: const InputDecoration(
                labelText: 'Reject Pieces',
                border: OutlineInputBorder(),
                prefixIcon:
                Icon(Icons.cancel_outlined),
              ),

              validator: (value) {
                final number =
                int.tryParse(value ?? '');

                if (number == null ||
                    number < 0) {
                  return 'Enter valid reject pieces';
                }

                return null;
              },
            ),

            const SizedBox(height: 28),

            // ─────────────────────────────
            // Save
            // ─────────────────────────────

            SizedBox(
              height: 52,

              child: ElevatedButton.icon(
                onPressed:
                _submitting ? null : _submit,

                icon: _submitting
                    ? const SizedBox(
                  width: 20,
                  height: 20,
                  child:
                  CircularProgressIndicator(
                    strokeWidth: 2,
                    color: Colors.white,
                  ),
                )
                    : const Icon(Icons.save),

                label: Text(
                  _submitting
                      ? 'Saving...'
                      : 'Save Production',
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
// Production Progress
// ─────────────────────────────────────────────

class _ProductionProgress extends ConsumerWidget {
  const _ProductionProgress({
    required this.planId,
  });

  final int planId;

  @override
  Widget build(
      BuildContext context,
      WidgetRef ref,
      ) {
    final progressAsync =
    ref.watch(
      cuttingPlanProgressProvider(planId),
    );

    return progressAsync.when(
      loading: () => const Card(
        child: Padding(
          padding: EdgeInsets.all(20),
          child: Center(
            child: CircularProgressIndicator(),
          ),
        ),
      ),

      error: (error, stack) => Card(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Text(
            'Unable to load production progress.',
            style: TextStyle(
              color: Colors.red.shade700,
            ),
          ),
        ),
      ),

      data: (progress) {
        return Card(
          elevation: 3,

          shape: RoundedRectangleBorder(
            borderRadius:
            BorderRadius.circular(16),
          ),

          child: Padding(
            padding: const EdgeInsets.all(16),

            child: Column(
              crossAxisAlignment:
              CrossAxisAlignment.start,

              children: [

                const Text(
                  'Production Progress',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 16),

                Row(
                  children: [

                    Expanded(
                      child: _ProgressItem(
                        title: 'Target',
                        value:
                        '${progress.target ?? 0}',
                      ),
                    ),

                    Expanded(
                      child: _ProgressItem(
                        title: 'Cut So Far',
                        value:
                        '${progress.cutSoFar ?? 0}',
                      ),
                    ),

                    Expanded(
                      child: _ProgressItem(
                        title: 'Remaining',
                        value:
                        '${progress.remaining ?? 0}',
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}


class _ProgressItem extends StatelessWidget {
  const _ProgressItem({
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
            fontSize: 24,
            fontWeight: FontWeight.bold,
          ),
        ),

        const SizedBox(height: 4),

        Text(
          title,
          textAlign: TextAlign.center,
          style: TextStyle(
            fontSize: 13,
            color: Colors.grey.shade600,
          ),
        ),
      ],
    );
  }
}