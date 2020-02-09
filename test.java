import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[] diceVals = {1, 2, 3, 4, 4};
        System.out.println(":" + calr(diceVals));
    }

    static int calr(int[] diceVals) {

        Arrays.sort(diceVals);
        int sum = 0;
        for (int k = 0; k < 5; k++) {
            sum += diceVals[k];
        }
        int n = 0;
        for (int i = 0; i < 4; i++) {
            if (diceVals[i] == diceVals[i + 1] - 1) {
                n++;
            }
        }
        if (n = 4) {
            return sum;
        } else {
            return 0;
        }
    }
}

