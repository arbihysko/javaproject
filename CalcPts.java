import java.util.Arrays;

public class CalcPts {

    //round -> raundi; diceVals -> array me vlerat e zareve; points -> array me piket
    static int calcPoits(int round, int[] diceVals, int[][] points, int turn) {
        int pts = -1;

        switch (round) {
            case 0:
                pts = calcNjishe(diceVals);
                break;
            case 1:
                pts = calcDyshe(diceVals);
                break;
            case 2:
                pts=calcTreshe(diceVals);
                break;
            case 3:
                pts=calcKatra(diceVals);
                break;
            case 4:
                pts=calcPesa(diceVals);
                break;
            case 5:
                pts=calcGjashta(diceVals);
                break;
            case 6:
                pts=calcPiketeSiperme(points, turn);
                break;
            case 7:
                pts=calcBonus(points, turn);
                break;
            case 8:
                pts=calcTreshet(diceVals);
                break;
            case 9:
                pts=calckatershet(diceVals);
                break;
            case 10:
                pts=calcTreDy(diceVals);
                break;
            case 11:
                pts=calcTeKaterta(diceVals);
                break;
            case 12:
                pts=calcTePesta(diceVals);
                break;
            case 13:
                pts=calcTeNjejtat(diceVals);
                break;
            case 14:
                pts=calcSkaRendesi(diceVals);
                break;
            case 15:
                pts=calcPiketePoshtme(points, turn);
                break;
            case 16:
                pts=calcTotali(points, turn);
                break;

        }

        return pts;
    }

    static int calcNjishe(int[] diceVals) {
        int pts = 0;

        for (int i = 0; i < 5; i++) {
            if (diceVals[i] == 1)
                pts++;
        }

        return pts;
    }

    static int calcDyshe(int[] diceVals) {
        int pts = 0;
        for (int i = 0; i < 5; i++) {
            if (diceVals[i] == 2)
                pts++;
        }
        pts=2*pts;
        return pts;
    }

    static int calcTreshe(int[] diceVals) {
        int pts = 0;
        for (int i = 0; i < 5; i++) {
            if (diceVals[i] == 3)
                pts++;
        }
        pts=3*pts;
        return pts;
    }

    static int calcKatra(int[] diceVals) {
        int pts = 0;
        for (int i = 0; i < 5; i++) {
            if (diceVals[i] == 4)
                pts++;
        }
        pts=4*pts;
        return pts;

    }

    static int calcPesa(int[] diceVals) {
        int pts = 0;
        for (int i = 0; i < 5; i++) {
            if (diceVals[i] == 5)
                pts++;
        }
        pts=5*pts;
        return pts;
    }

    static int calcGjashta(int[] diceVals) {
        int pts = 0;
        for (int i = 0; i < 5; i++) {
            if (diceVals[i] == 6)
                pts++;
        }
        pts=6*pts;
        return pts;
    }
    static int calcPiketeSiperme(int[][] points, int turn){
        int sum=0;
        for(int i=0;i<6;i++){
            sum+=points[turn][i];
        }
        return sum;
    }
    static int calcBonus(int[][] points,int turn){
        if(points[turn][6]>63) {
            return 35;
        }
        else {
            return 0;
        }
        }

    static int calcTreshet(int[] diceVals) {
        int pts = 0;

        int n = 0;

        for (int i = 0; i < 3; i++) {
            n = 0;
            for (int j = i + 1; j < 5; j++) {
                if (diceVals[i] == diceVals[j]) {
                    n++;
                }
            }
            if (n > 1) {
                break;
            }
        }
        if (n > 1) {
            int sum = 0;
            for (int k = 0; k < 5; k++) {
                sum += diceVals[k];
            }
            return sum;
        } else {
            return 0;
        }
    }

    static int calckatershet(int[] diceVals) {
        int pts = 0;

        int n = 0;

        for (int i = 0; i < 2; i++) {
            n = 0;
            for (int j = i + 1; j < 5; j++) {
                if (diceVals[i] == diceVals[j]) {
                    n++;
                }
            }
            if (n > 2) {
                break;
            }
        }
        if (n > 2) {
            int sum = 0;
            for (int k = 0; k < 5; k++) {
                sum += diceVals[k];
            }
            return sum;
        } else {
            return 0;
        }
    }

    static int calcTreDy(int[] diceVals){
        boolean flag1 = false;
        boolean flag2 = false;
        Arrays.sort(diceVals);
        int[] numrat ={1,2,3,4,5,6};
        int[] frekuenca= new int[5];
        Arrays.fill(frekuenca, 0);
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(numrat[j]==diceVals[i]){
                    frekuenca[j]++;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (frekuenca[i]==2){
                flag1 = true;
                break;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (frekuenca[i]==3){
                flag2 = true;
                break;
            }
        }

        if (flag1&&flag2){
            return 25;
        }

        return 0;
    }



    static int calcTeKaterta(int[] diceVals) {
        Arrays.sort(diceVals);

        int n = 0;
        for (int i = 0; i < 4; i++) {
            if (diceVals[i] == diceVals[i + 1] - 1) {
                n++;
            }
        }
        if (n == 3) {
            return 30;
        } else {
            return 0;
        }

    }

    static int calcTePesta(int[] diceVals) {
        Arrays.sort(diceVals);

        int n = 0;
        for (int i = 0; i < 4; i++) {
            if (diceVals[i] == diceVals[i + 1] - 1) {
                n++;
            }
        }
        if (n == 4) {
            return 40;
        } else {
            return 0;
        }

    }
    static int calcTeNjejtat(int[] diceVals){

        int n=0;
        for(int i=0;i<4;i++){
            if(diceVals[i]==diceVals[i+1]){
                n++;
            }
        }
        if(n==4) {
            return 50;
        }
        else{
            return 0;
        }
    }
    static int calcSkaRendesi(int[] diceVals){
        int sum = 0;
        for (int k = 0; k < 5; k++) {
            sum += diceVals[k];
        }
        return sum;

    }
    static int calcPiketePoshtme(int[][] points,int turn){
        int sum=0;
        for (int i=7;i<15;i++){
            sum+=points[turn][i];
        }
        return sum;
    }
    static int calcTotali(int[][] points,int turn){
        int sum=0;
        sum=points[turn][6]+points[turn][7]+points[turn][15];
        return  sum;
    }

}
