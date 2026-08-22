class DayWiseCuttingProductionUpdateRequest {

  int? actualCutPieces;
  int? rejectPieces;

  DayWiseCuttingProductionUpdateRequest({
    this.actualCutPieces,
    this.rejectPieces,
  });

  DayWiseCuttingProductionUpdateRequest.fromJson(
      Map<String, dynamic> json) {

    actualCutPieces = json['actualCutPieces'];
    rejectPieces = json['rejectPieces'];
  }

  Map<String, dynamic> toJson() {

    final Map<String, dynamic> data = {};

    data['actualCutPieces'] = actualCutPieces;
    data['rejectPieces'] = rejectPieces;

    return data;
  }
}