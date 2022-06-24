import java.util.ArrayList;

class Bishop extends Piece {
  private ArrayList<Integer> squaresOfInfluence = new ArrayList<Integer>();
  Bishop(int position, int color) {
    super(position, "bishop", color);
  }
  
  public double findValue(Board board) {
    return 3;
  }
  
  
  
  private void findUpperSquares(Board board, int position, int direction) {
    if (direction > 0 && position/10 < 8 && position%10 > 1) {
      if (board.emptySquare(position + direction * 10 - 1)) {
        squaresOfInfluence.add(position + direction * 10 - 1);
        findUpperSquares(board, position + direction * 10 - 1, direction);
      } else {
        squaresOfInfluence.add(position + direction * 10 - 1);
      }
    } else if (direction < 0 && position/10 > 1 && position%10 > 1) {
      if (board.emptySquare(position + direction * 10 - 1)) {
        squaresOfInfluence.add(position + direction * 10 - 1);
        findUpperSquares(board, position + direction * 10 - 1, direction);
      } else {
        squaresOfInfluence.add(position + direction * 10 - 1);
      }
    }
  }

  private void findLowerSquares(Board board, int position, int direction) {
    if (direction > 0 && position/10 < 8 && position%10 < 8) {
      if (board.emptySquare(position + direction * 10 + 1)) {
        squaresOfInfluence.add(position + direction * 10 + 1);
        findLowerSquares(board, position + direction * 10 + 1, direction);
      } else {
        squaresOfInfluence.add(position + direction * 10 + 1);
      }
    } else if (direction < 0 && position/10 > 1 && position%10 < 8) {
      if (board.emptySquare(position + direction * 10 + 1)) {
        squaresOfInfluence.add(position + direction * 10 + 1);
        findLowerSquares(board, position + direction * 10 + 1, direction);
      } else {
        squaresOfInfluence.add(position + direction * 10 + 1);
      }
    }
  }
  
  
  public ArrayList<Integer> findPossibleMoves(Board board) {
    squaresOfInfluence.clear();
    findUpperSquares(board, getPosition(), 1);
    findUpperSquares(board, getPosition(), -1);
    findLowerSquares(board, getPosition(), 1);
    findLowerSquares(board, getPosition(), -1);
    
    return squaresOfInfluence;
  }
  
//  public ArrayList<Integer> findLegalMoves(Board board) { return null; }
  
  
  
}
