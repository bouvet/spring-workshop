# Quick introduction to Spring Boot

The goal of this introduction course is to learn the basics of developing REST APIs using Java and SpringBoot.

### prerequisite
- Install IntelliJ Ultimate
- Install Maven
- Install some tool to perform API request (e.g. Postman)
- Install Java 11 (this app is not tested for newer java versions, so compatibility with anything other than java 11 is not guaranteed.)

### Topics
- REST API
- Dependency injection
- Beans
- RestController
- DTO objects
- JPA
- H2 database
- JUnit


### The goal of this workshop
We will create a simple REST API where the end product should be a service where you can create, and view movies displayed at a Cinema.

We will also create some unit tests to verify our application work as intended.

## Exercises

> It can be a good idea to look for examples in the PowerPoint 💡

### Initial setup
1. Open the project folder in IntelliJ.
2. If IntelliJ asks it you want to install dependencies, hit yes. If not open the built-in terminal and run `mvn clean install`.
3. Run the spring application with one of the following methods:
    1. Open [CinemaApplication.java](src/main/java/com/endre/cinema/CinemaApplication.java) and hit the green play button to the left of the code.
    2. run `mvn spring-boot:run` in the terminal.


### Ex 1
Create a Controller - branch: `ex-1`

We should now create our first API endpoint 🙌

1. Create a new Java class which will function as our REST Controller class, using the annotation `@RestController` at the class level with the following endpoint: `/movies`. 
2. Now add one method for a GET request using the `@GetMapping` annotation. The method should only return a String.
3. Run the application and open your web browser. Go to the following URL: [localhost:8080/movies](http://localhost:8080/movies). You should now see the String you wrote in the Controller Class, in your browser window.

Note that the endpoint paths doesn't need to be set at the class level. You can define paths on each method in the class, but they will be sub-paths under the class path. 

> Remember to restart the application between every code change.

### Ex 2
Create a Service class using `@Bean` - branch: `ex-2`

1. Create a normal java class (herby referred to as our Service class). This should have a field variable which is a list of movie objects and method that returns a list of Strings (Movie Titles). 
2. In the controller class, create a field variable of the Service class. Instantiate the field variable using the `@Autowired` annotation.
3. Inside the `@GetMapping` method in the Controller class, call the Service class-method that return a list of movies, and return that list instead of the string.
4. For this to work we need to create a `@Bean` of the service Class we just created. Put the bean in our `CinemaApplication`-class
5. Your API should now return a list of Movie titles.


### Ex 2-b
Create a Service class using `@Service` - branch: `ex-2b`

1. Annotate the service class with `@Service`. Now we can remove the bean created in the `CinemaApplication`-class.
2. Test the API endpoint again.


### Ex 3
Create a Movie object - branch: `ex-3`

1. Create a Movie class containing two fields: `title` (String) and `ageLimit` (of type `Integer` not `int` to avoid NullPointerException). 
2. Now populate the previously created list of strings (Movie titles) with Movie objects instead. 
3. You will now see that by calling your API, that Spring automatically serializes the java objects into JSON objects

Now we want to create new Movie objects by doing an HTTP POST request to our API: 
1. Create a new method in the Service class which takes in a Movie object as a parameter and adds it into our list of Movies.
2. Create a new method in the Controller class. This should handle a POST request (you can guess the annotation we need 🙌).
   1. Add the newly created Movie class as a parameter to the new "post-movie-method". Annotate the parameter with `@RequestBody`
   2. Call the new "create-movie" method in the Service class and pass along the movie object.
3. Use Postman or Curl to perform a POST request to our API with a JSON object representing a Movie object in the body.

We will now see that Spring automatically deserializes the JSON object into a Java class, and when calling the GET endpoint we will see the new movie object in the response.

### Ex 4
Make use of a database - branch: `ex-4`

Now we will make use of an H2 database (in-memory-database). 

1. Create an entity class to represent a table in our database. It should contain the same fields as the Movie class, but should have one more field variable: id. 
2. The id should be our Primary key (`@Id`), and should be auto generated (`@GeneratedValue`).
3. Create a new Interface which should be annotated with the `@Repository` annotation. This will enable us to communicate with our database.
   1. In the Interface extend CrudRepository and use our entity class and primary key type (`Integer`) in combination with CrudRepository.
4. Now you can remove the list of movies in the service class, and autowire the new repository interface.
5. In the Service class; use the Repository [Interface-methods](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html) which is available from extending CrudRepository to save and query to our database.

### Ex 4b
View movies in our database - branch: `ex-4b`

We will now use the h2 console to view the data stored in our database. 
1. After you have started the application go to the following url [http://localhost:8080/h2-console](http://localhost:8080/h2-console).
2. Replace the JDBC URL with: `jdbc:h2:mem:testdb`
3. username: `sa`
4. let the password field be empty.
5. Hit login and navigate the database.

### Ex 5
Create our first unit test and populate test database with data - branch: `ex-5`

We will use the [test class](src/test/java/com/endre/cinema/CinemaApplicationTests.java) already generated in the test folder. 

1. Autowire the Movie Service class as a field variable.
2. Create your own test method or use the one that already exist, just make sure to annotate each test method with `@Test`.
3. In the test method use the service class to retrieve the number of objects in the database. Then use the service class to create a new movie.
4. Compare the number of objects in the database using `assertTrue`, `assertFalse` or whatever "assert" you think work the best. 


### Extra
- Create a configuration (`@Configuration`) class in the test folder that will populate our database with some test data.
- Make it possible to query moves by their age limit using query parameters on the `@GetMapping` method and implement the necessary logic.
- Create a new endpoint to delete a movie from the database.
