import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class CellComponents extends JPanel {
    private final int cellX;
    private final int cellY;
    private final int imageDim = 50;
   

    CellComponents(ChessBoard chessBoard, int xCoord, int yCoord) {
        super(new GridLayout());
        this.cellX = xCoord;
        this.cellY = yCoord;
       
        setCellBackgroundColor();
        addPiecesToBoard();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (chessBoard.getOriginCell() == null) {
                    chessBoard.setOriginCell(Chess.getCell(cellX, cellY));
        
                } else if (chessBoard.getOriginCell() != null &&
                        chessBoard.getOriginCell().getPiece() != null) {
                    chessBoard.setDestinationCell(Chess.getCell(cellX, cellY));
                    int originX = chessBoard.getOriginCell().getRank();
                    int originY = chessBoard.getOriginCell().getFile();
                    int destX = chessBoard.getDestinationCell().getRank();
                    int destY = chessBoard.getDestinationCell().getFile();
                    
                    ChessMove attemptedMove = new ChessMove(originX, originY, destX, destY);
                    final boolean move = 
                            chessBoard.getChessModel().move(attemptedMove, 
                            chessBoard.getChessModel().getCurrentPlayer());
                    if (move) {

                        if (chessBoard.getChessModel().getKingCheckStatus()) {
                            boolean attacked = chessBoard.getChessModel().getCurrentPlayer();
                            if (attacked) {
                                JOptionPane.showMessageDialog(null, 
                                        "White, your king is under attack!");
                            } else {
                                JOptionPane.showMessageDialog(null, 
                                        "Black, you king is under attack!");
                            }   
                        }
//                        CapturedPieces captured = chessBoard.getCapturedPiecesPanel();
//                        MoveHistory moveUpdate = chessBoard.getMoveHistory();                     
//                        moveUpdate.repaint();
//                        captured.repaint();
                        
                    }                    
                    resetListenerValues(chessBoard);
                    updateStatus(chessBoard);
                    chessBoard.repaint();
                    repaint();
                }
            }
        });
    }
    
    private void resetListenerValues(ChessBoard chessBoard) {
        chessBoard.setOriginCell(null);
        chessBoard.setDestinationCell(null);   
    }

    private void setCellBackgroundColor() {
        if (cellX == 0 || cellX == 2 || cellX == 4 || cellX == 6) {
            setBackground(this.cellY % 2 == 0 ? new Color(102, 77, 0) : new Color(179, 134, 0));
        } else {
            setBackground(this.cellY % 2 == 0 ? new Color(179, 134, 0) : new Color(102, 77, 0));
        }        
    }
    
    private void addPiecesToBoard() {
        this.removeAll();
        if (Chess.getCell(cellX, cellY).isOccupied()) {
            try {
                String color = Chess.getCell(cellX, cellY).getPiece().getColor().toString();
                String type = Chess.getCell(cellX, cellY).getPiece().getType().toString();
                final BufferedImage pieceImage = 
                        ImageIO.read(new File("files/" + color + "_" + type + ".png"));
                add(new JLabel(resizePiecePicture(pieceImage)));
                
            } catch (IIOException e) {
                System.out.println("Image IO exception");
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private ImageIcon resizePiecePicture(BufferedImage pieceImage) {
        ImageIcon original = new ImageIcon(pieceImage);        
        Image image = original.getImage();
        Image newimg = image.getScaledInstance(this.imageDim, this.imageDim, Image.SCALE_SMOOTH); 
        return new ImageIcon(newimg);          
    }
    
    private void updateStatus(ChessBoard board) {
        if (board.getChessModel().getCurrentPlayer()) {
            board.setStatus("Player 1's Turn");
        } else {
            board.setStatus("Player 2's Turn");
        }
    }
    
    public void drawCell(Graphics g) {
        removeAll();
        setCellBackgroundColor();
        addPiecesToBoard();
       
        validate();         
    }
   
    
    // The painting procedure for each cell
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);     
    }
}
