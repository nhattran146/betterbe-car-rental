# BetterBe6

Small Java web application (JAX-RS) that serves a simple car catalogue and a static front-end.

## Quick overview
- REST resources implemented in [`nl.utwente.di.recources.CarsResource`](src/main/java/nl/utwente/di/recources/CarsResource.java) and [`nl.utwente.di.recources.CarResource`](src/main/java/nl/utwente/di/recources/CarResource.java).  
- In-memory data provider: [`nl.utwente.di.dao.CarDao`](src/main/java/nl/utwente/di/dao/CarDao.java).  
- Domain model: [`nl.utwente.di.model.Car`](src/main/java/nl/utwente/di/model/Car.java).  
- Example JAX‑RS client: [`nl.utwente.di.client.CarClient`](src/main/java/nl/utwente/di/client/CarClient.java).  
- Web UI: [src/main/webapp/index.html](src/main/webapp/index.html) with styles [src/main/webapp/css/style.css](src/main/webapp/css/style.css) and [src/main/webapp/css/styleguide.css](src/main/webapp/css/styleguide.css).  
- Servlet mapping: [src/main/webapp/WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml).  
- Build: Maven (`pom.xml`) — see [`pom.xml`](pom.xml).  
- Additional DB utility (standalone): [`nl.utwente.di.database.IsolationBreach`](src/main/java/nl/utwente/di/database/IsolationBreach.java).

## Build
Generate the WAR with Maven:
```sh
mvn clean package
```
Result: `target/BetterBe6.war` (or exploded folder `target/BetterBe6/`).

## Run / Deploy
Deploy the produced WAR to a servlet container (Tomcat, Jetty, etc.). After deployment the app is reachable at:
- Base (static UI): http://localhost:8080/BetterBe6/ (serves [src/main/webapp/index.html](src/main/webapp/index.html))
- API root (servlets/JAX-RS): mapped in [src/main/webapp/WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml) to `/BetterBe6/cars/*`.

## Important REST endpoints (examples)
- GET all (JSON/XML): GET http://localhost:8080/BetterBe6/cars/all  
  Example:
  ```sh
  curl -H "Accept: application/json" http://localhost:8080/BetterBe6/cars/all
  ```
- GET browser-friendly XML: GET http://localhost:8080/BetterBe6/cars  
- GET count: GET http://localhost:8080/BetterBe6/cars/size
- POST (form) create car: POST to http://localhost:8080/BetterBe6/cars with `application/x-www-form-urlencoded` fields `id`, `color`, `price`, `model`.  
  Example:
  ```sh
  curl -X POST -d "id=5&color=blue&price=3500&model=Audi" \
       -H "Content-Type: application/x-www-form-urlencoded" \
       http://localhost:8080/BetterBe6/cars
  ```
- Individual car resource: GET/PUT/DELETE under `/BetterBe6/cars/{id}` handled by [`nl.utwente.di.recources.CarResource`](src/main/java/nl/utwente/di/recources/CarResource.java).

## Notes & known issues
- The in-memory DAO uses string keys and a single sample entry in [`nl.utwente.di.dao.CarDao`](src/main/java/nl/utwente/di/dao/CarDao.java). Adjust keys/initial data as needed.
- The sample client [`nl.utwente.di.client.CarClient`](src/main/java/nl/utwente/di/client/CarClient.java) demonstrates using JAX‑RS client APIs and expected endpoints.
- If using JAXB/JSON with Jersey, the required dependencies are in [`pom.xml`](pom.xml). Ensure the container uses Java 11 (project configured for release 11).

## Useful files
- Project descriptor: [`pom.xml`](pom.xml)  
- Web UI: [src/main/webapp/index.html](src/main/webapp/index.html)  
- REST resources: [`nl.utwente.di.recources.CarsResource`](src/main/java/nl/utwente/di/recources/CarsResource.java), [`nl.utwente.di.recources.CarResource`](src/main/java/nl/utwente/di/recources/CarResource.java)  
- DAO/model: [`nl.utwente.di.dao.CarDao`](src/main/java/nl/utwente/di/dao/CarDao.java), [`nl.utwente.di.model.Car`](src/main/java/nl/utwente/di/model/Car.java)  
- Example client: [`nl.utwente.di.client.CarClient`](src/main/java/nl/utwente/di/client/CarClient.java)  
- DB utility: [`nl.utwente.di.database.IsolationBreach`](src/main/java/nl/utwente/di/database/IsolationBreach.java)

If additional adjustments are required (CORS, persistence, or improved JSON/XML handling), update the resource classes and `pom.xml` dependencies accordingly.