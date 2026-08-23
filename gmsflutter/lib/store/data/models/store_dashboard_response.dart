class StoreDashboardResponse {
  int? totalItems;
  int? currentStockItems;
  int? lowStockItems;
  int? pendingStoreRequisitions;
  int? todayGoodsReceive;
  int? todayMaterialIssue;

  List<RecentGrnResponse> recentGoodsReceives;
  List<RecentMaterialIssueResponse> recentMaterialIssues;
  List<LowStockResponse> lowStockList;

  StoreDashboardResponse({
    this.totalItems,
    this.currentStockItems,
    this.lowStockItems,
    this.pendingStoreRequisitions,
    this.todayGoodsReceive,
    this.todayMaterialIssue,
    this.recentGoodsReceives = const [],
    this.recentMaterialIssues = const [],
    this.lowStockList = const [],
  });

  factory StoreDashboardResponse.fromJson(Map<String, dynamic> json) {
    return StoreDashboardResponse(
      totalItems: json['totalItems'],
      currentStockItems: json['currentStockItems'],
      lowStockItems: json['lowStockItems'],
      pendingStoreRequisitions: json['pendingStoreRequisitions'],
      todayGoodsReceive: json['todayGoodsReceive'],
      todayMaterialIssue: json['todayMaterialIssue'],

      recentGoodsReceives:
      (json['recentGoodsReceives'] as List?)
          ?.map(
            (e) => RecentGrnResponse.fromJson(
          e as Map<String, dynamic>,
        ),
      )
          .toList() ??
          [],

      recentMaterialIssues:
      (json['recentMaterialIssues'] as List?)
          ?.map(
            (e) => RecentMaterialIssueResponse.fromJson(
          e as Map<String, dynamic>,
        ),
      )
          .toList() ??
          [],

      lowStockList:
      (json['lowStockList'] as List?)
          ?.map(
            (e) => LowStockResponse.fromJson(
          e as Map<String, dynamic>,
        ),
      )
          .toList() ??
          [],
    );
  }
}

class RecentGrnResponse {
  String? grnNo;
  String? grnDate;
  String? supplier;
  double? grandTotal;

  RecentGrnResponse({
    this.grnNo,
    this.grnDate,
    this.supplier,
    this.grandTotal,
  });

  factory RecentGrnResponse.fromJson(Map<String, dynamic> json) {
    return RecentGrnResponse(
      grnNo: json['grnNo'],
      grnDate: json['grnDate'],
      supplier: json['supplier'],
      grandTotal: (json['grandTotal'] as num?)?.toDouble(),
    );
  }
}

class RecentMaterialIssueResponse {
  String? issueNo;
  String? issueDate;
  String? department;
  String? requestedBy;
  String? status;

  RecentMaterialIssueResponse({
    this.issueNo,
    this.issueDate,
    this.department,
    this.requestedBy,
    this.status,
  });

  factory RecentMaterialIssueResponse.fromJson(
      Map<String, dynamic> json,
      ) {
    return RecentMaterialIssueResponse(
      issueNo: json['issueNo'],
      issueDate: json['issueDate'],
      department: json['department'],
      requestedBy: json['requestedBy'],
      status: json['status'],
    );
  }
}

class LowStockResponse {
  String? itemName;
  String? category;
  String? unit;
  double? availableQuantity;

  LowStockResponse({
    this.itemName,
    this.category,
    this.unit,
    this.availableQuantity,
  });

  factory LowStockResponse.fromJson(Map<String, dynamic> json) {
    return LowStockResponse(
      itemName: json['itemName'],
      category: json['category'],
      unit: json['unit'],
      availableQuantity:
      (json['availableQuantity'] as num?)?.toDouble(),
    );
  }
}