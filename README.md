# legless-crane
First iteration of RoboRally.

07.02 - Sprint 1


## Known bugs
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

Boards with more than one element on a single tile do not read correctly in Board.java, only the last element read in the given tile is saved.
