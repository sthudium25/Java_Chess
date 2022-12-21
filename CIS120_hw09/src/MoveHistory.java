import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import java.util.LinkedList;

import javax.swing.BorderFactory;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
//import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class MoveHistory extends JPanel {
    
    private final int paneldim = 200;
    private final String newline = "\n";
    
    private JTable movePanel;
    private final JScrollPane scrollPanel;
    private DefaultTableModel defaultModel;
    
    
    public MoveHistory() {       
        setBorder(BorderFactory.createTitledBorder("Previous Moves"));
        movePanel = new JTable();
        defaultModel = new DefaultTableModel(0, 0);
        
        this.scrollPanel = new JScrollPane(movePanel);
        
        scrollPanel.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        this.add(scrollPanel, BorderLayout.EAST);
            
        this.setVisible(true);
        
    }
    
    public void addMovesToHistoryTable() {
        this.removeAll();
        String[] header = new String[] {"Previous Moves"};
        defaultModel.setColumnIdentifiers(header);
        movePanel.setModel(defaultModel);
        
        LinkedList<ChessMove> moves = Chess.getMovesHistory();
       
        for (ChessMove move : moves) {
            String moveToAdd =  "";
            String color = move.getPiece().getColor().toString();
            String type = move.getPiece().getType().toString();
            int x = move.getDestinationX();
            int y = move.getDestinationY();
            moveToAdd = color + " " + type + " to " + x + ", " + y + newline;
            System.out.println(moveToAdd);
            defaultModel.addRow(new Object[] {moveToAdd}); 
            
        }
        this.setVisible(true);
    }
    
 // The painting procedure for the move history panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        addMovesToHistoryTable();
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(paneldim, paneldim);
    }
    
    
    
}
