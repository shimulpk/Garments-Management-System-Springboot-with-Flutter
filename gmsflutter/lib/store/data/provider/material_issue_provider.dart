import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/auth/providers/providers.dart';
import 'package:gmsflutter/store/data/repositories/material_issue_repository.dart';
import '../models/material_issue_response.dart';


final materialIssueRepositoryProvider =
Provider<MaterialIssueRepository>((ref) {
  return MaterialIssueRepository(
    ref.read(apiClientProvider),
  );
});

// ===============================
// All Material Issues
// ===============================

final allMaterialIssuesProvider =
FutureProvider<List<MaterialIssueResponse>>((ref) async {
  return ref
      .read(materialIssueRepositoryProvider)
      .getAll();
});

// ===============================
// Material Issue By ID
// ===============================

final materialIssueByIdProvider =
FutureProvider.family<MaterialIssueResponse, int>(
      (ref, id) async {
    return ref
        .read(materialIssueRepositoryProvider)
        .getById(id);
  },
);