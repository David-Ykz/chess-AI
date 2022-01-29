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
                    if (Chess.evaluationData.containsKey(board.getPieces().size()) && Chess.evaluationData.get(board.getPieces().size()).containsKey(fen)) {
                        evaluation = Chess.evaluationData.get(board.getPieces().size()).get(fen);
                        Move move = new Move(oldPosition, piece.getPosition(), evaluation);
                        moves.add(move);
                    }
                    board.revertMove(piece, capturedPiece, oldPosition);
                }
            }
        }
        Move bestMove;
        if (moves.size() == 0) {
            bestMove = generateDepthSearch(board, 3);
        } else if (board.getTurn() > 0) {
            bestMove = max(moves);
        } else {
            bestMove = min(moves);
        }
        board.movePiece(board.getPiece(bestMove.getOldPosition()), bestMove.getNewPosition());
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


    public Move generateDepthSearch(Board board, int depth) {
        ArrayList<Move> moves = new ArrayList<>();
        Chess.checkPromotion(board);
        int color = board.getTurn();
        for (Piece piece : board.getPieces()) {
            if (piece.getColor() == color) {
                for (int eachMove : piece.findLegalMoves(board)) {
                    int oldPosition = piece.getPosition();
                    Piece capturedPiece = board.movePiece(piece, eachMove);
                    board.changeTurn();
                    if (depth == 0) {
                        moves.add(new Move(oldPosition, piece.getPosition(), board.evaluateBoard()));
                    } else {
                        Move bestMove = generateDepthSearch(board, depth - 1);
                        moves.add(new Move(oldPosition, piece.getPosition(), bestMove.getEvaluation()));
                    }
                    board.revertMove(piece, capturedPiece, oldPosition);
                    board.changeTurn();
                }
            }
        }
        if (moves.size() == 0) {
            return new Move(-1, -1, -9999 * color);
        }
        if (color > 0) {
            return max(moves);
        } else {
            return min(moves);
        }
    }
}