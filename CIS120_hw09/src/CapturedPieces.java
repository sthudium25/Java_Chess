import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CapturedPieces extends JPanel {
 
    public static final Dimension PANEL_DIM = new Dimension(200, 200);
    private final JPanel blackPieces;
    private final JPanel whitePieces;
    
    public CapturedPieces() {
        super(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Captured Pieces"));
               
        blackPieces = new JPanel(new GridLayout(2, 8));
               
        whitePieces = new JPanel(new GridLayout(2,8));
               
        this.add(blackPieces, BorderLayout.WEST);
        this.add(whitePieces, BorderLayout.EAST);       
        this.setVisible(true);
        
    }
    
    private void setCapturedPiecesPanel() {
        removeAll();
        List<Piece> captured = Chess.getCapturedPieces();
        for (Piece piece : captured) {
            String pieceCode = piece.getColor().toString().substring(0,1) + "_" +
                        piece.getType().toString().substring(0, 2);
            JLabel curr = new JLabel(pieceCode);
            if (piece.getColor() == PieceColor.WHITE) {               
                whitePieces.add(curr);
            } else {
                blackPieces.add(curr);               
            }          
        }       
    }
    
 // The painting procedure for the chess board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setCapturedPiecesPanel();
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return PANEL_DIM;
    }
}
