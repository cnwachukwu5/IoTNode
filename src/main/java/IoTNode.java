import java.io.*;
import java.net.Socket;

public class IoTNode {

    public static void main(String[] args) {
        try(Socket socketAtNode = new Socket("localhost", 10567);
            DataOutputStream out = new DataOutputStream(socketAtNode.getOutputStream());
            BufferedReader readIn = new BufferedReader(new InputStreamReader(socketAtNode.getInputStream()));){

            System.out.println("Connected to CacheNode ...");
            String msg_from_CacheNode = readIn.readLine();

            if(msg_from_CacheNode.equals("active")){
                FileReadFromResourceDir readFile = new FileReadFromResourceDir();
                File sendFile = readFile.getFile("instructor.json");
                byte[] byteArray = readByteFromFile(sendFile);
                out.write(byteArray); //send byte[] to server
                out.flush();
            }

            System.out.println("Closing connection  ...");

        }catch (Exception e){
            e.printStackTrace();
        }

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
