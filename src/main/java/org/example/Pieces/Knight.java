package org.example.Pieces;

import org.example.GamePanel;

public class Knight extends Piece {

    public Knight(int color,int col, int row) {
        super(color, col, row);
        type=Type.KNIGHT;
        if(color== GamePanel.WHITE){
            image=getImage("/Piece/w-knight");
        }else {
            image=getImage("/Piece/b-knight");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow)) {
            if((Math.abs(targetCol-preCol)==1 && Math.abs(targetRow-preRow)==2) || (Math.abs(targetCol-preCol)==2 && Math.abs(targetRow-preRow)==1)){
                if(isValidSquere(targetCol, targetRow)){
                    return true;
                }
            }
//            if (Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 || Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1) {
//                if(isValidSquere(targetCol, targetRow)){
//                    return true;
//                }
//            }
        }return false;
    }
}