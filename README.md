# ITALIAN DRAUGHTS 

This repository contains the Java implementation of the classic Italian Draughts game, made by Alessandro Cesa, Federico Monti Bragadin and Simone Divo for the Software Development Methods course at Università degli Studi di Trieste.

The game can be played either on a single computer, or with the two players playing against each other each one on his computer.

## How to run the software
In order to run the software and start playing, you can:
* On this Github page go to `Releases`, click on `Italian-Draughts`, then in the Assets click on `Italian-Draughts-1.0-SNAPSHOT.jar` in order to download the jar file. Then on your computer open the folder where you have the downloaded jar file in a terminal, and run `java -jar Italian-Draughts-1.0-SNAPSHOT.jar`

*If you want the whole source code, you can clone this repository, and then open the project's folder in a terminal and run the command `./gradlew run`

### Requirements

It is necessary to have the Java Development Kit 21 (and Gradle 8.5 if you want to download the source code and build it)

## Playing on one computer
If both players want to play on the same computer, they just need to click on `New Game`, insert their names, select the team (Black or White, of course the 2 players can't select the same team) and the game will start.

## Playing on two computers
To run multiplayer mode is necessary to connect two device together through hotspot. The device who run the hotspot have to run the game as Host, the other one have to run the game as Guest and both have to click on `Multiplayer` and insert the IP of the Host's hotspot. At this point you'll have to wait the line that indicates who's turn is to appear and the game will start.
