# Tetris Platformer

Game by:
- Nekopatis
- Pyriel


## Features:

Two Gamemodes:
- Solo
  - (Plays much like regular Tetris.)
- Duo: 1 "Piece" and 1 "Player"
  - Piece: (Much like regular Tetris.)
  - Player: Small, vulnerable character who can move and jump.
    - If a block is placed where the Player is, the Player gets eliminated which puts an end to the game.
    - Any (technical) square occupied by the Player counts as filled for line clearing.
    - Will you cooperate or fight each other? It's up to you to figure that out!

Controls (liste in-game):
- General:
  - Esc, P: Pause the game.
  - While the game is paused:
    - Any (assigned) key: Resume the game.
- Solo/Duo: "Piece"
  - Q: Move the Piece by 1 to the left.
  - D: Move the Piece by 1 to the right.
  - A: Rotate the Piece by 90° clockwise.
  - E: Rotate the Piece by 90° counter-clockwise.
  - S: Move the Piece by 1 downward.
- Duo: "Player"
  - ←: Move the Player to the left.
  - →: Move the Player to the right.
  - ↑: Make the Player jump.

Buttons:
- New Game Solo: Starts a new game* in Solo mode.
- New Game Duo: Starts a new game* in Duo mode.
<sub>* Game will start regardless of the game's current status and mode.<sub>


## How to execute

- On Windows: Execute `TetrisPlatformer.jar` with Java 21 or older.
- On Linux: At the root of the directory, run `java -jar TetrisPlatformer.jar`.


## Known issues

- If the Bottom of the game doesn't seem visible, try either of the following:
  - Maximize the window.
  - If Windows uses a different scaling (e.g: 125% for wider screens instead of 100%), switch back to 100%.