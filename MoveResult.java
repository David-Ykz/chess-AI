import java.util.ArrayList;

public class MoveResult {
    private Pieces piece;
    private Pieces capturedPiece;
    private int oldPosition;
    private int newPosition;
    private int turn;
    private ArrayList<MoveResult> children = new ArrayList<>();
    private double evaluation;


    public MoveResult(Pieces piece, Pieces capturedPiece, int oldPosition, int newPosition, int turn, double evaluation) {
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.turn = turn;
        this.evaluation = evaluation;
    }

    public Pieces getPiece() {
        return piece;
    }
    public Pieces getCapturedPiece() {
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
