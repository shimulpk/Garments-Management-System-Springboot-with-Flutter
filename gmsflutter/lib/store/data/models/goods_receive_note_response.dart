class GoodsReceiveNoteResponse {
  int? id;
  String? grnNo;
  String? grnDate;
  int? purchaseOrderId;
  String? poNo;
  String? challanNo;
  double? grandTotal;
  String? remarks;

  List<GoodsReceiveNoteItemResponse> items;

  GoodsReceiveNoteResponse({
    this.id,
    this.grnNo,
    this.grnDate,
    this.purchaseOrderId,
    this.poNo,
    this.challanNo,
    this.grandTotal,
    this.remarks,
    this.items = const [],
  });

  factory GoodsReceiveNoteResponse.fromJson(
      Map<String, dynamic> json) {
    return GoodsReceiveNoteResponse(
      id: json['id'],
      grnNo: json['grnNo'],
      grnDate: json['grnDate'],
      purchaseOrderId: json['purchaseOrderId'],
      poNo: json['poNo'],
      challanNo: json['challanNo'],
      grandTotal:
      (json['grandTotal'] as num?)?.toDouble(),
      remarks: json['remarks'],

      items: (json['items'] as List?)
          ?.map(
            (e) =>
            GoodsReceiveNoteItemResponse.fromJson(
              e as Map<String, dynamic>,
            ),
      )
          .toList() ??
          [],
    );
  }
}


class GoodsReceiveNoteItemResponse {
  int? id;
  int? purchaseOrderItemId;
  int? itemId;
  String? itemName;
  String? unit;
  double? quantity;
  double? unitPrice;
  double? lineTotal;

  GoodsReceiveNoteItemResponse({
    this.id,
    this.purchaseOrderItemId,
    this.itemId,
    this.itemName,
    this.unit,
    this.quantity,
    this.unitPrice,
    this.lineTotal,
  });

  factory GoodsReceiveNoteItemResponse.fromJson(
      Map<String, dynamic> json) {
    return GoodsReceiveNoteItemResponse(
      id: json['id'],
      purchaseOrderItemId:
      json['purchaseOrderItemId'],
      itemId: json['itemId'],
      itemName: json['itemName'],
      unit: json['unit'],
      quantity:
      (json['quantity'] as num?)?.toDouble(),
      unitPrice:
      (json['unitPrice'] as num?)?.toDouble(),
      lineTotal:
      (json['lineTotal'] as num?)?.toDouble(),
    );
  }
}