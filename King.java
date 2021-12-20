import java.util.ArrayList;

class King extends Pieces {
  private ArrayList<Integer> squaresOfInfluence = new ArrayList<Integer>();
  private int[] kingSquares = {-11, -1, 9, -10, 10, -9, 1, 11};
  private boolean hasMoved;
  King(int position, String pieceName, int color, String spriteName, boolean hasMoved) {
    super(position, pieceName, color, spriteName);
    this.hasMoved = hasMoved;
  }
  
  public double findValue(Board board) {
    return 999;
  }
  
  public void setMoved() {
    hasMoved = true;
  }
  
  public boolean hasMoved() {
    return hasMoved;
  }
  
  public ArrayList<Integer> findPossibleMoves(Board board) {
    squaresOfInfluence.clear();
    for (int i = 0; i < kingSquares.length; i++) {
      int newPos = getPosition() + kingSquares[i];
      if (newPos/10 > 0 && newPos/10 < 9 && newPos%10 > 0 && newPos%10 < 9) {
        squaresOfInfluence.add(newPos);
      }
    }

    return squaresOfInfluence;
  }
  
  
  
  public ArrayList<Integer> findLegalMoves(Board board) {
    Pieces tempPiece = this;
    int saveCurrentPos = getPosition();
    ArrayList<Pieces> savePieces = copyPieces(board.getPieces());
    ArrayList<Integer> possibleMoves = findPossibleMoves(board);
    ArrayList<Integer> legalMoves = new ArrayList<Integer>();

    if (hasMoved == false && getColor() < 0) {
      // Kingside Castle
      if (board.friendlySquare(81, getColor()) && board.emptySquare(61) && board.emptySquare(71)) {
        for (int i = 0; i < board.getPieces().size(); i++) {
          if (board.getPieces().get(i).getName().compareTo("rook") == 0
             && board.getPieces().get(i).getPosition() == 81
                && ((Rook)board.getPieces().get(i)).hasMoved() == false) {
            ArrayList<Integer> allMoves = findAllMoves(board, getColor());
            if (allMoves.contains(51) == false && allMoves.contains(61) == false && allMoves.contains(71) == false) {
              legalMoves.add(71);
            }
          }
        }
      }
      // Queenside Castle
      if (board.friendlySquare(11, getColor()) && board.emptySquare(21) && board.emptySquare(31) && board.emptySquare(41)) {
        for (int i = 0; i < board.getPieces().size(); i++) {
          if (board.getPieces().get(i).getName().compareTo("rook") == 0
             && board.getPieces().get(i).getPosition() == 11
                && ((Rook)board.getPieces().get(i)).hasMoved() == false) {
            ArrayList<Integer> allMoves = findAllMoves(board, getColor());
            if (allMoves.contains(51) == false && allMoves.contains(41) == false && allMoves.contains(31) == false) {
              legalMoves.add(31);
            }
          }
        }
      }
    } else if (hasMoved == false && getColor() > 0) {
      // Kingside Castle
      if (board.friendlySquare(88, getColor()) && board.emptySquare(68) && board.emptySquare(78)) {
        for (int i = 0; i < board.getPieces().size(); i++) {
          if (board.getPieces().get(i).getName().compareTo("rook") == 0
             && board.getPieces().get(i).getPosition() == 88
                && ((Rook)board.getPieces().get(i)).hasMoved() == false) {
            ArrayList<Integer> allMoves = findAllMoves(board, getColor());
            if (allMoves.contains(58) == false && allMoves.contains(68) == false && allMoves.contains(78) == false) {
              legalMoves.add(78);
            }
          }
        }
      }
      // Queenside Castle
      if (board.friendlySquare(18, getColor()) && board.emptySquare(28) && board.emptySquare(38) && board.emptySquare(48)) {
        for (int i = 0; i < board.getPieces().size(); i++) {
          if (board.getPieces().get(i).getName().compareTo("rook") == 0
             && board.getPieces().get(i).getPosition() == 18
                && ((Rook)board.getPieces().get(i)).hasMoved() == false) {
            ArrayList<Integer> allMoves = findAllMoves(board, getColor());
            if (allMoves.contains(58) == false && allMoves.contains(48) == false && allMoves.contains(38) == false) {
              legalMoves.add(38);
            }
          }
        }
      }
    }
    
    for (int i = 0; i < possibleMoves.size(); i++) {
      if (board.friendlySquare(possibleMoves.get(i), getColor()) == false) {
        board.movePiece(this, possibleMoves.get(i));
        if (findAllMoves(board, getColor()).contains(getPosition()) == false) {
          legalMoves.add(getPosition());
        }
        board.setPieces(savePieces);
        setPosition(saveCurrentPos);
        for (int j = 0; j < board.getPieces().size(); j++) {
          if (board.getPieces().get(j).getPosition() == saveCurrentPos) {
            board.overridePiece(j, tempPiece);
          }
        }
      }
    }
    return legalMoves;
  }
  
}