import java.util.ArrayList;

class Pawn extends Pieces {
    private ArrayList<Integer> squaresOfInfluence = new ArrayList<Integer>();

    Pawn(int position, String pieceName, int color, String spriteName) {
        super(position, pieceName, color, spriteName);
    }

    public double findValue(Board board) {
        return 1;
    }

    public void promotePawn(Board board) {
        System.out.println("promoting pawn");
        //Scanner input = new Scanner(System.in);
        //String pieceName = input.next();
        int saveIndex = -1;
        for (int i = 0; i < board.getPieces().size(); i++) {
            if (getPosition() == board.getPieces().get(i).getPosition()) {
                saveIndex = i;
            }
        }
        Queen queen = null;
        if (getColor() > 0) {
            queen = new Queen(getPosition(), "queen", 1, "White Queen.png");
        } else if (getColor() < 0) {
            queen = new Queen(getPosition(), "queen", -1, "Black Queen.png");
        }
        board.overridePiece(saveIndex, queen);

    }


    public ArrayList<Integer> findPossibleMoves(Board board) {
        squaresOfInfluence.clear();
        if (getColor() > 0) {
            if (getPosition() / 10 > 1) {
                squaresOfInfluence.add(getPosition() - 11);
            }
            if (getPosition() / 10 < 8) {
                squaresOfInfluence.add(getPosition() + 9);
            }
        } else if (getColor() < 0) {
            if (getPosition() / 10 > 1) {
                squaresOfInfluence.add(getPosition() - 9);
            }
            if (getPosition() / 10 < 8) {
                squaresOfInfluence.add(getPosition() + 11);
            }
        }
        return squaresOfInfluence;
    }


    public ArrayList<Integer> findForwardMove(Board board) {
        ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
        if (getColor() > 0) {
            if (board.emptySquare(getPosition() - 1)) {
                possibleMoves.add(getPosition() - 1);
            }
            if (getPosition() % 10 == 7 && board.emptySquare(getPosition() - 1) && board.emptySquare(getPosition() - 2)) {
                possibleMoves.add(getPosition() - 2);
            }
        } else {
            if (board.emptySquare(getPosition() + 1)) {
                possibleMoves.add(getPosition() + 1);
            }
            if (getPosition() % 10 == 2 && board.emptySquare(getPosition() + 1) && board.emptySquare(getPosition() + 2)) {
                possibleMoves.add(getPosition() + 2);
            }
        }
        return possibleMoves;
    }


    public ArrayList<Integer> findLegalMoves(Board board) {
        int saveCurrentPos = getPosition();
        ArrayList<Integer> possibleMoves = findPossibleMoves(board);
        ArrayList<Integer> legalMoves = new ArrayList<Integer>();
        int kingPos = findKingPos(board);


        for (int i = 0; i < possibleMoves.size(); i++) {
            if (board.hostileSquare(possibleMoves.get(i), getColor())) {
                Pieces capturedPiece = board.movePiece(this, possibleMoves.get(i));
                if (!findAllMoves(board, getColor()).contains(kingPos)) {
                    legalMoves.add(getPosition());
                }
                board.revertMove(this, capturedPiece, saveCurrentPos);
            }
        }

        possibleMoves.clear();
        possibleMoves.addAll(findForwardMove(board));

        for (int i = 0; i < possibleMoves.size(); i++) {
            if (board.friendlySquare(possibleMoves.get(i), getColor()) == false) {
                Pieces capturedPiece = board.movePiece(this, possibleMoves.get(i));
                if (findAllMoves(board, getColor()).contains(kingPos) == false) {
                    //promotePawn(board);
                    legalMoves.add(getPosition());
                }
                board.revertMove(this, capturedPiece, saveCurrentPos);
            }
        }
        return legalMoves;
    }

}