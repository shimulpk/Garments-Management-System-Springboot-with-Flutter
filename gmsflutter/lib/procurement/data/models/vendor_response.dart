class VendorResponse {
  final int? id;
  final String? companyName;
  final String? contactPerson;
  final String? phone;
  final String? address;
  final bool? active;

  VendorResponse({
    this.id,
    this.companyName,
    this.contactPerson,
    this.phone,
    this.address,
    this.active,
  });

  factory VendorResponse.fromJson(
      Map<String, dynamic> json,
      ) {
    return VendorResponse(
      id: json['id'] as int?,
      companyName: json['companyName'] as String?,
      contactPerson: json['contactPerson'] as String?,
      phone: json['phone'] as String?,
      address: json['address'] as String?,
      active: json['active'] as bool?,
    );
  }
}