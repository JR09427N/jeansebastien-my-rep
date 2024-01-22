import java.io.File;
import java.util.HashMap;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SnakeGame {

	public static void main(String[] args) {
		
		HashMap<Integer, String> musicIds = new HashMap<>();
		musicIds.put(0, "Fun Kid - Quincas Moreira.wav");
		musicIds.put(1, "Little Samba - Quincas Moreira.wav");
		musicIds.put(2, "mining-by-moonlight-kevin-macleod-main-version-03-15-12960.wav");
		
		Random rand = new Random();
		String filePath = musicIds.get(rand.nextInt(3)); // choose random song to play
		PlayMusic(filePath);
		
		new GameFrame();

	}

	private static void PlayMusic(String location) {
		try {
			File musicPath = new File(location);
			
			if(musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
			
			else {
				System.out.println("Can't fins file");
			}
		}
		
		catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
