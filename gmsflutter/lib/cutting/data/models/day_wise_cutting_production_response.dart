class DayWiseCuttingProductionResponse {

  int? id;
  int? cuttingPlanId;
  String? cuttingPlanCode;
  String? date;
  int? actualCutPieces;
  int? rejectPieces;
  String? styleNo;
  String? cuttingMaster;

  DayWiseCuttingProductionResponse({
    this.id,
    this.cuttingPlanId,
    this.cuttingPlanCode,
    this.date,
    this.actualCutPieces,
    this.rejectPieces,
    this.styleNo,
    this.cuttingMaster,
  });

  DayWiseCuttingProductionResponse.fromJson(Map<String, dynamic> json) {

    id = json['id'];
    cuttingPlanId = json['cuttingPlanId'];
    cuttingPlanCode = json['cuttingPlanCode'];
    date = json['date'];
    actualCutPieces = json['actualCutPieces'];
    rejectPieces = json['rejectPieces'];
    styleNo = json['styleNo'];
    cuttingMaster = json['cuttingMaster'];
  }

  Map<String, dynamic> toJson() {

    final Map<String, dynamic> data = {};

    data['id'] = id;
    data['cuttingPlanId'] = cuttingPlanId;
    data['cuttingPlanCode'] = cuttingPlanCode;
    data['date'] = date;
    data['actualCutPieces'] = actualCutPieces;
    data['rejectPieces'] = rejectPieces;
    data['styleNo'] = styleNo;
    data['cuttingMaster'] = cuttingMaster;

    return data;
  }
}