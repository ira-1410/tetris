# Tetris in Java

A recreation of the classic Tetris game entirely in Java with Java AWT/Swing.

## Play the Game!
1. **Clone from directory**:
   ```
   git clone https://github.com/ira-1410/tetris
   cd tetris/bin
   java tetris.Main
   ```
2. **Controls**
   - Use Arrows or WASD to move the tetromino
   - Up arrow / W key rotates the piece
   - Press X to drop the block down.
   - Press SPACE to pause the game
   - Press R to restart at any time

## Key Features
1. **Collision Detection**: Detecting when a tetromino collides with the boundaries of the grid or with other pieces, before moving or rotating it.
2. **Line Clearing**: Checking for completed lines and removing them from the grid.
3. **Score Calculation**: Tracking the score, with 10 points for each line cleared, and 1 point for each manual drop of a tetromino (pressing the down key).
4. **Increasing Difficulty**: Blocks start falling faster as the game continues.

## Project Structure
`/src` files:
- Main.java
- GamePanel.java is a JPanel to draw and run the game.
- GameManager.java implements the game logic
- KeyEventHandler.java is the Key Listener
- Sound.java is a controller the background music and sound effects.

`/tetromino` files:
- Block.java is the building block extending Rectangle
- Tetromino.java is the base class for all tetrominos
- Individual classes for each tetromino shape with naming convention: "Tetromino" + Letter. Refer to [Wikipedia](https://en.wikipedia.org/wiki/Tetromino#One-sided_tetrominoes) for the letter naming of each tetromino.
