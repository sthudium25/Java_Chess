import java.util.Collection;
import java.util.LinkedList;
//import java.util.Set;

public class MoveChecker {
    
    private PieceColor playerColor;
    private int xDirection;
    private int yDirection;
    private Collection<Cell> possibleWhiteKingLocations = new LinkedList<>();
    private Collection<Cell> possibleBlackKingLocations = new LinkedList<>();
    private Collection<Cell> whiteKingAdjacentCellsAttacked = new LinkedList<>();
    private Collection<Cell> blackKingAdjacentCellsAttacked = new LinkedList<>();
    private int xKingPosWhite = 3;
    private int yKingPosWhite = 0;
    private int xKingPosBlack = 3;
    private int yKingPosBlack = 7;
    private boolean whiteKingHasMoved;
    private boolean blackKingHasMoved;
    
    public MoveChecker(ChessMove move, boolean player) {
        this.playerColor = player ? PieceColor.WHITE : PieceColor.BLACK;        
        
        if (move.getDestinationX() - move.getCurrentX() > 0) {
            xDirection = 1;
        } else if (move.getDestinationX() - move.getCurrentX() < 0) {
            xDirection = -1;
        } else {
            xDirection = 0;
        }
        
        if (move.getDestinationY() - move.getCurrentY() > 0) {
            yDirection = 1;
        } else if (move.getDestinationY() - move.getCurrentY() < 0) {
            yDirection = -1;
        } else {
            yDirection = 0;
        }
        
        if (player) {
            updateKingDodgeLocations(xKingPosBlack, yKingPosBlack, player);
        } else {
            updateKingDodgeLocations(xKingPosWhite, yKingPosWhite, player);
        }
        
    }

    public boolean checkMoveOnBoardIsValid(ChessMove move, boolean player) {
        
        // check bounds
        if (move.getDestinationX() < 0 || move.getDestinationX() > 7 
                || move.getDestinationY() < 0 || move.getDestinationY() > 7) {
           
            return false;
        }
        
        // Check that the player is grabbing their own piece
        if (move.getPiece().getColor() == PieceColor.WHITE && !player) {
            
            return false;
        }
        if (move.getPiece().getColor() == PieceColor.BLACK && player) {
            
            return false;
        }
        
        // Check that there is a piece on the origin cell
        if (move.getPiece() == null) {
            
            return false;
        }
        
        if (move.getPiece().getType() == PieceType.KNIGHT) {
            Cell dest = Chess.getCell(move.getDestinationX(), move.getDestinationY());
            
            if (dest.isOccupied() && dest.getPiece().getColor() == getMoveColor()) {
                
                return false;
            }

            return true;
        }
        
        return checkClearPath(move);
    }
    
    
    
    private boolean checkClearPath(ChessMove move) {
        
        // Check paths along same X and same Y (important for queens, rooks, double pawn
        // moves
        // first check for team mate pieces vertically
        if (xDirection == 0) {          
            for (int y = move.getCurrentY() + yDirection; 
                    y != move.getDestinationY() + yDirection; y += yDirection) {
                
                Cell curr = Chess.getCell(move.getCurrentX(), y);
                if (curr.isOccupied()) {
                    if (curr.getPiece().getColor() == getMoveColor()) {
                       
                        return false;
                    }
                }
            }
            return true;
        }
        
        // check horizontal move
        if (yDirection == 0) {
            for (int x = move.getCurrentX() + xDirection; 
                    x != move.getDestinationX() + xDirection; x += xDirection) {
                Cell curr = Chess.getCell(x, move.getCurrentY());
                if (curr.isOccupied()) {
                    if (curr.getPiece().getColor() == getMoveColor()) {
                       
                        return false;
                    }
                }
            }
            return true;
        }
        
        // check diagonal move
        // important for pawn capture, queens, bishops, kings
        if (Math.abs(xDirection) == 1 && Math.abs(yDirection) == 1) {
            int x = move.getCurrentX() + xDirection;
            int y = move.getCurrentY() + yDirection;
            while (x != move.getDestinationX() + xDirection 
                    && y != move.getDestinationY() + yDirection) {
                
                Cell curr = Chess.getCell(x, y);
                if (curr.isOccupied()) {
                    if (curr.getPiece().getColor() == getMoveColor()) {
                        
                        return false;
                    } 
                }
                x += xDirection;
                y += yDirection;
            }
            return true;
        }
        
        
        return false;
    }
    
    public boolean movePutsKingInCheck(ChessMove move, boolean player) {
        int enemyKingX = getEnemyKingPositionX(player);
        int enemyKingY = getEnemyKingPositionY(player);
        int fromX = move.getDestinationX();
        int fromY = move.getDestinationY();
        ChessMove possibleCheckMove = new ChessMove(fromX, fromY, enemyKingX, enemyKingY);
 
        if (move.getPiece().checkPieceTypeMoveIsValid(possibleCheckMove, Chess.getBoard())
                && possibleCheckMove.validBoardMove(player)) {
            if (player) {
                blackKingAdjacentCellsAttacked.add(Chess.getCell(enemyKingX, enemyKingY));
            } else {
                whiteKingAdjacentCellsAttacked.add(Chess.getCell(enemyKingX, enemyKingY));
            }
            return true;
        }
        return false;
    }
    
   
    
