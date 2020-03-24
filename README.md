# legless-crane

[![Build Status](https://travis-ci.com/inf112-v20/legless-crane.svg?branch=master)](https://travis-ci.com/inf112-v20/legless-crane) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/a90f767e283a4cf7b88e8bb3c344fded)](https://www.codacy.com/gh/inf112-v20/legless-crane?utm_source=github.com&utm_medium=referral&utm_content=inf112-v20/staring-horse&utm_campaign=Badge_Grade) 
## How to build and run the game
This guide assumes that you have Maven, Java and Git installed
### From Commandline:
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
java -jar roborally-0.3.jar
```

### From IDE:

Download [latest release](https://github.com/inf112-v20/legless-crane/releases) of source code (zip/tar.gz) from github

#### Build
1.  Import project to IntelliJ (or suitable alternative) from the `pom.xml` file.
2.  Run a clean install with Maven

#### Run
1.  Locate target directory in `..\legless-crane\target`
2.  Run `roborally-0.3.jar`

### If all else fails
Run main in "Application.java" with your IDE of choice

## Known bugs
~~When executing .jar file, it crashes on some PCs.~~ - fixed in version 0.3

~~Boards with more than one element on a single tile do not read correctly in Board.java, only the last element read in the given tile is saved.~~ - fixed in version 0.3

Left rotate sometime prevents robot moving back and ahead, until you use right rotate.

When resizing the game window clickListeners move coordinates, making the buttons useless.

Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.
