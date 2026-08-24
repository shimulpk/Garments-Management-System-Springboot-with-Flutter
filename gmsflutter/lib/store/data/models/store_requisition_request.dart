import 'package:gmsflutter/store/data/models/store_requisition_item_request.dart';

class StoreRequisitionRequest {
  String? requisitionDate;
  String? requestedBy;
  String? department;
  String? remarks;
  List<StoreRequisitionItemRequest> items;

  StoreRequisitionRequest({
    this.requisitionDate,
    this.requestedBy,
    this.department,
    this.remarks,
    this.items = const [],
  });

  Map<String, dynamic> toJson() {
    return {
      'requisitionDate': requisitionDate,
      'requestedBy': requestedBy,
      'department': department,
      'remarks': remarks,
      'items': items.map((e) => e.toJson()).toList(),
    };
  }
}