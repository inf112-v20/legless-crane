# legless-crane

[![Build Status](https://travis-ci.com/inf112-v20/legless-crane.svg?branch=master)](https://travis-ci.com/inf112-v20/legless-crane) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/a90f767e283a4cf7b88e8bb3c344fded)](https://www.codacy.com/gh/inf112-v20/legless-crane?utm_source=github.com&utm_medium=referral&utm_content=inf112-v20/staring-horse&utm_campaign=Badge_Grade) 
## How to build and run the game
This guide assumes that you have Maven, Java and Git installed
### Commandline
#### Get Sourcecode
```html
git clone https://github.com/inf112-v20/legless-crane/
```

#### Build with commandline
```html
cd C:\..\legless-crane
mvn clean install
```
#### Run with commandline
```html
cd C:\..\legless-crane\target
java -jar roborally-1.0.jar
```

### Alternative

Download [latest release](https://github.com/inf112-v20/legless-crane/releases) of source code (zip/tar.gz) from github

#### Build
1.  Import project to IntelliJ (or suitable alternative) from the `pom.xml` file.
2.  Run a clean install with Maven

#### Run
1.  Locate target directory in `..\legless-crane\target`
2.  Run `roborally-1.0.jar`

### Emergency measures
In case none of the options above work, run main method in "Application.java" from IntelliJ (or other IDEs).

## How to test the project
Run the automated tests using maven, either while building or separately. Manual tests can be completed as specified in 
[`src/test/java/testing/ManualTesting.md`](https://github.com/inf112-v20/legless-crane/blob/master/src/test/java/testing/ManualTesting.md)

## Known bugs
When resizing the game window clickListeners move coordinates, making the buttons useless.

When a robot has been hit by a laser, they are represented by a flame until they move to another tile.

When a robot occupies another robot's backup point as they respawn, both robots will occupy the same tile for a phase.

Currently, throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.
