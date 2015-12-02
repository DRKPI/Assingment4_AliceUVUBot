/**
 * Assignmenet4 Alice UVU Help BOT
 * Created by David Koch on 11/25/2015.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class AliceServer {
    //For testing purposes: set to false before turn in
    private static boolean debug = true;
    private static ArrayList<Object[]> lines = new ArrayList<>();

    public static void main(String[] args) {


        for (; ; ) {//need to keep the socket open
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
            readFromFile();
                String inputLine;
                String returnedResult;

                //while there is input from client, send it back to client
                for (;;) {
                    inputLine = in.readLine();
                    if(inputLine != null) {
                        //Print result to query
                        out.print("Alice: ");

                        //Send users query from inputLine to compareAgainstSearchWords
                        returnedResult = compareAgainstSearchWords(inputLine);
                        out.println(returnedResult);
                    }
                    else
                    {
                        break;
                    }
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
    public static String compareAgainstSearchWords(String question) {

        for (Object[] line : lines) {
            ArrayList[] query = (ArrayList[]) line[0];
            String response = (String) line[1];

            //TODO: Need to code something to tell difference between UVU phone number and HR phone number


            for (String keyWord1 : (ArrayList<String>) query[0]) {
                if (question.contains(keyWord1)) {
                    for (String keyWord2 : (ArrayList<String>) query[1]) {
                        if (question.contains(keyWord2)) {
                            return response;
                        }
                    }
                }
            }
        }
        return "Sorry, I am not too sure...";
    }//end compareAgainstSearchWords method


    //Method readFromFile
    //Purpose: read file,
    public static void readFromFile() {
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
            while ((sCurrentLine = br.readLine()) != null) {
                //Read currentLine and split at =
                mapValue = sCurrentLine.split("=");
                //Separate searchable words further at & symbol, this is to create two arrayLists that will be nested
                //for || & search
                orValues = mapValue[0].split("&");
                Object[] line = new Object[2];
                ArrayList<String>[] query = new ArrayList[2];
                query[0]= new ArrayList<String>(Arrays.asList(orValues[0].split(" ")));
                query[1]= new ArrayList<String>(Arrays.asList(orValues[1].split(" ")));
                line[0] = query;
                line[1] = mapValue[1];
                lines.add(line);
                orValues[1].split(" ");
            }// end while loop
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end readFromFile method
}//end AliceServer class