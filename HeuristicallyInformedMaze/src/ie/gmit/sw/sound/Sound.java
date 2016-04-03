package ie.gmit.sw.sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	public Sound() {
		// TODO Auto-generated constructor stub
	}

	private Clip clip;

	public void plaSound() {
		try {
			URL url = this.getClass().getClassLoader().getResource("collected.mp3");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Sound().plaSound();
	}
}
