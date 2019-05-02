public class RuntimeJS {

    public static void runScript(String game, String category) {
        try {

            // create a new array of 2 strings
            String[] cmdArray = new String[4];

            // first argument is the program we want to open
            cmdArray[0] = "node";

            // second argument is the script path
            cmdArray[1] = "C:/Users/iramk/repos/SpeedrunPointSystem/src/Test.js";

            // third arg is the game abbreviation
            cmdArray[2] = game;

            //fourth arg is the category
            cmdArray[3] = category;

            // print a message
            System.out.println("Getting leaderboard data.");

            // create a process and execute cmdArray and currect environment
            Process process = Runtime.getRuntime().exec(cmdArray, null);
            process.waitFor();
            process.destroy();

            // print another message
            System.out.println("Leaderboard should be added.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}