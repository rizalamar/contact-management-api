# User API Spec

## Register User

Endpoint: POST /api/users

Request body: 
```json
{
  "username" : "rizalamar",
  "password" : "rahasiaya",
  "name" : "Rizal Amarulloh"
}
```

Response body (Success): 
```json
{
  "data": "OK"
}
```

Response body (Failed):
```json
{
  "errors": "Password at least must be 8 characters"
}
```

## Login User
Endpoint: POST /api/auth/login

Request body:
```json
{
  "username" : "rizalamar",
  "password" : "rahasiaya"
}
```

Response body (Success):
```json
{
  "data": {
    "token": "Token",
    "expiredAt": "100000000ms"
  }
}
```

Response body (Failed):
```json
{
  "errors": "Wrong password"
}
```

## Get User
Endpoint: GET /api/users/current

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Response body (Success):
```json
{
  "data": {
    "useranme": "rizalamar",
    "name": "Rizal Amarulloh"
  }
}
```

Response body (Failed):
```json
{
  "errors": "Unauthorized"
}
```

## Update User
Endpoint: PATCH /api/users/current

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Request body:
```json
{
  "name" : "<YOUR NEW NAME>", // put if you only want to update name
  "password" : "<YOUR NEW PASSWORD>" // put if you only want to update password
}
```

Response body (Success):
```json
{
  "data": "OK"
}
```

Response body (Failed):
```json
{
  "errors": "Password at least must be 8 characters"
}
```

## Logout User
Endpoint: DELETE /api/auth/logout

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Response body:

```json
{
  "data": "OK"
}
```