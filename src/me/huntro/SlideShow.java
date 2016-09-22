package me.huntro;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SlideShow implements Runnable {
	
	public static final long INTERVAL = 2000L;
	public static final BorderWidths BORDER_WIDTH = new BorderWidths(50);
	public static final CornerRadii RADII = new CornerRadii(50);
	
	private Stage stage;
	private Pane root;
	
	public SlideShow(Stage stage, Pane root) {
		
		this.stage = stage;
		this.root = root;
	}
	
	@Override
	public void run() {
		
		ImageHandler imgHandler = new ImageHandler();
		ImageView imgView = new ImageView();
		
		imgView.setSmooth(true);
		root.getChildren().add(imgView);
		
		while(true) {
			Image img = imgHandler.getNextImage();
			imgView.setImage(img);
			
			Platform.runLater(() -> {
				root.setBorder(new Border(new BorderStroke(img.getAverageColor(), BorderStrokeStyle.SOLID, RADII, BORDER_WIDTH)));
				
				if(!stage.isShowing()) {
					stage.show();
				}
				
				stage.setWidth(img.getWidth());
				stage.setHeight(img.getHeight());
				
			});
			
			try {
				Thread.sleep(INTERVAL);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}