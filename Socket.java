import java.net.* ;

	/*
	 *	Sockets needed for run the program 
	 */

public class Socket{
	public static int hostPort;
	public static DatagramSocket receiveSocket;
	public static Packet packet;
	private InetAddress sendIP;
	private int sendPort;

	/*
	 *	Constructor supporting Play audio class
	 */

	public Socket(String hostPort){
		this.hostPort=Integer.parseInt(hostPort);
		try{
			receiveSocket = new DatagramSocket( this.hostPort ) ;
		}catch(SocketException e){
			System.out.println(e);
			System.exit(0);
		}

	}


	/*
	 *	Counstructor supporting CaptureAdio
	 */

	public Socket(String hostIP,String hostPort){
		try{
			this.sendIP 	=InetAddress.getByName(hostIP);
			this.sendPort	=Integer.parseInt(hostPort);
		}catch(UnknownHostException e){
			System.out.println(e);
			System.exit(0);
		}
	}


	/*
	 *	open a new socket for send a packet
	 */

	public DatagramSocket openNewSocket(){
		DatagramSocket socket = null;
		try{
			socket = new DatagramSocket();
		}catch(SocketException e){
			System.out.println(e);
			System.exit(0);
		}
		return socket;
	}

	public InetAddress getSendIP(){
		return this.sendIP;
	}

	public int getSendPort(){
		return this.sendPort;
	}



}