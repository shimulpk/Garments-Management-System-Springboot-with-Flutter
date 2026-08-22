class  CuttingPlanResponse {

  int? id;
  String? cuttingPlanId;
  String? poNumber;
  String? buyerName;
  String? styleNo;
  int? plannedPieces;

  String? status;

  int? actualCutPieces;
  int? rejectedPieces;
  int? remainingPieces;

  double? progress;


  CuttingPlanResponse({
    this.id,
    this.cuttingPlanId,
    this.poNumber,
    this.buyerName,
    this.styleNo,
    this.plannedPieces,
    this.status,
    this.actualCutPieces,
    this.rejectedPieces,
    this.remainingPieces,
    this.progress,
  });


  CuttingPlanResponse.fromJson(Map<String, dynamic> json) {

    id = json['id'];
    cuttingPlanId = json['cuttingPlanId'];
    poNumber = json['poNumber'];
    buyerName = json['buyerName'];
    styleNo = json['styleNo'];
    plannedPieces = json['plannedPieces'];

    status = json['status'];

    actualCutPieces = json['actualCutPieces'];
    rejectedPieces = json['rejectedPieces'];
    remainingPieces = json['remainingPieces'];

    progress = json['progress'] != null
        ? (json['progress'] as num).toDouble()
        : null;
  }


  Map<String, dynamic> toJson() {

    final Map<String, dynamic> data = {};

    data['id'] = id;
    data['cuttingPlanId'] = cuttingPlanId;
    data['poNumber'] = poNumber;
    data['buyerName'] = buyerName;
    data['styleNo'] = styleNo;
    data['plannedPieces'] = plannedPieces;

    data['status'] = status;

    data['actualCutPieces'] = actualCutPieces;
    data['rejectedPieces'] = rejectedPieces;
    data['remainingPieces'] = remainingPieces;

    data['progress'] = progress;

    return data;
  }
}