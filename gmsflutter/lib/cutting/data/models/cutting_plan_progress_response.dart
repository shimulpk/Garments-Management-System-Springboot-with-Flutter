class CuttingPlanProgressResponse {

  int? target;
  int? cutSoFar;
  int? remaining;
  double? progress;
  int? rejected;
  String? status;


  CuttingPlanProgressResponse({
    this.target,
    this.cutSoFar,
    this.remaining,
    this.progress,
    this.rejected,
    this.status,
  });


  CuttingPlanProgressResponse.fromJson(Map<String, dynamic> json) {

    target = json['target'];
    cutSoFar = json['cutSoFar'];
    remaining = json['remaining'];

    progress = json['progress'] != null
        ? (json['progress'] as num).toDouble()
        : null;

    rejected = json['rejected'];
    status = json['status'];
  }


  Map<String, dynamic> toJson() {

    final Map<String, dynamic> data = {};

    data['target'] = target;
    data['cutSoFar'] = cutSoFar;
    data['remaining'] = remaining;
    data['progress'] = progress;
    data['rejected'] = rejected;
    data['status'] = status;

    return data;
  }
}