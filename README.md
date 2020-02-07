# legless-crane

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a90f767e283a4cf7b88e8bb3c344fded)](https://www.codacy.com/gh/inf112-v20/staring-horse?utm_source=github.com&utm_medium=referral&utm_content=inf112-v20/staring-horse&utm_campaign=Badge_Grade) [![Build Status](https://travis-ci.com/inf112-v20/legless-crane.svg?branch=master)](https://travis-ci.com/inf112-v20/legless-crane)


First iteration of RoboRally.

07.02 - Sprint 1


## Known bugs
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

Boards with more than one element on a single tile do not read correctly in Board.java, only the last element read in the given tile is saved.
