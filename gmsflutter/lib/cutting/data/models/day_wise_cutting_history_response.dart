class DayWiseCuttingHistoryResponse {
  int? cuttingPlanId;
  String? cuttingPlanCode;
  String? styleNo;
  String? date;
  int? totalCutPieces;
  int? totalRejectPieces;
  int? totalEntries;

  DayWiseCuttingHistoryResponse({
    this.cuttingPlanId,
    this.cuttingPlanCode,
    this.styleNo,
    this.date,
    this.totalCutPieces,
    this.totalRejectPieces,
    this.totalEntries,
  });

  DayWiseCuttingHistoryResponse.fromJson(Map<String, dynamic> json) {
    cuttingPlanId = json['cuttingPlanId'];
    cuttingPlanCode = json['cuttingPlanCode'];
    styleNo = json['styleNo'];
    date = json['date'];
    totalCutPieces = json['totalCutPieces'];
    totalRejectPieces = json['totalRejectPieces'];
    totalEntries = json['totalEntries'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    data['cuttingPlanId'] = cuttingPlanId;
    data['cuttingPlanCode'] = cuttingPlanCode;
    data['styleNo'] = styleNo;
    data['date'] = date;
    data['totalCutPieces'] = totalCutPieces;
    data['totalRejectPieces'] = totalRejectPieces;
    data['totalEntries'] = totalEntries;
    return data;
  }
}