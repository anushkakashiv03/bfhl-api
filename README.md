# BFHL API

Spring Boot REST API for data processing

## 🚀 Live Deployment

**API URL**: https://bfhl-api-1-2hye.onrender.com/bfhl

- **GET** `/bfhl` - Returns operation code
- **POST** `/bfhl` - Process data array

## Build & Run

```bash
./mvnw clean package
java -jar target/bfhl-api-1.0.0.jar
```

## API Endpoint

POST `/bfhl`

Request:
```json
{
  "data": ["1", "334", "4", "A", "R", "@", "#"]
}
```

Response:
```json
{
  "is_success": true,
  "user_id": "anushka_kashiv_03092005",
  "email": "anushkakashiv231170@acropolis.in",
  "roll_number": "0827AL231030",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["@", "#"],
  "sum": "339",
  "concat_string": "Ra"
}
```
