
public class Cell {
// This class will represent an individual cell within the chess board
    
    private int rank;
    private int file;
    private Piece currentPiece;
    
    public Cell(int x, int y) {
        this.rank = x;
        this.file = y;
    }
    
    public Cell(int x, int y, Piece p) {
        this.rank = x;
        this.file = y;
        this.currentPiece = p;
    }
   
    public boolean isOccupied() {
        return (currentPiece != null);
    };
    
    public Piece getPiece() {
        return this.currentPiece;
    };
    
    public void setNewPiece(Piece newPiece) {
        this.currentPiece = newPiece;
    }
    
    public int getRank() {
        return this.rank;
    }
    
    public int getFile() {
        return this.file;
    }
    
    @Override
    public String toString() {
        if (currentPiece != null) {
            return String.format("Cell [" + this.rank + "] [" + this.file + "]" + 
                "contains the Piece: " + this.currentPiece.getType().toString());
        } else {
            return String.format("Cell [" + this.rank + "] [" + this.file + "]" + 
                    "contains the Piece: " + "null");
        }
    }

}
