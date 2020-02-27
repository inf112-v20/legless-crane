# legless-crane

[![Build Status](https://travis-ci.com/inf112-v20/legless-crane.svg?branch=master)](https://travis-ci.com/inf112-v20/legless-crane) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/a90f767e283a4cf7b88e8bb3c344fded)](https://www.codacy.com/gh/inf112-v20/legless-crane?utm_source=github.com&utm_medium=referral&utm_content=inf112-v20/staring-horse&utm_campaign=Badge_Grade) 
## How to build and run the game

### Commandline:
Be aware that Java and Maven will be required

#### Get Sourcecode
```
git clone https://github.com/inf112-v20/legless-crane/
```

#### Build with commandline
```
cd C:\..\legless-crane
mvn clean install
```
#### Run with commandline
```
cd C:\..\legless-crane\target
java -jar roborally-0.2.jar
```

### Alternative:

Download [latest release](https://github.com/inf112-v20/legless-crane/releases) of source code (zip/tar.gz) from github

####Build:
1. Import project to IntelliJ (or suitable alternative) from the `pom.xml` file.

2. Run a clean install with Maven

####Run:
1. Locate target directory in `..\legless-crane\target`

2. Run `roborally-0.2.jar`


## Known bugs
When executing .jar file, it crashes on some PCs.

Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

Boards with more than one element on a single tile do not read correctly in Board.java, only the last element read in the given tile is saved.
