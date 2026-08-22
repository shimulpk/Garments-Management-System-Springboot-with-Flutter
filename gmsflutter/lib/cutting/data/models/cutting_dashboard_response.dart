class CuttingDashboardResponse {

  int? todayCutting;
  int? todayReject;
  int? totalPlans;
  int? pendingPlans;
  int? completedPlans;

  CuttingDashboardResponse({
    this.todayCutting,
    this.todayReject,
    this.totalPlans,
    this.pendingPlans,
    this.completedPlans,
  });

  CuttingDashboardResponse.fromJson(Map<String, dynamic> json) {

    todayCutting = json['todayCutting'];
    todayReject = json['todayReject'];
    totalPlans = json['totalPlans'];
    pendingPlans = json['pendingPlans'];
    completedPlans = json['completedPlans'];

  }

  Map<String, dynamic> toJson() {

    final Map<String, dynamic> data = {};

    data['todayCutting'] = todayCutting;
    data['todayReject'] = todayReject;
    data['totalPlans'] = totalPlans;
    data['pendingPlans'] = pendingPlans;
    data['completedPlans'] = completedPlans;

    return data;
  }
}