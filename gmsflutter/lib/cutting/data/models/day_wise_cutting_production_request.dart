class DayWiseCuttingProductionRequest {

  int? cuttingPlanId;
  String? date;
  int? actualCutPieces;
  int? rejectPieces;

  DayWiseCuttingProductionRequest({
    this.cuttingPlanId,
    this.date,
    this.actualCutPieces,
    this.rejectPieces,
  });

  DayWiseCuttingProductionRequest.fromJson(Map<String, dynamic> json) {

    cuttingPlanId = json['cuttingPlanId'];
    date = json['date'];
    actualCutPieces = json['actualCutPieces'];
    rejectPieces = json['rejectPieces'];
  }

  Map<String, dynamic> toJson() {

    final Map<String, dynamic> data = {};

    data['cuttingPlanId'] = cuttingPlanId;
    data['date'] = date;
    data['actualCutPieces'] = actualCutPieces;
    data['rejectPieces'] = rejectPieces;

    return data;
  }
}