package iznauy.ui;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InitStage extends Stage {

	public InitStage() throws Exception {

		Pane pane = new Pane();
		ImageView backGround = new ImageView(new Image(getClass().getResource("init.jpg").toString()));
		pane.getChildren().add(backGround);

		ProgressBar progressBar = new ProgressBar();
		progressBar.setLayoutX(400);
		progressBar.setLayoutY(350);
		pane.getChildren().add(progressBar);

		this.setScene(new Scene(pane, 600, 400));
		this.initStyle(StageStyle.TRANSPARENT);
	}

}
