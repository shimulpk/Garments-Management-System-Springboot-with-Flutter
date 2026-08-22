class DayWiseCuttingHistorySummaryResponse {
  int? totalCutPieces;
  int? totalRejectPieces;
  int? totalEntries;
  String? lastUpdated;

  DayWiseCuttingHistorySummaryResponse({
    this.totalCutPieces,
    this.totalRejectPieces,
    this.totalEntries,
    this.lastUpdated,
  });

  DayWiseCuttingHistorySummaryResponse.fromJson(Map<String, dynamic> json) {
    totalCutPieces = json['totalCutPieces'];
    totalRejectPieces = json['totalRejectPieces'];
    totalEntries = json['totalEntries'];
    lastUpdated = json['lastUpdated'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    data['totalCutPieces'] = totalCutPieces;
    data['totalRejectPieces'] = totalRejectPieces;
    data['totalEntries'] = totalEntries;
    data['lastUpdated'] = lastUpdated;
    return data;
  }
}