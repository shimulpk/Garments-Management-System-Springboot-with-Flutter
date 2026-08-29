import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:gmsflutter/widget/app_sidebar.dart';
import 'package:go_router/go_router.dart';

import '../provider/material_issue_provider.dart';

class MaterialIssueListScreen extends ConsumerStatefulWidget {
  const MaterialIssueListScreen({super.key});

  @override
  ConsumerState<MaterialIssueListScreen> createState() =>
      _MaterialIssueListScreenState();
}

class _MaterialIssueListScreenState
    extends ConsumerState<MaterialIssueListScreen> {
  String _search = '';

  @override
  Widget build(BuildContext context) {
    final issuesAsync = ref.watch(allMaterialIssuesProvider);

    return Scaffold(
      drawer: const AppSidebar(),
      appBar: AppBar(
        title: const Text('Material Issues'),
        actions: [
          IconButton(
            onPressed: () {
              ref.invalidate(allMaterialIssuesProvider);
            },
            icon: const Icon(Icons.refresh),
          ),
        ],
      ),

      body: issuesAsync.when(
        loading: () => const Center(
          child: CircularProgressIndicator(),
        ),

        error: (error, stack) => Center(
          child: Padding(
            padding: const EdgeInsets.all(20),
            child: Text(
              'Failed to load material issues\n$error',
              textAlign: TextAlign.center,
            ),
          ),
        ),

        data: (issues) {
          final filtered = issues.where((issue) {
            final search = _search.toLowerCase();

            return (issue.issueNo ?? '')
                .toLowerCase()
                .contains(search) ||
                (issue.department ?? '')
                    .toLowerCase()
                    .contains(search) ||
                (issue.requestedBy ?? '')
                    .toLowerCase()
                    .contains(search) ||
                (issue.status ?? '')
                    .toLowerCase()
                    .contains(search);
          }).toList();

          return Column(
            children: [
              Padding(
                padding: const EdgeInsets.all(12),
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      _search = value;
                    });
                  },
                  decoration: const InputDecoration(
                    hintText:
                    'Search issue no, department, requester...',
                    prefixIcon: Icon(Icons.search),
                    border: OutlineInputBorder(),
                  ),
                ),
              ),

              Expanded(
                child: filtered.isEmpty
                    ? const Center(
                  child: Text(
                    'No material issues found.',
                  ),
                )
                    : ListView.builder(
                  padding: const EdgeInsets.symmetric(
                    horizontal: 12,
                  ),
                  itemCount: filtered.length,
                  itemBuilder: (context, index) {
                    final issue = filtered[index];

                    return Card(
                      margin: const EdgeInsets.only(
                        bottom: 10,
                      ),
                      child: ListTile(
                        onTap: () {
                          if (issue.id != null) {
                            context.push(
                              '/store/material-issues/details/${issue.id}',
                            );
                          }
                        },

                        leading: const CircleAvatar(
                          child: Icon(
                            Icons.outbox_outlined,
                          ),
                        ),

                        title: Text(
                          issue.issueNo ?? 'Issue',
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),

                        subtitle: Text(
                          '${issue.department ?? '-'} • '
                              '${issue.requestedBy ?? '-'}\n'
                              'Date: ${issue.issueDate ?? '-'}',
                        ),

                        isThreeLine: true,

                        trailing: Chip(
                          label: Text(
                            issue.status ?? 'PENDING',
                          ),
                        ),
                      ),
                    );
                  },
                ),
              ),
            ],
          );
        },
      ),

      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
          context.push('/store/material-issues/add');
        },
        icon: const Icon(Icons.add),
        label: const Text('New Issue'),
      ),
    );
  }
}