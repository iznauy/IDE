package iznauy.ui;

import iznauy.exception.InvaildRequsetException;
import iznauy.exception.NetWorkException;
import iznauy.request.ExecuteRequest;
import iznauy.request.Request;
import iznauy.response.ExecuteResponse;
import iznauy.response.Response;
import iznauy.utils.Config;
import iznauy.utils.NetUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UnitTestStage extends Stage {
	
	@SuppressWarnings("unused")
	private String fileName;
	
	private String fileType;
	
	private String program;
	
	private MenuBar menuBar;
	
	private MenuItem executeMenuItem;
	
	private Menu executeMenu;
	
	private Scene scene;
	
	private Pane mainPane;
	
	private TextArea codeTextArea;
	
	private TextArea inputArea;
	
	private TextArea outputArea;
	
	private Label inputLabel;
	
	private Label outputLabel;
	

	public UnitTestStage() {
		
	}

	public UnitTestStage(StageStyle style) {
		super(style);
	}
	
	public UnitTestStage(String fileName, String fileType, String rawProgram) {
		this();
		this.fileName = fileName;
		this.fileType = fileType;
		program = rawProgram;
		init();
	}
	
	public void init() {
		
		executeMenuItem = new MenuItem("Execute Test");
		executeMenu = new Menu("Execute");
		executeMenu.getItems().add(executeMenuItem);
		menuBar = new MenuBar();
		menuBar.getMenus().add(executeMenu);
		
		codeTextArea = new TextArea();
		codeTextArea.setPrefHeight(240);
		codeTextArea.setPrefWidth(576);
		codeTextArea.setLayoutX(0);
		codeTextArea.setLayoutY(30);
		codeTextArea.setCursor(Cursor.TEXT);
		codeTextArea.setDisable(true);
		codeTextArea.setText(program);
		
		inputLabel = new Label();
		inputLabel.setText("Input:");
		inputLabel.setPrefHeight(16);
		inputLabel.setLayoutX(8);
		inputLabel.setLayoutY(272);
		
		inputArea = new TextArea();
		inputArea.setPrefSize(280, 160);
		inputArea.setLayoutX(4);
		inputArea.setLayoutY(296);
		inputArea.setCursor(Cursor.TEXT);
		
		outputLabel = new Label();
		outputLabel.setText("Expected Output:");
		outputLabel.setPrefHeight(16);
		outputLabel.setLayoutX(296);
		outputLabel.setLayoutY(272);
		
		outputArea = new TextArea();
		outputArea.setPrefSize(280, 160);
		outputArea.setLayoutX(292);
		outputArea.setLayoutY(296);
		
		mainPane = new Pane();
		scene = new Scene(mainPane, 576, 464);
		mainPane.getChildren().add(codeTextArea);
		mainPane.getChildren().add(inputArea);
		mainPane.getChildren().add(outputArea);
		mainPane.getChildren().add(inputLabel);
		mainPane.getChildren().add(outputLabel);
		mainPane.getChildren().add(menuBar);
		this.setScene(scene);
		this.setResizable(false);
		registerListener();
	}
	
	private void registerListener() {
		executeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String rawSource = codeTextArea.getText();
				String input = inputArea.getText();
				if (rawSource == null || rawSource.trim() == null || rawSource.trim().equals("")) {
					return;
				}
				Request request = new ExecuteRequest(Config.getUser(), rawSource, input, fileType);
				try {
					Response response = NetUtils.CommunicateWithServer(request);
					if (response instanceof ExecuteResponse) {
						ExecuteResponse executeResponse = (ExecuteResponse) response;
						String output = executeResponse.getOutput();
						if (output.equals(outputArea.getText())) {
							new Alert(AlertType.WARNING, "通过测试！").show();
						} else {
							new Alert(AlertType.ERROR, "失败！实际输出：" + output).show();
							Config.playWarningSound();
						}
					} else {
						throw new Exception();
					}
				} catch (NetWorkException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "网络连接失败！");
					alert.setResizable(false);
					alert.show();
					Config.playWarningSound();
				} catch (InvaildRequsetException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "未知错误！");
					alert.setResizable(false);
					alert.show();
					Config.playWarningSound();
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "未知错误！");
					alert.setResizable(false);
					alert.show();
					Config.playWarningSound();
				}
			}
		});
	}
	

}
