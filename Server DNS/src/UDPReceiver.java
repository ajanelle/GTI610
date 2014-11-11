import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

import message.Message;

/**
 * Cette classe permet la r�ception d'un paquet UDP sur le port de r�ception
 * UDP/DNS. Elle analyse le paquet et extrait le hostname
 * 
 * Il s'agit d'un Thread qui �coute en permanance 
 * pour ne pas affecter le d�roulement du programme
 * @author Max
 *
 */


public class UDPReceiver extends Thread {
	/**
	 * Les champs d'un Packet UDP
	 * --------------------------
	 * En-t�te (12 octects)
	 * Question : l'adresse demand�
	 * R�ponse : l'adresse IP
	 * Autorit� : info sur le serveur d'autorit�
	 * Additionnel : information suppl�mentaire
	 */
	
	/**
	 * D�finition de l'En-t�te d'un Packet UDP
	 * ---------------------------------------
	 * Identifiant Param�tres
	 * QDcount Ancount
	 * NScount ARcount
	 * 
	 *� identifiant est un entier permettant d�identifier la requete.
	 *� parametres contient les champs suivant :
	 *	� QR (1 bit) : indique si le message est une question (0) ou une reponse (1).
	 *	� OPCODE (4 bits) : type de la requete (0000 pour une requete simple).
	 *	� AA (1 bit) : le serveur qui a fourni la reponse a-t�il autorite sur le domaine?
	 *	� TC (1 bit) : indique si le message est tronque.
	 *	� RD (1 bit) : demande d�une requete recursive.
	 *	� RA (1 bit) : indique que le serveur peut faire une demande recursive.
	 *	� UNUSED, AD, CD (1 bit chacun) : non utilises.
	 *	� RCODE (4 bits) : code de retour. 0 : OK, 1 : erreur sur le format de la requete, 2: probleme du serveur,
	 *    3 : nom de domaine non trouve (valide seulement si AA), 4 : requete non supportee, 5 : le serveur refuse
	 *    de repondre (raisons de s�ecurite ou autres).
	 * � QDCount : nombre de questions.
	 * � ANCount, NSCount, ARCount : nombre d�entrees dans les champs �Reponse�, �Autorite�, �Additionnel�.
	 */
	
	/**
	 * Les champs Reponse, Autorite, Additionnel sont tous representes de la meme maniere :
	 *
	 * � Nom (16 bits) : Pour eviter de recopier la totalite du nom, on utilise des offsets. Par exemple si ce champ
	 *   vaut C0 0C, cela signifie qu�on a un offset (C0) de 12 (0C) octets. C�est-a-dire que le nom en clair se trouve
	 *   au 12eme octet du message.
	 * � Type (16 bits) : idem que pour le champ Question.
	 * � Class (16 bits) : idem que pour le champ Question.
	 * � TTL (32 bits) : dur�ee de vie de l�entr�ee.
	 * � RDLength (16 bits): nombre d�octets de la zone RDData.
	 * � RDData (RDLength octets) : reponse
	 */
	
	private DataInputStream d = null;
	protected final static int BUF_SIZE = 1024;
	protected String SERVER_DNS = null;
	protected int port = 53;  // port de r�ception
	private String DomainName = "none";
	private String DNSFile = null;
	private String adrIP = null;
	private boolean RedirectionSeulement = false;
	private String adresseIP = null;
	private DatagramSocket receiveSocket;
	
	public void setport(int p) {
		this.port = p;
	}
	
	public void setRedirectionSeulement(boolean b){
		this.RedirectionSeulement = b;
	}
	
	public String gethostNameFromPacket(){
		return DomainName;
	}
	
	public String getAdrIP(){
		return adrIP;
	}
	
	private void setAdrIP(String ip){
		adrIP = ip;
	}
	
	public void sethostNameFromPacket(String hostname){
		this.DomainName = hostname;
	}
	
	public String getSERVER_DNS(){
		return SERVER_DNS;
	}
	
	public void setSERVER_DNS(String server_dns){
		this.SERVER_DNS = server_dns;
	}
	
	public void UDPReceiver(String SERVER_DNS,int Port) {
		this.SERVER_DNS = SERVER_DNS;
		this.port = Port;
	}
	
	public void setDNSFile(String filename){
		DNSFile = filename;
	}
	
	public void run(){

		try{
			
			//*Creation d'un socket UDP
			receiveSocket = new DatagramSocket(port);
			
			//*Boucle infinie de reception
			while(true){
				
				//*Reception d'un paquet UDP via le socket
				byte [] buffer = new byte[BUF_SIZE];
				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
				receiveSocket.receive(receivePacket);
				
				//*Creation d'un DataInputStream ou ByteArrayInputStream pour manipuler les bytes du paquet	
				DataInputStream input = new DataInputStream(new ByteArrayInputStream(buffer));

				//create a message object with the input stream to make it easier to read
				Message message = new Message(input);
				

				//******  Dans le cas d'un paquet requ�te *****
				if(message.getHeader().getQR() == 0){

			     	 //*Si le mode est redirection seulement
					if(RedirectionSeulement){
					    //*Rediriger le paquet vers le serveur DNS
					}
					else{
						for(int i=0; i<message.getQuestions().size(); i++){
					    	//*Rechercher l'adresse IP associe au Query Domain name dans le fichier de 
						    //*correspondance de ce serveur
							QueryFinder queryFinder = new QueryFinder(DNSFile);
							
						    //*Si la correspondance n'est pas trouvee
							if(queryFinder.StartResearch(message.getQuestions().get(i).getName()) != "none")
							{
								//TODO
							     //*Rediriger le paquet vers le serveur DNS
								//UDPSender sender = new UDP;
							}
							else{
							
					             //*Creer le paquet de reponse a l'aide du UDPAnswerPaquetCreator
								UDPAnswerPacketCreator packetCreator = new UDPAnswerPacketCreator();
								packetCreator.CreateAnswerPacket(buffer, message.getQuestions().get(i).getName());
								
								//TODO
						   	     //*Placer ce paquet dans le socket
								//TODO
							     //*Envoyer le paquet
							}
						}
					}
				}
				//******  Dans le cas d'un paquet reponse *****
				else{
					
					//*Ajouter la ou les correspondance(s) dans le fichier DNS si elles ne y sont pas d�j�
					AnswerRecorder answerRecorder = new AnswerRecorder(DNSFile);
					answerRecorder.StartRecord(message.getQuestions().get(0).getName(), message.getAnswers().get(0).getAddress());
					
					//TODO
					//*Faire parvenir le paquet reponse au demandeur original, ayant emis une requete 
					//*avec cet identifiant	
				}
			}
		}catch(Exception e){
			System.err.println("Probl�me � l'ex�cution :");
			e.printStackTrace(System.err);
		}	
	}
}

