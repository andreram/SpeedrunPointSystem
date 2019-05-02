import java.util.ArrayList;

public abstract class Converter {
    ArrayList<Integer> data;

    public void addTime(int time) {
        data.add(time);
    }

    public abstract void setData();
    public abstract double getPoints(int time);
}
