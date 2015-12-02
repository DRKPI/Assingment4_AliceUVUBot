/**Assignmenet4 Alice UVU Help BOT
 * Created by David Koch on 11/25/2015.
 *
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class AliceServer {
     //For testing purposes: set to false before turn in
     private static boolean debug = true;
    private static LinkedHashMap<String, String> hm;
    private static ArrayList<String> firstOr;
    private static ArrayList<String> secondOr;

    public static void main(String[] args) {

       for(;;) {//need to keep the socket open
            try (
                    //create a server on port 9000
                    ServerSocket serverSocket = new ServerSocket(9000);

                    //when there is a connection from server(wait till then)
                    Socket clientSocket = serverSocket.accept();

                    //open writer to write to client
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    //create reader to read from client
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            )//end try
            {
                String inputLine;
                String returnedResult;
                readFromFile();
                //while there is input from client, send it back to client
                while ((inputLine = in.readLine()) != null) {
                    //Print result to query
                    out.println("Alice: ");

                    //Send users query from inputLine to compareAgainstSearchWords
                    returnedResult = compareAgainstSearchWords(inputLine);
                    out.println(returnedResult);

                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end infinite for loop
    }//end AliceServer main method


    //Method compareAgainstSearchWords
    //Purpose: compare users input to file in order to return correct response
    public static String compareAgainstSearchWords(String query) {
        //Variables
        String response = "";
        int i = 0;

        //have this here to hopefully keep track of where the query matches in the linkedHashMap
        for (Map.Entry<String, String> currentLine : hm.entrySet()) {
            i++;//need to keep track of line number in LinkedHashMap
            //For loop to check users query against first set of search terms
            for(String s : firstOr){
                //if query matches first set of terms send to second set to be searched
                if(query.contains(s)){
                    for(String r : secondOr){
                        //if query matches second set of terms match with result to be returned
                        if(query.contains(r)){
                            //match line in linkedHashMap (i) and return the value as response

                        }else{
                            response = "Sorry, I am not too sure...";
                        }
                    }
                }else{
                    response = "Sorry, I am not too sure...";
                }
            }
        };

        //Need to check if endResponse needs to be sent
        //This does not do what is needed, just an idea to run with
        if(i == hm.size() & response.equals(null)){
            response = "endResponse";
        }
//        if(debug){
//            for(String s : currentLineFromFile)
//            System.out.println(s);
//        }


        return response;
    }


    //Method readFromFile
    //Purpose: read file,
    public static void readFromFile(){
        //Variables
        FileReader reader = null;
        BufferedReader br = null;
        String sCurrentLine = "temp";
        String[] mapValue;
        String[] orValues;

        //Check for errors while opening, reading, and closing file
        try {

            reader = new FileReader("input.txt");
            br = new BufferedReader(reader);

            //Loop to read in lines from file and split into search words and results key value pairs
            //Split on & in key and then split on space for each word
            while (sCurrentLine != null) {
                sCurrentLine = br.readLine();
                //Read currentLine and split at =
                mapValue = sCurrentLine.split("=");
                // save searchable words and results as a key value pair in linked hashmap
                hm.put(mapValue[0], mapValue[1]);
                //Separate searchable words further at & symbol, this is to create two arrayLists that will be nested
                //for || & search
                orValues = mapValue[0].split("&");
                firstOr.addAll(Arrays.asList(orValues[0].split(" ")));
                secondOr.addAll(Arrays.asList(orValues[1].split(" ")));
            }// end while loop
        }catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//end readFromFile method
}//end AliceServer class