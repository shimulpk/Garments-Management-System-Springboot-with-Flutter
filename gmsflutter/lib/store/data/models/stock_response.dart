class StockResponse {
  final int? id;
  final int? itemId;
  final String? itemName;
  final String? category;
  final String? unit;
  final double? availableQuantity;

  StockResponse({
    this.id,
    this.itemId,
    this.itemName,
    this.category,
    this.unit,
    this.availableQuantity,
  });

  factory StockResponse.fromJson(Map<String, dynamic> json) {
    return StockResponse(
      id: json['id'],
      itemId: json['itemId'],
      itemName: json['itemName'],
      category: json['category'],
      unit: json['unit'],
      availableQuantity:
      (json['availableQuantity'] as num?)?.toDouble(),
    );
  }
}