class PurchaseOrderItemRequest {
  final int itemId;
  final double unitPrice;

  PurchaseOrderItemRequest({
    required this.itemId,
    required this.unitPrice,
  });

  Map<String, dynamic> toJson() {
    return {
      'itemId': itemId,
      'unitPrice': unitPrice,
    };
  }
}