import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class WordleBoardCreator {
	String correctWord;
	int wordCount;
	ArrayList<Label> letters;
	ArrayList<Rectangle> squaresForLetters;
	ArrayList<Button> buttons;
	StackPane wordleLayout;
	Pane boardLayout;
	boolean acceptUserInput;
	StatisticsPageCreator statistics;
	WordGenerator randomWord;
	
	public WordleBoardCreator() {
		wordCount = 0;
		wordleLayout = new StackPane();
		acceptUserInput = true;
		statistics = new StatisticsPageCreator();
		randomWord = new WordGenerator();
	}
	
 	
	public Parent makeBoard(String targetWord) {
		correctWord = targetWord;
		
		
		boardLayout = new Pane();
		letters = new ArrayList<>();
		squaresForLetters = new ArrayList<>();
		buttons = new ArrayList<>();
		
		boardLayout.setPrefSize(700, 700);
		boardLayout.setStyle("-fx-background-color: black");

		//Creating the squares where input letters are displayed
		
		for (int y = 0; y <= 5; y++) {
			HBox letterSquaresLayout = new HBox(5);
			
			for (int x = 0; x <= 4; x++) {
				StackPane squareAndLabel = new StackPane();
				
				Rectangle squareForLetter = new Rectangle(60, 60);
				Label letter = new Label("");
				
				squareForLetter.setFill(Color.BLACK);
				squareForLetter.setStroke(Color.GRAY);
				
				letter.setPrefSize(60, 60);
				letter.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
				letter.setTextFill(Color.WHITE);
				letter.setAlignment(Pos.CENTER);
				
				letters.add(letter);
				squaresForLetters.add(squareForLetter);
				
				squareAndLabel.getChildren().addAll(squareForLetter, letter);
				letterSquaresLayout.getChildren().add(squareAndLabel);
			}
			
			letterSquaresLayout.setLayoutX(190);
			letterSquaresLayout.setLayoutY(70 + 65 * y);
			
			boardLayout.getChildren().add(letterSquaresLayout);
		}
		
		//Creating the buttons displayed at the bottom of the screen
		
		VBox buttonLayout = new VBox(5);
		HBox horizantalButtons = new HBox(5);
		
		buttonLayout.setTranslateX(boardLayout.getPrefWidth() / 2 - 197.5);
		buttonLayout.setTranslateY(boardLayout.getPrefHeight() / 2 + 170);
		
		String keyBoardLettersForWordle = "QWERTYUIOPASDFGHJKLZXCVBNM";
		String[] keyBoardLetters = keyBoardLettersForWordle.split("");
		
		for (int letterIndex = 0; letterIndex < keyBoardLetters.length ; letterIndex++) {
			Button letterButton = new Button(keyBoardLetters[letterIndex]);
			
			letterButton.setStyle("-fx-background-color: grey");
			letterButton.setTextFill(Color.WHITE);
			letterButton.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
			letterButton.setPrefSize(35, 45);
			
			letterButton.setOnAction(event -> handleUserInput(letterButton.getText()));
			
			horizantalButtons.getChildren().add(letterButton);
			buttons.add(letterButton);
			
			if (letterIndex == 9 || letterIndex == 18 || letterIndex == 25) {
				Button specialActionButton = new Button();
				specialActionButton.setPrefSize(52.5, 45);
				specialActionButton.setStyle("-fx-background-color: grey");
				specialActionButton.setTextFill(Color.WHITE);
				
				if (letterIndex == 25) {
					specialActionButton.setText("⌫");
					specialActionButton.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
					
					specialActionButton.setOnAction(event -> handleUserInput("DELETE"));
					
					horizantalButtons.getChildren().add(specialActionButton);
				} 
				
				buttonLayout.getChildren().add(horizantalButtons);
				horizantalButtons = new HBox(5);
				horizantalButtons.setAlignment(Pos.CENTER);
				
				if (letterIndex == 18) {
					specialActionButton.setText("ENTER");
					specialActionButton.setFont(Font.font("Verdana", FontWeight.BOLD, 9.9));
					specialActionButton.setOnAction(event -> handleUserInput("ENTER"));
					
					horizantalButtons.getChildren().add(specialActionButton);
				}
			}
		}
		
		boardLayout.getChildren().add(buttonLayout);
		wordleLayout.getChildren().add(boardLayout);
		
		return wordleLayout;
	}
	
	public void handleUserInput(String userInput) {
		if (acceptUserInput == false) {
			return;
		}
		
		if (userInput.equals("DELETE")) {
			for (int letterLabelIndex = wordCount * 5 + 4; letterLabelIndex >= wordCount * 5; letterLabelIndex--) {
				if (!letters.get(letterLabelIndex).getText().isEmpty()) {
					letters.get(letterLabelIndex).setText("");
					break;
				}
			}
		}
		
		else if (userInput.equals("ENTER")) {
			if (letters.get(wordCount*5+4).getText().isEmpty()) {
				notEnoughLettersLabel();
			} else {
				squareFlippingAnimation();
			}
		}
		
		else {
			if (!userInput.isEmpty() && userInput.toUpperCase().charAt(0) > 64 && userInput.toUpperCase().charAt(0) < 91) {
				for (int i = wordCount * 5; i < wordCount * 5 + 5; i++) {
					if (letters.get(i).getText().isEmpty()) {
						letters.get(i).setText(userInput.toUpperCase());
						break;
					}
				}
			}
			
		}
	}
	
	private void notEnoughLettersLabel() {
		Label notEnoughLetters = new Label("Not enough letters");
		notEnoughLetters.setPrefSize(150, 35);
		notEnoughLetters.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
		notEnoughLetters.setTextFill(Color.BLACK);
		notEnoughLetters.setLayoutX(275);
		notEnoughLetters.setLayoutY(15);
		notEnoughLetters.setStyle("-fx-background-color: white;");
		notEnoughLetters.setAlignment(Pos.CENTER);
		boardLayout.getChildren().add(notEnoughLetters);
		
		FadeTransition ft = new FadeTransition(Duration.millis(300), notEnoughLetters);
		ft.setDelay(Duration.millis(1200));
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.play();
	}
	
	private void squareFlippingAnimation() {
		acceptUserInput = false;
		
		String guessedWord = "";
		
		for (int i = wordCount * 5; i < wordCount*5 + 5; i++) {
			guessedWord += letters.get(i).getText();
		}
		
		SequentialTransition finalTransition = new SequentialTransition();
		
		ArrayList<String> lettersToChangeColor = new ArrayList<>();
		ArrayList<Color> colorOfLetterChange = new ArrayList<>();
		
		for (int squareIndex = wordCount * 5; squareIndex <= wordCount * 5 + 4; squareIndex++) {
			RotateTransition squareFlipper = new RotateTransition(Duration.millis(400), squaresForLetters.get(squareIndex));
			RotateTransition firstHalfLabelFlipper = new RotateTransition(Duration.millis(200), letters.get(squareIndex));
			RotateTransition secondHalfLabelFlipper = new RotateTransition(Duration.millis(200), letters.get(squareIndex));
			
			squareFlipper.setAxis(Rotate.X_AXIS);
			squareFlipper.setFromAngle(0);
			squareFlipper.setToAngle(180);
			
			firstHalfLabelFlipper.setAxis(Rotate.X_AXIS);
			firstHalfLabelFlipper.setFromAngle(0);
			firstHalfLabelFlipper.setToAngle(90);
			
			secondHalfLabelFlipper.setAxis(Rotate.X_AXIS);
			secondHalfLabelFlipper.setDelay(Duration.millis(225));
			secondHalfLabelFlipper.setFromAngle(90);
			secondHalfLabelFlipper.setToAngle(0);
			
			Color colorOfSquare;
			
			if (letters.get(squareIndex).getText().equals(String.valueOf(correctWord.charAt(squareIndex % 5)))) {
				colorOfSquare = Color.valueOf("#538d4e");
			} else if (checkLetter(squareIndex % 5, guessedWord)) {
				colorOfSquare = Color.valueOf("#b59f3b");
			} else {
				colorOfSquare = Color.valueOf("#3a3a3c");
			}

			lettersToChangeColor.add(letters.get(squareIndex).getText());
			colorOfLetterChange.add(colorOfSquare);
			
			FillTransition squareColorFiller = new FillTransition(Duration.millis(1), squaresForLetters.get(squareIndex), Color.BLACK, colorOfSquare);
			squareColorFiller.setDelay(Duration.millis(200));
			
			ParallelTransition totalTransition =  new ParallelTransition(squareFlipper, firstHalfLabelFlipper, squareColorFiller, secondHalfLabelFlipper);
			
			finalTransition.getChildren().add(totalTransition);	
		}
		
		finalTransition.play();
		
		final String finalGuessedWord = guessedWord;
		
		finalTransition.setOnFinished(whenFinished -> {
			animateLetterColor(lettersToChangeColor, colorOfLetterChange);
			if (wordCount == 5 || finalGuessedWord.equals(correctWord)) {
				endGame(finalGuessedWord);
			} else {
				acceptUserInput = true;
				wordCount++;
			}
		});
		
		
	}
	private void animateLetterColor(ArrayList<String> lettersToChangeColor, ArrayList<Color> colorOfLetterChange) {
		for (int i = 0; i < 5; i++) {
			for (Button buttonToChangeColor : buttons) {
				if (buttonToChangeColor.getText().equals(lettersToChangeColor.get(i))) {
					
					if (colorOfLetterChange.get(i).equals(Color.valueOf("#538d4e"))) {
						buttonToChangeColor.setStyle("-fx-background-color: #538d4e");
					}
					
					if (colorOfLetterChange.get(i).equals(Color.valueOf("#b59f3b"))) {
						if (!buttonToChangeColor.getStyle().contains("#538d4e")) {
							buttonToChangeColor.setStyle("-fx-background-color: #b59f3b");
						}
					}
					
					if (colorOfLetterChange.get(i).equals(Color.valueOf("#3a3a3c")))  {
						if (!buttonToChangeColor.getStyle().contains("#538d4e") && !buttonToChangeColor.getStyle().contains("#b59f3b"))
							buttonToChangeColor.setStyle("-fx-background-color: #3a3a3c");
						
					}
					
				}
			}
		}
		
	}
	
	private boolean checkLetter(int indexOfGuessedLetter, String guessedWord) {
		
		char letter = guessedWord.charAt(indexOfGuessedLetter);
		
		if (!correctWord.contains(String.valueOf(letter))) {
			return false;
		}
		
		int occurencesOfLetterInGuessedWord = 0;
		int occurencesOfLetterInTargetWord = 0;
		
		
		for (int i = 0; i < 5; i++) {
			if (guessedWord.charAt(i) == letter) {
				occurencesOfLetterInGuessedWord++;
			}
			
			if (correctWord.charAt(i) == letter) {
				occurencesOfLetterInTargetWord++;
			}
		}
		
		if (occurencesOfLetterInTargetWord >= occurencesOfLetterInGuessedWord) {
			return true;
		}
		
		int occurencesOfLetterBeforeGuessedIndex = 0; 
		for (int i = 0; i < indexOfGuessedLetter; i++) {
			if (guessedWord.charAt(i) == letter) {
				occurencesOfLetterBeforeGuessedIndex++;
			}
			
			if (occurencesOfLetterBeforeGuessedIndex >= occurencesOfLetterInTargetWord) {
				return false;
			}
		}
		
		return true;	
	}
	
	public void endGame(String finalGuessedWord) {
		
		  Label endMessage = new Label(); endMessage.setPrefSize(150, 35);
		  endMessage.setTextFill(Color.BLACK); endMessage.setLayoutX(275);
		  endMessage.setLayoutY(15);
		  endMessage.setStyle("-fx-background-color: white;");
		  endMessage.setAlignment(Pos.CENTER); endMessage.setFont(Font.font("Verdana",
		  FontWeight.BOLD, 13));
		  
		  if (wordCount > 5 || !finalGuessedWord.equals(correctWord)) {
		  endMessage.setText(correctWord); statistics.addResult(0); } else {
		  endMessage.setText("Splendid"); statistics.addResult(wordCount+1); }
		  
		  boardLayout.getChildren().add(endMessage);
		  
		  Button playAgain = new Button("PLAY AGAIN! ");
		  playAgain.setStyle("-fx-background-color: green");
		  playAgain.setTextFill(Color.WHITE);
		  playAgain.setFont(Font.font("Verdana",FontWeight.BOLD, 13)); 
		  playAgain.setPrefSize(127, 30);
		  playAgain.setTranslateY(85);
		  
		  playAgain.setOnAction(event -> resetGame());
		  
		  PauseTransition pauseAfterEndGame = new
		  PauseTransition(Duration.millis(2000)); 
		  pauseAfterEndGame.setOnFinished(event-> {
		  boardLayout.setOpacity(0.8);
		  wordleLayout.getChildren().addAll(statistics.makeStatisticsBoard(), playAgain);
		  
		  
		  });
		  
		  
		  pauseAfterEndGame.play();
			
	}
	
	public void resetGame() {
		
		  wordCount = 0; 
		  randomWord.createWordsList(); 
		  String newWord = randomWord.chooseRandomWord(); 
		  wordleLayout.getChildren().clear();
		  acceptUserInput = true;
		  letters.clear();
		 		
		  makeBoard(newWord);
	}

}