    public boolean checkSubsequentMoveForKingAttack(ChessMove move, boolean player) {
        int fromX = move.getDestinationX();
        int fromY = move.getDestinationY();
        for (Cell c : getEnemyKingsAdjCells(player)) {
            int x = c.getRank();
            int y = c.getFile();
            ChessMove kingDodgeLeadsToCheck = new ChessMove(fromX, fromY, x, y);
            if (move.getPiece().checkPieceTypeMoveIsValid(kingDodgeLeadsToCheck, Chess.getBoard())
                    && kingDodgeLeadsToCheck.validBoardMove(player)) {
                if (player) {
                    blackKingAdjacentCellsAttacked.add(c);
                } else {
                    whiteKingAdjacentCellsAttacked.add(c);
                }
                return true;
            }       
        }
        return false;
    }
    
    public boolean validateKingDodgeMove(ChessMove move, boolean player) {
        int x = move.getDestinationX();
        int y = move.getDestinationY();
        Cell kingOccupies = Chess.getCell(x, y);
        if (player) {
            if (this.blackKingAdjacentCellsAttacked.contains(kingOccupies)) {
                return false;
            }
        } else if (!player) {
            if (this.whiteKingAdjacentCellsAttacked.contains(kingOccupies)) {
                return false;
            }
        }        
        return true;
    }
    
    public boolean updateKingCheckStatus(boolean player) {
        int x = getEnemyKingPositionX(player);
        int y = getEnemyKingPositionY(player);
        Cell kingOccupies = Chess.getCell(x, y);
        if (player) {
            if (this.blackKingAdjacentCellsAttacked.contains(kingOccupies)) {
                return true;
            }
        } else if (!player) {
            if (this.whiteKingAdjacentCellsAttacked.contains(kingOccupies)) {
                return true;
            }
        }        
        return false;
    }
    
    public Collection<Cell> getEnemyKingsAdjCells(boolean player) {
        if (player) {
            return this.possibleWhiteKingLocations;
        } else {
            return this.possibleBlackKingLocations;
        }
    }
    
    public Collection<Cell> getEnemyKingCellsAttacked(boolean player) {
        if (player) {
            return this.blackKingAdjacentCellsAttacked;
        } else {
            return this.whiteKingAdjacentCellsAttacked;
        }
    }
    
    public void updateKingDodgeLocations(int x, int y, boolean player) {
        if (player) {
            possibleWhiteKingLocations.clear();
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    
                    if (i >= 0 && i < 8 && j >= 0 && j < 8) {
                        
                        Cell possibleKingMove = Chess.getCell(i, j);
                        if (!possibleKingMove.isOccupied() && !updateKingCheckStatus(player)) {
                            possibleWhiteKingLocations.add(possibleKingMove);
                        }
                    }
                }
            }
        } else {
            possibleBlackKingLocations.clear();
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y + 1; j >= y - 1; j--) {
                    if (i >= 0 && i < 8 && j >= 0 && j < 8) {
                        Cell possibleKingMove = Chess.getCell(i, j);
                        if (!possibleKingMove.isOccupied() && !updateKingCheckStatus(player)) {
                            possibleBlackKingLocations.add(possibleKingMove);
                        }
                    }
                }
            }
        }
    }
    
    public Cell getFriendlyKingPosition(boolean player) {
        if (player) {
            return Chess.getCell(xKingPosWhite, yKingPosWhite);
        } else {
            return Chess.getCell(xKingPosBlack, yKingPosBlack);
        }
    }
    
    public int getEnemyKingPositionX(boolean player) {
        if (player) {
            return xKingPosBlack;
            
        } else {
            return xKingPosWhite;
        }
    }
    
    public int getEnemyKingPositionY(boolean player) {
        if (player) {
            return yKingPosBlack;
            
        } else {
            return yKingPosWhite;
        }
    }
    
    
    public PieceColor getMoveColor() {
        return playerColor;
    }
    
    public void setKingCoords(int x, int y, boolean player) {
        if (player) {
            xKingPosWhite = x;
            yKingPosWhite = y;
            
        } else {
            xKingPosBlack = x;
            yKingPosBlack = y;          
        }
    }
    
    public boolean getWhiteKingHasMoved() {
        return this.whiteKingHasMoved;
    }
    
    public boolean getBlackKingHasMoved() {
        return this.blackKingHasMoved;
    }
    
    public void setWhiteKingHasMoved() {
        this.whiteKingHasMoved = true;
    }
    
    public void setBlackKingHasMoved() {
        this.blackKingHasMoved = true;
    }
    
}


