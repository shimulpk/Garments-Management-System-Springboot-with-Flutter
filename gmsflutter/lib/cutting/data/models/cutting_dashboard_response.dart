import 'today_cutting_response.dart';
import 'recent_cutting_plan_response.dart';

class CuttingDashboardResponse {
  int? todayCutting;
  int? todayReject;
  int? totalPlans;
  int? pendingPlans;
  int? completedPlans;

  List<TodayCuttingResponse>? todayCuttings;
  List<RecentCuttingPlanResponse>? recentPlans;

  CuttingDashboardResponse({
    this.todayCutting,
    this.todayReject,
    this.totalPlans,
    this.pendingPlans,
    this.completedPlans,
    this.todayCuttings,
    this.recentPlans,
  });

  CuttingDashboardResponse.fromJson(
      Map<String, dynamic> json) {

    todayCutting = json['todayCutting'];
    todayReject = json['todayReject'];
    totalPlans = json['totalPlans'];
    pendingPlans = json['pendingPlans'];
    completedPlans = json['completedPlans'];

    // Today's Cutting
    if (json['todayCuttings'] != null) {
      todayCuttings = <TodayCuttingResponse>[];

      json['todayCuttings'].forEach((item) {
        todayCuttings!.add(
          TodayCuttingResponse.fromJson(
            item as Map<String, dynamic>,
          ),
        );
      });
    }

    // Recent Plans
    if (json['recentPlans'] != null) {
      recentPlans = <RecentCuttingPlanResponse>[];

      json['recentPlans'].forEach((item) {
        recentPlans!.add(
          RecentCuttingPlanResponse.fromJson(
            item as Map<String, dynamic>,
          ),
        );
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};

    data['todayCutting'] = todayCutting;
    data['todayReject'] = todayReject;
    data['totalPlans'] = totalPlans;
    data['pendingPlans'] = pendingPlans;
    data['completedPlans'] = completedPlans;

    if (todayCuttings != null) {
      data['todayCuttings'] =
          todayCuttings!
              .map((item) => item.toJson())
              .toList();
    }

    if (recentPlans != null) {
      data['recentPlans'] =
          recentPlans!
              .map((item) => item.toJson())
              .toList();
    }

    return data;
  }
}