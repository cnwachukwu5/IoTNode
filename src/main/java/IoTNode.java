import java.io.*;
import java.net.Socket;
import java.util.Random;

public class IoTNode {

    public static void main(String[] args) {
        try(Socket socketAtNode = new Socket("localhost", 10567);
            //DataOutputStream out = new DataOutputStream(socketAtNode.getOutputStream());
            PrintWriter sendStatusToCachingNode = new PrintWriter(socketAtNode.getOutputStream(), true);
            BufferedReader readIn = new BufferedReader(new InputStreamReader(socketAtNode.getInputStream()));){

            System.out.println("Connected to CacheNode ...");
            FeedBackLoop feedBackLoop = new FeedBackLoop();

            //Begin a loop of sending to the CacheNode base on Feedback loop
            while(true){
                //Get IoT node status and send to CachingNode
                String nodeStatus = feedBackLoop.currentNodeStatus();
                System.out.println("currentStatus: " + nodeStatus);
                sendStatusToCachingNode.println(nodeStatus);

                //Get status duration, and system time in milliseconds before entering the sending loop
                int status_duration = feedBackLoop.duration_of_Status();
                System.out.println("duration: "+ status_duration);
                long startTime = System.currentTimeMillis();
                long endTime = 0;
                int streaming_frequency = feedBackLoop.stream_sending_frequency(nodeStatus);

                while((endTime - startTime) <= status_duration){
//                    FileReadFromResourceDir readFile = new FileReadFromResourceDir();
//                    File sendFile = readFile.getFile("instructor.json");
//                    byte[] byteArray = readByteFromFile(sendFile);
//                    out.write(byteArray); //send byte[] to server
//                    out.flush();
                    //sendStatusToCachingNode.println(randomTemperatureValue());
                    endTime = System.currentTimeMillis();
                    if(!nodeStatus.equals("IDLE"))
                        System.out.println("Data sent");
                    Thread.sleep(streaming_frequency);
                }
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
}
