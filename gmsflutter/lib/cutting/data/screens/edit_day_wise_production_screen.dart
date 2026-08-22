import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../models/day_wise_cutting_production_update_request.dart';
import '../provider/cutting_provider.dart';

class EditDayWiseProductionScreen extends ConsumerStatefulWidget {
  const EditDayWiseProductionScreen({
    super.key,
    required this.entryId,
    required this.actualCutPieces,
    required this.rejectPieces,
  });

  final int entryId;
  final int actualCutPieces;
  final int rejectPieces;

  @override
  ConsumerState<EditDayWiseProductionScreen> createState() =>
      _EditDayWiseProductionScreenState();
}

class _EditDayWiseProductionScreenState
    extends ConsumerState<EditDayWiseProductionScreen> {
  final _formKey = GlobalKey<FormState>();

  late final TextEditingController _actualCutCtrl;
  late final TextEditingController _rejectCtrl;

  bool _saving = false;

  @override
  void initState() {
    super.initState();

    _actualCutCtrl = TextEditingController(
      text: widget.actualCutPieces.toString(),
    );

    _rejectCtrl = TextEditingController(
      text: widget.rejectPieces.toString(),
    );
  }

  Future<void> _update() async {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      _saving = true;
    });

    final request = DayWiseCuttingProductionUpdateRequest(
      actualCutPieces:
      int.tryParse(_actualCutCtrl.text.trim()) ?? 0,
      rejectPieces:
      int.tryParse(_rejectCtrl.text.trim()) ?? 0,
    );

    try {
      await ref
          .read(cuttingRepositoryProvider)
          .updateDayWiseCuttingProduction(
        widget.entryId,
        request,
      );

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Production updated successfully'),
        ),
      );

      Navigator.of(context).pop(true);
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
    _actualCutCtrl.dispose();
    _rejectCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Edit Production'),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const Text(
                'Update Production Entry',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 24),

              TextFormField(
                controller: _actualCutCtrl,
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(
                  labelText: 'Actual Cut Pieces',
                  prefixIcon: Icon(Icons.content_cut),
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  final number = int.tryParse(value ?? '');

                  if (number == null || number <= 0) {
                    return 'Enter valid cut pieces';
                  }

                  return null;
                },
              ),

              const SizedBox(height: 18),

              TextFormField(
                controller: _rejectCtrl,
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(
                  labelText: 'Reject Pieces',
                  prefixIcon: Icon(Icons.cancel_outlined),
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  final number = int.tryParse(value ?? '');

                  if (number == null || number < 0) {
                    return 'Enter valid reject pieces';
                  }

                  return null;
                },
              ),

              const SizedBox(height: 28),

              ElevatedButton.icon(
                onPressed: _saving ? null : _update,
                icon: _saving
                    ? const SizedBox(
                  width: 18,
                  height: 18,
                  child: CircularProgressIndicator(
                    strokeWidth: 2,
                  ),
                )
                    : const Icon(Icons.save),
                label: Text(
                  _saving ? 'Updating...' : 'Update Production',
                ),
                style: ElevatedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(
                    vertical: 14,
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