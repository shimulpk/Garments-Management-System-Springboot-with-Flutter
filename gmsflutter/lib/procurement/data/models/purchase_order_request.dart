import 'purchase_order_item_request.dart';

class PurchaseOrderRequest {
  final String poDate;
  final int vendorId;
  final int storeRequisitionId;
  final String remarks;
  final List<PurchaseOrderItemRequest> items;

  PurchaseOrderRequest({
    required this.poDate,
    required this.vendorId,
    required this.storeRequisitionId,
    required this.remarks,
    required this.items,
  });

  Map<String, dynamic> toJson() {
    return {
      'poDate': poDate,
      'vendorId': vendorId,
      'storeRequisitionId': storeRequisitionId,
      'remarks': remarks,
      'items': items.map((item) => item.toJson()).toList(),
    };
  }
}