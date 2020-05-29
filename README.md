# Quick introduction to Spring Boot

The goal of this introduction course is to learn the basics of developing REST API´s using Java and SpringBoot.

### Pre requirements
- Have IntelliJ Ultimate installed
- Have Maven installed
- Some tool to perform API request (e.g. Postman)
- Have Java 11 installed

### Topics
- REST API
- Dependency injection
- RestController
- DTO objects
- JPA
- H2 database
- JUnit


## The end Product
We will create a simple REST API where the end product should be a service where you can create and view movies displayed at a Cinema.

We will also create some unit tests to verify our application work as intended.

## Exercises

#### Ex 1
Create a Controller - branch: `ex-1`

You should create a Controller class (using the annotation `@RestController`) that contains one method for a GET request. It should only return a String.
Now run the application an open your web browser. Go to the following URL: [localhost:8080/movies](http://localhost:8080/movies). You should now see the String you wrote in the Controller Class, in your browser window.

Note that the endpoint does not need to be at class level, every method inside a class can have its own endpoint as well.

#### Ex 2
Create a Service class - branch: `ex-2`

Create a service Class. This should have a method that returns a list of Strings (Movie Titles). 
Make use of the Service class in the Controller, using the `@Autowired` annotation.
For this to work we need to create a `@Bean` of the service Class we just created.
Your API should now return a list of Movie titles.


#### Ex 2-b
Create a Service class - branch: `ex-2`

annotated with `@Service`

#### Ex 3
Create a Movie object - branch: `ex-3`

Create a Movie class containing two fields: `title` and `ageLimit`. 
Now populate the previously created list of strings (Movie titles) with Movie objects instead.

You will now see that by calling your API, that Spring automatically serialize the java objects into JSON objects

Now we want to create new Movie objects by doing an HTTP POST request to our API: 
- First, we need to create a new method in the Controller class. This should handle a POST request, with a request body in JSON.
- Lastly we need to create a method in the Service class to add the new Movie into our list of Movies.

We will now see that Spring automatically deserializes the JSON object into a Java class.

#### Ex 4
Make use of a database - branch: `ex-5`

Now we will make use of an H2 database (in-memory-database). We will create an entity class to represent a table in our database.
We will also make use of the `@Repository` annotation in a class we will use to communicate with our database.

#### Ex 4b
View movies in our database - branch: `ex-5b`

We will now use the h2 console to view the data stored in our database. 
- After you have started the application go to the following url [http://localhost:8080/h2-console](http://localhost:8080/h2-console).
- Replace the JDBC URL with: `jdbc:h2:mem:testdb`
- username: `sa`
- password: <let this field be empty>

#### Ex 5
Create our first unit test and populate test database with data- branch `ex-6`

We will use the test class already generated in the test folder. 
Here we will create a test method testing if we can create a movie.

We will also create a configuration class in the test folder that will populate our database with some test data.

#### Extra
- You should also be able to query moves by their age limit.
- Delete a movie