class ItemRequest {
  String? itemName;
  String? category;
  String? unit;

  ItemRequest({
    this.itemName,
    this.category,
    this.unit,
  });

  Map<String, dynamic> toJson() {
    return {
      'itemName': itemName,
      'category': category,
      'unit': unit,
    };
  }
}