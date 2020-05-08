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


### Player can chose and un-chose program cards before the look in their choice
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

### If a robot stops at a yellow belt at the end of a phase, the robot will move one step in the direction indicated by belt graphic
#### (Last tested 08.05.20)
**Process**
-   At the end of a phase a robot steps on a yellow belt

**Aim**
-   The robot moves one step in the direction indicated by arrow in belt graphic

**Result**
-   Pass

### If a robot stops at a blue belt at the end of a phase, the robot will move two steps in the direction indicated by belt graphic
#### (Last tested 08.05.20)
**Process**
-   At the end of a phase a robot steps on a blue belt

**Aim**
-   The robot moves two steps in the direction indicated by arrow in belt graphic

**Result**
-   Pass

### All cards are used (all moves made unless dead)
#### (Last tested 08.05.20)
**Process**
-   Launch application and start a game.
-   Pick any cards (should not matter, just don't kill yourself and get game over)
-   Confirm that the cards you've picked are executed correctly

**Aim**
-   All cards should  be executed unless the player dies

**Result**
-   Pass

### All cards played by players are executed in order of priority (descending)
#### (Last tested 08.05.20)
**Process**
-   Launch application and start a game.
-   Pick any cards (should not matter, just don't kill yourself and get game over)
-   Confirm that the cards you've picked are executed in correct order

**Aim**
-   Move forward cards should be executed before rotation cards, move 3 before move 2 before move 1 etc.

**Result**
-   Pass

### Player is affected by elements on the board at the correct time
#### (Last tested 08.05.20)
**Process**
-   Launch application and start a game.
-   Pick any cards that move you towards elements on the board(should not matter, just don't kill yourself and get game over)
-   Confirm that you are affected by the board elements in the correct order and at the correct time. 

**Aim**
-   Picking up flags and repairing should only happen at the end of a round if you're standing on that tile etc.
-   Consult rulebook for the entire list

**Result**
-   Pass

### GameState Cycle
#### (Last tested 08.05.20)
**Process**
-   Launch application and start a game.
-   Pick any cards (should not matter, just don't kill yourself and get game over)
-   Confirm that the GameState (listed at the bottom) cycles through correctly for each phase of the game

**Aim**
-   Order for a new round should be:

DEAL_CARDS -> REVEAL_CARDS -> MOVE_PLAYER -> MOVE_BOARD -> FIRE_LASERS -> FIRE_PLAYER_LASER -> RESOLVE_INTERACTIONS -> CLEANUP
-   Order for each phase in that round should be the same, but skipping DEAL_CARDS

**Result**
-   Pass