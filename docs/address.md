# Address API Spec

## Create Address
Endpoint: POST /api/contacts/{idContact}/addresses

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Request Body:
```json
{
  "street": "Nama jalan",
  "city": "Kota",
  "province": "Provinsi",
  "country": "Negara",
  "postalCode": "123123"
}
```

Response Body (Success):
```json
{
  "data": {
      "id": "<RANDOM-STRING>",
      "street": "Nama jalan",
      "city": "Kota",
      "province": "Provinsi",
      "country": "Negara",
      "postalCode": "123123"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "Contact is not found"
}
```

## Update Address
Endpoint: PUT /api/contacts/{idContact}/addresses/{idAddress}

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Request Body:
```json
{
  "street": "Nama jalan",
  "city": "Kota",
  "province": "Provinsi",
  "country": "Negara",
  "postalCode": "123123"
}
```

Response Body (Success):

```json
{
  "data": {
      "id": "<RANDOM-STRING>",
      "street": "Nama jalan",
      "city": "Kota",
      "province": "Provinsi",
      "country": "Negara",
      "postalCode": "123123"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "Address is not found"
}
```

## Get Address
Endpoint: GET /api/contacts/{idContact}/addresses/{idAddress}

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Response Body (Success):
```json
{
  "data": {
      "id": "<RANDOM-STRING>",
      "street": "Nama jalan",
      "city": "Kota",
      "province": "Provinsi",
      "country": "Negara",
      "postalCode": "123123"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "Address is not found"
}
```

## Delete Address
Endpoint: DELETE /api/contacts/{idContact}/addresses/{idAddress}

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
  "errors": "Address not found"
}
```

## List Address
Endpoint: GET /api/contacts/{idContact}/addresses

Request header:
- X-API-TOKEN: TOKEN (Mandatory);

Response Body (Success):
```json
{
  "data": [
    {
      "id": "<RANDOM-STRING>",
      "street": "Nama jalan",
      "city": "Kota",
      "province": "Provinsi",
      "country": "Negara",
      "postalCode": "123123"
    }
  ]
}
```

Response Body (Failed):
```json
{
  "errors": "Address is not found"
}
```
