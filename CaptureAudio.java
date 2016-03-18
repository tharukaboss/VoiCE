
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.DataLine;

	/*
	 *	This class have features to capture the audio
	 * 	and send it to the other end who is listning
	 *
	 *	This run in a seperate thread which is totaly
	 * 	independant from the audio receiving and playing
	 *	process
	 */

public class CaptureAudio extends Media {
	TargetDataLine targetDataLine;
	byte tempBuffer[] = new byte[bufferSize];
	boolean stopCapture;
	Socket sendSocket;
	
	
	/*
	 *	Constructer for this class which make the socket
	 *	to connect to listening end
	 */
	
	public CaptureAudio(String ip, String portHost){
		this.sendSocket = new Socket(ip,portHost);
	}
	



	/*
	 *	Setting up mixture for caputre audio
	 */

	public void settingCapture(){
		try{
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

        targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
        targetDataLine.open(audioFormat);
        targetDataLine.start();
		}catch(LineUnavailableException e){
			System.out.println(e);
			System.exit(0);
		}
	}
	
	public void startCapture(){
		this.stopCapture=false;
	}
	
	public void stopCapture(){
		this.stopCapture=true;
	}
	
	
	/*
	 *	the audio capture and send to other end
	 */

	@Override
	public void run (){
		try {
            int readCount;
            while (!this.stopCapture) {
				readCount = targetDataLine.read(tempBuffer, 0, tempBuffer.length-Integer.BYTES);  //capture sound into 
				if(readCount>0){
					Packet sendPacket = new Packet(sendSocket,tempBuffer);
					sendPacket.sendPacket(sendSocket.openNewSocket());
				}
            }
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
	}
	
}