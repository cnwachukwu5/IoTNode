import java.util.Random;

public class FeedBackLoop {

    private String[] nodeStatus = {"HIGH_SEND", "MODERATE_SEND", "LOW", "IDLE"};

    public String currentNodeStatus(int index){
        return nodeStatus[index];
    }

    private int getIndex(){
        Random rand = new Random();

        return rand.nextInt(4);
    }

    public int duration_of_Status(){
        Random rand = new Random();

        //Using (max-min+1) + 1 -- between 100 - 1000 milliseconds
        return rand.nextInt(1000-100+1) + 1;
    }
}
