class StoreRequisitionItemRequest {
  int? itemId;
  double? quantity;

  StoreRequisitionItemRequest({
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