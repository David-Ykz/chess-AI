import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class EvaluationReader {
    private HashMap<Integer, HashMap<String, Integer>> evaluations = new HashMap<>();

    EvaluationReader(String fileName) {
        try {
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            input.nextLine();
            input.nextLine();
            while (input.hasNext()) {
                String[] message = input.nextLine().split(",", -1);
                String fen = message[0];
                fen = fen.substring(0, fen.indexOf(" ") + 2);
                int evaluation;
                if (message[1].contains("#")) {
                    evaluation = evaluationToInt(message[1].substring(1)) * 1000;
                } else {
                    evaluation = evaluationToInt(message[1]);
                }

                putInHashMap(fen, evaluation);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void putInHashMap(String fen, int evaluation) {
        if (evaluations.containsKey(numberOfPieces(fen))) {
            evaluations.get(numberOfPieces(fen)).put(fen, evaluation);
        } else {
            evaluations.put(numberOfPieces(fen), new HashMap<String, Integer>());
            evaluations.get(numberOfPieces(fen)).put(fen, evaluation);
        }
    }

    public int numberOfPieces(String fen) {
        int counter = 0;
        String pieceNames = "kqrbnpKQRBNP";
        for (int i = 0; i < pieceNames.length(); i++) {
            if (pieceNames.contains(fen.substring(i, i + 1))) {
                counter++;
            }
        }
        return counter;
    }

    public int evaluationToInt(String message) {
        if (message.contains("-")) {
            return Integer.parseInt(message.substring(message.indexOf("-") + 1));
        } else if (message.contains("+")) {
            return Integer.parseInt(message.substring(message.indexOf("+") + 1));
        }
        return 0;
    }
    public static void main(String[]args) {
        EvaluationReader reader = new EvaluationReader("chessData.csv");
        System.out.println("finished parsing");
    }



}
