=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: sthudium
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D array: I have stored my chess board as a 2D array of Cell objects, each of which holds an x and y coordinate and a Piece object. I chose this because it seemed to best model the actual game of Chess.

  2. Collections: I use a number of collections in the game. Primarily, all of the completed moves are stored in a collection.  I use this to update the GUI with a text representation of each completed move as well as to implement complex logic like en passant. 

  3. Dynamic Dispatch: I used the class hierarchy that intrinsic to Chess to utilize dynamic dispatch. I have an abstract Piece class that contains a single abstract method, checkPieceTypeMoveIsValid(). This method is invoked when the move() method in the model is called. The static type is always Piece, but the checkPiece method is called dynamically depending on the piece that was selected for the move.

  4. Testable Component: I have two test files that attempt to separate more simplistic tests from more complex game situation/logic tests. 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Chess is a model for the game that contains the backend of how the game is played. It is comprised of an 8x8 2D array of Cells. Cell represents a single space in the Chess model; each cell contains an x, y coordinate and a current Piece that occupies it (or null). The abstract Piece class has a color and a type (bishop, pawn, etc) and a method defined in each type-specific subclass that checks if a piece can perform the requested move. ChessMove stores an instance of a move that a player tries to perform. This is then checked against the piece-specific move requirements and a MoveChecker class which checks more basic things like out of bounds, correct color, etc. ChessBoard shows and reflects changes to the model. 

The GUI: The run method in Game creates a status panel, a chess board and side bar for menu options. The ChessBoard which extends JPanel sets up both the Chess model itself as well as a list of CellComponents (extend JPanel), which are placed into an 8x8 GridLayout. I then set up each of these CellComponents to reflect game state. This is also where I have my mouse listeners for moving pieces (first click gets the Cell and a second puts it at the destination square if there was a piece in the cell and the move was valid). I also tried to wire two side panels, the Move History and the Captured Pieces, to respond to the CellComponent mouse listener but I was unable to get them to respond. These classes have methods to get the collection of moves or captured pieces, respectively, and I wanted to then add a text representation of each piece in the collection to the proper panel. 


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

I feel like I've run into stumbling blocks the entire time. Getting the state to properly model all of the information needed to keep track of whether or not the king is in check/checkmate or if a move led to check or a king move placed him in check, was beyond me. Each of those pieces of information is necessary for implementing castling, so I wasn't able to do that either. I also feel like using a 2D array may not be the best idea if I were to try this again. It's so much more difficult/costly to check for membership/update state at individual coordinates. As for the GUI, I'm not sure where exactly I've gone wrong, but it is not behaving as I had hoped. Maybe I should have kept the piece icons just as JLabels on the ChessBoard itself? This didn't seem like very good OOP practice though, so I made an array to hold 64 instances of the cells.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
I think my design is decent, though I have more static variables/methods than I would have liked. But I think I did a good job separating the model from the pieces, cells, and moves. I started to feel much less confident when it came to the GUI portion. I tried to separate components here as well, and I'm unclear if that is partially responsible for my struggles. 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

I used the java docs and java tutorials quite a bit, especially for JPanel, JLabel, JTable, JOptionPane, and ImageIO.

