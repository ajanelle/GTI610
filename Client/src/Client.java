import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class Client {
    
	public static void main(String[] args) {
		
		Socket MyClient;
		BufferedReader input;
	    PrintWriter output;
	    
		try {
            MyClient = new Socket("127.0.0.0", 9999);
            output = new PrintWriter(MyClient.getOutputStream(),true);
            input = new BufferedReader((new InputStreamReader(MyClient.getInputStream())));
        } 
        catch (IOException ex) 
        {
            System.err.println(ex.getMessage());
        }

	}

}
