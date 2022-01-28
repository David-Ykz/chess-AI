import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Locale;

class Board {
    private int turn;
    private HashMap<Integer, Piece> pieces;
    private HashMap<String, String> pieceNames = new HashMap<>();

    Board(int turn, HashMap<Integer, Piece> pieces) {
        this.turn = turn;
        this.pieces = pieces;
        pieceNames.put("king", "k");
        pieceNames.put("queen", "q");
        pieceNames.put("rook", "r");
        pieceNames.put("knight", "n");
        pieceNames.put("bishop", "b");
        pieceNames.put("pawn", "p");
    }

    public int getTurn() {
        return this.turn;
    }
    public void changeTurn() {
        this.turn = turn * -1;
    }
    public ArrayList<Piece> getPieces() {
        return new ArrayList<Piece>(this.pieces.values());
    }
    public void overridePiece(int position, Piece piece) {
        this.pieces.put(position, piece);
    }
    public Piece getPiece(int position) {
        return this.pieces.get(position);
    }
    public boolean emptySquare(int square) {
        return !this.pieces.containsKey(square);
    }
    public boolean friendlySquare(int square, int color) {
        return (!emptySquare(square) && getPiece(square).getColor() == color);
    }
    public boolean hostileSquare(int square, int color) {
        return (!emptySquare(square) && getPiece(square).getColor() != color);
    }
    public void changePiecePos(Piece piece, int newPosition) {
        Piece movedPiece = this.pieces.remove(piece.getPosition());
        movedPiece.setPosition(newPosition);
        this.pieces.put(newPosition, movedPiece);
    }
    public Piece movePiece(Piece piece, int position) {
        Piece removedPiece = null;
        if (pieces.containsKey(position) && getPiece(position).getColor() != piece.getColor()) {
             removedPiece = pieces.remove(position);
        }
        changePiecePos(piece, position);
        return removedPiece;
    }
    public void revertMove(Piece piece, Piece capturedPiece, int oldPosition) {
        changePiecePos(piece, oldPosition);
        if (capturedPiece != null) {
            pieces.put(capturedPiece.getPosition(), capturedPiece);
        }
    }
    public boolean isCheckmate() {
        int numOfLegalMoves = 0;
        for (Piece piece : getPieces()) {
            if (piece.getColor() == turn) {
                numOfLegalMoves += piece.findLegalMoves(this).size();
            }
        }
        return numOfLegalMoves == 0;
    }
    public String toFEN() {
        String[][] chessboard = new String[8][8];
        for (Piece piece : getPieces()) {
            String pieceCharacter = pieceNames.get(piece.getName());
            if (piece.getColor() == 1) {
                pieceCharacter = pieceCharacter.toUpperCase();
            }
            chessboard[piece.getPosition() % 10 - 1][piece.getPosition() / 10 - 1] = pieceCharacter;
        }

        String fen = "";

        for (int i = 0; i < chessboard.length; i++) {
            String[] row = chessboard[i];
            int counter = 0;
            for (int j = 0; j < row.length; j++) {
                if (row[j] == null) {
                    counter++;
                } else if (counter == 0) {
                    fen += row[j];
                } else {
                    fen += counter + row[j];
                    counter = 0;
                }
            }
            if (counter != 0) {
                fen += counter;
            }
            fen += "/";
        }
        if (this.turn > 0) {
            fen += "w";
        } else {
            fen += "b";
        }
        return fen;
    }





    public void drawEvaluation(Graphics g, int GRIDSIZE) {
        Color whiteGrey = new Color(200, 200, 200);
        Color blackGrey = new Color(100, 100, 100);
        double evaluation = evaluateBoard();
        int evaluationDisplay = (int)(-evaluation * 20);
        // Black Gauge
        g.setColor(blackGrey);
        g.fillRect(GRIDSIZE * 8, 0, 20, evaluationDisplay + GRIDSIZE * 4);
        // White Gauge
        g.setColor(whiteGrey);
        g.fillRect(GRIDSIZE * 8, evaluationDisplay + GRIDSIZE * 4, 20, GRIDSIZE * 4 - evaluationDisplay);
    }
    public void drawBoard(Graphics g, int GRIDSIZE) {
        Color lightSquare = new Color(241, 217, 182);
        Color darkSquare = new Color(181, 137, 99);
        Color highlightedLightSquare = new Color(205, 210, 106);
        Color highlightedDarkSquare = new Color(170, 162, 58);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if ((x + y) % 2 == 0) {
                    if (Chess.selectedSquares.contains(10 * (x + 1) + y + 1)) {
                        g.setColor(highlightedLightSquare);
                    } else {
                        g.setColor(lightSquare);
                    }
                } else {
                    if (Chess.selectedSquares.contains(10 * (x + 1) + y + 1)) {
                        g.setColor(highlightedDarkSquare);
                    } else {
                        g.setColor(darkSquare);
                    }
                }
                g.fillRect(x * GRIDSIZE, y * GRIDSIZE, GRIDSIZE, GRIDSIZE);
            }
        }
        for (Piece piece : this.pieces.values()) {
            piece.drawPiece(g, GRIDSIZE);
        }
    }

    // Important methods
    public double evaluateBoard() {
        double evaluation = 0;
        for (Piece piece : this.pieces.values()) {
            evaluation += piece.findValue(this) * piece.getColor();
        }
        return evaluation;
    }
}