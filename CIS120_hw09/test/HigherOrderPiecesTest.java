import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class HigherOrderPiecesTest {
    private Chess g;

    @BeforeEach
    public void setUp() {
        g = new Chess();
       // A series of moves to open up the board for other pieces
        
        g.move(new ChessMove(7, 1, 7, 3), g.getCurrentPlayer()); // W pawn 2h to 4h
        g.move(new ChessMove(3, 6, 3, 5), g.getCurrentPlayer()); // B pawn 7d to 7d
        g.move(new ChessMove(0, 1, 0, 3), g.getCurrentPlayer()); // W pawn 2a to 4a
        g.move(new ChessMove(7, 6, 7, 5), g.getCurrentPlayer()); // B pawn 7h to 6h
        g.move(new ChessMove(0, 3, 0, 4), g.getCurrentPlayer()); // W pawn 4a to 5a
       
    }
    
    // First move after set up performed by black
    @Test
    public void testRookAndBishopMoves() {
        g.move(new ChessMove(2, 7, 5, 4), g.getCurrentPlayer()); // B bishop 8c to 5f
        
        Piece p = Chess.getCell(5,  4).getPiece();
        assertEquals(p.getType(), PieceType.BISHOP);
        assertEquals(p.getColor(), PieceColor.BLACK);
        
        g.move(new ChessMove(7, 0, 7, 2), g.getCurrentPlayer()); // W rook 1h to 3h
        g.move(new ChessMove(5, 4, 7, 2), g.getCurrentPlayer()); // B bishop capture rook
        
        Piece p2 = Chess.getCell(7,  2).getPiece();
        assertEquals(p2.getColor(), PieceColor.BLACK);
        assertEquals(p2.getType(), PieceType.BISHOP);
        assertEquals(Chess.getCapturedPieces().size(), 1);
        assertEquals(Chess.getMovesHistory().size(), 8);
    }
    
    @Test
    public void testKnightSimpleStartMove() {
        
        g.move(new ChessMove(6,7,5, 5), g.getCurrentPlayer());
        
        Piece p = Chess.getCell(5,  5).getPiece();
        assertEquals(p.getType(), PieceType.KNIGHT);
        assertEquals(p.getColor(), PieceColor.BLACK);
        
        assertFalse(Chess.getCell(6, 7).isOccupied());
    }
    
    @Test
    public void testKnightCaptureMove() {
        
        g.move(new ChessMove(2, 7, 7, 2), g.getCurrentPlayer()); // B bishop 8c to 3h
        
        g.move(new ChessMove(6, 0, 7, 2), g.getCurrentPlayer());
        
        Piece p = Chess.getCell(7, 2).getPiece();
        assertEquals(p.getType(), PieceType.KNIGHT);
        assertEquals(p.getColor(), PieceColor.WHITE);
        assertEquals(Chess.getCapturedPieces().size(), 1);
        assertEquals(Chess.getMovesHistory().size(), 7);
    
    }
    
    @Test
    public void testDoubleKnightAttackingMove() {
        g.move(new ChessMove(1, 7, 2, 5), g.getCurrentPlayer());
        g.move(new ChessMove(1, 1, 1, 2), g.getCurrentPlayer());
        g.move(new ChessMove(2, 5, 0, 4), g.getCurrentPlayer());
        
        Piece p = Chess.getCell(0, 4).getPiece();
        assertEquals(p.getType(), PieceType.KNIGHT);
        assertEquals(p.getColor(), PieceColor.BLACK);
        
        assertEquals(Chess.getCapturedPieces().size(), 1);
        assertEquals(Chess.getMovesHistory().size(), 8);
    }
    
    @Test
    public void testInvalidKnightMove() {
        g.move(new ChessMove(1, 7, 1, 5), g.getCurrentPlayer());
        Piece p = Chess.getCell(1, 7).getPiece();
        assertEquals(p.getType(), PieceType.KNIGHT);
        assertEquals(p.getColor(), PieceColor.BLACK);
        
        assertFalse(Chess.getCell(1, 5).isOccupied());
        
    }
    
    @Test
    public void testInvalidBishopMove() {
        g.move(new ChessMove(2, 7, 4, 5), g.getCurrentPlayer());
        g.move(new ChessMove(1, 1, 1, 2), g.getCurrentPlayer());
        g.move(new ChessMove(4, 5, 4, 3), g.getCurrentPlayer());
        
        Piece p = Chess.getCell(4, 5).getPiece();
        assertEquals(p.getType(), PieceType.BISHOP);
        assertEquals(p.getColor(), PieceColor.BLACK);
        
        assertFalse(Chess.getCell(4, 3).isOccupied());
    }
    
    @Test
    public void testWhiteEnPassantCapture() {
        g.move(new ChessMove(1, 6, 1, 4), g.getCurrentPlayer());
        g.move(new ChessMove(0, 4, 1, 5), g.getCurrentPlayer());
        
        Piece p = Chess.getCell(1, 5).getPiece();
        assertEquals(p.getType(), PieceType.PAWN);
        assertEquals(p.getColor(), PieceColor.WHITE);
        
        assertFalse(Chess.getCell(1, 4).isOccupied());
        assertFalse(Chess.getCell(0, 4).isOccupied());
    }
    
    @Test
    public void testBlackEnPassantCapture() {
        g.move(new ChessMove(1, 6, 1, 4), g.getCurrentPlayer());
        g.move(new ChessMove(0, 4, 0, 5), g.getCurrentPlayer());
        g.move(new ChessMove(1, 4, 1, 3), g.getCurrentPlayer());
        g.move(new ChessMove(2, 1, 2, 3), g.getCurrentPlayer());
        g.move(new ChessMove(1, 3, 2, 2), g.getCurrentPlayer());
        
        Piece p = Chess.getCell(2, 2).getPiece();
        
        assertEquals(p.getType(), PieceType.PAWN);
        assertEquals(p.getColor(), PieceColor.BLACK);
        
        assertFalse(Chess.getCell(1, 3).isOccupied());
        assertFalse(Chess.getCell(2, 3).isOccupied());
        
        assertEquals(Chess.getMovesHistory().size(), 10);
    }
    
    @Test
    public void testLateEnPassantAttemptFails() {
        g.move(new ChessMove(1, 6, 1, 4), g.getCurrentPlayer()); // B
        g.move(new ChessMove(5, 1, 5, 2), g.getCurrentPlayer()); // W
        g.move(new ChessMove(5, 6, 5, 5), g.getCurrentPlayer()); // B
        int diff = (int) Math.abs(Chess.getPreviousMove().getDestinationX() - 0);
        assertNotEquals(diff, 1);
        assertFalse(g.move(new ChessMove(0, 4, 1, 5), g.getCurrentPlayer())); // W enP
    }
    
    @Test 
    public void testPutKingInCheck() {
        g.move(new ChessMove(0, 6, 0, 5), g.getCurrentPlayer()); // b P
       
        g.move(new ChessMove(4, 1, 4, 3), g.getCurrentPlayer()); // w P reveal bishop
       
        g.move(new ChessMove(7, 5, 7, 4), g.getCurrentPlayer()); // b P
 
        g.move(new ChessMove(5, 0, 2, 3), g.getCurrentPlayer()); // w B
  
        g.move(new ChessMove(3, 7, 3, 6), g.getCurrentPlayer()); // b K
        
        Piece p2 = Chess.getCell(3, 6).getPiece();
        assertEquals(p2.getType(), PieceType.KING);

        assertFalse(Chess.getCell(4, 1).isOccupied());
        Piece p = Chess.getCell(2, 3).getPiece();
        
        assertEquals(p.getType(), PieceType.BISHOP);
        assertEquals(p.getColor(), PieceColor.WHITE);
        g.move(new ChessMove(2, 3, 1, 4), g.getCurrentPlayer());
        
        assertTrue(g.getKingCheckStatus());   
        
    }
    
    @Test
    public void testKingDodgeSuccessful() {
        g.move(new ChessMove(0, 6, 0, 5), g.getCurrentPlayer()); // b P
        
        g.move(new ChessMove(4, 1, 4, 3), g.getCurrentPlayer()); // w P reveal bishop
       
        g.move(new ChessMove(7, 5, 7, 4), g.getCurrentPlayer()); // b P
 
        g.move(new ChessMove(5, 0, 2, 3), g.getCurrentPlayer()); // w B
  
        g.move(new ChessMove(3, 7, 3, 6), g.getCurrentPlayer()); // b K
        
        assertFalse(Chess.getCell(4, 1).isOccupied());
        Piece p = Chess.getCell(2, 3).getPiece();
        
        assertEquals(p.getType(), PieceType.BISHOP);
        assertEquals(p.getColor(), PieceColor.WHITE);
        g.move(new ChessMove(2, 3, 1, 4), g.getCurrentPlayer());
        
        assertTrue(g.getKingCheckStatus());   
 
        g.move(new ChessMove(3, 6, 3, 7), g.getCurrentPlayer());
        
        assertFalse(g.getKingCheckStatus());

    }
    
    @Test
    public void testKingCannotMoveIntoCheck() {
        g.move(new ChessMove(0, 6, 0, 5), g.getCurrentPlayer()); // b P
        
        g.move(new ChessMove(4, 1, 4, 3), g.getCurrentPlayer()); // w P reveal bishop
       
        g.move(new ChessMove(7, 5, 7, 4), g.getCurrentPlayer()); // b P
 
        g.move(new ChessMove(5, 0, 1, 4), g.getCurrentPlayer()); // w B
  
        assertFalse(g.move(new ChessMove(3, 7, 3, 6), g.getCurrentPlayer())); // b K
     
    }
}
