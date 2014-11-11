import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMultiThread {
	
	public static void main(String[] args) {
		ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(9999);
            while (true) {
                new Thread(new serverInstance(serverSocket.accept())).start();
             }
            
        } catch (IOException ex) {
        	System.err.println(ex.getMessage());
        }
	}

	public static class serverInstance implements Runnable {
	
		private Socket clientSocket;
		
		public serverInstance(Socket socket){
			this.clientSocket = socket;
		}
		
		public void run() {
		      try {
		    	BufferedReader input;
		    	PrintWriter output;
				
		  		String message;
		        input = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));
		        
		        message = input.readLine();
		        
		        System.out.println(message + " recu de : " + clientSocket.getRemoteSocketAddress());
		        
		        output = new PrintWriter(clientSocket.getOutputStream(), true);
		        output.println(message.toUpperCase());
		        
		        clientSocket.close();
		        
		      }
		      catch (IOException e) {
		        System.out.println(e);
		      }
		   }
	
	}
}

