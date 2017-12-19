package ahhh;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ahhh.MusicFile.PlayedSample;
import ahhh.SampleManager.Sample;

public class Recorder {
	
	private final double bpm;
	
	private final Map<Integer, String> samples = new HashMap<>();
	
	private final List<PlayedSample> content = new ArrayList<>();
	
	private long startTime;
	
	public Recorder(double bpm) {
		this.bpm = bpm;
	}
	
	public void addSample(String name, Sample s) {
		samples.put(s.id, name);
	}
	
	
	
	public void addPlayedSample(long t, Note n, Sample s) {
		if (content.size() == 0) {
			t = 0;
			startTime = System.currentTimeMillis();
		}
		else {
			t -= startTime;
		}
		content.add(new PlayedSample(t, n, s, 1));
	}
	
	
	
	
	
	public void save(File f) {
		try (PrintStream ps = new PrintStream(f)) {
			ps.println(bpm);
			samples.forEach((i, n) -> ps.println("s "+i+" "+n));
			content.sort((ps1, ps2) -> Long.compare(ps1.playTime, ps2.playTime));
			
			PlayedSample p = content.get(0);
			ps.println("p " + p.sample.id + " " + p.note.tone + " " + p.note.octave + " " + p.volume);
			
			for (int i = 1; i < content.size(); i++) {
				long tPrev = content.get(i - 1).playTime;
				p = content.get(i);
				long tCurr = p.playTime;
				if (tCurr > tPrev) {
					double beatInterval = (tCurr - tPrev) / 60000d * bpm;
					ps.println("w "+beatInterval);
				}
				ps.println("p " + p.sample.id + " " + p.note.tone + " " + p.note.octave + " " + p.volume);
			}
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
