import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    
	public static void main(String[] args) {
		
		Socket myClient;
		BufferedReader input;
		BufferedReader serverResponse;
		PrintWriter output;
		String texte;
		     
		try {
		    	myClient = new Socket("127.0.0.1", 9999);
		        output = new PrintWriter(myClient.getOutputStream(), true);
		        input = new BufferedReader((new InputStreamReader(System.in)));
		        serverResponse = new BufferedReader((new InputStreamReader(myClient.getInputStream())));
		            
		        texte = input.readLine();
		        
		        output.println(texte);
		        
		        
		        String response;
		        response = serverResponse.readLine();
		        
		        
		        output.close();
		        serverResponse.close();
		        myClient.close();
		        
		        System.out.println(response);
		        
		    } 
		catch (IOException ex) 
		    {
		    	System.err.println(ex.getMessage());
		    }

		}

	}
