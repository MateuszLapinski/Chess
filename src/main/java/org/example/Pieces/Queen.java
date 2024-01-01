package org.example.Pieces;

import org.example.GamePanel;

public class Queen extends Piece {

    public Queen(int color,int col, int row) {
        super(color, col, row);
        type=Type.QUEEN;
        if(color== GamePanel.WHITE){
            image=getImage("/Piece/w-queen");
        }else {
            image=getImage("/Piece/b-queen");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && !isSameSquere(targetCol, targetRow)) {

            if (targetCol==preCol || targetRow==preRow) {
                if(isValidSquere(targetCol, targetRow) && pieceIsOnStraightLine(targetCol, targetRow)==false){
                    return true;
                }
            }
            //Diagonal
            if (Math.abs(targetCol-preCol)==Math.abs(targetRow-preRow)){
                if(isValidSquere(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)){
                    return true;
                }
            }

        }return false;
    }
}