import java.util.ArrayList;

public class MoveResult {
    private Piece piece;
    private Piece capturedPiece;
    private int oldPosition;
    private int newPosition;
    private int turn;
    private ArrayList<MoveResult> children = new ArrayList<>();
    private double evaluation;


    public MoveResult(Piece piece, Piece capturedPiece, int oldPosition, int newPosition, int turn, double evaluation) {
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.turn = turn;
        this.evaluation = evaluation;
    }

    public Piece getPiece() {
        return piece;
    }
    public Piece getCapturedPiece() {
        return capturedPiece;
    }
    public int getOldPosition() {
        return oldPosition;
    }
    public int getNewPosition() {
        return newPosition;
    }
    public int getTurn() {
        return turn;
    }
    public ArrayList<MoveResult> getChildren() {
        return children;
    }
    public double getEvaluation() {
        return evaluation;
    }
    public void setEvaluation(double newEvaluation) {
        this.evaluation = newEvaluation;
    }
    public void addChild(MoveResult move) {
        this.children.add(move);
    }

}
