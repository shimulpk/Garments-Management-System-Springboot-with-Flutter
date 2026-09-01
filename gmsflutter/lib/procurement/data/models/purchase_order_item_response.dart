class PurchaseOrderItemResponse {
  final int? id;
  final int? itemId;
  final String? itemName;
  final String? unit;
  final double? quantity;
  final double? unitPrice;
  final double? lineTotal;

  PurchaseOrderItemResponse({
    this.id,
    this.itemId,
    this.itemName,
    this.unit,
    this.quantity,
    this.unitPrice,
    this.lineTotal,
  });

  factory PurchaseOrderItemResponse.fromJson(
      Map<String, dynamic> json) {
    return PurchaseOrderItemResponse(
      id: json['id'],
      itemId: json['itemId'],
      itemName: json['itemName'],
      unit: json['unit'],
      quantity: (json['quantity'] as num?)?.toDouble(),
      unitPrice: (json['unitPrice'] as num?)?.toDouble(),
      lineTotal: (json['lineTotal'] as num?)?.toDouble(),
    );
  }
}