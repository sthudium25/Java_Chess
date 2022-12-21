import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Menu extends JPanel {
  
    // Menu size constants
    public static final int MENU_WIDTH = 200;
    public static final int MENU_HEIGHT = 200;
    
    public Menu(ChessBoard board) {  
        
        setBorder(BorderFactory.createTitledBorder("Options"));
        setLayout(new GridLayout(4, 1));
        
        JButton instructions = new JButton("Instructions");
        JButton quit = new JButton("Quit Game");
        JButton reset = new JButton("Reset Game");
        
        
        
        add(instructions);
        add(quit);
        add(reset);
        
        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        
        instructions.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                readInstructions();
                repaint();
            }
        });
       
        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to reset the game?",
                        "Confirm reset", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    board.reset();
                }             
                repaint();
            }
            
        });
    }
    
    public void readInstructions() {
        
        try {
            FileReader fr = new FileReader("files/chess_instructions.txt");
            BufferedReader br = new BufferedReader(fr);                   
            
            String instructionMessage = "";
            String currLine;
            while ((currLine = br.readLine()) != null) {                       
                instructionMessage += currLine + "\n";
            }
            
            br.close();
            
            JOptionPane.showMessageDialog(null, instructionMessage,
                    "Chess Instructions", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex2) {
            ex2.printStackTrace();
        } catch (IOException ex3) {
            
        }
    }
    
 // The painting procedure for the chess board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MENU_WIDTH, MENU_HEIGHT);
    }
}
