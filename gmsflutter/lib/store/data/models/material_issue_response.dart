class MaterialIssueResponse {
  int? id;
  String? issueNo;
  String? issueDate;
  String? department;
  String? requestedBy;
  String? remarks;
  String? status;
  List<MaterialIssueItemResponse> items;

  MaterialIssueResponse({
    this.id,
    this.issueNo,
    this.issueDate,
    this.department,
    this.requestedBy,
    this.remarks,
    this.status,
    this.items = const [],
  });

  MaterialIssueResponse.fromJson(Map<String, dynamic> json)
      : items = (json['items'] as List<dynamic>?)
      ?.map(
        (item) => MaterialIssueItemResponse.fromJson(
      item as Map<String, dynamic>,
    ),
  )
      .toList() ??
      [] {
    id = json['id'];
    issueNo = json['issueNo'];
    issueDate = json['issueDate'];
    department = json['department'];
    requestedBy = json['requestedBy'];
    remarks = json['remarks'];
    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'issueNo': issueNo,
      'issueDate': issueDate,
      'department': department,
      'requestedBy': requestedBy,
      'remarks': remarks,
      'status': status,
      'items': items.map((item) => item.toJson()).toList(),
    };
  }
}

class MaterialIssueItemResponse {
  int? id;
  int? itemId;
  String? itemName;
  String? unit;
  double? quantity;

  MaterialIssueItemResponse({
    this.id,
    this.itemId,
    this.itemName,
    this.unit,
    this.quantity,
  });

  MaterialIssueItemResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    itemId = json['itemId'];
    itemName = json['itemName'];
    unit = json['unit'];
    quantity = (json['quantity'] as num?)?.toDouble();
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'itemId': itemId,
      'itemName': itemName,
      'unit': unit,
      'quantity': quantity,
    };
  }
}