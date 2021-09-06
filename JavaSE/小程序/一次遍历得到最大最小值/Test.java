import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        double[] d = new double[20];
        for(int i = 0; i < d.length; i++){
            d[i] = 100*Math.random();
        }
        MyArray.Pair pair = MyArray.maxMin(d);
        System.out.println(Arrays.toString(d));
        System.out.println("max : "+ pair.getMax());
        System.out.println("min : "+ pair.getMin());
    }
}

class MyArray{

    // 静态内部类
    public static class Pair{
        private double max;
        private double min;

        public Pair(double max, double min) {
            this.max = max;
            this.min = min;
        }
        public double getMax() {
            return max;
        }
        public double getMin() {
            return min;
        }
    }

    public static Pair maxMin(double[] d){
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        for(double n : d){
            if(n > max) max = n;
            if(n < min) min = n;
        }

        return new Pair(max, min);
    }
}