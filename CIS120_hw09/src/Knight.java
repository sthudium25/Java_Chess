

public class Knight extends Piece {

    public Knight(PieceColor pc) {
        super(pc, PieceType.KNIGHT); 
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
           
                if ((!occupied && Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 2) 
                    || (Math.abs(toX - fromX) == 2 && Math.abs(toY - fromY) == 1)) {
                    return true;
                }
                if (occupied && (Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 2) 
                    || (Math.abs(toX - fromX) == 2 && Math.abs(toY - fromY) == 1)
                    && pieceAtDestination.getColor() == PieceColor.BLACK) {
                    
                    return true;
                }
                break;
            
            case BLACK :
            
                if ((!occupied && Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 2) 
                    || (Math.abs(toX - fromX) == 2 && Math.abs(toY - fromY) == 1)) {
                    return true;
                }
                if (occupied && (Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 2) 
                    || (Math.abs(toX - fromX) == 2 && Math.abs(toY - fromY) == 1)
                    && pieceAtDestination.getColor() == PieceColor.WHITE) {
                  
                    return true;
                }
                break;
            default :
                return false;
        }     
        
        return false;
    }
    
    

}
