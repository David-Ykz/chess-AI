import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Chess {
    static ArrayList<Integer> selectedSquares = new ArrayList<Integer>();
    static Board currentBoard;
    static Pieces selectedPiece;
    static ChessAI chessAI = new ChessAI();



    public static ArrayList<Pieces> setupCustomBoard() {
        ArrayList<Pieces> startingPieces = new ArrayList<Pieces>();
        Pawn pawn;
        Knight knight;
        Bishop bishop;
        Rook rook;
        Queen queen;
        King king;

        king = new King(58, "king", 1, "White King.png", false);
        startingPieces.add(king);
        king = new King(51, "king", -1, "Black King.png", false);
        startingPieces.add(king);

        knight = new Knight(66, "knight", -1, "Black Knight.png");
        startingPieces.add(knight);

        return startingPieces;

    }

    public static ArrayList<Pieces> fillStartingPieces() {
        ArrayList<Pieces> startingPieces = new ArrayList<Pieces>();
        Pawn pawn;
        Knight knight;
        Bishop bishop;
        Rook rook;
        Queen queen;
        King king;
        for (int i = 1; i < 9; i++) {
            pawn = new Pawn(i * 10 + 7, "pawn", 1, "White Pawn.png");
            startingPieces.add(pawn);
            pawn = new Pawn(i * 10 + 2, "pawn", -1, "Black Pawn.png");
            startingPieces.add(pawn);
        }
        rook = new Rook(18, "rook", 1, "White Rook.png", false);
        startingPieces.add(rook);
        rook = new Rook(88, "rook", 1, "White Rook.png", false);
        startingPieces.add(rook);
        knight = new Knight(28, "knight", 1, "White Knight.png");
        startingPieces.add(knight);
        knight = new Knight(78, "knight", 1, "White Knight.png");
        startingPieces.add(knight);
        bishop = new Bishop(38, "bishop", 1, "White Bishop.png");
        startingPieces.add(bishop);
        bishop = new Bishop(68, "bishop", 1, "White Bishop.png");
        startingPieces.add(bishop);
        queen = new Queen(48, "queen", 1, "White Queen.png");
        startingPieces.add(queen);
        king = new King(58, "king", 1, "White King.png", false);
        startingPieces.add(king);

        rook = new Rook(11, "rook", -1, "Black Rook.png", false);
        startingPieces.add(rook);
        rook = new Rook(81, "rook", -1, "Black Rook.png", false);
        startingPieces.add(rook);
        knight = new Knight(21, "knight", -1, "Black Knight.png");
        startingPieces.add(knight);
        knight = new Knight(71, "knight", -1, "Black Knight.png");
        startingPieces.add(knight);
        bishop = new Bishop(31, "bishop", -1, "Black Bishop.png");
        startingPieces.add(bishop);
        bishop = new Bishop(61, "bishop", -1, "Black Bishop.png");
        startingPieces.add(bishop);
        queen = new Queen(41, "queen", -1, "Black Queen.png");
        startingPieces.add(queen);
        king = new King(51, "king", -1, "Black King.png", false);
        startingPieces.add(king);

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
        chessAI.traverseTree(levelQueue);
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
        for (int i = 0; i < board.getPieces().size(); i++) {
            if (board.getPieces().get(i).getName().compareTo("pawn") == 0) {
                if (board.getPieces().get(i).getColor() > 0) {
                    if (board.getPieces().get(i).getPosition() % 10 == 1) {
                        ((Pawn) (board.getPieces().get(i))).promotePawn(board);
                    }
                } else if (board.getPieces().get(i).getColor() < 0) {
                    if (board.getPieces().get(i).getPosition() % 10 == 8) {
                        ((Pawn) (board.getPieces().get(i))).promotePawn(board);
                    }
                }
            }
        }
    }

    public static void processClick(int position, Board board) {
        boolean foundPiece = false;
        if (selectedSquares.contains(position)) {
            for (int i = 0; i < board.getPieces().size(); i++) {
                if (board.getPieces().get(i).getPosition() == selectedPiece.getPosition()) {
                    selectedPiece = board.getPieces().get(i);
                }
            }
            if (selectedPiece.getName().compareTo("rook") == 0) {
                ((Rook) selectedPiece).setMoved();
            } else if (selectedPiece.getName().compareTo("king") == 0) {
                ((King) selectedPiece).setMoved();

                if (selectedPiece.getColor() > 0) {
                    if (position == 78) {
                        Pieces tempPiece = board.getPieceByPos(88);
                        board.movePiece(tempPiece, 68);
                    } else if (position == 38) {
                        Pieces tempPiece = board.getPieceByPos(18);
                        board.movePiece(tempPiece, 48);
                    }
                } else if (selectedPiece.getColor() < 0) {
                    if (position == 71) {
                        Pieces tempPiece = board.getPieceByPos(81);
                        board.movePiece(tempPiece, 61);
                    } else if (position == 31) {
                        Pieces tempPiece = board.getPieceByPos(11);
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
            for (int i = 0; i < board.getPieces().size(); i++) {
                if (position == board.getPieces().get(i).getPosition()
                        && board.getPieces().get(i).getColor() == board.getTurn()) {
                    selectedPiece = board.getPieces().get(i);
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


    public static void displaySelectedMoves(Pieces piece, Board board) {
        selectedSquares.clear();
        selectedSquares.addAll(piece.findLegalMoves(board));
    }


    public boolean moveable(Board board, Pieces piece) {
        int saveCurrentPos = piece.getPosition();
        return false;
    }


    public static void main(String[] args) throws Exception {
        ArrayList<Pieces> startingPieces = new ArrayList<Pieces>();
//        startingPieces.addAll(fillStartingPieces());
        startingPieces.addAll(setupCustomBoard());
        currentBoard = new Board(1, startingPieces);
        ChessVisualizer visualizer = new ChessVisualizer(currentBoard);

//    for (int i = 0; i < currentBoard.getPieces().size(); i++) {
//      selectedSquares.addAll(currentBoard.getPiece(i).findPossibleMoves(currentBoard));
//    }
    }
}