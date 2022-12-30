# blogProject-api
team project for growth

#spec
* spring 4.0.0  
* java 8
* tomcat 8.5
* MySQL

#test
* TestController > method
1. Test server : http://127.0.0.1:8080/blog_project 
    --> "Hello World!!"
2. Test contoller : http://127.0.0.1:8080/blog_project/testController 
    --> "TEST CONTROLLER SUCESS!!!!"
3. Test Jdbc (MySQL) : http://127.0.0.1:8080/blog_project/testJdbcConnection
    --> "JDBC CONNECTION SUCCESS!!!"
4. Else if test : Junit

#order
1. client > filter > dispatcher-servlet > intercepter > controller > service > repository(mapper) 

#controller
1. @RestController, @Controller
2. @RequestMapping, @GetMapping
3. ResponseEntity<?>
4. DTO or HttpServletRequest (Map)
