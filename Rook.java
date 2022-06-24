import java.util.ArrayList;

class Rook extends Piece {
  private ArrayList<Integer> squaresOfInfluence = new ArrayList<Integer>();
  private ArrayList<Integer> verticalSquares = new ArrayList<Integer>();
  private boolean hasMoved;
  
  Rook(int position, int color, boolean hasMoved) {
    super(position, "rook", color);
    this.hasMoved = hasMoved;
  }
  
  public double findValue(Board board) {
    return 5;
  }
  
  public void setMoved() {
    hasMoved = true;
  }

  public void notMoved() {
    hasMoved = false;
  }

  public boolean hasMoved() {
    return hasMoved;
  }
  
  
  public ArrayList<Integer> findPossibleMoves(Board board) {
    squaresOfInfluence.clear();
    findVerticalSquare(getPosition(), board, 1);
    findVerticalSquare(getPosition(), board, -1);
    findHorizontalSquare(getPosition(), board, 10);
    findHorizontalSquare(getPosition(), board, -10);
    return squaresOfInfluence;
  }
  private void findVerticalSquare(int position, Board board, int direction) {
    if (direction > 0 && position%10 < 8) {
      if (board.emptySquare(position + direction) == true) {
        squaresOfInfluence.add(position + direction);
        findVerticalSquare(position + direction, board, direction);
      } else {
        squaresOfInfluence.add(position + direction);
      }
    } else if (direction < 0 && position%10 > 1) {
      if (board.emptySquare(position + direction) == true) {
        squaresOfInfluence.add(position + direction);
        findVerticalSquare(position + direction, board, direction);
      } else {
        squaresOfInfluence.add(position + direction);
      }
    }
  }
  private void findHorizontalSquare(int position, Board board, int direction) {
    if (direction > 0 && position/10 < 8) {
      if (board.emptySquare(position + direction)) {
        squaresOfInfluence.add(position + direction);
        findHorizontalSquare(position + direction, board, direction);
      } else {
        squaresOfInfluence.add(position + direction);
      }
    } else if (direction < 0 && position/10 > 1) {
      if (board.emptySquare(position + direction)) {
        squaresOfInfluence.add(position + direction);
        findHorizontalSquare(position + direction, board, direction);
      } else {
        squaresOfInfluence.add(position + direction);
      }
    }
  }
  
//  public ArrayList<Integer> findLegalMoves(Board board) { return null; }
  
  
  
  
  
}