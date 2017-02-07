package me.huntro;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		SlideShow slideShow = new SlideShow(stage);

		slideShow.setTitle("Pokemon");

		slideShow.start();
	}
}
