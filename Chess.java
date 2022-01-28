import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Chess {
    static ArrayList<Integer> selectedSquares = new ArrayList<Integer>();
    static Board currentBoard;
    static Piece selectedPiece;
    static ChessAI chessAI = new ChessAI();



    public static HashMap<Integer, Piece> setupCustomBoard() {
        HashMap<Integer, Piece> startingPieces = new HashMap<>();
        Pawn pawn;
        Knight knight;
        Bishop bishop;
        Rook rook;
        Queen queen;
        King king;
        king = new King(58, "king", 1, "White King.png", false);
        startingPieces.put(king.getPosition(), king);
        king = new King(51, "king", -1, "Black King.png", false);
        startingPieces.put(king.getPosition(), king);
        knight = new Knight(66, "knight", -1, "Black Knight.png");
        startingPieces.put(knight.getPosition(), knight);
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
            pawn = new Pawn(i * 10 + 7, "pawn", 1, "White Pawn.png");
            startingPieces.put(pawn.getPosition(), pawn);
            pawn = new Pawn(i * 10 + 2, "pawn", -1, "Black Pawn.png");
            startingPieces.put(pawn.getPosition(), pawn);
        }
        rook = new Rook(18, "rook", 1, "White Rook.png", false);
        startingPieces.put(rook.getPosition(), rook);
        rook = new Rook(88, "rook", 1, "White Rook.png", false);
        startingPieces.put(rook.getPosition(), rook);
        knight = new Knight(28, "knight", 1, "White Knight.png");
        startingPieces.put(knight.getPosition(), knight);
        knight = new Knight(78, "knight", 1, "White Knight.png");
        startingPieces.put(knight.getPosition(), knight);
        bishop = new Bishop(38, "bishop", 1, "White Bishop.png");
        startingPieces.put(bishop.getPosition(), bishop);
        bishop = new Bishop(68, "bishop", 1, "White Bishop.png");
        startingPieces.put(bishop.getPosition(), bishop);
        queen = new Queen(48, "queen", 1, "White Queen.png");
        startingPieces.put(queen.getPosition(), queen);
        king = new King(58, "king", 1, "White King.png", false);
        startingPieces.put(king.getPosition(), king);

        rook = new Rook(11, "rook", -1, "Black Rook.png", false);
        startingPieces.put(rook.getPosition(), rook);
        rook = new Rook(81, "rook", -1, "Black Rook.png", false);
        startingPieces.put(rook.getPosition(), rook);
        knight = new Knight(21, "knight", -1, "Black Knight.png");
        startingPieces.put(knight.getPosition(), knight);
        knight = new Knight(71, "knight", -1, "Black Knight.png");
        startingPieces.put(knight.getPosition(), knight);
        bishop = new Bishop(31, "bishop", -1, "Black Bishop.png");
        startingPieces.put(bishop.getPosition(), bishop);
        bishop = new Bishop(61, "bishop", -1, "Black Bishop.png");
        startingPieces.put(bishop.getPosition(), bishop);
        queen = new Queen(41, "queen", -1, "Black Queen.png");
        startingPieces.put(queen.getPosition(), queen);
        king = new King(51, "king", -1, "Black King.png", false);
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
        int depth = 2;
        double start = System.nanoTime();
        System.out.println("changed turn right before");
        MoveResult root = new MoveResult(null, null, -1, -1, currentBoard.getTurn(), currentBoard.evaluateBoard());
        chessAI.generateDepthSearch(currentBoard, root, depth);
        double end = System.nanoTime();
        System.out.println("Execution time: " + (end - start) / 1000000000);
        Queue<MoveResult> levelQueue = new LinkedList<>();
        levelQueue.add(root);
//        chessAI.traverseTree(levelQueue);
        chessAI.findBest(currentBoard, root);
        System.out.println();
        chessAI.traverseTree(levelQueue);
        ArrayList<MoveResult> bestMoves = new ArrayList<>();
        System.out.println(root.getEvaluation());
        for (MoveResult eachMove : root.getChildren()) {
            System.out.print("Move: " + eachMove.getPiece().getName() + " " +eachMove.getNewPosition() + " Score: " + eachMove.getEvaluation() + " ");
            if (eachMove.getEvaluation() == root.getEvaluation()) {
                bestMoves.add(eachMove);
            }
        }
        MoveResult eachMove = bestMoves.get((int)(Math.random() * bestMoves.size()));
        currentBoard.movePiece(eachMove.getPiece(), eachMove.getNewPosition());
      //  currentBoard.changeTurn();
        // TURNS ARE RANDOMLY CHANGING SOMEWHERE PLZ FIX

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

                if (selectedPiece.getColor() > 0) {
                    if (position == 78) {
                        Piece tempPiece = board.getPiece(88);
                        board.movePiece(tempPiece, 68);
                    } else if (position == 38) {
                        Piece tempPiece = board.getPiece(18);
                        board.movePiece(tempPiece, 48);
                    }
                } else if (selectedPiece.getColor() < 0) {
                    if (position == 71) {
                        Piece tempPiece = board.getPiece(81);
                        board.movePiece(tempPiece, 61);
                    } else if (position == 31) {
                        Piece tempPiece = board.getPiece(11);
                        board.movePiece(tempPiece, 41);
                    }
                }

            }
            board.movePiece(selectedPiece, position);
            checkPromotion(board);
            board.changeTurn();

            if (board.isCheckmate()) {
                System.exit(0);
            }
//      System.out.println("changed turn");
            int currentBoardTurn = board.getTurn();
            makeAIMove();
            if (currentBoardTurn == board.getTurn()) {
                board.changeTurn();
            }


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
        HashMap<Integer, Piece> startingPieces = setupCustomBoard();
        currentBoard = new Board(1, startingPieces);
        ChessVisualizer visualizer = new ChessVisualizer(currentBoard);
        System.out.println(currentBoard.toFEN());

//    for (int i = 0; i < currentBoard.getPieces().size(); i++) {
//      selectedSquares.addAll(currentBoard.getPiece(i).findPossibleMoves(currentBoard));
//    }
    }
}