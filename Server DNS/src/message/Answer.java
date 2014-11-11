package message;

public class Answer {

	private int[] rdData;
	
	public Answer(int[] rdData){
		this.rdData = rdData;
	}
	
	public String getAddress(){
		String address = "";
		String dot = ".";
		for(int i=0; i< rdData.length; i++){
			address += rdData[i];
			
			//add the . in the IP address
			if(i != rdData.length-1){
				address += dot;
			}
		}
		
		return address;
	}
}
