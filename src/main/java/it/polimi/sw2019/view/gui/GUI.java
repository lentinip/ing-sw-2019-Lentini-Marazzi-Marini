package it.polimi.sw2019.view.gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class GUI extends Application {

    /* Attributes */

    private Stage primaryStage;

    /* Methods */

    public static void main(String[] args) {
        launch(args);
    }



    public void setBoard() throws IOException{

        //TODO implementation

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/Board.fxml"));

        Parent board = fxmlLoader.load();

        Scene scene = new Scene(board);

        primaryStage.setScene(scene);

        //TODO implementation
        BoardController boardController = fxmlLoader.getController();
        //Set the board parameters in the controller

        primaryStage.setTitle("Adrenalina");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void askMatchSetting() throws IOException{

        //TODO implementation

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/MatchSettingScreen.fxml"));

        Parent board = fxmlLoader.load();

        Scene scene = new Scene(board);

        //Creates a new window for the match setting
        Stage newWindow = new Stage();
        newWindow.setTitle("Match Settings");

        //The next line sets that this new window will lock the parent window.
        //Is impossible to interact with the parent window until this window is closed.
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);

        newWindow.setScene(scene);
        newWindow.show();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/StartScreen.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Adrenalina");
        primaryStage.setResizable(false);

        primaryStage.show();

        this.primaryStage = primaryStage;
    }
}
