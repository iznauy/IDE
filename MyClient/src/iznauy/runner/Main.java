package iznauy.runner;

import iznauy.ui.InitStage;
import iznauy.ui.LoginStage;
import iznauy.utils.Config;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = new InitStage();
		Config.setInitStage(stage);
		stage.show();

		Task<Void> delayTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				Thread.sleep(2000);
				return null;
			}

		};
		delayTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				new LoginStage().show();
				
			}
		});
		new Thread(delayTask).start();
	}

}
