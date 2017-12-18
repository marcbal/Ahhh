package ahhh;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class MIDIToAhhhConverter {
	
	
	/**
	 * Convert a midi file to Ahhh format and save it to a file.
	 * 
	 * This method can display dialog message to ask custom settings to the user (channel selection for example).
	 * This method can also ask the user the location where the target file will be saved.
	 * @param midiFile the MIDI input file
	 * @param stage The Stage used to open usefull windows
	 * @return the File pointing to where the content was saved with Ahhh file format.
	 */
	public static File convert(File midiFile, Stage stage) {
		try {
			FileChooser saveChooser = new FileChooser();
			saveChooser.setInitialDirectory(midiFile.getParentFile());
			File targetFile = saveChooser.showSaveDialog(stage);
			
			
			// TODO the convertion, saved into targetFile
			throw new UnsupportedOperationException("not implemented");
			
			
			
			//return targetFile;
		} catch(Exception e) {
			throw new RuntimeException("Erreur lors de la convertion du fichier MIDI vers le format Ahhh", e);
		}
	}
	
	
	
	
	private MIDIToAhhhConverter() {}
	
}
