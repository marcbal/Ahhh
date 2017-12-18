package ahhh;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioClip;

public class SampleManager {
	
	public static final DoubleProperty masterVolume = new SimpleDoubleProperty(null, "masterVolume", 0.2);
	
	public static final Sample ahhh = loadFromClassLoader("samples/died_cut.wav", 680, 596);
	
	
	
	
	public static final Map<String, Sample> samples = new HashMap<>();
	
	static {
		samples.put("ahhh", ahhh);
	}
	
	
	
	
	
	private static Sample loadFromClassLoader(String path, double freq, long d) {
		return new Sample(new AudioClip(SampleManager.class.getClassLoader().getResource(path).toExternalForm()), freq, d);
	}
	
	public static void loadFromFile(String path, String name, double freq, long d) {
		samples.put(name, new Sample(new AudioClip(path), freq, d));
	}
	
	public static class Sample {
		final AudioClip audio;
		final double initialFrequency;
		final long duration;
		
		public Sample(AudioClip a, double freq, long d) {
			audio = a;
			initialFrequency = freq;
			duration = d;
			audio.setCycleCount(AudioClip.INDEFINITE);
			audio.play(0, 0, 0.130, 0, 1000);
			audio.setCycleCount(1);
		}
		
		public void play(Note note) {
			play(note, 1);
		}
		
		public void play(Note note, double volume) {
			double rate = note.frequency / initialFrequency;
			audio.play(Math.min(1, Math.max(0, masterVolume.get() * volume)), 0, rate, 0, 0);
		}
	}
	
}
