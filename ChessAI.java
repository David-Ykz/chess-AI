import java.util.ArrayList;


class ChessAI {
    ChessAI() {
    }

    public double findMax(ArrayList<MoveResult> moves) {
        MoveResult bestMove = moves.get(0);
        for (MoveResult move : moves) {
            if (move.getEvaluation() > bestMove.getEvaluation()) {
                bestMove = move;
            }
        }
        return bestMove.getEvaluation();
    }

    public double findMin(ArrayList<MoveResult> moves) {
        MoveResult bestMove = moves.get(0);
        for (MoveResult move : moves) {
            if (move.getEvaluation() < bestMove.getEvaluation()) {
                bestMove = move;
            }
        }
        return bestMove.getEvaluation();
    }

    public void findBest(Board board, MoveResult move, int depth) {
        if (depth == 0) { return; }

        findBest(board, move, depth - 1);
        if (move.getTurn() > 0) {
            move.setEvaluation(findMax(move.getChildren()));
        } else if (move.getTurn() < 0) {
            move.setEvaluation(findMin(move.getChildren()));
        }
    }

    public void generateDepthSearch(Board board, MoveResult prevMove, int depth) {
        Chess.checkPromotion(board);
        int color = board.getTurn();
        if (depth == 0) { return; }
        for (int i = 0; i < board.getPieces().size(); i++) {
            Pieces piece = board.getPieces().get(i);
            if (piece.getColor() == color) {
                for (int eachMove : piece.findLegalMoves(board)) {
                    int oldPosition = piece.getPosition();
                    Pieces capturedPiece = board.movePiece(piece, eachMove);
                    MoveResult moveResult = new MoveResult(piece, capturedPiece, oldPosition, piece.getPosition(), -color, board.evaluateBoard());
                    prevMove.addChild(moveResult);
                    board.changeTurn();
                    generateDepthSearch(board, moveResult, depth - 1);
                    board.revertMove(piece, capturedPiece, oldPosition);

                }
            }
        }
    }
}



//
//    public void generateDepthSearch(Board board, int depth) {
//        Chess.checkPromotion(board);
//        int color = board.getTurn();
//        if (depth == 0) { return; }
//        for (int i = 0; i < board.getPieces().size(); i++) {
//            Pieces piece = board.getPieces().get(i);
//            if (piece.getColor() == color) {
//                for (int eachMove = 0; eachMove < piece.findLegalMoves(board).size(); eachMove++) {
//                    double start, end;
//                    start = System.nanoTime();
//                    ArrayList<Pieces> newPieces = Pieces.copyPieces(board.getPieces());
//                    Board newBoard = new Board(-color, newPieces);
//                    newBoard.setPrev(board);
//                    board.addNextBoard(newBoard);
//                    end = System.nanoTime();
//                    System.out.println("Creating new board copy: " + (end - start)/1000000000);
////                    System.out.println("Piece Name: " + newBoard.getPieceByPos(piece.getPosition()).getName() + ", Move: " + piece.findLegalMoves(board).get(eachMove));
//                    start = System.nanoTime();
//                    //    newBoard.movePiece(newBoard.getPieceByPos(piece.getPosition()), piece.findLegalMoves(board).get(eachMove));
//                    end = System.nanoTime();
//                    System.out.println("Moving piece on copy: " + (end - start)/1000000000);
//                    start = System.nanoTime();
//                    generateDepthSearch(newBoard, depth - 1);
//                    end = System.nanoTime();
//                    System.out.println("Searching Depth: " + (end - start)/1000000000);
//                }
//            }
//        }
//    }
