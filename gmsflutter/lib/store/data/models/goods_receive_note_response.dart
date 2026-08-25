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

  GoodsReceiveNoteResponse.fromJson(Map<String, dynamic> json)
      : items = (json['items'] as List<dynamic>?)
      ?.map(
        (item) => GoodsReceiveNoteItemResponse.fromJson(
      item as Map<String, dynamic>,
    ),
  )
      .toList() ??
      [] {
    id = json['id'];
    grnNo = json['grnNo'];
    grnDate = json['grnDate'];
    purchaseOrderId = json['purchaseOrderId'];
    poNo = json['poNo'];
    challanNo = json['challanNo'];
    grandTotal = (json['grandTotal'] as num?)?.toDouble();
    remarks = json['remarks'];
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'grnNo': grnNo,
      'grnDate': grnDate,
      'purchaseOrderId': purchaseOrderId,
      'poNo': poNo,
      'challanNo': challanNo,
      'grandTotal': grandTotal,
      'remarks': remarks,
      'items': items.map((item) => item.toJson()).toList(),
    };
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

  GoodsReceiveNoteItemResponse.fromJson(
      Map<String, dynamic> json,
      ) {
    id = json['id'];
    purchaseOrderItemId = json['purchaseOrderItemId'];
    itemId = json['itemId'];
    itemName = json['itemName'];
    unit = json['unit'];
    quantity = (json['quantity'] as num?)?.toDouble();
    unitPrice = (json['unitPrice'] as num?)?.toDouble();
    lineTotal = (json['lineTotal'] as num?)?.toDouble();
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'purchaseOrderItemId': purchaseOrderItemId,
      'itemId': itemId,
      'itemName': itemName,
      'unit': unit,
      'quantity': quantity,
      'unitPrice': unitPrice,
      'lineTotal': lineTotal,
    };
  }
}