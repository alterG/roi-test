## **Test task for Return on Intelligence**   
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
