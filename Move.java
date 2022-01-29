public class Move {
    private Piece piece;
    private Piece capturedPiece;
    private int newPosition;
    private int evaluation;

    public Move(Piece piece, Piece capturedPiece, int newPosition, int evaluation) {
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.newPosition = newPosition;
        this.evaluation = evaluation;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public int getEvaluation() {
        return evaluation;
    }
}
