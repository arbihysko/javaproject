public class CalcPts {
    static int calcPoits(int round, int[] diceVals){
        int pts = -1;

        switch (round){
            case 0:
                pts = calcNjishe(diceVals);
                break;
        }

        return pts;
    }

    static int calcNjishe(int[] diceVals){
        int pts = 0;

        for (int i = 0; i < 5; i++) {
            if(diceVals[i]==1)
                pts++;
        }

        return pts;
    }
}
