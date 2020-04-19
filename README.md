# Adrenaline
![ADRENALINE README LOGO](https://github.com/lentinip/ing-sw-2019-Lentini-Marazzi-Marini/raw/master/AdrenalineReadmeLogo.jpg)

## What is Adrenaline?
>Adrenaline is a first-person shooter on your gaming table. Grab some ammo, grab a gun, and start shooting. Build up an arsenal for a killer turn. Combat resolution is quick and diceless. And if you get shot, you get faster!

>For 3 to 5 players. Plays in about 60 minutes.

More informations about the game [here](https://czechgames.com/en/adrenaline/).

## Getting Started
This game was developed as a computer game for the final examination of the Software engineering course at Politecnico di Milano (A.Y. 2018/2019) - Bachelor of Science thesis project.

## Index of the README
* [Prerequisites](#prerequisites)
* [Usage](#usage)
* [Specification covered](#specification)
* [Requirements](#requirements)
* [Built with](#built)
* [Authors](#authors)
* [License](#license)

## Prerequisites
The game requires [Java 8] to run.

## Usage
>The server must be up and running to allow you to play.

### Server

```bash
java -jar server.jar
```

> You can change the timers of the game.
To do that edit the configurations.json file that has to be outside of the jar (as default the program uses the one inside the jar).


### Client

```bash
java -jar client.jar [cli/gui] [ip server]
```

#### Options
* `[cli/gui]` - type `cli` for the Command Line Interface or `gui` for the Graphical User Interface (default: `gui`)
* `[ip server]` - you can specify the ip address of the server (default: `localhost`)

<a name="specification"></a>
## Specification covered
* Multiple matches (one server can host more than one match simultaneously)
* Complete rules
* Socket
* RMI
* CLI
* GUI

## Requirements
The requirements can be found [here](https://github.com/lentinip/ing-sw-2019-Lentini-Marazzi-Marini/blob/master/Documento%20requisiti.pdf). 

<a name="built"></a>
## Built with
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Java 8]
* [JavaFx](https://openjfx.io)
* [Maven](https://maven.apache.org)
* [JUnit](https://junit.org/junit5/)
* [SonarQube](https://www.sonarqube.org)

### External libraries used
The external libraries we used to implement some game's features are linked below.

| Library | Link | Use |
| ------ | ------ | ------ |
| GSON | https://github.com/google/gson | We use this library to load weapons, poweups and settings from file. We also use this library to serialize and deserialize the content of messages exchanged between server and client. |

## Authors
* [Pietro Lentini](https://github.com/lentinip)
* [Michele Marazzi](https://github.com/Mi97ch)
* [Marco Marini](https://github.com/poligenius)

## License
This project is distributed under the terms of the Apache License v2.0.
See file [LICENSE] for further information.

[Java 8]: <https://www.java.com/it/download/>
[LICENSE]: <https://github.com/lentinip/ing-sw-2019-Lentini-Marazzi-Marini/blob/master/LICENSE>
