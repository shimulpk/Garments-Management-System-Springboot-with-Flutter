class DayWiseCuttingHistoryDetailsResponse {
  int? id;
  String? createdAt;
  int? actualCutPieces;
  int? rejectPieces;

  DayWiseCuttingHistoryDetailsResponse({
    this.id,
    this.createdAt,
    this.actualCutPieces,
    this.rejectPieces,
  });

  DayWiseCuttingHistoryDetailsResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    createdAt = json['createdAt'];
    actualCutPieces = json['actualCutPieces'];
    rejectPieces = json['rejectPieces'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    data['id'] = id;
    data['createdAt'] = createdAt;
    data['actualCutPieces'] = actualCutPieces;
    data['rejectPieces'] = rejectPieces;
    return data;
  }
}