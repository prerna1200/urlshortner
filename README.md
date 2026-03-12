URL Shortener Assignment

This project is a simple URL shortener service built using Java and Spring Boot.

Features:
- Create short URL
- Redirect to original URL
- Same URL returns same short code
- In-memory storage
- Top 3 domain stats API

APIs:
POST /shorten
GET /{code}
GET /stats

Run:
mvn spring-boot:run
