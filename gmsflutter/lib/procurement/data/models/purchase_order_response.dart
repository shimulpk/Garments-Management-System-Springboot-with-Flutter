import 'purchase_order_item_response.dart';

class PurchaseOrderResponse {
  final int? id;
  final String? poNo;
  final String? poDate;
  final int? vendorId;
  final String? vendorName;
  final int? storeRequisitionId;
  final String? requisitionNo;
  final String? status;
  final double? grandTotal;
  final String? remarks;
  final List<PurchaseOrderItemResponse> items;

  PurchaseOrderResponse({
    this.id,
    this.poNo,
    this.poDate,
    this.vendorId,
    this.vendorName,
    this.storeRequisitionId,
    this.requisitionNo,
    this.status,
    this.grandTotal,
    this.remarks,
    this.items = const [],
  });

  factory PurchaseOrderResponse.fromJson(
      Map<String, dynamic> json) {
    return PurchaseOrderResponse(
      id: json['id'],
      poNo: json['poNo'],
      poDate: json['poDate'],
      vendorId: json['vendorId'],
      vendorName: json['vendorName'],
      storeRequisitionId: json['storeRequisitionId'],
      requisitionNo: json['requisitionNo'],
      status: json['status'],
      grandTotal:
      (json['grandTotal'] as num?)?.toDouble(),
      remarks: json['remarks'],
      items: (json['items'] as List?)
          ?.map(
            (item) =>
            PurchaseOrderItemResponse.fromJson(
              item as Map<String, dynamic>,
            ),
      )
          .toList() ??
          [],
    );
  }
}