
public class Rook extends Piece {
    
    private int timesMoved; 
    
    public Rook(PieceColor pc) {
        super(pc, PieceType.ROOK);
        timesMoved = 0;
    }
    

    @Override
    public boolean checkPieceTypeMoveIsValid(ChessMove move, Cell[][] board) {
        int toX = move.getDestinationX();
        int toY = move.getDestinationY();
        int fromX = move.getCurrentX();
        int fromY = move.getCurrentY();
        
        Piece pieceAtDestination = board[toX][toY].getPiece();
        boolean occupied = board[toX][toY].isOccupied();
        
        switch (this.getColor()) {
            case WHITE:
            
            // Moving to open square
                if (!occupied && (fromX == toX || fromY == toY)) {
                    timesMoved++;
                    return true;
                }
            
                if (occupied && pieceAtDestination.getColor() == PieceColor.BLACK 
                    && (fromX == toX || fromY == toY)) {
                    timesMoved++;
                    return true;
                }
               
                break;
        
            case BLACK : 
            
                if (!occupied && (fromX == toX || fromY == toY)) {
                    timesMoved++;
                    return true;
                }
            
                if (occupied && pieceAtDestination.getColor() == PieceColor.WHITE 
                    && (fromX == toX || fromY == toY)) {
                    timesMoved++;
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }
    
    public int getNumMoves() {
        return this.timesMoved;
    }
    

}
