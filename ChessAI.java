import java.util.ArrayList;


class ChessAI {
  ChessAI() {
  }
  
  public void makeMove(Board board, int depth) {
    if (depth == 0) {
      return;
    }
    
    int color = board.getTurn();
    ArrayList<Pieces> availablePieces = new ArrayList<Pieces>();
    
    for (int i = 0; i < board.getPieces().size(); i++) {
      Pieces piece = board.getPieces().get(i);
      if (piece.getColor() == color) {
        ArrayList<Integer> pieceMoves = piece.findLegalMoves(board);
        for (int eachMove = 0; eachMove < pieceMoves.size(); eachMove++) {
          Board newBoard = new Board(color, Pieces.copyPieces(board.getPieces()));
          Pieces newPiece = newBoard.getPieceByPos(piece.getPosition());
          newBoard.movePiece(newPiece, pieceMoves.get(eachMove));
          newBoard.changeTurn();
          board.addNextBoard(newBoard);
          makeMove(newBoard, depth - 1);
        }
      }
    }
  }
    
    
  
  
  
  
  
  
  
  
  
  public void makeRandomMove(Board board) {
    Chess.checkPromotion(board);
    int color = board.getTurn();
    ArrayList<Pieces> availablePieces = new ArrayList<Pieces>();
    
    for (int i = 0; i < board.getPieces().size(); i++) {
      Pieces piece = board.getPieces().get(i);
      if (piece.getColor() == color) {
        availablePieces.add(piece);
      }
    }
    
    Pieces randPiece = null;
    ArrayList<Integer> pieceMoves = new ArrayList<Integer>();
    
    while (pieceMoves.size() == 0) {
      randPiece = availablePieces.get((int)(Math.random() * availablePieces.size()));
      pieceMoves.clear();
      pieceMoves.addAll(randPiece.findLegalMoves(board));
    }
    int randMove = pieceMoves.get((int)(Math.random() * pieceMoves.size()));
    board.movePiece(randPiece, randMove);
    
  }

    
  
}