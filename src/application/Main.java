package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Main extends Application {
	
	public static Main mainApp;
	
	

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
			Parent root = loader.load();
			RegisterScreenController registerController = loader.getController();
			registerController.setMainApp(this);
			Scene scene = new Scene(root, 1100, 550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showTradeScreen() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("TradeScreen.fxml"));
	        Parent root = loader.load();
	        TradeScreenController tradeController = loader.getController();
	        tradeController.setMainApp(this);
	        Scene scene = new Scene(root, 1100, 550);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        Stage primaryStage = new Stage();
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	

	
	public static void main(String[] args) {
		launch(args);
	}
}
