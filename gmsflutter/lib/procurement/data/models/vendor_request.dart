class VendorRequest {
  final String companyName;
  final String contactPerson;
  final String phone;
  final String address;

  VendorRequest({
    required this.companyName,
    required this.contactPerson,
    required this.phone,
    required this.address,
  });

  Map<String, dynamic> toJson() {
    return {
      'companyName': companyName,
      'contactPerson': contactPerson,
      'phone': phone,
      'address': address,
    };
  }
}