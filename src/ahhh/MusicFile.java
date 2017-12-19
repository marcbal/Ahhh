package ahhh;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ahhh.Note.Tone;
import ahhh.SampleManager.Sample;
import ahhh.util.EnumUtil;
import ahhh.util.TimeUtil;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class MusicFile extends Thread {
	
	private GUI gui;
	
	public final File file;
	
	public final double bpm;
	
	private final List<PlayedSample> content = new ArrayList<>();
	
	private long startTime = 0;
	
	private boolean playing = false;
	
	public final long duration;
	
	public MusicFile(File f, GUI g) throws Exception {
		super("MusicFile Thread");
		file = f;
		gui = g;
		try (Scanner s = new Scanner(f)) {
			
			bpm = Double.parseDouble(s.next());
			
			Map<Integer, Sample> samples = new HashMap<>();
			
			double currentBeatProgress = 0; // number of metronome tick since the begining
			
			long currentTime = 0; // currentTime = currentBeatProgress * 60000 / bpm
			
			while (s.hasNext()) {
				String type = s.next();
				
				
				if (type.toLowerCase().startsWith("s")) {
					// s = sample identification
					// s 1 ahhh
					int sId = s.nextInt();
					String name = s.next();
					Sample sp = SampleManager.samples.get(name);
					if (sp == null) {
						throw new IllegalArgumentException("Le sample '"+name+"' n'existe pas.");
					}
					samples.put(sId, sp);
				}
				else if (type.toLowerCase().startsWith("p")) {
					// p = play note
					// p 1 DO 3 1 (sampleId tone octave relativeVolume)
					int sId = s.nextInt();
					Sample sp = samples.get(sId);
					if (sp == null) {
						throw new IllegalArgumentException("Le sample d'id '"+sId+"' n'existe pas.");
					}
					String tone = s.next();
					Tone t = EnumUtil.searchEnum(Tone.class, tone);
					if (t == null) {
						throw new IllegalArgumentException("La Tone '"+tone+"' n'existe pas.");
					}
					int octave = s.nextInt();
					double vol = Double.parseDouble(s.next());
					content.add(new PlayedSample(currentTime, new Note(t, octave), sp, vol));
				}
				else {
					// w = wait nb beat
					// w 1 (attendre 1 * (60 / bpm) secondes)
					double d = Double.parseDouble(s.next());
					if (d < 0) d = 0;
					
					currentBeatProgress += d;
					currentTime = (long) (currentBeatProgress * 60000 / bpm);
				}
				
			}
			
		}
		
		duration = content.get(content.size() - 1).playTime;
		
		start();
	}
	
	private int i = 0;
	
	@Override
	public void run() {
		AnimationTimer guiUpdater = new AnimationTimer() {
			@Override
			public void handle(long now) {
				gui.playProgress.setProgress(currentProgress() / (double)duration);
				updateGUITitle();
			}
		};
		
		
		startTime = System.currentTimeMillis();
		playing = true;
		guiUpdater.start();
		for (i = 0; i < content.size(); i++) {
			PlayedSample ps = content.get(i);
			while (currentProgress() < ps.playTime) {
				try { Thread.sleep(ps.playTime - currentProgress()); }
				catch (InterruptedException e) { playing = false; return; }
			}
			ps.play(gui);
		}
		playing = false;
		guiUpdater.stop();
		gui.playProgress.setProgress(0);
		Platform.runLater(() -> gui.setTitle(null));
	}
	
	public long currentProgress() {
		return System.currentTimeMillis() - startTime;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	
	private void updateGUITitle() {
		gui.setTitle(file.getName()
				+ " - " + TimeUtil.durationToString(currentProgress())
				+ "/" + TimeUtil.durationToString(duration)
				+ " - " + i + "/" + content.size());
	}
	
	
	
	public static class PlayedSample {
		public final long playTime;
		public final Note note;
		public final Sample sample;
		public final double volume;
		
		public PlayedSample(long t, Note n, Sample s, double v) {
			playTime = t;
			note = n;
			sample = s;
			volume = v;
		}
		
		private void play(GUI gui) {
			sample.play(note, volume);
			gui.press(note, sample);
		}
	}
	
	
	
	
}
