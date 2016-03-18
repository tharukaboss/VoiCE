

public class SystemPlay{
	
	public static void main(String[] args){
		if( args.length != 3 ){
			System.out.println( "usage:server &  peer ip,port" ) ;
			return ;
		}
		
		CaptureAudio cap = new CaptureAudio(args[1],args[2]);
		PlayAudio play = new PlayAudio(args[0]);
		
		cap.settingCapture();
		play.settingPlay();
		
		cap.start();
		play.start();
		
		
		
	}
	

}