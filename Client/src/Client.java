import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    
	public static void main(String[] args) {
		
		Socket MyClient;
		BufferedReader input;
		BufferedWriter output;
		String texte;
		     
		try {
		    	MyClient = new Socket("127.0.0.1", 9999);
		        output = new BufferedWriter(new OutputStreamWriter(MyClient.getOutputStream()));
		        input = new BufferedReader((new InputStreamReader(System.in)));
		            
		        texte = input.readLine();
		            
		        output.write(texte);
		        output.newLine();
		        output.flush();
		            
		        
		    } 
		catch (IOException ex) 
		    {
		    	System.err.println(ex.getMessage());
		    }

		}

	}
