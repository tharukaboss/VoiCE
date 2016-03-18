
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.net.DatagramPacket ;
import java.net.SocketException;


	/*
	 *	This class have features to play the audio
	 * 	 which send by the sender
	 *
	 *	This run in a seperate thread which is totaly
	 * 	independant from the audio sending and capturing
	 *	process
	 */


public class PlayAudio extends Media{
	private final static int packetsize = bufferSize ;
	ByteArrayOutputStream byteArrayOutputStream;
	boolean stopPlay;
	SourceDataLine sourceDataLine;
	DatagramPacket packet;
	byte tempPlayBuffer[] = new byte[bufferSize-4];
	Socket receiveSocket;
	Packet receivedPacket;

	/*
	 *	counstructor make the socket for receive audio packets
	 */
	
	public PlayAudio(String port){
		this.stopPlay=false;
		receiveSocket = new Socket(port);
		this.packet= new DatagramPacket( new byte[Media.bufferSize], Media.bufferSize);
	}
	
	public void startPlay(){
		this.stopPlay=false;
	}
	
	public void stopPlay(){
		this.stopPlay=true;
	}
	
	/*
	 *	Setting up mixture for play audio
	 */
	
	public void settingPlay(){
		try{
		DataLine.Info dataLineInfo1 = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();
        
        //Setting the maximum volume
        FloatControl control = (FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(control.getMaximum());
		}catch(LineUnavailableException e){
			System.out.println(e);
			System.exit(0);
		}
	}
	
	
	/*
	 *	recieve packet and play it
	 */
	
	@Override
	public void run(){
		byteArrayOutputStream = new ByteArrayOutputStream();
		try {
            while (!stopPlay) {
					Socket.receiveSocket.receive(packet);
					receivedPacket = new Packet(packet);
					tempPlayBuffer = receivedPacket.getPlayData();
					//System.out.println(receivedPacket.getSequenceNumber());
   	                byteArrayOutputStream.write(tempPlayBuffer, 0, bufferSize-4);
                    sourceDataLine.write(tempPlayBuffer, 0, bufferSize-4);   //playing audio available in tempBuffer
            }
            byteArrayOutputStream.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
	}
	
	
}
