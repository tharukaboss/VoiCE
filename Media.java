import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

	/*
	 *	This is the main class which have settings to play
	 * 	and caputure the audio.
	 *
	 */


public class Media extends Thread{
	public static final int bufferSize = 500;
	AudioFormat audioFormat;
	Mixer mixer;
	
	public Media(){
		this.settingMedia();
	}
	
	/*
	 * method make initial setting for audio format
	 */
	private AudioFormat getAudioFormat() {
		float sampleRate = 16000.0F;
		int sampleSizeInBits = 16;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
	

	/*
	 *	Setting java mixers for play and capture
	 */

	private void settingMedia() {
		try {
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();    //get available mixers
			System.out.println("Available mixers:");
			for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
				System.out.println(cnt + " " + mixerInfo[cnt].getName());
				mixer = AudioSystem.getMixer(mixerInfo[cnt]);

				Line.Info[] lineInfos = mixer.getTargetLineInfo();
				if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
					System.out.println(cnt + " Mic is supported!");
					break;
				}
			}

			audioFormat = getAudioFormat();     //get the audio format 

		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}


}