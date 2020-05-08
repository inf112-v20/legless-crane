## Manual testing

**Note**: These tests are run by moving the robot in the desired way by choosing programcards.

### Open game
#### (Last tested 08.05.20)
**Process**
-   Run the program
-   In menu screen chose "Let's play"

**Aim**
-   Game should start
-   Player can start choosing program cards for their robot

**Result**
-   Pass

### Close/Quit game
#### (Last tested 08.05.20)
**Process**
-   Run the program
-   In menu screen chose "quit"

**Aim**
-   Game should be exited
-   Game window should close

**Result**
-   Pass

### Player moves off the board, respawn and lose 1 life
#### (Last tested 08.05.20)
**Process**
-   Start Game
-   Chose movement cards that move player of the board

**Aim**
-   Player respawn with one less life than before moving of the board
-   Player respawn with Max_Health - 2 after moving of the board.
-   Player respawn at the most resent backup point

**Result**
-   Pass


### PLayer can chose and un-chose program cards before the look in their choice
#### (Last tested 08.05.20)
**Process**
-   At beginning of a round player can choose cards from their "hand"
-   Before the round is locked-in the player can undo their choice and change their cards

**Aim**
-   Player can undo their program card choices as long as the round is not yet locked-in

**Result**
-   Pass

### If a robot loses liefe so they have life < 1, the game over screen will appear
#### (Last tested 08.05.20)
**Process**
-   Player has chosen program cards that leads the robot to take damage
-   A robot has lost enough life to die

**Aim**
-   Game over screen should appear

**Result**
-   Pass

### A robot can collect all the flags. as long as they visit in the correct order (1,2,3).
#### (Last tested 08.05.20)
**Process**
-   Player pick up flag 1
-   Player pick up flag 2
-   Player pick up flag 3
-   If player try to pick up a flag out of order they will not get it, but a new backup is created.

**Aim**
-   All flags can be picked up in the correct order

**Result**
-   Pass

### If a robot has collected all the flags on the board the WinScreen will appear
#### (Last tested 08.05.20)
**Process**
-   A robot has collected all flags in correct order

**Aim**
-   WinScreen declaring victory appear

**Result**
-   Pass
