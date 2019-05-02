import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;

public class NormalDistConverter extends Converter {
    private double mean;
    private double cumulant;
    private NormalDistribution dist;
    private static final double SKEWNESS_BOUND = 0.5;

    NormalDistConverter() {
        data = new ArrayList<>();
    }

    public void addTime(int time) {
        data.add(time);
    }

    public void setData() {
        //data = list;
        calcMean();
        calcCumulant();

        while (skewness() > SKEWNESS_BOUND)
            removeTime();

        dist = new NormalDistribution(mean, cumulant);

    }

    private void calcMean() {
        for (Integer e : data)
            mean += e;

        mean /= data.size();
    }

    private void calcCumulant() {
        for (Integer e : data) {
            cumulant += Math.pow(e - mean, 2);
        }

        cumulant /= data.size();
        cumulant = Math.sqrt(cumulant);
    }

    public double getMean() {
        return mean;
    }

    public double getSD() {
        return cumulant;
    }

    private double skewness() {
        double skew = 0;
        for (Integer e : data) {
            skew += Math.pow(e - mean, 3);
        }

        skew /= data.size();
        skew /= Math.pow(cumulant, 3);
        // System.out.println(skew);

        return skew;
    }

    private void removeTime() {
        mean *= data.size();
        mean -= data.get(data.size() - 1);
        data.remove(data.size() - 1);
        mean /= data.size();
        calcCumulant();
    }

    public double getPoints(int time) {
        return 100 * (1 - dist.cumulativeProbability(time));
    }
}
