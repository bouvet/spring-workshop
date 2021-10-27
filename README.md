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
You will find the solution for each exercise in branches named after the exercise number (e.g: `ex-1`, `ex-2`, `ex-2b`, etc...).
> It can be a good idea to look for examples in the PowerPoint 💡


### Initial setup
1. Open the project folder in IntelliJ.
2. If IntelliJ asks it you want to install dependencies, hit yes. If not open the built-in terminal and run `mvn clean install`.
3. Run the spring application with one of the following methods:
    1. Open [CinemaApplication.java](src/main/java/com/endre/cinema/CinemaApplication.java) and hit the green play button to the left of the code.
    2. run `mvn spring-boot:run` in the terminal.


### Ex 1
Create a Controller - solution branch: `ex-1`

We should now create our first API endpoint 🙌

1. Create a new Java class which will function as our REST Controller class, using the annotation `@RestController` at the class level with the following endpoint: `/movies`. 
2. Now add one method for a GET request using the `@GetMapping` annotation. The method should only return a String.
3. Run the application and open your web browser. Go to the following URL: [localhost:8080/movies](http://localhost:8080/movies). You should now see the String you wrote in the Controller Class, in your browser window.

Note that the endpoint paths doesn't need to be set at the class level. You can define paths on each method in the class, but they will be sub-paths under the class path. 

> !!!! **Remember to restart the application between every code change.** !!!!

### Ex 2
Create a Service class using `@Bean` - solution branch: `ex-2`

1. Create a normal java class called `MovieService`. This should have a field variable which should contain a list of movie titles (as Strings) and a method that returns the list of movie titles. 
2. Now we need a bean (instantiated object of `MovieService`). Go to the `CinemaApplication`-class and here you should create a bean that returns a `MovieService` object.
3. In the controller class, create a field variable of the `MovieService`. Instantiate the field variable using the `@Autowired` annotation. 
   1. > This way spring will find the bean (instance) of `MovieService` we created in step 2 and inject it here in the controller class.
4. Inside the `@GetMapping` method in the Controller class, use the `MovieService` field variable to call the method that returns the list of movies, and make the Controller method return that list.
5. Your API should now return a list of Movie titles.


### Ex 2-b
Create a Service class using `@Service` - solution branch: `ex-2b`

1. Annotate the `MovieService` class with `@Service`. Now we can remove the bean created in the `CinemaApplication`-class.
2. Test the API endpoint again.


### Ex 3
Create a Movie DTO ([Data transfer object](https://www.baeldung.com/java-dto-pattern)) - solution branch: `ex-3`

1. Create a class called MovieDto.
   1. Create two field variables with getters and setters: `title` (String) and `ageLimit` (of type `Integer` not `int` to avoid NullPointerException).
   2. Create a constructor that can accept and set both field variables.
2. Now populate the previously created list of strings (Movie titles) in `MovieService` with MovieDto objects instead. 
3. You will now see that by calling your API, that Spring automatically serializes the java objects into JSON objects.

Now we want to create new Movies by doing an HTTP POST request to our API. By doing a POST request a new movie should be added in the list of movies: 
1. Create a new method in `MovieService` which takes in a MovieDto object as a parameter and adds it into our list of MovieDtos.
2. Create a new method in the Controller class. This should handle a POST request (you can guess the annotation we need 🙌).
   1. Add the newly created MovieDto class as a parameter to the new "post-movie-method". Annotate the parameter with `@RequestBody`
   2. Call the new "create-movie" method in `MovieService` and pass along the MovieDto object.
3. Use Postman or Curl to perform a POST request to our API with a JSON object representing a MovieDto object in the request body.

We will now see that Spring automatically deserializes the JSON object into a Java class, and when calling the GET endpoint we will see the new movie object in the response.

### Ex 4
Make use of a database - solution branch: `ex-4`

Now we will make use of an H2 database (in-memory-database). 

1. Create an [entity](https://www.baeldung.com/jpa-entities#define-entity) class called `Movie` to represent a table in our database. 
   1. The new `Movie` class should contain the same fields as the MovieDto class, but should have one more field variable: `id`.
   2. Create an empty (default) constructor. You can also create a constructor that takes in parameters, but you need an empty constructor anyways. Note that you should never set the value of the `id` field.
2. The id should be our Primary key (`@Id`), and should be auto generated (`@GeneratedValue`).
3. Create a new java Interface which should be annotated with the `@Repository` annotation. This will enable us to communicate with our database.
   1. In the Interface extend CrudRepository and use our entity class and primary key type (`Integer`) in combination with CrudRepository. This is to make it possible to communicate with the `Movie` table.
4. In the `MovieService` class, we will not make use of a database instead of the previously created list of MovieDto's:
   1. Remove the list of movies in `MovieService`.
   2. Autowire the new repository interface as a field variable.
   3. In the create-movie-method, convert the incoming `MoviceDto` object to a `Movie`-entity object (remember not to se the `id` property). Use the repository interface variable to [save](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html) the `Movie`-entity object to the database.
   4. In the get-movie-method, use the repository interface field variable to [query](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html) the database for all movies. Convert the `Movie`-entity objects to `MoviceDto's` and return all the `MoviceDto's`.
5. The database will now be empty each time you restart your application so test that you can create a new movie by doing a POST and GET request. 

> It's [best practise](https://www.amitph.com/spring-entity-to-dto/) not to use entity objects as request body input in controllers, and instead convert from Dto's to Entity objects before sending data to the database. 
> You should also convert entity objects to DTO's before returning them to the user.

### Ex 4b
View movies in our database - solution branch: `ex-4b`

We will now use the h2 console to view the data stored in our database.
1. To enable the H2 console paste in the following in your [application.yml](src/main/resources/application.yml) file:

   ```yaml
   spring:
    h2:
      console:
        enabled: true 
        path: /h2-console
    ```
   
2. Restart the application.
3. Look for a terminal output that says the following: `H2 console available at '/h2-console'. Database available at` and copy the database URL (looking something like this: `jdbc:h2:mem:XXXXX`)
4. Open the H2 console by going to this url [http://localhost:8080/h2-console](http://localhost:8080/h2-console).
5. Replace the JDBC URL with the one you copied in step 2.
6. The username should be: `sa`
7. Let the password field be empty.
8. Hit Connect and navigate the database.

### Ex 5
Create our first unit test and populate test database with data - solution branch: `ex-5`

We will use the [test class](src/test/java/com/endre/cinema/CinemaApplicationTests.java) already generated in the test folder. 

1. Autowire the `MovieService` class as a field variable.
2. Create your own test method or use the one that already exist, just make sure to annotate each test method with `@Test`.
3. In the test method use `MovieService` to retrieve the number of objects in the database. Then use `MovieService` to create a new movie.
4. Compare the number of objects in the database using `assertTrue`, `assertFalse` or whatever "assert" you think work the best. 


### Extra
- Create a configuration (`@Configuration`) class in the test folder that will populate our database with some test data.
- Make it possible to query moves by their age limit using query parameters on the `@GetMapping` method and implement the necessary logic.
- Create a new endpoint to delete a movie from the database.
