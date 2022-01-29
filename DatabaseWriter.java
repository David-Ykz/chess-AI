import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class DatabaseWriter {
    public static void main(String[]args) {
        try {
//            File file = new File("copy200k.csv");
            File file = new File("tactic_evals.csv");
            Scanner input = new Scanner(file);
  //          File write = new File("newDatabase.csv");
            File write = new File("tactical.csv");
            PrintWriter output = new PrintWriter(write);
            input.nextLine();
            for (int i = 0; i < 1000; i++) {
                String message = input.nextLine();
                output.println(message);

            }
            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
