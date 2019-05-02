import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BoardReader {

    public static void readBoard(String path, ArrayList<Player> lb) throws Exception {
        //ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        Converter converter = new NormalDistConverter();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        String line = in.readLine();

        while (line != null) {
            //System.out.println(line);
            String player = line.substring(0, line.indexOf(" "));

            Player p = new Player(player);
            if (!lb.contains(p)) {
                players.add(p);
                lb.add(p);
            }
            else
                players.add(playerFromList(lb, p));

            String time = line.substring(line.indexOf(" ") + 1);
            int secs = Integer.parseInt(time);
            converter.addTime(secs);
            list.add(secs);
            //System.out.println(secs);

            line = in.readLine();
        }

        converter.setData();
        for (int i = 0; i < list.size(); i++) {
            players.get(i).addPoints(converter.getPoints(list.get(i)));
            players.get(i).addRun();
        }

        //System.out.println("Length: " + list.size());
    }

    private static Player playerFromList(ArrayList<Player> list, Player p) {
        for (int i = 0; i < list.size() - 1; i++)
            if (list.get(i).equals(p))
                return list.get(i);

        return null;
    }

//    private static int convertTime(String time) {
//        int seconds = 0;
//        if (time.contains("h")) {
//            seconds += Integer.valueOf(time.substring(0, time.indexOf("h"))) * 3600;
//            seconds += Integer.valueOf(time.substring(time.indexOf("m") - 2, time.indexOf("m"))) * 60;
//        } else
//            seconds += Integer.valueOf(time.substring(0, time.indexOf("m"))) * 60;
//
//        seconds += Integer.valueOf(time.substring(time.indexOf("s") - 2, time.indexOf("s")));
//        return seconds;
//    }
}
