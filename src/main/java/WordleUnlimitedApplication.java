
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class WordleUnlimitedApplication extends Application implements EventHandler<KeyEvent> {
	WordleBoardCreator wordleBoard;

	@Override
	public void start(Stage window) {
		wordleBoard = new WordleBoardCreator();
		WordGenerator randomWord = new WordGenerator();		
		randomWord.createWordsList();

		Scene wordleScene = new Scene(wordleBoard.makeBoard(randomWord.chooseRandomWord()));
		wordleScene.setOnKeyPressed(this);
		
		window.setScene(wordleScene);
		window.setTitle("Wordle Unlimited");
		window.show();
	}
	

	@Override
	public void handle(KeyEvent keyBoardInput) {
		if (keyBoardInput.getCode() == KeyCode.ENTER) {
			wordleBoard.handleUserInput("ENTER");;
		} else if (keyBoardInput.getCode() == KeyCode.BACK_SPACE) {
			wordleBoard.handleUserInput("DELETE");
		} else {
			wordleBoard.handleUserInput(keyBoardInput.getText());
		}
		
	}
	
	public static void main(String[] args) {
		launch(WordleUnlimitedApplication.class);
	}

}
