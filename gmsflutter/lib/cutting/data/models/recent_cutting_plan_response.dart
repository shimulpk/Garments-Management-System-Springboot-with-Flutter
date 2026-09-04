class RecentCuttingPlanResponse {
  int? cuttingPlanId;
  String? styleNo;
  int? orderId;
  int? plannedPieces;
  String? status;

  RecentCuttingPlanResponse({
    this.cuttingPlanId,
    this.styleNo,
    this.orderId,
    this.plannedPieces,
    this.status,
  });

  RecentCuttingPlanResponse.fromJson(
      Map<String, dynamic> json) {

    cuttingPlanId = json['cuttingPlanId'];
    styleNo = json['styleNo'];
    orderId = json['orderId'];
    plannedPieces = json['plannedPieces'];

    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};

    data['cuttingPlanId'] = cuttingPlanId;
    data['styleNo'] = styleNo;
    data['orderId'] = orderId;
    data['plannedPieces'] = plannedPieces;
    data['status'] = status;

    return data;
  }
}