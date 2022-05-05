# cloudflare_url_shortener
Take home project for Cloudflare Interview, based on specifications [here](SPECIFICATIONS.md).

## Requirements/Dependencies
Java 8 (some sources for jdks [here](https://code.visualstudio.com/docs/java/java-tutorial#_installing-a-java-development-kit-jdk)) & Gradle ([docs](https://docs.gradle.org/current/userguide/installation.html))
## How to Test
Use `./gradlew build` to compile and unit test this project.
## How to Run
Use `./gradlew bootrun`

## Architecture
### Rest API
See the [swagger docs](http://localhost:8080/swagger-ui.html) to view API calls and test them out.  If you aren't deploying to localhost:8080, append `/swagger-ui.html` to where it is running.
### Database
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

## Future Improvements
### Better Error Handling
Currently, errors do not provide meaningful information to users. Error messages should be clear so users understand, or meaningful errors could be presented in whatever frontend interfaces with this API. 
One example: if the longUrl submitted on the `/shorten` endpoint isn't a valid url, a meaningful message should be returned along with the error -- something like `Provided URL is invalid, Illegal character in path at index 0: "string"`. This would allow the user to adjust their parameters to use the endpoint correctly.

### Caching
Recently used url mappings can be cached within the process to prevent multiple database calls to fetch information. However, for usage statas to be accurate I would not cache that information, and read and write directly to/from the db. 

### Expirable links
This could be accomplished by storing an optional column in the `url_mappings` table that captured an expiry date. MySQL has an [Event Scheduler](https://dev.mysql.com/doc/refman/5.7/en/event-scheduler.html) which could periodically delete entries based on the value in that column. 

### Logging and Metrics
Several metrics would be useful for monitoring this service: 
* avg and max latency of requests
* requests per minute
* unique api consumers 
* cpu usage
* memory usage
* health of db connection (and any other eventual dependencies)