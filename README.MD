## **Test task for Return on Intelligence**   
**Task:**
Program should monitor the incoming folder for log files (i.e file1.csv). Log file contains information about user navigation. Log file is comma delimited file (see CSV format) with header. Each line consists of four fields: time (unix timestamp), ID user, URL (to which the user has passed), number of seconds (how much time the user spent).

Implement calculation of the average time spent by the user (ID User) on the page. Put the result into a CSV file with “avg_” prefix (i.e. avg_file1.csv) and with header ordered by ID User in ASC order. Fields and order described below: ID user, URL, Average

Calculation of average time should take time of session into account, and provide statistic for each day separately. For each day additional string should be put in file to separate dates (see example below).

In case if session was started on one day and ended on another – for each day we need to count part of session time, which was spend on each day from particular session and this amount should be distributed between this dates.  
**Requirements:**
- [X] program should be created in OOP concept
- [X] each file should be processed in independent threads
- [X] up to 10 files should be processed in parallel; other files should be kept in queue and processed by first free thread
- [X] incoming files can be deleted or moved if required
- [X] input and output folders should be pointed in property file
- [X] program should starts from command line and work until killed or stopped
- [X] instruction how to run the program

**How to run**  
1. Clone repository
```
git clone git@github.com:alterG/roi-test.git
```
2. Build application with maven
```
mvn install
```
3. Execute jar-file
```
java -jar roi-test-1.0-SNAPSHOT.jar
```

**Available commands**   
* calc - to calculate sessions average visit time
* mov filename directory - to move file to directory
* rm filename - to delete file
* ls - to show files in work directory
* cd dirname - to change work directory
* cat filename - to print file content
* help - show menu
* exit - close program

**Available commands**   
Author: Igor Shchipanov  
Email: oneyearday@gmail.com  
phone: 8 (911) 827-72-00  
