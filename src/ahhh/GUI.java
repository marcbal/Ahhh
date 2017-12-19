package ahhh;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import ahhh.Note.Tone;
import ahhh.SampleManager.Sample;
import ahhh.util.FXDialogUtils;
import ahhh.util.TimeUtil;
import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI {
	

	
	private List<Note> notes = new ArrayList<>();
	private Map<KeyCode, Note> keys = new HashMap<>();
	
	ProgressBar playProgress;
	private Recorder recorder;
	
	private void addNote(Note s) {
		notes.add(s);
		if (s.key != null)
			keys.put(s.key, s);
	}
	
	
	private Stage stage;
	
	private MusicFile currentPlayingFile = null;
	
	private Note lastClicked = null;
	
	
	public GUI(Stage s) {
		stage = s;

		addNote(new Note(Tone.FA  , 1, null));
		addNote(new Note(Tone.FAd , 1, null));
		addNote(new Note(Tone.SOL , 1, null));
		addNote(new Note(Tone.SOLd, 1, null));
		addNote(new Note(Tone.LA  , 1, null));
		addNote(new Note(Tone.LAd , 1, null));
		addNote(new Note(Tone.SI  , 1, null));
		addNote(new Note(Tone.DO  , 2, null));
		addNote(new Note(Tone.DOd , 2, null));
		addNote(new Note(Tone.RE  , 2, null));
		addNote(new Note(Tone.REd , 2, null));
		addNote(new Note(Tone.MI  , 2, KeyCode.DIGIT1));
		addNote(new Note(Tone.FA  , 2, KeyCode.DIGIT2));
		addNote(new Note(Tone.FAd , 2, KeyCode.DIGIT3));
		addNote(new Note(Tone.SOL , 2, KeyCode.DIGIT4));
		addNote(new Note(Tone.SOLd, 2, KeyCode.DIGIT5));
		addNote(new Note(Tone.LA  , 2, KeyCode.DIGIT6));
		addNote(new Note(Tone.LAd , 2, KeyCode.DIGIT7));
		addNote(new Note(Tone.SI  , 2, KeyCode.DIGIT8));
		addNote(new Note(Tone.DO  , 3, KeyCode.DIGIT9));
		addNote(new Note(Tone.DOd , 3, KeyCode.DIGIT0));
		addNote(new Note(Tone.RE  , 3, KeyCode.RIGHT_PARENTHESIS));
		addNote(new Note(Tone.REd , 3, KeyCode.EQUALS));
		addNote(new Note(Tone.MI  , 3, KeyCode.A));
		addNote(new Note(Tone.FA  , 3, KeyCode.Z));
		addNote(new Note(Tone.FAd , 3, KeyCode.E));
		addNote(new Note(Tone.SOL , 3, KeyCode.R));
		addNote(new Note(Tone.SOLd, 3, KeyCode.T));
		addNote(new Note(Tone.LA  , 3, KeyCode.Y));
		addNote(new Note(Tone.LAd , 3, KeyCode.U));
		addNote(new Note(Tone.SI  , 3, KeyCode.I));
		addNote(new Note(Tone.DO  , 4, KeyCode.O));
		addNote(new Note(Tone.DOd , 4, KeyCode.P));
		addNote(new Note(Tone.RE  , 4, KeyCode.DEAD_CIRCUMFLEX));
		addNote(new Note(Tone.REd , 4, KeyCode.DOLLAR));
		addNote(new Note(Tone.MI  , 4, KeyCode.Q));
		addNote(new Note(Tone.FA  , 4, KeyCode.S));
		addNote(new Note(Tone.FAd , 4, KeyCode.D));
		addNote(new Note(Tone.SOL , 4, KeyCode.F));
		addNote(new Note(Tone.SOLd, 4, KeyCode.G));
		addNote(new Note(Tone.LA  , 4, KeyCode.H));
		addNote(new Note(Tone.LAd , 4, KeyCode.J));
		addNote(new Note(Tone.SI  , 4, KeyCode.K));
		addNote(new Note(Tone.DO  , 5, KeyCode.L));
		addNote(new Note(Tone.DOd , 5, KeyCode.M));
		addNote(new Note(Tone.RE  , 5, KeyCode.UNDEFINED));
		addNote(new Note(Tone.REd , 5, KeyCode.ASTERISK));
		addNote(new Note(Tone.MI  , 5, KeyCode.LESS));
		addNote(new Note(Tone.FA  , 5, KeyCode.W));
		addNote(new Note(Tone.FAd , 5, KeyCode.X));
		addNote(new Note(Tone.SOL , 5, KeyCode.C));
		addNote(new Note(Tone.SOLd, 5, KeyCode.V));
		addNote(new Note(Tone.LA  , 5, KeyCode.B));
		addNote(new Note(Tone.LAd , 5, KeyCode.N));
		addNote(new Note(Tone.SI  , 5, KeyCode.COMMA));
		addNote(new Note(Tone.DO  , 6, KeyCode.SEMICOLON));
		addNote(new Note(Tone.DOd , 6, KeyCode.COLON));
		addNote(new Note(Tone.RE  , 6, KeyCode.EXCLAMATION_MARK));
		addNote(new Note(Tone.REd , 6, null));
		addNote(new Note(Tone.MI  , 6, null));
		addNote(new Note(Tone.FA  , 6, null));
		addNote(new Note(Tone.FAd , 6, null));
		addNote(new Note(Tone.SOL , 6, null));
		addNote(new Note(Tone.SOLd, 6, null));
		addNote(new Note(Tone.LA  , 6, null));
		addNote(new Note(Tone.LAd , 6, null));
		addNote(new Note(Tone.SI  , 6, null));
		addNote(new Note(Tone.DO  , 7, null));
		addNote(new Note(Tone.DOd , 7, null));
		addNote(new Note(Tone.RE  , 7, null));
		
		
		
		
		
		BorderPane pane = new BorderPane();
		
		stage.setScene(new Scene(pane));
		setTitle(null);
		
		
		
		
		
		Canvas canvas = new Canvas(800, 400) {
			@Override
			public boolean isResizable() {
				return true;
			}
		};
		

		pane.setCenter(canvas);
		
		canvas.widthProperty().bind(pane.widthProperty());
		canvas.heightProperty().bind(pane.heightProperty().add(-30));


		double minIndex = notes.get(0).pianoIndex;
		double maxIndex = notes.get(notes.size()- 1).pianoIndex + 1;
		
		
		Function<Note, BoundingBox> bbCalculator = (st) -> {
			double x, y = 0, w, h;
			if (st.tone.isAccidental()) {
				x = (st.pianoIndex + .125 - minIndex) * canvas.getWidth() / (maxIndex - minIndex);
				w = canvas.getWidth() / (maxIndex - minIndex) * .75;
				h = canvas.getHeight() * .67;
			}
			else {
				x = (st.pianoIndex - minIndex) * canvas.getWidth() / (maxIndex - minIndex);
				w = canvas.getWidth() / (maxIndex - minIndex);
				h = canvas.getHeight();
			}
			return new BoundingBox(x, y, w, h);
		};
		
		
		AnimationTimer anim = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				GraphicsContext gc = canvas.getGraphicsContext2D();
				
				gc.setStroke(Color.BLACK);
				gc.setTextAlign(TextAlignment.LEFT);
				gc.setTextBaseline(VPos.BOTTOM);
				gc.setFontSmoothingType(FontSmoothingType.LCD);
				gc.setFont(Font.font(10));
				
				for (Note n : notes) {
					if (n.tone.isAccidental())
						continue;
					boolean isSelected = isDisplayedPressed(n);
					BoundingBox bb = bbCalculator.apply(n);
					
					gc.setFill(isSelected ? Color.web("#FCC") : Color.WHITE);
					gc.fillRect(bb.getMinX(), bb.getMinY(), bb.getWidth(), bb.getHeight());
					gc.strokeRect(bb.getMinX(), bb.getMinY(), bb.getWidth(), bb.getHeight());
					gc.setFill(Color.BLACK);
					gc.fillText(n.tone.name()+n.octave, bb.getMinX(), bb.getMaxY() - 26, bb.getWidth());
					gc.fillText("x"+(((int)(n.frequency / SampleManager.ahhh.initialFrequency * 1000)) / 1000f), bb.getMinX(), bb.getMaxY() - 13, bb.getWidth());
					if (n.key != null)
						gc.fillText(n.key.name(), bb.getMinX(), bb.getMaxY(), bb.getWidth());
				}
				
				for (Note n : notes) {
					if (!n.tone.isAccidental())
						continue;
					boolean isSelected = isDisplayedPressed(n);
					BoundingBox bb = bbCalculator.apply(n);
					
					gc.setFill(isSelected ? Color.web("#800") : Color.BLACK);
					gc.fillRect(bb.getMinX(), bb.getMinY(), bb.getWidth(), bb.getHeight());
					gc.strokeRect(bb.getMinX(), bb.getMinY(), bb.getWidth(), bb.getHeight());
					gc.setFill(Color.WHITE);
					gc.fillText(""+n.tone.name().replace('d', '#')+n.octave, bb.getMinX(), bb.getMaxY() - 26, bb.getWidth());
					gc.fillText("x"+(((int)(n.frequency / SampleManager.ahhh.initialFrequency * 1000)) / 1000f), bb.getMinX(), bb.getMaxY() - 13, bb.getWidth());
					if (n.key != null)
						gc.fillText(n.key.name(), bb.getMinX(), bb.getMaxY(), bb.getWidth());
				}
				
				
				if (currentPlayingFile == null || !currentPlayingFile.isPlaying()) {
					setTitle(null);
				}
			}
		};
		
		

		canvas.setOnMousePressed(e -> {
			double mX = e.getX(), mY = e.getY();
			
			Note clickedNote = null;
			for (Note n : notes) {
				if (!n.tone.isAccidental())
					continue;
				BoundingBox bb = bbCalculator.apply(n);
				if (bb.contains(mX,  mY)) {
					clickedNote = n;
					break;
				}
			}
			
			if (clickedNote == null)
				for (Note n : notes) {
					if (n.tone.isAccidental())
						continue;
					BoundingBox bb = bbCalculator.apply(n);
					if (bb.contains(mX,  mY)) {
						clickedNote = n;
						break;
					}
				}
			
			if (clickedNote != null) {
				if (clickedNote != lastClicked) {
					SampleManager.ahhh.play(clickedNote);
					manualPress(clickedNote, SampleManager.ahhh);
					lastClicked = clickedNote;
				}
				
			}
		});
		
		canvas.setOnMouseDragged(canvas.getOnMousePressed());
		
		canvas.setOnMouseReleased(e -> lastClicked = null);
		
		
		
		
		
		stage.getScene().setOnKeyPressed(e -> {
			KeyCode c = e.getCode();
			
			Note st = keys.get(c);
			
			if (st != null) {
				SampleManager.ahhh.play(st);
				manualPress(st, SampleManager.ahhh);
			}
		});
		
		
		Label volumeLabel = new Label("Volume : ");
		volumeLabel.setMinWidth(50);
		
		Slider volume = new Slider(0, 1, 1);
		SampleManager.masterVolume.bind(volume.valueProperty());
		volume.setValue(0.2);
		volume.setMinWidth(100);
		
		playProgress = new ProgressBar(0);
		playProgress.setPrefWidth(2000);
		
		ToggleButton record = new ToggleButton("Enregistrer");
		record.selectedProperty().addListener((obj, o, n) -> {
			if (n) {
				double bpm = 0;
				do {
					try {
						bpm = Double.parseDouble(FXDialogUtils.showTextInputDialog("BPM pour l'enregistrement",
								"Indiquer le BPM pour l'enregistrement (type double > 0)", "120"));
					} catch(NumberFormatException e) {}
				} while (bpm <= 0);
				recorder = new Recorder(bpm);
				recorder.addSample("ahhh", SampleManager.ahhh);
			}
			else {
				FileChooser saveChooser = new FileChooser();
				saveChooser.setInitialDirectory(new File("."));
				File f = saveChooser.showSaveDialog(stage);
				if (f != null)
					recorder.save(f);
				
				recorder = null;
			}
		});
		record.setMinWidth(100);
		
		HBox bottomBox = new HBox(2, volumeLabel, volume, record, playProgress);
		
		HBox.setHgrow(playProgress, Priority.SOMETIMES);
		
		pane.setBottom(bottomBox);

		stage.setMinWidth(900);
		stage.setMinHeight(200);

		stage.setWidth(1200);
		stage.setHeight(300);
		
		
		// DRAG AND DROP
		stage.getScene().setOnDragOver(event -> {
			Dragboard db = event.getDragboard();
			if (db.hasFiles()) {
				event.acceptTransferModes(TransferMode.COPY);
			} else {
				event.consume();
			}
		});
		
		// Dropping over surface
		stage.getScene().setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasFiles()) {
				success = true;
				playFile(db.getFiles().get(0));
			}
			event.setDropCompleted(success);
			event.consume();
		});

		
		anim.start();
		
		
		stage.show();
		
	}
	
	
	
	
	
	
	
	
	public void playFile(File f) {
		if (currentPlayingFile != null && currentPlayingFile.isPlaying()) {
			FXDialogUtils.showMessageDialog(AlertType.ERROR, "Déjà un fichier en cours de lecture", null, "Un fichier est déjà en cours de lecture. Patientez un instant.");
		}
		try {
			currentPlayingFile = new MusicFile(f, this);
		} catch (Exception e) {
			FXDialogUtils.showExceptionDialog(e.getClass().getName(), e.getLocalizedMessage(), e);
		}
	}
	
	
	
	
	private Map<Note, Long> displayedPressed = new HashMap<>();
	
	private boolean isDisplayedPressed(Note n) {
		Long t = displayedPressed.get(n);
		return (t != null && t > System.currentTimeMillis());
	}
	
	private void manualPress(Note n, Sample s) {
		press(n, s);
		if (recorder != null) {
			long t = System.currentTimeMillis();
			recorder.addPlayedSample(t, n, s);
		}
	}
	
	public void press(Note n, Sample s) {
		long d = (long) (s.duration / (n.frequency / s.initialFrequency));
		displayedPressed.put(n, System.currentTimeMillis() + d);
	}
	
	public void setTitle(String compT) {
		String recordText = "";
		if (recorder != null && recorder.getStartTime() != 0) {
			recordText = " - Enregistrement : " + TimeUtil.durationToString(System.currentTimeMillis() - recorder.getStartTime())
				+ ", " + recorder.getContentCount();
		}
		
		stage.setTitle("Ahhh" + recordText + (compT == null ? "" : (" - " + compT)));
	}
	
}
