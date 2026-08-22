class LoginResponse {

  String? token;
  String? tokenType;

  int? userId;
  String? name;
  String? email;
  String? phone;
  String? role;

  LoginResponse({
    this.token,
    this.tokenType,
    this.userId,
    this.name,
    this.email,
    this.phone,
    this.role,
  });

  LoginResponse.fromJson(Map<String, dynamic> json) {

    token = json['token'];
    tokenType = json['tokenType'];

    userId = json['userId'];
    name = json['name'];
    email = json['email'];
    phone = json['phone'];
    role = json['role'];
  }

  Map<String, dynamic> toJson() {

    final Map<String, dynamic> data = <String, dynamic>{};

    data['token'] = token;
    data['tokenType'] = tokenType;

    data['userId'] = userId;
    data['name'] = name;
    data['email'] = email;
    data['phone'] = phone;
    data['role'] = role;

    return data;
  }
}