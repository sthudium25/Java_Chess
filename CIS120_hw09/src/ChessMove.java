
public class ChessMove {

    private Piece piece;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private Piece enemyPieceCaptured;
    
    
    public ChessMove(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.piece = Chess.getCell(fromX, fromY).getPiece();
        this.enemyPieceCaptured = Chess.getCell(toX, toY).getPiece();
    }
      
    public boolean validBoardMove(boolean player) {
        MoveChecker mc = new MoveChecker(this, player);
        return mc.checkMoveOnBoardIsValid(this, player);      
    } 
    
    public Piece getPiece() {
        return this.piece;
    }
    
    public int getDestinationX() {
        return this.toX;
    }
    
    public int getDestinationY() {
        return this.toY;
    }
    
    public int getCurrentX() {
        return this.fromX;
    }
    
    public int getCurrentY() {
        return this.fromY;
    }
    
    public Piece getCapturedPiece() {
        return this.enemyPieceCaptured;
    }
    
    public void promotePawnToQueen() {
        this.piece = new Queen(this.piece.getColor());
    }
    
    public void enPassantAddCapturedPiece(ChessMove lastMove) {
        this.enemyPieceCaptured = lastMove.getPiece();
    }
}
