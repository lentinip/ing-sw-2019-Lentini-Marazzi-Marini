*** README FILE FOR ing-sw-2019-Lentini-Marazzi-Marini ***


REGOLE IMPLEMENTATE:
Regole Complete + CLI + GUI + Socket + RMI + 1 FA

FA = Partite Multiple.



HOW TO START THE GAME

- How to start the server:

java -jar server.jar

- How to start the client with GUI (server localhost):

java -jar client.jar 

Or

java -jar client.jar gui

- How to start the client with GUI (with the ip of the server):

java -jar client.jar gui <ip server>

- How to start the client with GUI (server localhost):

java -jar client.jar cli

- How to start the client with CLI (with the ip of the server):

java -jar client.jar cli <ip server>

*NOTE*

There's the possibility to change the timers of the game.
To do that change the configurations.json file that has to be outside of the jar.

If the configurations.json file is not outside of the jar the program uses the one inside the jar.

