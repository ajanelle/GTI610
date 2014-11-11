package message;

public class Header {

	private int id;
	private int QR;
	private int nbQuestion;
	private int nbAnswer;
	
	public Header(int id, int QR, int nbQuestion, int nbAnswer){
		this.id = id;
		this.QR = QR;
		this.nbQuestion = nbQuestion;
		this.nbAnswer = nbAnswer;
	}
	
	public int getId(){
		return id;
	}
	
	public int getQR(){
		return QR;
	}
	
	public int getNbQuestion(){
		return nbQuestion;
	}
	
	public int getNbAnswer(){
		return nbAnswer;
	}
}
