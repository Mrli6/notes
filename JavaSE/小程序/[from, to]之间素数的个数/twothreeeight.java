import java.util.*;

public class twothreeeight {
    public static void main(String[] args) {
        System.out.println(findPrimeCount(100, 200));
    }

    private static int findPrimeCount(int from, int to){
        int count = 0;

        BitSet bitSet = new BitSet(to + 1);
        for(int i = 2; i <= to; i++){
            bitSet.set(i);
        }

        int i = 2;
        while(i * i <= to){
            if(bitSet.get(i)){
                int k = 2*i;
                while(k <= to){
                    bitSet.clear(k);
                    k += i;
                }
            }
            i++;
        }

        i = from;
        while(i <= to){
            if(bitSet.get(i)){
                count++;
            }
            i++;
        }

        return count;
    }
}
