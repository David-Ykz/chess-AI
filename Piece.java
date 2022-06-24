import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

abstract class Piece {
    private int position;
    private String pieceName;
    private int color;
    private BufferedImage sprite;

    Piece(int position, String pieceName, int color) {
//        String filePrefix = "src/main/java/";
        String filePrefix = "";
        try {
            String prefix;
            if (color == 1) {
                prefix = "White ";
            } else {
                prefix = "Black ";
            }
            sprite = ImageIO.read(new File(filePrefix + prefix + pieceName + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(pieceName);
        }
        this.position = position;
        this.pieceName = pieceName;
        this.color = color;
    }

    public int getPosition() {
        return this.position;
    }
    public void setPosition(int newPosition) {
        this.position = newPosition;
    }
    public String getName() {
        return this.pieceName;
    }
    public int getColor() {
        return this.color;
    }
    abstract double findValue(Board board);
    abstract ArrayList<Integer> findPossibleMoves(Board board);
    public ArrayList<Integer> findAllMoves(Board board, int color) {
        ArrayList<Integer> allMoves = new ArrayList<>();
        for (Piece piece :board.getPieces()) {
            if (color > 0 && piece.getColor() < 0) {
                allMoves.addAll(piece.findPossibleMoves(board));
            } else if (color < 0 && piece.getColor() > 0) {
                allMoves.addAll(piece.findPossibleMoves(board));
            }
        }
        return allMoves;
    }
    public int findKingPos(Board board) {
        for (Piece piece :board.getPieces()) {
            if (this.color > 0 && piece.getName().compareTo("king") == 0 && piece.getColor() > 0) {
                return piece.getPosition();
            } else if (this.color < 0 && piece.getName().compareTo("king") == 0 && piece.getColor() < 0) {
                return piece.getPosition();
            }
        }
        return -1;
    }
    public ArrayList<Integer> findLegalMoves(Board board) {
        int saveCurrentPos = this.position;
        ArrayList<Integer> possibleMoves = findPossibleMoves(board);
        ArrayList<Integer> legalMoves = new ArrayList<Integer>();
        int kingPos = findKingPos(board);
        for (int i = 0; i < possibleMoves.size(); i++) {
            if (!board.friendlySquare(possibleMoves.get(i), this.color)) {
                Piece capturedPiece = board.movePiece(this, possibleMoves.get(i));
                if (!findAllMoves(board, this.color).contains(kingPos)) {
                    legalMoves.add(this.position);
                }
                board.revertMove(this, capturedPiece, saveCurrentPos);
            }
        }
        return legalMoves;
    }
    public void drawPiece(Graphics g, int GRIDSIZE) {
        g.drawImage(sprite, (position / 10 - 1) * GRIDSIZE, (position % 10 - 1) * GRIDSIZE, null);
    }
}