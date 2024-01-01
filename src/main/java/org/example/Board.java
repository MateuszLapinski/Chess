package org.example;

import java.awt.*;

public class Board {
    final int MAX_COL=8;
    final int MAX_ROW=8;

    public static final int SQUERE_SIZE=100;
    public static final int HALF_SQUERE_SIZE= SQUERE_SIZE/2;

    public void draw(Graphics2D g2){
        int c=0;
        for(int row =0; row<MAX_ROW; row++){
            for(int col=0;col<MAX_COL; col++) {
                g2.fillRect(col * SQUERE_SIZE, row * SQUERE_SIZE, SQUERE_SIZE, SQUERE_SIZE);
                if (c == 0) {
                    g2.setColor(new Color(210, 165, 125));
                    c = 1;
                } else {
                    g2.setColor(new Color(175, 115, 70));
                    c = 0;
                }

                g2.fillRect(col * SQUERE_SIZE, row * SQUERE_SIZE, SQUERE_SIZE, SQUERE_SIZE);
            }
            if(c==0){
                c=1;
            }else {
                c=0;
            }
        }
    }
}
