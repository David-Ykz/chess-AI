import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Chess {
    static ArrayList<Integer> selectedSquares = new ArrayList<Integer>();
    static Board currentBoard;
    static Piece selectedPiece;
    static ChessAI chessAI = new ChessAI();
    static HashMap<Integer, HashMap<String, Integer>> evaluationData;

    public static HashMap<Integer, Piece> fenToBoard(String fen) {
        HashMap<Integer, Piece> startingPieces = new HashMap<>();
        Pawn pawn;
        Knight knight;
        Bishop bishop;
        Rook rook;
        Queen queen;
        King king;
        for (int i = 0; i < 8; i++) {
            String row;
            if (i < 7) {
                row = fen.substring(0, fen.indexOf('/') - 1);
                System.out.println(row);
                fen = fen.substring(fen.indexOf('/') + 1);
            } else {
                row = fen.substring(0, fen.indexOf(' ') - 1);
                System.out.println(row);
                fen = fen.substring(fen.indexOf(' ') + 1);
            }

            for (int j = 0; j < row.length(); j++) {
                if (row.substring(j, j + 1).compareTo("p") == 0) {
//                    pawn = new Pawn(10 * j)
                }
            }

        }
        return startingPieces;

    }

    public static HashMap<Integer, Piece> setupCustomBoard() {
        HashMap<Integer, Piece> startingPieces = new HashMap<>();
        Pawn pawn;
        Knight knight;
        Bishop bishop;
        Rook rook;
        Queen queen;
        King king;
        king = new King(68, 1, false);
        startingPieces.put(king.getPosition(), king);
        king = new King(51, -1, false);
        startingPieces.put(king.getPosition(), king);
        rook = new Rook(11, -1, false);
        startingPieces.put(rook.getPosition(), rook);
        rook = new Rook(72, -1, true);
        startingPieces.put(rook.getPosition(), rook);
        rook = new Rook(88, 1, false);
        startingPieces.put(rook.getPosition(), rook);
        rook = new Rook(48, 1, true);
        startingPieces.put(rook.getPosition(), rook);
        queen = new Queen(13, -1);
        startingPieces.put(queen.getPosition(), queen);
        queen = new Queen(45, 1);
        startingPieces.put(queen.getPosition(), queen);
        pawn = new Pawn(77, 1);
        startingPieces.put(pawn.getPosition(), pawn);
        return startingPieces;
    }

    public static HashMap<Integer, Piece> fillStartingPieces() {
        HashMap<Integer, Piece> startingPieces = new HashMap<>();
        Pawn pawn;
        Knight knight;
        Bishop bishop;
        Rook rook;
        Queen queen;
        King king;
        for (int i = 1; i < 9; i++) {
            pawn = new Pawn(i * 10 + 7, 1);
            startingPieces.put(pawn.getPosition(), pawn);
            pawn = new Pawn(i * 10 + 2, -1);
            startingPieces.put(pawn.getPosition(), pawn);
        }
        rook = new Rook(18, 1, false);
        startingPieces.put(rook.getPosition(), rook);
        rook = new Rook(88, 1, false);
        startingPieces.put(rook.getPosition(), rook);
        knight = new Knight(28, 1);
        startingPieces.put(knight.getPosition(), knight);
        knight = new Knight(78, 1);
        startingPieces.put(knight.getPosition(), knight);
        bishop = new Bishop(38, 1);
        startingPieces.put(bishop.getPosition(), bishop);
        bishop = new Bishop(68, 1);
        startingPieces.put(bishop.getPosition(), bishop);
        queen = new Queen(48, 1);
        startingPieces.put(queen.getPosition(), queen);
        king = new King(58, 1, false);
        startingPieces.put(king.getPosition(), king);

        rook = new Rook(11, -1, false);
        startingPieces.put(rook.getPosition(), rook);
        rook = new Rook(81, -1, false);
        startingPieces.put(rook.getPosition(), rook);
        knight = new Knight(21, -1);
        startingPieces.put(knight.getPosition(), knight);
        knight = new Knight(71, -1);
        startingPieces.put(knight.getPosition(), knight);
        bishop = new Bishop(31, -1);
        startingPieces.put(bishop.getPosition(), bishop);
        bishop = new Bishop(61, -1);
        startingPieces.put(bishop.getPosition(), bishop);
        queen = new Queen(41, -1);
        startingPieces.put(queen.getPosition(), queen);
        king = new King(51, -1, false);
        startingPieces.put(king.getPosition(), king);

        return startingPieces;

    }

    /*
     * --Features to implement:
     * Create promotions
     * Make a undo button
     *
     * --Bugs:
     * King moves back into check
     * Pawns hop over other pieces
     *
     *
     *  -- More bugs:
     * Captures don't work
     * Doesn't change turns
     *
     *
     */


    public static void makeAIMove() {
        double start = System.nanoTime();
        Move bestMove = chessAI.generateDepthSearch(currentBoard, 3);
        currentBoard.movePiece(currentBoard.getPiece(bestMove.getOldPosition()), bestMove.getNewPosition());
        double end = System.nanoTime();
        System.out.println((end - start)/1000000000);
    }

    public static void checkPromotion(Board board) {
        for (Piece piece : board.getPieces()) {
            if (piece.getName().compareTo("pawn") == 0) {
                if (piece.getColor() > 0) {
                    if (piece.getPosition() % 10 == 1) {
                        ((Pawn) (piece)).promotePawn(board);
                    }
                } else if (piece.getColor() < 0) {
                    if (piece.getPosition() % 10 == 8) {
                        ((Pawn) (piece)).promotePawn(board);
                    }
                }
            }
        }
    }

    public static void processClick(int position, Board board) {
        boolean foundPiece = false;
        if (selectedSquares.contains(position)) {
            for (Piece piece : board.getPieces()) {
                if (piece.getPosition() == selectedPiece.getPosition()) {
                    selectedPiece = piece;
                }
            }
            if (selectedPiece.getName().compareTo("rook") == 0) {
                ((Rook) selectedPiece).setMoved();
            } else if (selectedPiece.getName().compareTo("king") == 0) {
                ((King) selectedPiece).setMoved();
            }
            board.movePiece(selectedPiece, position);
            checkPromotion(board);
            board.changeTurn();

            if (board.isCheckmate()) {
                System.exit(0);
            }
            int currentBoardTurn = board.getTurn();
            makeAIMove();
//            chessAI.findMove(board); // Uses database

            if (currentBoardTurn == board.getTurn()) {
                board.changeTurn();
            }
//            board.changeTurn();



        } else {
            for (Piece piece : board.getPieces()) {
                if (position == piece.getPosition()
                        && piece.getColor() == board.getTurn()) {
                    selectedPiece = piece;
                    displaySelectedMoves(selectedPiece, board);
                    foundPiece = true;
                }
            }
        }
        if (!foundPiece) {
            selectedSquares.clear();
            selectedPiece = null;
        }
    }


    public static void displaySelectedMoves(Piece piece, Board board) {
        selectedSquares.clear();
        selectedSquares.addAll(piece.findLegalMoves(board));
    }


    public boolean moveable(Board board, Piece piece) {
        int saveCurrentPos = piece.getPosition();
        return false;
    }


    public static void main(String[] args) throws Exception {
//        EvaluationReader evaluationReader = new EvaluationReader("chessData.csv");
  //      evaluationData = evaluationReader.getEvaluations();
        HashMap<Integer, Piece> startingPieces = fillStartingPieces();
//        HashMap<Integer, Piece> startingPieces = setupCustomBoard();
        currentBoard = new Board(1, startingPieces);
        ChessVisualizer visualizer = new ChessVisualizer(currentBoard);
        System.out.println(currentBoard.toFEN());

//    for (int i = 0; i < currentBoard.getPieces().size(); i++) {
//      selectedSquares.addAll(currentBoard.getPiece(i).findPossibleMoves(currentBoard));
//    }
    }
}