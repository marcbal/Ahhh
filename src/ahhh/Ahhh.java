package ahhh;
import java.io.File;
import java.util.List;

import javafx.stage.Stage;

public class Ahhh extends javafx.application.Application {
	public static void main(String[] args) { launch(args); }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GUI gui = new GUI(primaryStage);
		List<String> args = getParameters().getRaw();
		if (!args.isEmpty()) {
			gui.playFile(new File(args.get(0)));
		}
	}
	
}
