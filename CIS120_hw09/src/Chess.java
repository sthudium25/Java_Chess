import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


// This is the model class for the game
public class Chess {

    private static final int DIM = 8;
    private static Cell[][] board = new Cell[DIM][DIM];
    private int numTurns;
    private boolean player1;
    private boolean checkMate;
    private boolean gameOver;
    private boolean kingInCheck;
    
   
    private static LinkedList<ChessMove> allMoves;
    private static List<Piece> capturedPieces; 
     
    public Chess() {
        setUpCells();
        numTurns = 0;
        player1 = true;
        
        allMoves = new LinkedList<ChessMove>();
        capturedPieces = new LinkedList<Piece>();
        
        
    }
    
    public void reset() {
        new Chess();
    }
 
    public boolean getCurrentPlayer() {
        return player1;
    }
    
    public static Cell[][] getBoard() {
        return board;
    }
    
    // A getter method for getting an individual cell on the board
    public static Cell getCell(int x, int y) {
        return board[x][y];
    }
    
    
    public static List<Piece> getCapturedPieces() {
        return capturedPieces;
    }
    
    public static LinkedList<ChessMove> getMovesHistory() {
        return allMoves;
    }
    /*
     *  A setter method to update the state of a cell at given coords when 
     *  a valid move occurs
     */
    
    public void setNewCell(Cell c) {
        board[c.getRank()][c.getFile()] = c;
    }
    
    
    private void setUpCells() {
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {               
                board[i][j] = new Cell(i, j);
            }
        }
        setInitialPieceLocation();
    }
    
    
    
    private void setInitialPieceLocation() {
        if (board != null) {
            for (int x = 0; x < 8; x++) {
                getCell(x, 1).setNewPiece(new Pawn(PieceColor.WHITE));
                getCell(x, 6).setNewPiece(new Pawn(PieceColor.BLACK));
            }    
            setUpRooks();
            setUpKnights();
            setUpBishops();
            setUpQueens();
            setUpKings();
        }
    }
    
    private void setUpRooks() {
        getCell(0,0).setNewPiece(new Rook(PieceColor.WHITE));
        getCell(7,0).setNewPiece(new Rook(PieceColor.WHITE));
        getCell(0,7).setNewPiece(new Rook(PieceColor.BLACK));
        getCell(7,7).setNewPiece(new Rook(PieceColor.BLACK));
    }
    
    private void setUpKnights() {
        getCell(1,0).setNewPiece(new Knight(PieceColor.WHITE));
        getCell(6,0).setNewPiece(new Knight(PieceColor.WHITE));
        getCell(1,7).setNewPiece(new Knight(PieceColor.BLACK));
        getCell(6,7).setNewPiece(new Knight(PieceColor.BLACK));
    }
    
    private void setUpBishops() {
        getCell(2,0).setNewPiece(new Bishop(PieceColor.WHITE));
        getCell(5,0).setNewPiece(new Bishop(PieceColor.WHITE));
        getCell(2,7).setNewPiece(new Bishop(PieceColor.BLACK));
        getCell(5,7).setNewPiece(new Bishop(PieceColor.BLACK));
    }
    
    private void setUpQueens() {
        getCell(4,0).setNewPiece(new Queen(PieceColor.WHITE));  
        getCell(4,7).setNewPiece(new Queen(PieceColor.BLACK));
    }
    
    private void setUpKings() {
        getCell(3,0).setNewPiece(new King(PieceColor.WHITE));  
        getCell(3,7).setNewPiece(new King(PieceColor.BLACK));
            
    }
    
    private int checkForWinner() {
        if (checkMate) {
            gameOver = true;
            if (player1) {

                return 1;
            } else {

                return 2;
            }
        }

        return 0;
    }
    
    public boolean move(ChessMove move, boolean player) {
        MoveChecker currMove = new MoveChecker(move, player);
        if (gameOver) {
            return false;
        }
        if (move.getPiece() == null) {
            return false;
        }

        if (move.getPiece().checkPieceTypeMoveIsValid(move, Chess.board) && 
                move.validBoardMove(player)) {

            if (move.getPiece().getType() == PieceType.KING) {
                if (!currMove.validateKingDodgeMove(move, player)) {
                    return false;
                }
                if (move.getPiece().getColor() == PieceColor.WHITE) {
                    currMove.setWhiteKingHasMoved();
                    currMove.setKingCoords(move.getDestinationX(), 
                            move.getDestinationY(), player);
                } else {
                    currMove.setBlackKingHasMoved();
                    currMove.setKingCoords(move.getDestinationX(), 
                            move.getDestinationY(), player);
                }

            }

            if (move.getCapturedPiece() != null) {
                capturedPieces.add(move.getCapturedPiece());
            }

            addMoveToHistory(move);
            getCell(move.getDestinationX(), move.getDestinationY()).setNewPiece(move.getPiece());
            getCell(move.getCurrentX(), move.getCurrentY()).setNewPiece(null);
            
            kingInCheck = currMove.checkSubsequentMoveForKingAttack(move, player);
//            kingInCheck = currMove.updateKingCheckStatus(player);
            

            if (kingInCheck) {
                checkMate = kingInCheckMate(currMove, player);
            }

            numTurns++;
            if (checkForWinner() == 0) {
                player1 = !player1;
            }
            
            return true;
        }

        return false;
    }

    
    public int getTurns() {
        return numTurns;
    }
    
    public void addCapturedPiece(Piece p) {
        capturedPieces.add(p);
    }
    
    public void addMoveToHistory(ChessMove move) {
        allMoves.add(move);
    }
    
    public static ChessMove getPreviousMove() {
        return getMovesHistory().getLast();
    }    
    
    private boolean kingInCheckMate(MoveChecker currMove, boolean player) {
        if (player) {
            Collection<Cell> kingDodgeAttempts = currMove.getEnemyKingsAdjCells(player);
            Collection<Cell> cellsAttackedByWhite = currMove.getEnemyKingCellsAttacked(player);
            
            if (cellsAttackedByWhite.containsAll(kingDodgeAttempts)) {
                return true;
            }   
        } else {
            Collection<Cell> kingDodgeAttemps = currMove.getEnemyKingsAdjCells(player);
            Collection<Cell> cellsAttackedByBlack = currMove.getEnemyKingCellsAttacked(player);
            
            if (cellsAttackedByBlack.containsAll(kingDodgeAttemps)) {
                return true;
            }
        }
        return false;
    }
    
   
    
    public boolean getKingCheckStatus() {
        return this.kingInCheck;
    }
    
    public boolean getCheckMateStatus() {
        return this.checkMate;
    }
    
}
