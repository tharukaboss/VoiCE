
	/*
	 *	This class use to handle the incoming packets
	 *	and order them
	 *	Incoming packets insert in to a arraylist and check
	 * 	whether its in order
	 *
	 *	This class will remove the receive packet prcess
	 *	which is currently doing by the PlayAudio class.
	 *	Like that this removes network part from Play Audio class
	 *	
	 *	The ArrayList which have incoming packets, give access to
	 *	the PlayAudio class to play the audio.
	 *	
	 */

public class PacketHandle extends Thread{
	public static int incomingPacketCounter;
	public static ArrayList<Packet> incomingBuffer = new ArrayList<Packet>(10);
	public static int currentPosition;
	public static int currentPacketNumber;

	
}