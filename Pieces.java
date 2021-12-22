import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

abstract class Pieces implements Cloneable {
    private int position;
    private String pieceName;
    private int color;
    private BufferedImage sprite;
    private String spriteName;

    Pieces(int position, String pieceName, int color, String spriteName) {
        String filePrefix = "src/main/java/";
        this.spriteName = spriteName;
        try {
            sprite = ImageIO.read(new File(filePrefix + spriteName));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(spriteName);
        }
        this.position = position;
        this.pieceName = pieceName;
        this.color = color;
    }

    // Getters and Setters
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
        ArrayList<Integer> allMoves = new ArrayList<Integer>();
        for (int i = 0; i < board.getPieces().size(); i++) {
            if (color > 0 && board.getPieces().get(i).getColor() < 0) {
                allMoves.addAll(board.getPieces().get(i).findPossibleMoves(board));
            } else if (color < 0 && board.getPieces().get(i).getColor() > 0) {
                allMoves.addAll(board.getPieces().get(i).findPossibleMoves(board));
            }
        }
        return allMoves;
    }

    public int findKingPos(Board board) {
        for (int i = 0; i < board.getPieces().size(); i++) {
            if (this.color > 0 && board.getPieces().get(i).getName().compareTo("king") == 0 && board.getPieces().get(i).getColor() > 0) {
                return board.getPieces().get(i).getPosition();
            } else if (this.color < 0 && board.getPieces().get(i).getName().compareTo("king") == 0 && board.getPieces().get(i).getColor() < 0) {
                return board.getPieces().get(i).getPosition();
            }
        }
        return -1;
    }

    public static ArrayList<Pieces> copyPieces(ArrayList<Pieces> pieces) {
        Pieces tempPiece = null;
        ArrayList<Pieces> pieceList = new ArrayList<Pieces>();
        for (int i = 0; i < pieces.size(); i++) {
            Pieces piece = pieces.get(i);
            if (piece.pieceName.compareTo("king") == 0) {
                tempPiece = new King(piece.position, piece.pieceName, piece.color, piece.spriteName, ((King) piece).hasMoved());
            } else if (piece.pieceName.compareTo("queen") == 0) {
                tempPiece = new Queen(piece.position, piece.pieceName, piece.color, piece.spriteName);
            } else if (piece.pieceName.compareTo("rook") == 0) {
                tempPiece = new Rook(piece.position, piece.pieceName, piece.color, piece.spriteName, ((Rook) piece).hasMoved());
            } else if (piece.pieceName.compareTo("bishop") == 0) {
                tempPiece = new Bishop(piece.position, piece.pieceName, piece.color, piece.spriteName);
            } else if (piece.pieceName.compareTo("knight") == 0) {
                tempPiece = new Knight(piece.position, piece.pieceName, piece.color, piece.spriteName);
            } else if (piece.pieceName.compareTo("pawn") == 0) {
                tempPiece = new Pawn(piece.position, piece.pieceName, piece.color, piece.spriteName);
            }
            pieceList.add(tempPiece);
        }
        return pieceList;
    }


    public ArrayList<Integer> findLegalMoves(Board board) {
        int saveCurrentPos = this.position;
        ArrayList<Integer> possibleMoves = findPossibleMoves(board);
        ArrayList<Integer> legalMoves = new ArrayList<Integer>();
        int kingPos = findKingPos(board);

        for (int i = 0; i < possibleMoves.size(); i++) {
            if (!board.friendlySquare(possibleMoves.get(i), this.color)) {
                Pieces capturedPiece = board.movePiece(this, possibleMoves.get(i));
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