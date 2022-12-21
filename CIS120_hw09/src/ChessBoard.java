
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;


// stub code for a board class
@SuppressWarnings("serial")
public class ChessBoard extends JPanel {
    // This should contain the modeled representation of the state of the board

    
    final ArrayList<CellComponents> cellsOnBoard; 
    private Chess chess; // model for the game
    private JLabel status;
    private MoveHistory movesPanel;
    private CapturedPieces captured;

    // Game constants
    
    public static final Dimension BOARD_DIM = new Dimension(600, 600);
    
    private Cell originCell;
    private Cell destinationCell;

    // Initialize a Chess game
    public ChessBoard(JLabel init) {     
        super(new GridLayout(8, 8));
        chess = new Chess(); 
     //   this.movesPanel = moveLog;
    //    this.captured = captured;
        
        this.cellsOnBoard = new ArrayList<CellComponents>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final CellComponents cell = new CellComponents(this, i, j);
                this.cellsOnBoard.add(cell);
                add(cell);

            }

        }
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        status = init;
        status.setText("Player 1's Turn");
        setPreferredSize(BOARD_DIM);
        validate();
        
        repaint();
       
    }
    
    public void drawBoard(ChessBoard chessBoard, Graphics g) {
        removeAll();
        for (CellComponents component : this.cellsOnBoard) {

            component.drawCell(g);
            add(component);

        }
        setPreferredSize(BOARD_DIM);
        validate();
      
    }

    public void reset() {
        chess.reset();
        status.setText("Player 1's Turn");
        repaint();
        
        requestFocusInWindow();
    }
    
    public void setStatus(String newStatus) {
        this.status.setText(newStatus);
    }
    
    public Chess getChessModel() {
        return this.chess;
    }
    
    public MoveHistory getMoveHistory() {
        return this.movesPanel;
    }
    
    public CapturedPieces getCapturedPiecesPanel() {
        return this.captured;
    }
    
    public Cell getOriginCell() {
        return this.originCell;
    }
    
    public Cell getDestinationCell() {
        return this.destinationCell;
    }
    
    public void setOriginCell(Cell cell) {
        this.originCell = cell;
    }
    
    public void setDestinationCell(Cell cell) {
        this.destinationCell = cell;
    }
   
    
   
    // The painting procedure for the chess board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(this, g);
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_DIM);
    }
    
  
}

