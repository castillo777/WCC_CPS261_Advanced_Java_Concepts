package chess;


import javax.swing.*;
import java.awt.*;

public class ChessBoard extends JFrame {
    public ChessBoard() {

        super("Chess ... a great game");

        ChessPiece.readInImages();
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void paint(Graphics g) {
        super.paint(g);
        Insets insets = getInsets();
        int top = insets.top;
        int bottom = insets.bottom;
        int left = insets.left;
        int right = insets.right;

        int height = getHeight();
        int width = getWidth();


        int cell_h = (height - top - bottom) / 8;
        int cell_w = (width - left - right) / 8;


        BoardDimensions bd = new BoardDimensions(left,top,cell_w,cell_h);

        int x, y;

        for (int row = 0; row < 8; row++) {
            y = top + row * cell_h;
            for (int col = 0; col < 8; col++) {
                x = left + col * cell_w;
                boolean greenColor = (row + col) % 2 == 1;
                if (greenColor) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.white);
                }
                g.fillRect(x, y, cell_w, cell_h);

            }
        }

        for (int i = 0; i < 8; i++) {
            if (i == 0 || i == 7){
                drawInLocation(g,PieceType.Rook,ColorType.black,i,0,bd);
                drawInLocation(g,PieceType.Rook,ColorType.white,i,7,bd);
            }
            if (i == 1 || i == 6){
                drawInLocation(g,PieceType.Knight,ColorType.black,i,0,bd);
                drawInLocation(g,PieceType.Knight,ColorType.white,i,7,bd);
            }
            if (i == 2 || i == 5){
                drawInLocation(g,PieceType.Bishop,ColorType.black,i,0,bd);
                drawInLocation(g,PieceType.Bishop,ColorType.white,i,7,bd);
            }

            if (i == 3){
                drawInLocation(g,PieceType.King,ColorType.black,i,0,bd);
                drawInLocation(g,PieceType.King,ColorType.white,i,7,bd);

            }
            if (i == 4){
                drawInLocation(g,PieceType.Queen,ColorType.black,i,0,bd);
                drawInLocation(g,PieceType.Queen,ColorType.white,i,7,bd);

            }



            drawInLocation(g,PieceType.Pawn,ColorType.black,i,1,bd);
            drawInLocation(g,PieceType.Pawn,ColorType.white,i,6,bd);
        }

    }

    void drawInLocation (Graphics g, PieceType pt, ColorType ct, int col, int row, BoardDimensions bd){

        Piece blackRook = new Piece(pt,ct,col, row);
        blackRook.drawInPosition(g,bd);

    }

    public static void main(String[] args) {
        ChessBoard cb = new ChessBoard();

    }


}