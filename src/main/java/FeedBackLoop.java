import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FeedBackLoop {

    private String[] nodeStatus = null;
    private Map<String, Integer> stream_frequency = new HashMap<>();  //Simulate network delay based on oT status

    public FeedBackLoop() {
        this.nodeStatus = new String[] {"HIGH_SEND", "MODERATE_SEND", "LOW", "IDLE"};
        this.stream_frequency.put("HIGH_SEND",1000);
        this.stream_frequency.put("MODERATE_SEND",2000);
        this.stream_frequency.put("LOW",5000);
        this.stream_frequency.put("IDLE",10000);
    }

    public String currentNodeStatus(){
        return nodeStatus[getIndex()];
    }

    private int getIndex(){
        Random rand = new Random();

        return rand.nextInt(4);
    }

    public int duration_of_Status(){
        Random rand = new Random();

        //Using (max-min+1) + 1 -- between 100 - 1000 milliseconds
        return rand.nextInt(10000-100+1) + 1;
    }

    public int stream_sending_frequency(String status){
        return this.stream_frequency.get(status);
    }
}
