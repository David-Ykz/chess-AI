import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class ChessAI {
    ChessAI() {
    }

    // Find all possible moves
    // Convert to fen
    // Give evaluations for each move
    // Pick the best evaluation



    public void findMove(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Chess.checkPromotion(board);
        int color = board.getTurn();
        for (Piece piece : board.getPieces()) {
            if (piece.getColor() == color) {
                for (int eachMove : piece.findLegalMoves(board)) {
                    int oldPosition = piece.getPosition();
                    Piece capturedPiece = board.movePiece(piece, eachMove);
                    String fen = board.toFEN();
                    int evaluation;
                    System.out.println(fen);
                    System.out.println(Chess.evaluationData.get(board.getPieces().size()).containsKey(fen));
                    if (Chess.evaluationData.containsKey(board.getPieces().size()) && Chess.evaluationData.get(board.getPieces().size()).containsKey(fen)) {
                        evaluation = Chess.evaluationData.get(board.getPieces().size()).get(fen);
                        Move move = new Move(piece, capturedPiece, piece.getPosition(), evaluation);
                        moves.add(move);
                    }
                    board.revertMove(piece, capturedPiece, oldPosition);
                }
            }
        }

        if (moves.size() == 0) {
            MoveResult root = new MoveResult(null, null, -1, -1, board.getTurn(), board.evaluateBoard());
            generateDepthSearch(board, root, 2);
            findBest(board, root);
            ArrayList<MoveResult> bestMoves = new ArrayList<>();
            for (MoveResult eachMove : root.getChildren()) {
                System.out.print("Move: " + eachMove.getPiece().getName() + " " +eachMove.getNewPosition() + " Score: " + eachMove.getEvaluation() + " ");
                if (eachMove.getEvaluation() == root.getEvaluation()) {
                    bestMoves.add(eachMove);
                }
            }
            MoveResult eachMove = bestMoves.get((int)(Math.random() * bestMoves.size()));
            board.movePiece(eachMove.getPiece(), eachMove.getNewPosition());
            return;
        }

        Move bestMove;
        if (board.getTurn() > 0) {
            bestMove = max(moves);
        } else {
            bestMove = min(moves);
        }
        board.movePiece(bestMove.getPiece(), bestMove.getNewPosition());
    }



    public Move max(ArrayList<Move> moves) {
        Move bestMove = moves.get(0);
        for (Move move : moves) {
            if (move.getEvaluation() > bestMove.getEvaluation()) {
                bestMove = move;
            }
        }
        return bestMove;
    }

    public Move min(ArrayList<Move> moves) {
        Move bestMove = moves.get(0);
        for (Move move : moves) {
            if (move.getEvaluation() < bestMove.getEvaluation()) {
                bestMove = move;
            }
        }
        return bestMove;
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

    public void findBest(Board board, MoveResult move) {
        if (move.getChildren().isEmpty()) { return; }

        for (MoveResult eachMove : move.getChildren()) {
            findBest(board, eachMove);
        }

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
        for (Piece piece : board.getPieces()) {
            if (piece.getColor() == color) {
                for (int eachMove : piece.findLegalMoves(board)) {
                    int oldPosition = piece.getPosition();
                    Piece capturedPiece = board.movePiece(piece, eachMove);
                    MoveResult moveResult = new MoveResult(piece, capturedPiece, oldPosition, piece.getPosition(), -color, board.evaluateBoard());
                    prevMove.addChild(moveResult);
                    board.changeTurn();
                    generateDepthSearch(board, moveResult, depth - 1);
                    board.revertMove(piece, capturedPiece, oldPosition);
                }
            }
        }
    }

    public void traverseTree(Queue<MoveResult> levelQueue) {
        Queue<MoveResult> childQueue = new LinkedList<>();
        while (!levelQueue.isEmpty()) {
            MoveResult node = levelQueue.remove();
            System.out.print(node.getEvaluation() + " ");

            if (!node.getChildren().isEmpty()) {
                childQueue.addAll(node.getChildren());
            }
        }
        System.out.println("");
        if (childQueue.isEmpty()) { return; }
        traverseTree(childQueue);
    }


}



//
//    public void generateDepthSearch(Board board, int depth) {
//        Chess.checkPromotion(board);
//        int color = board.getTurn();
//        if (depth == 0) { return; }
//        for (int i = 0; i < board.getPieces().size(); i++) {
//            Piece piece = board.getPieces().get(i);
//            if (piece.getColor() == color) {
//                for (int eachMove = 0; eachMove < piece.findLegalMoves(board).size(); eachMove++) {
//                    double start, end;
//                    start = System.nanoTime();
//                    ArrayList<Piece> newPiece = Pieces.copyPieces(board.getPieces());
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
