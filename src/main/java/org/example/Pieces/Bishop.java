package org.example.Pieces;

import org.example.GamePanel;

public class Bishop extends Piece {

    public Bishop(int color,int col, int row) {
        super(color, col, row);
        type=Type.BISHOP;
        if(color== GamePanel.WHITE){
            image=getImage("/Piece/w-bishop");
        }else {
            image=getImage("/Piece/b-bishop");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && isSameSquere(targetCol,targetRow)==false) {
            if (Math.abs(targetCol-preCol)==Math.abs(targetRow-preRow)){
                if(isValidSquere(targetCol, targetRow) && pieceIsOnDiagonalLine(targetCol, targetRow)==false){
                    return true;
                }
            }
        }return false;
    }

}