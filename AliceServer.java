/**Assignmenet4 Alice UVU Help BOT
 * Created by David Koch on 11/25/2015.
 *
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class AliceServer {
     //For testing purposes: set to false before turn in
     private static boolean debug = true;

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

                //while there is input from client, send it back to client
                while ((inputLine = in.readLine()) != null) {
                    //Print result to query
                    out.println("Alice: ");

                    //Send users query from inputLine to compareAgainstSearchWords
                    returnedResult = compareAgainstSearchWords(inputLine);
                    out.println(returnedResult);
                    //Read in
                    //Use input string (which is users query) .contains() to search against my search terms in input.txt

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
        ArrayList<String> currentLineFromFile = null;
        String response = "";

        for (String s : currentLineFromFile = readFromFile()) {};

        //Parse file line

        if(debug){
            for(String s : currentLineFromFile)
            System.out.println(s);
        }


        return response;
    }


    //Method readFromFile
    //Purpose: read file,
    public static ArrayList<String> readFromFile(){
        //Variables
        ArrayList<String> fileLines = new ArrayList<String>();
        FileReader reader = null;
        BufferedReader br = null;
        String sCurrentLine = "temp";


        //Check for errors while opening, reading, and closing file
        try {

            reader = new FileReader("input.txt");
            br = new BufferedReader(reader);

            while (sCurrentLine != null) {
                sCurrentLine = br.readLine();

                //Read from file stream
                // save line to array list
                fileLines.add(sCurrentLine);
            }// end while loop
        }catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fileLines;
    }//end readFromFile method

}//end AliceServer class