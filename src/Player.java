import java.util.Objects;

public class Player implements Comparable<Player> {
    private String name;
    private double points;
    private int runs;

    Player(String name) {
        this.name = name;
        points = 0;
        runs = 0;
    }

    void addPoints(double points) {
        this.points += points;
    }

    void addRun() {
        this.runs++;
    }

    public String getName() {
        return name;
    }

    public double getPoints() {
        return points;
    }

    public int getRuns() {
        return runs;
    }

    public int compareTo(Player other) {
        Double pts = points;
        Double otherPts = other.points;
        return pts.compareTo(otherPts) * -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
