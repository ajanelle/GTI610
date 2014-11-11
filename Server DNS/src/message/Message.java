package message;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Message {
	private Header header;
	private ArrayList<Question> questions;
	private ArrayList<Answer> answers;
	
	public Message(DataInputStream message)
	{
		//create the header
		try {
			int id = message.readUnsignedShort();
			int QR = message.readShort();
			int nbQuestion = message.readUnsignedShort();
			int nbAnswer = message.readUnsignedShort();
			header = new Header(id,QR,nbQuestion,nbAnswer);
			
			message.readUnsignedShort(); //remove authority count
			message.readUnsignedShort(); //remove additional section count
			
			//create the questions
			for(int i=0; i<nbQuestion;i++){
				String name = extractNameFromStream(message);
				message.readUnsignedShort(); //remove the type
				message.readUnsignedShort(); //remove the class
				questions.add(new Question(name));
			}
			
			//create the answers
			for(int i=0; i<nbQuestion;i++){
				message.readUnsignedShort(); //remove the name
				message.readUnsignedShort(); //remove the type
				message.readUnsignedShort(); //remove the class
				message.readInt(); //remove the TTL
				
				int rdLenght = message.readUnsignedShort();
				int[] rdData = new int[rdLenght];
				
				for(int j=0; j<rdLenght; j++){
					rdData[j] = message.readUnsignedByte();
				}
				
				answers.add(new Answer(rdData));
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Header getHeader(){
		return header;
	}
	
	public ArrayList<Question> getQuestions(){
		return questions;
	}
	
	public ArrayList<Answer> getAnswers(){
		return answers;
	}
	
	private String extractNameFromStream(DataInputStream inputStream){
		String tempName = "";
		String finalName;
		String dot = ".";
		
		byte bytesLenght;
		
		try {
			while((bytesLenght = inputStream.readByte()) != 0X00){
				for(int i=0;i<bytesLenght;i++){
					tempName += (char) inputStream.readByte();
				}
				tempName += dot;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finalName = tempName.substring(0, tempName.length() - 1);
		
		return finalName;
	}
}
