# Contact API Spec

## Create Contact
Endpoint: POST /api/contacts

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Request Body:
```json
{
  "firstName": "Rizal",
  "lastName": "Amarulloh",
  "email": "amar@mail.com",
  "phone": "081113123424"
}
```

Response Body (Success):
```json
{
  "data": {
    "id": "<RANDOM-STRING>",
    "firstName": "Rizal",
    "lastName": "Amarulloh",
    "email": "amar@mail.com",
    "phone": "081113123424"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "email format invalid, ..."
}
```

## Update Contact
Endpoint: PUT /api/contacts/{idContact}

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Request Body:
```json
{
  "firstName": "Rizal",
  "lastName": "Amarulloh",
  "email": "amar@mail.com",
  "phone": "081113123424"
}
```

Response Body (Success):
```json
{
  "data": {
    "id": "<RANDOM-STRING>",
    "firstName": "Rizal",
    "lastName": "Amarulloh",
    "email": "amar@mail.com",
    "phone": "081113123424"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "email format invalid, ..."
}
```

## Get Contact
Endpoint: GET /api/contacts/{idContact}

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Response Body (Success):
```json
{
  "data": {
    "id": "<RANDOM-STRING>",
    "firstName": "Rizal",
    "lastName": "Amarulloh",
    "email": "amar@mail.com",
    "phone": "081113123424"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "contact is not found, ..."
}
```

## Search Contact
Endpoint: GET api/contacts

Query Params: 
- name: String, first or last name, using query like, optional
- phone: String, contact phone, using query like, optional
- email: String, contact email, using query like, optional
- page: Integer, start from 0, default 0
- size: Integer, default 10

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Response Body (Success):

```json
{
  "data": [
    {
      "id": "<RANDOM-STRING>",
      "firstName": "Rizal",
      "lastName": "Amarulloh",
      "email": "amar@mail.com",
      "phone": "081113123424"
    }
  ],
  "paging": {
    "currentPage": 0,
    "totalPages": 10,
    "size": 10
  }
}
```
Response Body (Failed):
```json
{
  "errors": "Unauthorized"
}
```

## Delete Contact 
Endpoint: DELETE /api/contacts/{idContact}

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Response Body (Success):
```json
{
  "data": "OK"
}
```

Response Body (Failed):

```json
{
  "errors": "contact is not found, ..."
}
```