public class Round {
    public static String[] rounds = {
        "Njesha",
        "Dysha",
        "Tresha",
        "Katra",
        "Pesa",
        "Gjashta",
        "Piket e siperme",
        "Bonus",
        "Tre me nje vlere",
        "Kater me nje vlere",
        "Tre dhe Dy",
        "Kater te njepasnjeshme",
        "Pese te njepasnjeshme",
        "E njejta vlere",
        "Cdo Rast",
        "Piket e siperme",
        "Total"
    };
    private String roundName;
    int [] playerOnePts = new int[13];
    int [] playerTwoPts = new int[13];
    int [] playerThreePts = new int[13];
    int [] playerFourPts = new int[13];

    public Round(int round, int pts1){
        roundName = rounds[round];
        playerOnePts[round] = pts1;
    }

    public String getRoundName() {
        return roundName;
    }

    public int[] getPlayerOnePts() {
        return playerOnePts;
    }

    public int[] getPlayerTwoPts() {
        return playerTwoPts;
    }

    public int[] getPlayerThreePts() {
        return playerThreePts;
    }

    public int[] getPlayerFourPts() {
        return playerFourPts;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public void setPlayerOnePts(int[] playerOnePts) {
        this.playerOnePts = playerOnePts;
    }

    public void setPlayerTwoPts(int[] playerTwoPts) {
        this.playerTwoPts = playerTwoPts;
    }

    public void setPlayerThreePts(int[] playerThreePts) {
        this.playerThreePts = playerThreePts;
    }

    public void setPlayerFourPts(int[] playerFourPts) {
        this.playerFourPts = playerFourPts;
    }
}
