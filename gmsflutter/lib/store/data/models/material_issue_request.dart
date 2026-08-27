class MaterialIssueRequest {
  String? issueDate;
  String? department;
  String? requestedBy;
  String? remarks;
  List<MaterialIssueItemRequest> items;

  MaterialIssueRequest({
    this.issueDate,
    this.department,
    this.requestedBy,
    this.remarks,
    this.items = const [],
  });

  Map<String, dynamic> toJson() {
    return {
      'issueDate': issueDate,
      'department': department,
      'requestedBy': requestedBy,
      'remarks': remarks,
      'items': items.map((item) => item.toJson()).toList(),
    };
  }
}

class MaterialIssueItemRequest {
  int? itemId;
  double? quantity;

  MaterialIssueItemRequest({
    this.itemId,
    this.quantity,
  });

  Map<String, dynamic> toJson() {
    return {
      'itemId': itemId,
      'quantity': quantity,
    };
  }
}