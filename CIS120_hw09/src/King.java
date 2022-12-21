

public class King extends Piece {

    private int timesMoved;
    
    public King(PieceColor pc) {
        super(pc, PieceType.KING);
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
        boolean isDiagonal = Math.abs(toX - fromX) == Math.abs(toY - fromY) ? true : false;

        switch (this.getColor()) {
            case WHITE:
               
            // Moving to open square
                if (!occupied && (fromX == toX || fromY == toY || isDiagonal) 
                    && (Math.abs(toX - fromX) <= 1 && Math.abs(toY - fromY) <= 1)) {
                    timesMoved++;
                    return true;
                }

                if (occupied && pieceAtDestination.getColor() == PieceColor.BLACK && 
                    (fromX == toX || fromY == toY || isDiagonal) 
                    && (Math.abs(toX - fromX) <= 1 && Math.abs(toY - fromY) <= 1)) {
                    timesMoved++;
                    return true;
                }
                break;

            case BLACK:
                
                if (!occupied && (fromX == toX || fromY == toY || isDiagonal) 
                    && (Math.abs(toX - fromX) <= 1 && Math.abs(toY - fromY) <= 1)) {
                    timesMoved++;
                    return true;
                }

                if (occupied && pieceAtDestination.getColor() == PieceColor.WHITE && 
                    (fromX == toX || fromY == toY || isDiagonal) && 
                    (Math.abs(toX - fromX) <= 1 && Math.abs(toY - fromY) <= 1)) {
                    timesMoved++;
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }
    
    public int getMovesMade() {
        return timesMoved;
    }
}
