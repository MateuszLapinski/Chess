package org.example;

import org.example.Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

     ArrayList<Piece> promoPieces = new ArrayList<>();
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activePiece, checkingPiece;
    public static Piece castlingPiece, lastActivePiece;
    //BOOLEANS
    boolean canMove, validSquare, promotion, gameOver;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
       // setPieces();
        testsetPieces();
        copyPieces(pieces, simPieces);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    private void update() {
        if (promotion==true) {
            promoting();
        } else {
            if (mouse.pressed) {
                if (activePiece == null) {
                    for (Piece piece : simPieces) {
                        if (piece.color == currentColor &&
                                piece.col == mouse.x / Board.SQUERE_SIZE &&
                                piece.row == mouse.y / Board.SQUERE_SIZE) {
                            activePiece = piece;
                        }
                    }
                } else {
                    simulate();
                }
            }

            // Mouse Button released //
            if (mouse.pressed == false) {
                if (activePiece != null) {

                    if (validSquare == true) {
                        copyPieces(simPieces, pieces);
                        activePiece.updatePosition();
                        if (castlingPiece != null) {
                            castlingPiece.updatePosition();
                        }

                        if(isKingInCheck()){
                            //TODO: possibly game over
                        }
//                        else{
//                            if (canPromote()) {
//                                promotion = true;
//                            } else {
//                                changePlayer();
//                            }
//                        }
                        if (canPromote()) {
                            promotion = true;
                        } else {
                            changePlayer();
                        }

                    } else {
                        copyPieces(pieces, simPieces);
                        activePiece.resetPosition();
                        activePiece = null;
                    }


                }
            }
        }
    }

    private void simulate() {

        canMove = false;
        validSquare = false;

        copyPieces(pieces, simPieces);

        //Reset the castling piece's position
        if (castlingPiece != null) {
            castlingPiece.col = castlingPiece.preCol;
            castlingPiece.x = castlingPiece.getX(castlingPiece.col);
            castlingPiece = null;
        }
        activePiece.x = mouse.x - Board.HALF_SQUERE_SIZE;
        activePiece.y = mouse.y - Board.HALF_SQUERE_SIZE;
        activePiece.col = activePiece.getCol(activePiece.x);
        activePiece.row = activePiece.getRow(activePiece.y);

        if (activePiece.canMove(activePiece.col, activePiece.row) == true) {
            canMove = true;

            if (activePiece.hittingP != null) {
                simPieces.remove(activePiece.hittingP.getIndex());
            }
            checkCastling();
            if(isIllegal(activePiece)==false && opponentCanCaptureKing()){
                validSquare=true;
            }

            if(isIllegal(activePiece)==false){
                validSquare=true;
            }
        }
    }

    private void changePlayer() {
        if (currentColor == WHITE) {
            currentColor = BLACK;
            for (Piece piece : pieces) {
                if (piece.color == BLACK) {
                    piece.twoStepped = false;
                }
            }
        } else {
            currentColor = WHITE;
            for (Piece piece : pieces) {
                if (piece.color == WHITE) {
                    piece.twoStepped = false;
                }
            }
        }
        activePiece = null;
    }

    private boolean opponentCanCaptureKing(){

        Piece king=getKing(false);
        for(Piece piece:simPieces){
            if(piece.color !=king.color && piece.canMove(king.col, king.row)){
                return true;
            }
        }
        return false;
    }
    private boolean isKingInCheck(){
        Piece king=getKing(true);
        if(activePiece.canMove(king.col,king.row)){
            checkingPiece=activePiece;
            return true;
        }else{
            checkingPiece=null;
        }
       return false;
    }

    private Piece getKing(boolean opponent){
        Piece king=null;
        for(Piece piece: simPieces){
            if(opponent){
                if(piece.type==Type.KING && piece.color != currentColor){
                    king=piece;
                }
            }else {
                if(piece.type==Type.KING && piece.color == currentColor){
                    king=piece;
                }
            }
        }return king;
    }
    private void checkCastling() {
        if (castlingPiece != null) {
            if (castlingPiece.col == 0) {
                castlingPiece.col += 3;
            } else if (castlingPiece.col == 7) {
                castlingPiece.col -= 2;
            }
            castlingPiece.x = castlingPiece.getX(castlingPiece.col);

        }
    }

    private boolean isIllegal(Piece king){
        if(king.type== Type.KING){
            for(Piece piece: simPieces){
                if(piece!=king && piece.color != king.color && piece.canMove(king.col, king.row)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canPromote() {
        if (activePiece.type == Type.PAWN) {
            if (currentColor == WHITE && activePiece.row == 0 || currentColor == BLACK && activePiece.row == 7) {
                promoPieces.clear();
                promoPieces.add(new Rook(currentColor, 9, 2));
                promoPieces.add(new Knight(currentColor, 9, 3));
                promoPieces.add(new Bishop(currentColor, 9, 4));
                promoPieces.add(new Queen(currentColor, 9, 5));
                return true;
            }
        }
        return false;
    }

    private boolean isCheckmante(){
        return false;
    }

    private boolean kingCanMove(Piece king){
        return false;
    }
    private boolean isValidMove(Piece king, int colPlus, int rowPlus){
        return false;
    }
    private void promoting() {
        if(mouse.pressed){
            for(Piece piece: promoPieces){
                if(piece.col==mouse.x/Board.SQUERE_SIZE && piece.row==mouse.y/Board.SQUERE_SIZE){
                    switch(piece.type)  {
                        case ROOK: simPieces.add(new Rook(currentColor, activePiece.col, activePiece.row));break;
                        case KNIGHT: simPieces.add(new Knight(currentColor, activePiece.col, activePiece.row));break;
                        case BISHOP: simPieces.add(new Bishop(currentColor, activePiece.col, activePiece.row));break;
                        case QUEEN: simPieces.add(new Queen(currentColor, activePiece.col, activePiece.row));break;
                        default:break;
                    }
                    simPieces.remove(activePiece.getIndex());
                    copyPieces(simPieces, pieces);
                    activePiece=null;
                    promotion=false;
                    changePlayer();
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //BOARD
        board.draw(g2);
        g2.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
        //PIECES
        for (Piece p : simPieces) {
            p.draw(g2);
        }


        if (activePiece != null) {
            if (canMove) {
                if(isIllegal(activePiece) || opponentCanCaptureKing()==true)
                {
                    g2.setColor(Color.gray);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect(activePiece.col * Board.SQUERE_SIZE, activePiece.row * Board.SQUERE_SIZE, Board.SQUERE_SIZE, Board.SQUERE_SIZE);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    g2.drawString("Potential check", 820, 150);
                } else {
                    g2.setColor(Color.white);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect(activePiece.col * Board.SQUERE_SIZE, activePiece.row * Board.SQUERE_SIZE, Board.SQUERE_SIZE, Board.SQUERE_SIZE);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
            }
            activePiece.draw(g2);
        }

        //Status info
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        if (promotion) {
            g2.drawString("Promote to:", 840, 150);

            for (Piece piece : promoPieces) {
                g2.drawImage(piece.image,piece.getX(piece.col),  piece.getY(piece.row), Board.SQUERE_SIZE,Board.SQUERE_SIZE,null);
            }

        }else{
            if (currentColor == WHITE) {
                g2.drawString("White's turn", 840, 550);
                if(checkingPiece!=null && checkingPiece.color==BLACK){
                    g2.setColor(Color.red);
                    g2.drawString("The King", 840, 650);
                    g2.drawString("is in check!", 840, 700);
                }
            } else {
                g2.drawString("Black's turn", 840, 250);
                if(checkingPiece!=null && checkingPiece.color==WHITE){
                    g2.setColor(Color.red);
                    g2.drawString("The King", 840, 100);
                    g2.drawString("is in check!", 840, 150);
                }
            }
        }
    }


    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }

    public void setPieces() {
        //WHITE
        pieces.add(new Pawn(WHITE, 0, 6));
        pieces.add(new Pawn(WHITE, 1, 6));
        pieces.add(new Pawn(WHITE, 2, 6));
        pieces.add(new Pawn(WHITE, 3, 6));
        pieces.add(new Pawn(WHITE, 4, 6));
        pieces.add(new Pawn(WHITE, 5, 6));
        pieces.add(new Pawn(WHITE, 6, 6));
        pieces.add(new Pawn(WHITE, 7, 6));
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Knight(WHITE, 1, 7));
        pieces.add(new Knight(WHITE, 6, 7));
        pieces.add(new Bishop(WHITE, 2, 7));
        pieces.add(new Bishop(WHITE, 5, 7));
        pieces.add(new Queen(WHITE, 3, 7));
        pieces.add(new King(WHITE, 4, 7));

//        //BLACK
        pieces.add(new Pawn(BLACK, 0, 1));
        pieces.add(new Pawn(BLACK, 1, 1));
        pieces.add(new Pawn(BLACK, 2, 1));
        pieces.add(new Pawn(BLACK, 3, 1));
        pieces.add(new Pawn(BLACK, 4, 1));
        pieces.add(new Pawn(BLACK, 5, 1));
        pieces.add(new Pawn(BLACK, 6, 1));
        pieces.add(new Pawn(BLACK, 7, 1));
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 7, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));
    }

    public void testsetPieces() {
        pieces.add(new Pawn(WHITE, 7, 6));
        pieces.add(new King(WHITE, 3, 7));
        pieces.add(new King(BLACK, 0, 3));
        pieces.add(new Bishop(BLACK, 1, 4));
        pieces.add(new Queen(BLACK, 4, 5));
    }
}
