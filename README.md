## Velocity product application
### Java application with Spring boot and a mysql database

Project structure:
```
├── docker-compose.yml
├── Dockerfile
├── HELP.md
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── product
│   │   │               ├── controller
│   │   │               │   └── ProductController.java
│   │   │               ├── entities
│   │   │               │   ├── Product.java
│   │   │               │   └── Supplier.java
│   │   │               ├── exception
│   │   │               │   ├── DataNotFoundException.java
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               ├── models
│   │   │               │   ├── api
│   │   │               │   │   ├── CreateOrUpdateProduct.java
│   │   │               │   │   ├── CreateOrUpdateSupplier.java
│   │   │               │   │   ├── ProductResponse.java
│   │   │               │   │   └── SupplierResponse.java
│   │   │               │   ├── ApiError.java
│   │   │               │   └── ApiValidationError.java
│   │   │               ├── ProductServiceApplication.java
│   │   │               ├── ProjectConfig.java
│   │   │               ├── repositories
│   │   │               │   ├── ProductRepository.java
│   │   │               │   └── SupplierRepository.java
│   │   │               ├── service
│   │   │               │   ├── ProductService.java
│   │   │               │   └── SupplierService.java
│   │   │               └── Version.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── data.sql
│   │       ├── schema.sql
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── product
│                       └── controller
│                           └── ProductControllerTest.java
└── target
    ├── classes
    │   ├── application.properties
    │   ├── data.sql
    │   ├── META-INF
    │   │   ├── MANIFEST.MF
    │   │   └── maven
    │   │       └── com.example
    │   │           └── velocity-product-service
    │   │               ├── pom.properties
    │   │               └── pom.xml
    │   └── schema.sql
    └── test-classes

29 directories, 36 files

```

[_compose.yaml_](compose.yaml)
```
The compose file defines an application with two services `app` and `mysqldb`.
When deploying the application, docker compose maps port 8080 of the backend service container to port 8080 of the host as specified in the file.
Make sure port 8080 on the host is not already being in use.

## Deploy with docker compose

```
$ docker compose up -d
CWARN[0000] Found orphan containers ([mysql]) for this project. If you removed or renamed this service in your compose file, you can run this command with the --remove-orphans flag to clean it up. 
[+] Running 2/2
 ⠿ Container velocity-product-service-mysqldb-1  Started                                                                                                                                              0.3s
 ⠿ Container velocity-product-service-app-1      Started
```

## Expected result

Listing containers must show two containers running and the port mapping as below:
```
$ docker ps
CONTAINER ID   IMAGE                          COMMAND                  CREATED         STATUS          PORTS                               NAMES
80f90382c586   velocity-product-service-app   "/usr/local/bin/mvn-…"   3 hours ago     Up 27 seconds   0.0.0.0:8080->8080/tcp              velocity-product-service-app-1
```
## Swagger documentation
After the application starts, navigate to `localhost:8080/swagger-ui/index.html#/` in your web browser. It will load the swagger page with documentation for all apis defined under products. It contains sample data with schema for creating products, updating products. Example data is also provided.

## Postman collection
You can import the postman collection present in the root directory to test the apis. 
	file name: velocity product service.postman_collection.json

Stop and remove the containers
```
$ docker compose down
[+] Running 3/3
 ⠿ Container velocity-product-service-app-1      Removed                                                                                                                                              0.4s
 ⠿ Container velocity-product-service-mysqldb-1  Removed                                                                                                                                              1.4s
 ⠿ Network velocity-product-service_default      Removed  
```
