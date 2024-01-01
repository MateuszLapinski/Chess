package org.example;

import org.example.Pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    public int x,y;
    public boolean pressed;
    public boolean entered;

    @Override
    public void mousePressed(MouseEvent e){
        pressed=true;
    }

    @Override
    public void mouseReleased(MouseEvent e){
        pressed=false;
    }

    @Override
    public void mouseDragged(MouseEvent e){
        x=e.getX();
        y= e.getY();
    }
    @Override
    public void mouseMoved(MouseEvent e){
        x=e.getX();
        y= e.getY();

        for(Piece piece : GamePanel.simPieces){
            // Sprawdzenie, czy kursor myszy jest na figurze
            if(piece.x <= x && x <= piece.x + piece.image.getWidth() && piece.y <= y && y <= piece.y + piece.image.getHeight()){
                // Sprawdzenie, czy figura jest inna niż ostatnio aktywna
                if (GamePanel.lastActivePiece != piece) {
                    // Resetowanie flagi dla wszystkich figur
                    for (Piece p : GamePanel.simPieces) {
                        p.infoDisplayed = false;
                    }
                    // Wyświetlanie informacji o nowej figurze
                    if (!piece.infoDisplayed) {
                        System.out.println(piece.toString());
                        piece.infoDisplayed = true;
                    }
                    // Aktualizacja ostatnio aktywnej figury
                    GamePanel.lastActivePiece = piece;
                }
                break;
            }
        }
    }

}
