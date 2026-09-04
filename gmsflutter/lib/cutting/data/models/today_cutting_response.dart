class TodayCuttingResponse {
  String? styleNo;
  String? cuttingMaster;
  int? actualCutPieces;
  int? rejectPieces;

  TodayCuttingResponse({
    this.styleNo,
    this.cuttingMaster,
    this.actualCutPieces,
    this.rejectPieces,
  });

  TodayCuttingResponse.fromJson(Map<String, dynamic> json) {
    styleNo = json['styleNo'];
    cuttingMaster = json['cuttingMaster'];
    actualCutPieces = json['actualCutPieces'];
    rejectPieces = json['rejectPieces'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};

    data['styleNo'] = styleNo;
    data['cuttingMaster'] = cuttingMaster;
    data['actualCutPieces'] = actualCutPieces;
    data['rejectPieces'] = rejectPieces;

    return data;
  }
}