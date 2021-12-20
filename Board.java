import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
class Board {
  private Board previousBoard;
  private Board nextBoard;
  private ArrayList<Board> nextBoards;
  private int turn;
  private ArrayList<Pieces> pieces = new ArrayList<Pieces>();

  Board(int turn, ArrayList<Pieces> pieces) {
    this.turn = turn;
    this.pieces = pieces;
  }

  
  // Getters and Setters
  public void addNextBoard(Board board) {
    this.nextBoards.add(board);
  }
  
  public ArrayList<Board> getNextBoards() {
    return this.nextBoards;
  }
  public Board getNext() {
    return this.nextBoard;
  }
  public void setNext(Board next) {
    this.nextBoard = next;
  }
  public Board getPrev() {
    return this.previousBoard;
  }
  public void setPrev(Board prev) {
    this.previousBoard = prev;
  }
  public int getTurn() {
    return this.turn;
  }
  public void changeTurn() {
    if (turn == -1) {
      turn = 1;
    } else {
      turn = -1;
    }
  }
  public ArrayList<Pieces> getPieces() {
    return this.pieces;
  }
  
  public void setPieces(ArrayList<Pieces> changedPieces) {
    this.pieces.clear();
    this.pieces.addAll(changedPieces);
  }
  
  public void overridePiece(int index, Pieces piece) {
    this.pieces.set(index, piece);
  }
  
  
  public Pieces getPiece(int index) {
    return this.pieces.get(index);
  }
  
  public Pieces getPieceByPos(int position) {
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).getPosition() == position) {
        return pieces.get(i);
      }
    }
    return null;
  }
  
  public void movePiece(Pieces piece, int position) {
    int saveIndex = -1;
    if (emptySquare(position) == false) {
      for (int i = 0; i < this.pieces.size(); i++) {
        if (pieces.get(i).getPosition() == position && pieces.get(i).getColor() != piece.getColor()) {
          saveIndex = i;
        }
      }
      pieces.remove(saveIndex);
    }
    piece.setPosition(position);
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).getPosition() == position) {
      }
    }
  }
  
  
  public boolean emptySquare(int square) {
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).getPosition() == square) {
        return false;
      }
    }
    return true;
  }
  
  public boolean friendlySquare(int square, int color) {
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).getPosition() == square && pieces.get(i).getColor() == color) {
        return true;
      }
    }
    return false;
  }
  
  public boolean hostileSquare(int square, int color) {
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).getPosition() == square && pieces.get(i).getColor() != color) {
        return true;
      }
    }
    return false;
  }
  
  public void findAllLegalMoves() {
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).getColor() == turn) {
        pieces.get(i).findLegalMoves(this);
      }
    }
  }
  
  public boolean isCheckmate() {
    int numOfLegalMoves = 0;
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).getColor() == turn) {
        numOfLegalMoves += pieces.get(i).findLegalMoves(this).size();
      }
    }
    if (numOfLegalMoves == 0) {
      return true;
    } else {
      return false;
    }
  }
  
  
  public void printBoard() {
    for (int y = 1; y < 9; y++) {
      for (int x = 1; x < 9; x++) {
        boolean foundPiece = false;
        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i).getPosition() == x * 10 + y) {
            System.out.print(pieces.get(i).getName().substring(0,1));
            foundPiece = true;
          }
        }
        if (foundPiece == false) {
          System.out.print("#");
        }
      }
      System.out.println("");
    }
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
    
    for (int i = 0; i < pieces.size(); i++) {
      pieces.get(i).drawPiece(g, GRIDSIZE);
    }
    
  }
  
  // Important methods
  public double evaluateBoard() {
    double evaluation = 0;
    
    for (int i = 0; i < pieces.size(); i++) {
      evaluation += pieces.get(i).findValue(this) * pieces.get(i).getColor();
    }
    return evaluation;
  }
  
  
  
}