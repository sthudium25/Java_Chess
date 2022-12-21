
public class Bishop extends Piece {

    public Bishop(PieceColor pc) {
        super(pc, PieceType.BISHOP);
        
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
               
                if (!occupied && (Math.abs(toX - fromX) == Math.abs(toY - fromY))) {
                    return true;
                }
                if (occupied && pieceAtDestination.getColor() == PieceColor.BLACK
                    && (Math.abs(toX - fromX) == Math.abs(toY - fromY))) {
                    return true;
                }
                break;
            case BLACK:
               
                if (!occupied && (Math.abs(toX - fromX) == Math.abs(toY - fromY))) {
                    return true;
                }
                if (occupied && pieceAtDestination.getColor() == PieceColor.WHITE
                    && (Math.abs(toX - fromX) == Math.abs(toY - fromY))) {
                    return true;
                }
                break;
            default :
                return false;
        }
        return false;
    }


}
