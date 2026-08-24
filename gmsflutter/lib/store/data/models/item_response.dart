class ItemResponse {
  int? id;
  String? itemName;
  String? category;
  String? unit;
  bool? active;

  ItemResponse({
    this.id,
    this.itemName,
    this.category,
    this.unit,
    this.active,
  });

  ItemResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    itemName = json['itemName'];
    category = json['category'];
    unit = json['unit'];
    active = json['active'];
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'itemName': itemName,
      'category': category,
      'unit': unit,
      'active': active,
    };
  }
}