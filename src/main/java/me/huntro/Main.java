package me.huntro;

import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class Main extends Application
{
	private double x;
	private double y;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		Pane root = new Pane();
		
		configStage(stage, root);
		
		new Thread(new SlideShow(stage, root)).start();
	}
	
	private void configStage(Stage stage, Pane root)
	{
		Platform.runLater(() ->
		{
			try
			{
				URL url = new URL("https://upload.wikimedia.org/wikipedia/en/3/39/Pokeball.PNG");
				
				stage.getIcons().add(new Image(ProxyHandler.openConnection(url).getInputStream()));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		});
		
		stage.setTitle("Pokemon");
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		stage.centerOnScreen();
		
		stage.addEventHandler(KeyEvent.KEY_RELEASED, e ->
		{
			if(e.getCode() == KeyCode.ESCAPE)
			{
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
		});
		stage.setOnCloseRequest(e -> System.exit(0));
		
		stage.setScene(getScene(stage, root));
		stage.sizeToScene();
	}
	
	private Scene getScene(Stage stage, Pane root)
	{
		Scene scene = new Scene(root);
		
		scene.setCursor(Cursor.OPEN_HAND);
		scene.setFill(null);
		
		scene.setOnMousePressed(e ->
		{
			if(e.getButton() == MouseButton.PRIMARY)
			{
				x = stage.getX() - e.getScreenX();
				y = stage.getY() - e.getScreenY();
				
				scene.setCursor(Cursor.CLOSED_HAND);
			}
		});
		scene.setOnMouseDragged(e ->
		{
			if(e.getButton() == MouseButton.PRIMARY)
			{
				stage.setX(e.getScreenX() + x);
				stage.setY(e.getScreenY() + y);
			}
		});
		scene.setOnMouseReleased(e ->
		{
			if(e.getButton() == MouseButton.PRIMARY)
			{
				scene.setCursor(Cursor.OPEN_HAND);
			}
		});
		
		return scene;
	}
}
