

public abstract class Piece {
    
    
    // The color and type of the piece
    
    private PieceColor getColor;
    private PieceType type;
    
    public Piece(PieceColor pc, PieceType pt) {
        this.getColor = pc;
        this.type = pt;
    }
    
    public abstract boolean checkPieceTypeMoveIsValid(ChessMove move, Cell[][] board);
    
    public PieceColor getColor() {
        return this.getColor;
    }
    
    public PieceType getType() {
        return this.type;
    }
    
    
    
 
}
