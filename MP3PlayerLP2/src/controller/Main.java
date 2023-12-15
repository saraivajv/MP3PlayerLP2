package controller;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Classe main
 */
public class Main extends Application {
	
	/**
	 * Inicia a cena do login
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("MP3Player");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * inicia a aplicação
	 * @param args array de argumentos
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
