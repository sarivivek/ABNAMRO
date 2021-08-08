
# ABNAMRO Coding Challenege


# Solution Approach

* The input file is assumed to be available in the classpath 
* User can get the daily summary report in csv format by invoking the below API
        http://localhost:8080/abnamro/getDailyReport
* The configuration of how to fetch data from the input file is done in application.yml as below
  * New field addition or index changes can be done here 
  * The  sumRequired tag indicated whether the field is to be grouped or summed up


    data:
 filefields: 
      client_information:
        startIndex: '3'
        endIndex: '18'
        sumRequired: false
        actionRequired: 'ADD'
      product_information:
        startIndex: '25'
        endIndex: '45'
        sumRequired: false
        actionRequired: 'ADD'
      quantity_long:
        startIndex: '52'
        endIndex: '62'
        sumRequired: true
        actionRequired: 'ADD'
      quantity_short:
        startIndex: '62'
        endIndex: '72'
        sumRequired: true
        actionRequired: 'SUB'


# How to run the application

* Clone the source code from git 
* Place the input file in the location - abnamro/src/main/resources
* Execute as spring boot application
* Log levels can be controlled in logback-spring.xml





