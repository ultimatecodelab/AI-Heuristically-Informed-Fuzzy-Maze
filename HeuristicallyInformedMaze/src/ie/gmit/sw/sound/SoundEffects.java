package ie.gmit.sw.sound;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public enum SoundEffects {	
	MOVE("resources/move"), ALARM("resources/alarm");

	private Clip clip;
	SoundEffects(String fileName) {
		try {
			URL url = this.getClass().getClassLoader().getResource(fileName);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if (clip.isRunning()) clip.stop(); 
		clip.setFramePosition(0);
		clip.start(); 
	}
	 
	public static void init() {
		values();
	}
	public static void main(String[] args) {
		init();
		SoundEffects.ALARM.play();
	}
}