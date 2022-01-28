import java.util.ArrayList;

class Queen extends Piece {
  private ArrayList<Integer> squaresOfInfluence = new ArrayList<Integer>();
  Queen(int position, String pieceName, int color, String spriteName) {
    super(position, pieceName, color, spriteName);
  }

  public double findValue(Board board) {
    return 9;
  }
  
  
  public ArrayList<Integer> findPossibleMoves(Board board) {
    squaresOfInfluence.clear();
    findVerticalSquare(getPosition(), board, 1);
    findVerticalSquare(getPosition(), board, -1);
    findHorizontalSquare(getPosition(), board, 10);
    findHorizontalSquare(getPosition(), board, -10);
    findUpperSquares(board, getPosition(), 1);
    findUpperSquares(board, getPosition(), -1);
    findLowerSquares(board, getPosition(), 1);
    findLowerSquares(board, getPosition(), -1);
    return squaresOfInfluence;
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