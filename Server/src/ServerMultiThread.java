import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMultiThread implements Runnable {
	
	private static Socket clientSocket;
	
	public ServerMultiThread(Socket socket){
		this.clientSocket = socket;
	}

	public static void main(String[] args) {
		ServerSocket serverSocket;
		
		BufferedReader input;
	    PrintWriter output;
		String text;
		
        try {
            serverSocket = new ServerSocket(9999);
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Connected");
                new Thread(new ServerMultiThread(clientSocket)).start();
             }
            
        } catch (IOException ex) {
        	System.err.println(ex.getMessage());
        }
	}
	
	public void run() {
	      try {
	         PrintStream pstream = new PrintStream
	         (clientSocket.getOutputStream());
	         for (int i = 100; i >= 0; i--) {
	            pstream.println(i + 
	            " bottles of beer on the wall");
	         }
	         pstream.close();
	         clientSocket.close();
	      }
	      catch (IOException e) {
	         System.out.println(e);
	      }
	   }

}
