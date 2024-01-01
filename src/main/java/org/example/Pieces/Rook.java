package org.example.Pieces;

import org.example.GamePanel;

public class Rook extends Piece {

    public Rook(int color,int col, int row) {
        super(color, col, row);
        type=Type.ROOK;
        if(color== GamePanel.WHITE){
            image=getImage("/Piece/w-rook");
        }else {
            image=getImage("/Piece/b-rook");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBoard(targetCol, targetRow) && isSameSquere(targetCol,targetRow)==false) {
            if (targetCol==preCol || targetRow==preRow){
                if(isValidSquere(targetCol, targetRow) && pieceIsOnStraightLine(targetCol, targetRow)==false){
                    return true;
                }
            }
        }return false;
    }
}