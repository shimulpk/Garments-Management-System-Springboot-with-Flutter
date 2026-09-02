class GoodsReceiveNoteRequest {
  String grnDate;
  int purchaseOrderId;
  String challanNo;
  String remarks;

  GoodsReceiveNoteRequest({
    required this.grnDate,
    required this.purchaseOrderId,
    required this.challanNo,
    required this.remarks,
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