import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class IoTNode {

    public static void main(String[] args) {
        try(Socket socketAtNode = new Socket("localhost", 10567);

            PrintWriter sendStatusToCachingNode = new PrintWriter(socketAtNode.getOutputStream(), true);
            BufferedReader readIn = new BufferedReader(new InputStreamReader(socketAtNode.getInputStream()));){

            System.out.println("Connected to CacheNode ...");
            FeedBackLoop feedBackLoop = new FeedBackLoop();

            List<String> strings = randomString(5000);
            strings.add("EODATA");

            int count = 0;
            //Begin a loop of sending to the CacheNode base on Feedback loop
            while(count < strings.size()){
                //Get IoT node status
                String nodeStatus = feedBackLoop.currentNodeStatus();
                System.out.println("currentStatus: " + nodeStatus);

                //Get status duration, and system time in milliseconds before entering the sending loop
                int status_duration = feedBackLoop.duration_of_Status();
                System.out.println("duration: "+ status_duration);

                long startTime = System.currentTimeMillis();
                long endTime = 0;
                int streaming_frequency = feedBackLoop.stream_sending_frequency(nodeStatus);

                //Send IoT data and status to cacheNode
                //while((endTime - startTime) <= status_duration){

                        String data_value = strings.get(count);


                        if(!nodeStatus.equals("IDLE")){
                            sendStatusToCachingNode.println(nodeStatus+":"+data_value);
                            System.out.println(nodeStatus+":"+data_value);
                            count++;
                            Thread.sleep(streaming_frequency); //Simulate network delay
                        }else{
                            sendStatusToCachingNode.println("IoT_Node is idle");
                            Thread.sleep(streaming_frequency);
                        }

                    //endTime = System.currentTimeMillis();


                //}
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static String randomTemperatureValue(){
        Random rand = new Random();
        return ""+rand.nextInt(150-50+1) + 1;
    }

    private static byte[] readByteFromFile(File file){
        /*
        Takes a file, convert to a byte array and returns the array...
         */
        FileInputStream fis = null;
        byte[] fileInByte = null;

        try{
            fis = new FileInputStream(file);
            fileInByte = new byte[(int)file.length()];
            fis.read(fileInByte);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fis != null){
                try{
                    fis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return fileInByte;
    }

    private static String anyString(){
        String name = UUID.randomUUID().toString();
        name = name.replace("-","");
        return name;
    }

    private static List<String> randomString(int lengthOfList){
        ArrayList<String> listOfString = new ArrayList<>();
        for(int x = 0; x < lengthOfList; x++){
            listOfString.add(anyString());
        }

        return listOfString;
    }
}
