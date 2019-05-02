import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        ArrayList<Player> lb = new ArrayList<>();
        final String path = "./data/";
        String input;
        boolean update = false;
        Scanner in = new Scanner(System.in);

        System.out.println("Do you want to update your boards from srcom? (y/n)");
        input = in.nextLine();

        if (input.equals("y")) {
            update = true;
            System.out.println("Will update each requested board.");
        }

        System.out.println("Type \"<game> <category>\" to add, or f to end");
        input = in.nextLine();

        while (!input.equals("f")) {
            if (update) {
                String game = input.substring(0, input.indexOf(" "));
                String category = input.substring(input.indexOf(" ") + 1);
                RuntimeJS.runScript(game, category);
            }
            try {
                BoardReader.readBoard(path + input + ".txt", lb);
            } catch (FileNotFoundException e) {
                System.err.println("Error: Category not found");
            } finally {
                System.out.println("Type \"<game> <category>\" to add, or f to end");
            }
            input = in.nextLine();
        }

        Collections.sort(lb);
        int i = 1;
        for (Player e : lb) {
            String avg = String.format("Avg: %.2f", e.getPoints()/e.getRuns());
            String s = String.format("%d. %s %.2f", i, e.getName(), e.getPoints());
            String format = String.format("%-30s %s", s, avg);
            System.out.println(format);
            i++;
        }
    }
}
