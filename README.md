# CMPE273-Project
CMPE273 Project

This application recommends courses to user based on the top trending skills in the industry. It leverages the following APIs:

StackExchange API(tags API)
Coursera (search , Courses, sessions )
Linkedin (r_basicprofile, email address)

The technologies used for the implementation are :

Spring Boot(rest application)
MongoDB(NoSQL database)
ThymeLeaf , Bootstrap js , Ajax , HTML5
Kafka (producer+consumer for messaging service for mail functionality)
Github(version control)

The main code base for version 2.0 is inside CourSuggest ver-2.0 folder.

The Kafka Consumer code base is in the folder KafkaConsumer. Producer is inside the CourSuggest ver-2.0 source folder.

Run Instructions:

There are four steps to run the project.

1. Start Zookeeper Server (Use Kafka version 2.9.1-0.8.2.1 (preferred)).
2. Start Kafka Server (Use Kafka version 2.9.1-0.8.2.1 (preferred)).
3. Start the KafkaConsumer from the code base specified above.
4. Start the CourSuggest Code ( REST + UI code ).

For Steps 1 and 2 use the reference http://kafka.apache.org/07/quickstart.html .
For the steps 3 and 4 use the gradle build and gradle bootRun commands.
