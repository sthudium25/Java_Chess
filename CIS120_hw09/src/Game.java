
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class Game implements Runnable {
    
    final Dimension guiDim = new Dimension(600, 800);
    
    
    public void run() {

        JFrame frame = new JFrame("Chess");
        frame.setLayout(new BorderLayout());

        frame.setSize(guiDim);

        JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        statusPanel.add(status);
        MoveHistory moveLog = new MoveHistory();
        CapturedPieces captured = new CapturedPieces();
        
        ChessBoard board = new ChessBoard(status);
        frame.add(board, BorderLayout.CENTER);
        

        JPanel sidebar = new JPanel();
        frame.add(sidebar, BorderLayout.EAST);
        sidebar.setLayout(new GridLayout(3,1));
        sidebar.add(new Menu(board));
        sidebar.add(captured);
        sidebar.add(moveLog);
        
        

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}

