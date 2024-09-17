import java.util.Random;

public class Main {
    private static short[] w = new short[9];
    private static double[] d = new double[19];
    private static double[][] res = new double[9][19];


    public static double cal10(int j) {
        return (double)(Math.asin(0.2 * Math.sin(d[j])));
    }

    public static double cal618(int j) {
        double x = d[j];
        return (double)Math.pow(Math.pow((double)(3/16) + (double)(3 * x / 8), Math.pow(x / 12, 3)) / 12, 3);
    }

    public static double calFnother(int j) {
        return Math.atan(0.4 * Math.exp(Math.cbrt(-Math.sqrt(Math.abs(d[j])))));
    }

    private static void init() {
        int j = 0;
        for(short i = 4; i <= 20; i += 2) {
            w[j] = i;
            j++;
        }
        Random random = new Random();
        for(int i = 0; i < d.length; i++) {
            d[i] = random.nextDouble(-9.0, 5.0);
        }
    }
    public static void main(String[] args) {
        init();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 19; j++) {
                if(w[i] == 10) {
                    res[i][j] = cal10(j);
                } else if(w[i] > 4 && w[i] < 20) {
                    res[i][j] = cal618(j);
                } else {
                    res[i][j] = calFnother(j);
                }
            }
        }
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 19; j++) {
                System.out.printf("%.5f", res[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}