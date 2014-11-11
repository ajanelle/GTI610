import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

 public static void main(String[] args) {
  ServerSocket serverSocket;
  Socket clientSocket;
  BufferedReader input;
  PrintWriter output;
  String text;
  
        try {
            serverSocket = new ServerSocket(9999);
            clientSocket = serverSocket.accept();
            

            output = new PrintWriter(clientSocket.getOutputStream(),true);
            input = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));

            text = input.readLine();
            System.out.println(text);
           
            clientSocket.close();
            serverSocket.close();
            
        } catch (IOException ex) {
         System.err.println(ex.getMessage());
        }
 }

}