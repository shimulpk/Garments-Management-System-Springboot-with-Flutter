class StoreRequisitionResponse {
  int? id;
  String? prNo;
  String? requisitionDate;
  String? requestedBy;
  String? department;
  String? remarks;
  String? status;
  List<StoreRequisitionItemResponse> items;

  StoreRequisitionResponse({
    this.id,
    this.prNo,
    this.requisitionDate,
    this.requestedBy,
    this.department,
    this.remarks,
    this.status,
    this.items = const [],
  });

  factory StoreRequisitionResponse.fromJson(Map<String, dynamic> json) {
    return StoreRequisitionResponse(
      id: json['id'],
      prNo: json['prNo'],
      requisitionDate: json['requisitionDate'],
      requestedBy: json['requestedBy'],
      department: json['department'],
      remarks: json['remarks'],
      status: json['status'],
      items: (json['items'] as List?)
          ?.map(
            (e) => StoreRequisitionItemResponse.fromJson(e),
      )
          .toList() ??
          [],
    );
  }
}

class StoreRequisitionItemResponse {
  int? id;
  int? itemId;
  String? itemName;
  String? unit;
  double? quantity;

  StoreRequisitionItemResponse({
    this.id,
    this.itemId,
    this.itemName,
    this.unit,
    this.quantity,
  });

  factory StoreRequisitionItemResponse.fromJson(
      Map<String, dynamic> json,
      ) {
    return StoreRequisitionItemResponse(
      id: json['id'],
      itemId: json['itemId'],
      itemName: json['itemName'],
      unit: json['unit'],
      quantity: (json['quantity'] as num?)?.toDouble(),
    );
  }
}