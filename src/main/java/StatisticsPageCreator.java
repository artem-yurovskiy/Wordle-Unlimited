import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class StatisticsPageCreator {
	ArrayList<Integer> totalResults;

	public StatisticsPageCreator() {
		totalResults = new ArrayList<>();
	}
	
	public Parent makeStatisticsBoard() {
		Pane statisticsResults = new Pane();
		Rectangle statisticsResultsBackground = new Rectangle(450, 350);
		
		statisticsResults.setTranslateX(125);
		statisticsResults.setTranslateY(125);
		statisticsResults.setPrefSize(statisticsResultsBackground.getWidth(), statisticsResultsBackground.getHeight());
		
		statisticsResultsBackground.setFill(Color.BLACK);
		statisticsResultsBackground.setArcWidth(30.0);
		statisticsResultsBackground.setArcHeight(20.0);
		
		statisticsResults.getChildren().add(statisticsResultsBackground);
		
		int[] totalNumberOfTriesList = new int[7];
		
		for (int numberOfTries : totalResults) {
				totalNumberOfTriesList[numberOfTries]++;
		}
		
		int totalTimesPlayed = totalResults.size();
		
		int totalWon = 0;
		for (int numberOfTries : totalResults) {
			if (numberOfTries != 0) {
				totalWon++;
			}
			
		}
		int winPercentage = (int) (1.0*totalWon/totalTimesPlayed * 100);

		
		int currentStreak = 0;
		for (int i = totalTimesPlayed-1; i >= 0; i--) {
			if (totalResults.get(i) == 0) {
				break;
			}
			
			currentStreak++;
		}
		
		int maxStreak = 0;
		int temporaryMaxStreak = 0;
		int maxStreakTemporary = 0;
		for (int i = 0; i < totalResults.size(); i++) {
			if (totalResults.get(i) == 0) {
				if (maxStreakTemporary > maxStreak) {
					maxStreak = maxStreakTemporary;
				}
				maxStreakTemporary = 0;
			} else {
				maxStreakTemporary++;
				
				if (maxStreakTemporary > maxStreak) {
					maxStreak = maxStreakTemporary;
				}
			}
		}
		
		Label statisticsPageTitle = new Label("STATISTICS");
		statisticsPageTitle.setAlignment(Pos.CENTER);
		statisticsPageTitle.setPrefSize(110, 30);
		statisticsPageTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		statisticsPageTitle.setTextFill(Color.WHITE);
		statisticsPageTitle.setTranslateX(170);
		statisticsPageTitle.setTranslateY(10);
		statisticsPageTitle.setTextAlignment(TextAlignment.CENTER);
		
		HBox statisticsLayout = new HBox(10);
		String[] statisticsText = {totalTimesPlayed+"", "Played", winPercentage+"", "Win %", currentStreak+"", "Current\nStreak", maxStreak+"", "Max\nStreak"};

		statisticsLayout.setTranslateX(90);
		statisticsLayout.setTranslateY(40);
		
		for (int statisticsTextIndex = 0; statisticsTextIndex < 8; statisticsTextIndex+=2) {
			Label statisticsNumberLabel = new Label(statisticsText[statisticsTextIndex]);
			Label statisticsTextLabel = new Label(statisticsText[statisticsTextIndex+1]);
			VBox statisticsTextLayout = new VBox(1);
			
			statisticsNumberLabel.setAlignment(Pos.CENTER);
			statisticsNumberLabel.setPrefSize(60, 20);
			statisticsNumberLabel.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 31));
			statisticsNumberLabel.setTextFill(Color.WHITE);
			statisticsNumberLabel.setTextAlignment(TextAlignment.CENTER);
			
			statisticsTextLabel.setAlignment(Pos.TOP_CENTER);
			statisticsTextLabel.setPrefSize(60, 25);
			statisticsTextLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
			statisticsTextLabel.setTextFill(Color.WHITE);
			statisticsTextLabel.setTextAlignment(TextAlignment.CENTER);
			
			statisticsTextLayout.getChildren().addAll(statisticsNumberLabel, statisticsTextLabel);
			statisticsLayout.getChildren().add(statisticsTextLayout);
		}
		
		int longestBar = totalNumberOfTriesList[1];
		ArrayList<Label> barChartLabels = new ArrayList<>();
		
		for (int i = 1; i < 7; i++) {;
			Label barChartLabel = new Label(totalNumberOfTriesList[i]+"");
			
			if (totalNumberOfTriesList[i] > longestBar) {
				longestBar = totalNumberOfTriesList[i];
			}
			
			barChartLabel.setAlignment(Pos.CENTER);
			barChartLabel.setStyle("-fx-text-fill:WHITE;");
			barChartLabel.setPrefSize(20, 20);
			barChartLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
			barChartLabel.setTranslateX(45);
			barChartLabel.setTranslateY(130 + 20.3 * i);
			
			barChartLabels.add(barChartLabel);
		}
		
		ArrayList<Label> yAxisLabels = new ArrayList<>();
		
		for (int i = 1; i < 7; i++) {
			Label yAxisLabel = new Label(i+"");
			yAxisLabel.setAlignment(Pos.CENTER);
			yAxisLabel.setStyle("-fx-text-fill:WHITE;");
			yAxisLabel.setPrefSize(15, 15);
			yAxisLabel.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 11));
			yAxisLabel.setTranslateX(33);
			yAxisLabel.setTranslateY(133.5 + 20.3 * i);
			
			yAxisLabels.add(yAxisLabel);
		}
		
		Label guessDistributionLabel = new Label("GUESS DISTRIBUTION");
		guessDistributionLabel.setAlignment(Pos.CENTER);
		guessDistributionLabel.setPrefSize(178, 30);
		guessDistributionLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		guessDistributionLabel.setTextFill(Color.WHITE);
		guessDistributionLabel.setTranslateX(136);
		guessDistributionLabel.setTranslateY(110);
		guessDistributionLabel.setTextAlignment(TextAlignment.CENTER);
		
		CategoryAxis yAxis = new CategoryAxis();
		NumberAxis xAxis = new NumberAxis();
		
		xAxis.setTickLabelsVisible(false);
		xAxis.setMinorTickVisible(false);
		xAxis.setTickMarkVisible(false);
		
		yAxis.setTickLabelFill(Color.WHITE);
		yAxis.setTickLabelFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 11));
		
		StackedBarChart<Number, String> barChart = new StackedBarChart<Number, String>(xAxis, yAxis); 
		
		barChart.setMaxSize(380, 165);
		barChart.setLegendVisible(false);
		barChart.setTranslateX(15);
		barChart.setTranslateY(130);
		barChart.setHorizontalGridLinesVisible(false);
		barChart.setHorizontalZeroLineVisible(false);
		barChart.setVerticalGridLinesVisible(false);
		barChart.setVerticalZeroLineVisible(false);
		barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
		barChart.getXAxis().setOpacity(0);
		barChart.getYAxis().setOpacity(0);
		barChart.setCategoryGap(3);
		
		
		XYChart.Series barChartData = new XYChart.Series();
		
		for (int i = 1; i < 7; i++) {
			barChartData.getData().add(new XYChart.Data(totalNumberOfTriesList[7-i]+ (longestBar * 0.05), 7-i+""));
			
		}
	
		barChart.getData().add(barChartData);
		
		statisticsResults.getChildren().addAll(statisticsPageTitle, statisticsLayout, barChart, guessDistributionLabel);
		
		barChartLabels.forEach(number -> statisticsResults.getChildren().add(number));
		yAxisLabels.forEach(axisLabel -> statisticsResults.getChildren().add(axisLabel));
		
		return statisticsResults;
	}
	
	public void addResult(int result) {
		totalResults.add(result);
	}

}
