class GoodsReceiveNoteRequest {
  String? grnDate;
  int? purchaseOrderId;
  String? challanNo;
  String? remarks;

  GoodsReceiveNoteRequest({
    this.grnDate,
    this.purchaseOrderId,
    this.challanNo,
    this.remarks,
  });

  Map<String, dynamic> toJson() {
    return {
      'grnDate': grnDate,
      'purchaseOrderId': purchaseOrderId,
      'challanNo': challanNo,
      'remarks': remarks,
    };
  }
}