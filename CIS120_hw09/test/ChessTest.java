import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class ChessTest {
    private Chess g;

    @BeforeEach
    public void setUp() {
        g = new Chess();
    }
    
    @Test
    public void testGameBoardSetup() {
        assertEquals(0, g.getTurns());
        assertTrue(g.getCurrentPlayer());       
    }
    
    @Test
    public void testProperPieceColorInitialization() {
        Piece p = Chess.getCell(0, 0).getPiece();
        assertEquals(p.getColor(), PieceColor.WHITE);
       
        Piece p2 = Chess.getCell(1, 1).getPiece();
        assertEquals(p2.getColor(), PieceColor.WHITE);
        
        Piece p3 = Chess.getCell(7, 7).getPiece();
        assertEquals(p3.getColor(), PieceColor.BLACK);
             
    }
    
    @Test
    public void testProperPieceTypeInit() {
        Piece p = Chess.getCell(0,1).getPiece();
        assertEquals(p.getType(), PieceType.PAWN);
        
        Piece p2 = Chess.getCell(5,0).getPiece();
        assertEquals(p2.getType(), PieceType.BISHOP);
        
        Piece p3 = Chess.getCell(7,7).getPiece();
        assertEquals(p3.getType(), PieceType.ROOK);     
    }
    
    @Test
    public void testGetCellInMiddle() {
        assertFalse(Chess.getCell(4, 4).isOccupied());
    }

    @Test
    public void testCreateNewMoveObjects() {
        ChessMove m = new ChessMove(0, 1, 0, 2);
        Piece p = m.getPiece();
        assertEquals(p.getType(), PieceType.PAWN);
        assertEquals(p.getColor(), PieceColor.WHITE);
        
        ChessMove m2 = new ChessMove(0, 7, 0, 4);
        Piece p2 = m2.getPiece();
        assertEquals(p2.getType(), PieceType.ROOK);
        assertEquals(p2.getColor(), PieceColor.BLACK);
        
        ChessMove m3 = new ChessMove(3, 7, 4, 7);
        Piece p3 = m3.getPiece();
        assertEquals(p3.getType(), PieceType.KING);
        assertEquals(p3.getColor(), PieceColor.BLACK);
    }
    
    @Test
    public void testGetMoveDestinationInformation() {
        ChessMove m = new ChessMove(0, 1, 0, 2);
        Cell c = Chess.getCell(m.getDestinationX(), m.getDestinationY());
        assertFalse(c.isOccupied(), "The cell 0, 2 is empty");  
        
        assertTrue(m.getPiece().checkPieceTypeMoveIsValid(m, Chess.getBoard()));
        
        assertTrue(m.validBoardMove(g.getCurrentPlayer()));
    }
    
    
    @Test
    public void testSimpleMovePawnFromOrigin() {
        g.move(new ChessMove(0, 1, 0, 2), g.getCurrentPlayer());
        assertFalse(Chess.getCell(0, 1).isOccupied());
        Piece p = Chess.getCell(0, 2).getPiece();
        assertEquals(p.getColor(), PieceColor.WHITE);  
       
    }
    
    @Test
    public void testSimpleDoubleMovePawnFromOrigin() {
        g.move(new ChessMove(0, 1, 0, 3), g.getCurrentPlayer());
        assertFalse(Chess.getCell(0, 1).isOccupied());
        Piece p = Chess.getCell(0, 3).getPiece();
        assertEquals(p.getColor(), PieceColor.WHITE);
        assertEquals(1, g.getTurns());       
    }
    
    @Test
    public void testInvalidTriplePawnJump() {
        assertFalse(g.move(new ChessMove(0,1, 0, 4), g.getCurrentPlayer()));
        Piece p = Chess.getCell(0, 1).getPiece();
        assertEquals(p.getType(), PieceType.PAWN);
    }
    
    @Test
    public void testTwoMovesOneWhiteOneBlack() {
       
        // First move is a white pawn
        g.move(new ChessMove(0, 1, 0, 3), g.getCurrentPlayer());
        assertFalse(Chess.getCell(0, 1).isOccupied());
        Piece p = Chess.getCell(0, 3).getPiece();
        assertEquals(p.getColor(), PieceColor.WHITE);
        
        // Check that the destination is empty
        assertFalse(Chess.getCell(7, 5).isOccupied());
        
        // Check that I'm moving the desired piece
        Piece p2 = Chess.getCell(7,6).getPiece();
        assertEquals(p2.getColor(), PieceColor.BLACK);
        assertEquals(p2.getType(), PieceType.PAWN);

        // Update game state with the black pawn move
        g.move(new ChessMove(7, 6, 7, 5), g.getCurrentPlayer());
        // Origin cell should be empty
        assertFalse(Chess.getCell(7, 6).isOccupied());
        
        // Black pawn should now be located here
        Piece p3 = Chess.getCell(7, 5).getPiece();
        assertEquals(p3.getColor(), PieceColor.BLACK);
        
        
        assertEquals(2, g.getTurns());
        
        // Check that the initial white pawn moved is in proper position
        assertTrue(Chess.getCell(0, 3).isOccupied());
        
    }
    
    @Test
    public void testBlackPawnMoveFromOriginOneSpace() {
        
        g.move(new ChessMove(0, 1, 0, 3), g.getCurrentPlayer());
        g.move(new ChessMove(0, 6, 0, 5), g.getCurrentPlayer());
        
        assertFalse(Chess.getCell(0, 6).isOccupied());
        
        Piece p = Chess.getCell(0,  5).getPiece();
        assertEquals(p.getColor(), PieceColor.BLACK);
        assertEquals(p.getType(), PieceType.PAWN);
        
        Piece p2 = Chess.getCell(0, 3).getPiece();
        assertEquals(p2.getColor(), PieceColor.WHITE);
        assertEquals(p2.getType(), PieceType.PAWN);
    }
    
    @Test
    public void testMoveOutOfBoundsFails() { 
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            g.move(new ChessMove(0, 1, 9, 5), g.getCurrentPlayer());
        });
    }
   
    
    @Test
    public void testMoveBumpsIntoFriendly() {
        g.move(new ChessMove(0,0,0,2), g.getCurrentPlayer());
        
        Piece p = Chess.getCell(0, 0).getPiece();       
        assertEquals(p.getColor(), PieceColor.WHITE);
        assertEquals(p.getType(), PieceType.ROOK);
        
        Piece p2 = Chess.getCell(0, 1).getPiece();       
        assertEquals(p2.getColor(), PieceColor.WHITE);
        assertEquals(p2.getType(), PieceType.PAWN);
    }
    
    @Test
    public void testPawnCapture() {
        
        g.move(new ChessMove(0,1,0,3), g.getCurrentPlayer());
        g.move(new ChessMove(1, 6, 1, 4), g.getCurrentPlayer());
        assertEquals(0, Chess.getCapturedPieces().size());
        assertTrue(g.getCurrentPlayer());
        assertEquals(2, Chess.getMovesHistory().size());
        g.move(new ChessMove(0, 3, 1, 4), g.getCurrentPlayer());
        
        assertEquals(1, Chess.getCapturedPieces().size());
        assertEquals(3, Chess.getMovesHistory().size());
        
        Piece captured = Chess.getCapturedPieces().get(0);      
        assertEquals(captured.getColor(), PieceColor.BLACK);
        assertEquals(captured.getType(), PieceType.PAWN);    
        
        Piece p = Chess.getCell(1, 4).getPiece();
        assertEquals(p.getColor(), PieceColor.WHITE);
        assertEquals(p.getType(), PieceType.PAWN);
        
    }
    
    @Test
    public void complexCaptureSequence() {
        
        g.move(new ChessMove(0, 1, 0, 3), g.getCurrentPlayer());
        g.move(new ChessMove(1, 6, 1, 4), g.getCurrentPlayer());
        g.move(new ChessMove(0, 3, 1, 4), g.getCurrentPlayer());
        g.move(new ChessMove(3, 6, 3, 5), g.getCurrentPlayer());
        g.move(new ChessMove(7, 1, 7, 3), g.getCurrentPlayer());
        g.move(new ChessMove(4, 7, 1, 4), g.getCurrentPlayer());
        
        
        assertEquals(2, Chess.getCapturedPieces().size());
        assertEquals(6, Chess.getMovesHistory().size());
    }
    
    @Test
    public void testGetLastMoveFromHistory() {
        g.move(new ChessMove(0, 1, 0, 3), g.getCurrentPlayer());
        
        assertEquals(1, Chess.getMovesHistory().size());
        
        ChessMove cm = Chess.getPreviousMove();
        
        Piece p = cm.getPiece();
        assertEquals(p.getColor(), PieceColor.WHITE);
        assertEquals(p.getType(), PieceType.PAWN);
    }
    
    @Test
    public void testGetGameStatuses() {
        assertFalse(g.getCheckMateStatus());
        
        assertFalse(g.getKingCheckStatus());
        
        assertTrue(g.getCurrentPlayer());
        
        g.move(new ChessMove(0, 1, 0, 2), g.getCurrentPlayer());
        
        assertFalse(g.getCurrentPlayer());
    }
    
    @Test
    public void testVerticalPathBlocked() {
        assertFalse(g.move(new ChessMove(0,0, 0, 5), g.getCurrentPlayer()));      
    }
    
    @Test
    public void testHorizontalPathBlocked() {
        assertFalse(g.move(new ChessMove(0,0,5,0), g.getCurrentPlayer()));
    }
    
    @Test
    public void testDiagonalPathBlocked() {
        assertFalse(g.move(new ChessMove(2, 0, 4, 2), g.getCurrentPlayer()));
    }
    
    @Test
    public void testWhiteGrabOpposingPlayersPiece() {
        assertFalse(g.move(new ChessMove(0, 5, 0, 4), g.getCurrentPlayer()));
    }
    
    @Test
    public void testBlackGrabOpposingPlayersPiece() {
        g.move(new ChessMove(0,1,0,3), g.getCurrentPlayer());
        assertFalse(g.move(new ChessMove(0,3,0,4), g.getCurrentPlayer()));
    }
    
    @Test
    public void testMoveEmptyCellIsInvalid() {
        assertFalse(g.move(new ChessMove(0, 3, 0, 4), g.getCurrentPlayer()));
    }
    
    
    
}
