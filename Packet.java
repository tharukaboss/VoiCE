import java.net.* ;
import java.nio.ByteBuffer;
import java.io.IOException;

public class Packet{
	public static int sendingPacketCount=0;
	private ByteBuffer buffer = ByteBuffer.allocate(Media.bufferSize);
	private DatagramPacket packet;
	private int seqNumber;
	private byte[] playData = new byte[Media.bufferSize-4];

	

	/*
	 *	make a packet to send by capturing audio
	 * 	a packet number is added to packet to check
	 * 	the ordering in other end.
	 *	the packet number also send with the audio part
	 *	which is in a byte array
	 *	
	 *	sender is not added to the Packet, because
	 *	it can identified by the IP.
	 */
	public Packet(Socket sock,byte[] data){
		synchronized(this){
			sendingPacketCount++;
			this.seqNumber=sendingPacketCount;
			playData = data;
			this.buffer.putInt(this.seqNumber);
			this.buffer.put(this.playData,0,Media.bufferSize-4);

			this.packet = new DatagramPacket(buffer.array(), Media.bufferSize,
				sock.getSendIP(), sock.getSendPort());
		}
	}

	/*
	 *	make audio packet by recieving datagram packet
	 */
	public Packet(DatagramPacket packet){
		this.packet=packet;
		try{
			buffer = ByteBuffer.wrap(this.packet.getData());
			this.seqNumber=buffer.getInt();
			buffer.get(this.playData,0,Media.bufferSize-4);
		}catch(IndexOutOfBoundsException e){
			System.out.println(e);
			System.exit(0);
		}
	}



	public DatagramPacket makePacket(){
		return new DatagramPacket( new byte[Media.bufferSize], Media.bufferSize);
	}



	public int getSequenceNumber(){
		return this.seqNumber;
	}



	public byte[] getPlayData(){
		return this.playData;
	}



	public DatagramPacket getPacket(){
		return this.packet;
	}




	/*
	 *	send the generated packet
	 */
	public void sendPacket(DatagramSocket socket){
		try{
			socket.send(this.packet);
		}catch(IOException e){
			System.out.println(e);
			System.exit(0);
		}
	}

}