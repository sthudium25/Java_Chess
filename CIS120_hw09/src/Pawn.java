
public class Pawn extends Piece {

    public Pawn(PieceColor pc) {
        super(pc, PieceType.PAWN);
    }

    @Override
    public boolean checkPieceTypeMoveIsValid(ChessMove move, Cell[][] board) {
        int toX = move.getDestinationX();
        int toY = move.getDestinationY();
        int fromX = move.getCurrentX();
        int fromY = move.getCurrentY();

        boolean occupied = board[toX][toY].isOccupied();
        Piece pieceAtDestination = occupied ? move.getCapturedPiece() : null;
        boolean shouldCheckEnPassant = Chess.getMovesHistory().size() == 0 ? false : true;       
        ChessMove lastMove = shouldCheckEnPassant ? Chess.getPreviousMove() : null;
        
        
        switch (this.getColor()) {
            case WHITE:

                if (fromY == 1 && (toY - fromY == 1 || toY - fromY == 2) && !occupied) {
                    
                    return true;
                }
            // Pawn single move and auto-promote to Queen
                if (toY - fromY == 1 && fromX == toX && !occupied) {
                    if (toY == 7) {
                        move.promotePawnToQueen();
                        return true;
                    }
                
                    return true;
                }

                if (occupied && Math.abs(toX - fromX) == 1 && toY - fromY == 1
                    && pieceAtDestination.getColor() == PieceColor.BLACK) {

                    return true;
                }
                if (shouldCheckEnPassant && !occupied 
                    && Math.abs(toX - fromX) == 1 && toY - fromY == 1) {
                    return checkEnPassant(move, lastMove, occupied);
                }
                break;

            case BLACK:

                if (!occupied && fromY == 6 && (toY - fromY == -1 || toY - fromY == -2)) {
                    
                    return true;
                }
            // Pawn single move and auto-promote to Queen
                if (!occupied && toY - fromY == -1 && fromX == toX) {
                    if (toY == 0) {
                        move.promotePawnToQueen();
                        return true;
                    }

                    return true;
                }

                if (occupied && Math.abs(toX - fromX) == 1 && toY - fromY == -1 
                        && pieceAtDestination.getColor() == PieceColor.WHITE) {
                    return true;
                }
                if (shouldCheckEnPassant && !occupied
                    && Math.abs(toX - fromX) == 1 && toY - fromY == -1) {
                    return checkEnPassant(move, lastMove, occupied);
                }          
                break;
            default:
                return false;
        }

        return false;
    }
    
    private boolean checkEnPassant(ChessMove move, ChessMove lastMove,
            boolean occupied) {
        Piece previous = lastMove.getPiece();
        if (previous.getType() == PieceType.PAWN) {
            switch (previous.getColor()) {
                case WHITE :
                    if (lastMove.getCurrentY() == 1) {
                        if (lastMove.getDestinationY() == move.getCurrentY()
                                && Math.abs(lastMove.getDestinationX() - move.getCurrentX()) == 1 
                                && !occupied) {
                            move.enPassantAddCapturedPiece(lastMove);
                            Chess.getCell(lastMove.getDestinationX(), 
                                    lastMove.getDestinationY()).setNewPiece(null);
                            return true;
                        }
                    }
                    break;
                
                case BLACK :
                    if (lastMove.getCurrentY() == 6) {
                        if (lastMove.getDestinationY() == move.getCurrentY()
                                && Math.abs(lastMove.getDestinationX() - move.getCurrentX()) == 1 
                                && !occupied) {
                            move.enPassantAddCapturedPiece(lastMove);
                            Chess.getCell(lastMove.getDestinationX(), 
                            lastMove.getDestinationY()).setNewPiece(null);
                            return true;
                        }
                        
                    }                    
                    break;
                default:
                    return false;
            }

        }
        return false;
    }
}
