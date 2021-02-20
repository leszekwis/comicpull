# comicpull

## info
This project will pull the latest 10 comics from Poorly Drawn Lines and the latest 10 comics from XKCD
The comics will be returned as a JSON array sorted by publication date in descending order.

## running

    ./gradlew joobyRun

## building

    ./gradlew build

## docker

     docker-compose up -d
     
## api
Application starts up on port 8080 and has the following routes

    GET / -> returns the welcome message;
    GET /comics -> returns the comics in JSON format
