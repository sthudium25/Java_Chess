
public class Queen extends Piece {

    public Queen(PieceColor pc) {
        super(pc, PieceType.QUEEN);
     
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
                if (!occupied && (fromX == toX || fromY == toY 
                    || (Math.abs(toX - fromX) == Math.abs(toY - fromY)))) {
                    return true;
                }
            
                if (occupied && pieceAtDestination.getColor() == PieceColor.BLACK 
                    && (fromX == toX || fromY == toY 
                    || (Math.abs(toX - fromX) == Math.abs(toY - fromY)))) {
                    return true;
                }
                break;
        
            case BLACK : 
                
                if (!occupied && (fromX == toX || fromY == toY 
                    || (Math.abs(toX - fromX) == Math.abs(toY - fromY)))) {
                    return true;
                }
            
                if (occupied && pieceAtDestination.getColor() == PieceColor.WHITE 
                    && (fromX == toX || fromY == toY 
                    || (Math.abs(toX - fromX) == Math.abs(toY - fromY)))) {
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

}
