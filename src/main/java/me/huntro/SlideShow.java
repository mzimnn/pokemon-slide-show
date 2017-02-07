package me.huntro;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class SlideShow
{
	public static final long INTERVAL = 2000L;
	public static final BorderWidths BORDER_WIDTH = new BorderWidths(50);
	public static final CornerRadii RADII = new CornerRadii(50);

	private Stage stage;
	private Pane root = new Pane();

	private double x;
	private double y;


	public SlideShow(Stage stage)
	{
		this.stage = stage;

		stage.getIcons().add(Cache.getIcon());

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

		stage.setScene(getScene());
		stage.sizeToScene();
	}

	public void start()
	{
		new Thread(() ->
		{
			ImageHandler imgHandler = new ImageHandler();
			ImageView imgView = new ImageView();

			imgView.setSmooth(true);
			root.getChildren().add(imgView);

			while(true)
			{
				Image img = imgHandler.getNextImage();
				imgView.setImage(img);

				Platform.runLater(() ->
				{
					root.setBorder(new Border(new BorderStroke(img.getAverageColor(), BorderStrokeStyle.SOLID, RADII, BORDER_WIDTH)));

					if(!stage.isShowing())
					{
						stage.show();
					}

					stage.setWidth(img.getWidth());
					stage.setHeight(img.getHeight());
				});

				try
				{
					Thread.sleep(INTERVAL);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Scene getScene()
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
