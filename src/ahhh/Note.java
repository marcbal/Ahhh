package ahhh;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;

import javafx.scene.input.KeyCode;

public class Note implements Comparable<Note> {
	public final Tone tone;
	public final int octave;
	public final KeyCode key;
	public final double frequency;
	public final double pianoIndex;
	
	public Note(Tone t, int o, KeyCode k) {
		tone = t;
		octave = o;
		key = k;
		frequency = tone.getFreq(octave);
		pianoIndex = o * 7 + tone.pianoIndex;
	}
	
	public Note(Tone t, int o) {
		this(t, o, null);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Note))
			return false;
		Note o = (Note) obj;
		return (tone == o.tone && octave == o.octave);
	}
	
	@Override
	public int hashCode() {
		return tone.ordinal() << 4 + octave;
	}
	
	private Comparator<Note> comparator = Comparator.comparingInt((Note n) -> n.octave)
			.thenComparing((Note n) -> n.tone, Tone::compareTo);
	@Override
	public int compareTo(Note o) {
		return comparator.compare(this, o);
	}
	
	@Override
	public String toString() {
		return tone.name().replace('d', '#')+""+octave;
	}
	
	enum Tone {
		DO(0),
		DOd(0.5),
		RE(1),
		REd(1.5),
		MI(2),
		FA(3),
		FAd(3.5),
		SOL(4),
		SOLd(4.5),
		LA(5),
		LAd(5.5),
		SI(6);
		
		public final double pianoIndex;
		
		private Tone(double pI) {
			pianoIndex = pI;
		}
		
		public double getFreq(int octave) {
			int octaveDiff = octave - 3; // le La 440Hz est dans l'octave 3
			int noteDiff = ordinal() - LA.ordinal();
			int absoluteNoteDiff = noteDiff + octaveDiff * 12;
			
			return 440 * Math.pow(2, absoluteNoteDiff/12.0);
		}
		
		private static Set<Tone> accidentals = EnumSet.of(DOd, REd, FAd, SOLd, LAd);
		
		public boolean isAccidental() {
			return accidentals.contains(this);
		}
	}
}