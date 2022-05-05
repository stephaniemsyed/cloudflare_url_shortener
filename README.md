# cloudflare_url_shortener
Take home project for Cloudflare Interview, based on specifications [here](SPECIFICATIONS.md).

## How to Test
Use `./gradlew build` to compile and unit test this project.
## How to Run
Use `./gradlew bootrun`
## Architecture
### Rest API
See swagger docs at localhost:8080/swagger-ui.html
## Database
Not yet implemented. 

Two tables: `url_mapping` to hold the mapping of a short url to a long url, and `access_records`. 

```
CREATE TABLE IF NOT EXISTS url_mappings (
    mapping_id INT AUTO_INCREMENT PRIMARY KEY,
    short_url_id VARCHAR(255) NOT NULL UNIQUE
    long_url VARCHAR(255) NOT NULL UNIQUE
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE access_records (
    access_id INT AUTO_INCREMENT,
    short_url_id VARCHAR(255) NOT NULL UNIQUE,
    accessed TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (short_url_id)
        REFERENCES url_mappings
        ON UPDATE RESTRICT ON DELETE CASCADE
);
```
Once this is implemented, `data/UrlRepository` can be updated to store/access information in these tables. Additionally, the usage statistics can be gathered more efficiently by using select count() statements that filter on the `accessed` column in `access_records`.
