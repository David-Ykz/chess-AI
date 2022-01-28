import java.util.ArrayList;
class Knight extends Piece {
  private ArrayList<Integer> squaresOfInfluence = new ArrayList<Integer>();
  private int[] knightSquares = { -8, 12, 21, 19, 8, -12, -21, -19};
  Knight(int position, String pieceName, int color, String spriteName) {
    super(position, pieceName, color, spriteName);
  }
  
  public double findValue(Board board) {
    return 3;
  }
  
  
  public ArrayList<Integer> findPossibleMoves(Board board) {
    squaresOfInfluence.clear();
    for (int i = 0; i < knightSquares.length; i++) {
      int newPos = getPosition() + knightSquares[i];
      if (newPos/10 > 0 && newPos/10 < 9 && newPos%10 > 0 && newPos%10 < 9) {
        squaresOfInfluence.add(newPos);
      }
    }
    return squaresOfInfluence;
  }
  
//  public ArrayList<Integer> findLegalMoves(Board board) { return null; }
  
  
  
  
}