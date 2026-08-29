class PurchaseDashboardResponse {
  final int? totalVendors;
  final int? pendingRequisitions;
  final int? pendingPurchaseOrders;
  final int? todayPurchaseOrders;
  final int? todayGoodsReceive;

  final List<RecentPurchaseOrderResponse> recentPurchaseOrders;
  final List<PendingRequisitionResponse> pendingRequisitionsList;
  final List<RecentGrnResponse> recentGoodsReceives;

  PurchaseDashboardResponse({
    this.totalVendors,
    this.pendingRequisitions,
    this.pendingPurchaseOrders,
    this.todayPurchaseOrders,
    this.todayGoodsReceive,
    required this.recentPurchaseOrders,
    required this.pendingRequisitionsList,
    required this.recentGoodsReceives,
  });

  factory PurchaseDashboardResponse.fromJson(
      Map<String, dynamic> json,
      ) {
    return PurchaseDashboardResponse(
      totalVendors: json['totalVendors'],
      pendingRequisitions: json['pendingRequisitions'],
      pendingPurchaseOrders: json['pendingPurchaseOrders'],
      todayPurchaseOrders: json['todayPurchaseOrders'],
      todayGoodsReceive: json['todayGoodsReceive'],

      recentPurchaseOrders:
      (json['recentPurchaseOrders'] as List<dynamic>? ?? [])
          .map(
            (e) => RecentPurchaseOrderResponse.fromJson(e),
      )
          .toList(),

      pendingRequisitionsList:
      (json['pendingRequisitionsList'] as List<dynamic>? ?? [])
          .map(
            (e) => PendingRequisitionResponse.fromJson(e),
      )
          .toList(),

      recentGoodsReceives:
      (json['recentGoodsReceives'] as List<dynamic>? ?? [])
          .map(
            (e) => RecentGrnResponse.fromJson(e),
      )
          .toList(),
    );
  }
}

class RecentPurchaseOrderResponse {
  final String? poNo;
  final String? poDate;
  final String? vendorName;
  final double? grandTotal;
  final String? status;

  RecentPurchaseOrderResponse({
    this.poNo,
    this.poDate,
    this.vendorName,
    this.grandTotal,
    this.status,
  });

  factory RecentPurchaseOrderResponse.fromJson(
      Map<String, dynamic> json,
      ) {
    return RecentPurchaseOrderResponse(
      poNo: json['poNo'],
      poDate: json['poDate'],
      vendorName: json['vendorName'],
      grandTotal: (json['grandTotal'] as num?)?.toDouble(),
      status: json['status'],
    );
  }
}

class PendingRequisitionResponse {
  final String? prNo;
  final String? requisitionDate;
  final String? requestedBy;
  final String? department;

  PendingRequisitionResponse({
    this.prNo,
    this.requisitionDate,
    this.requestedBy,
    this.department,
  });

  factory PendingRequisitionResponse.fromJson(
      Map<String, dynamic> json,
      ) {
    return PendingRequisitionResponse(
      prNo: json['prNo'],
      requisitionDate: json['requisitionDate'],
      requestedBy: json['requestedBy'],
      department: json['department'],
    );
  }
}

class RecentGrnResponse {
  final String? grnNo;
  final String? grnDate;
  final String? supplier;
  final double? grandTotal;

  RecentGrnResponse({
    this.grnNo,
    this.grnDate,
    this.supplier,
    this.grandTotal,
  });

  factory RecentGrnResponse.fromJson(
      Map<String, dynamic> json,
      ) {
    return RecentGrnResponse(
      grnNo: json['grnNo'],
      grnDate: json['grnDate'],
      supplier: json['supplier'],
      grandTotal: (json['grandTotal'] as num?)?.toDouble(),
    );
  }
}