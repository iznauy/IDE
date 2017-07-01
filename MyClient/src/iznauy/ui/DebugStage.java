package iznauy.ui;

import iznauy.exception.InvaildRequsetException;
import iznauy.exception.NetWorkException;
import iznauy.request.DebugRequest;
import iznauy.request.NewFileRequest;
import iznauy.request.Request;
import iznauy.response.DebugResponse;
import iznauy.response.Response;
import iznauy.utils.Config;
import iznauy.utils.NetUtils;
import iznauy.utils.Tools;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DebugStage extends Stage {
	
	@SuppressWarnings("unused")
	private String fileName;
	
	private String fileType;
	
	private String program;
	
	private MenuBar menuBar;
	
	private Menu runMenu;
	
	private MenuItem runToEndMenuItem;
	
	private MenuItem backOneStepMenuItem;
	
	private MenuItem runOneStepMenuItem;
	
	private MenuItem backToBeginMenuItem;
	
	private int count = 0;
	
	private int maxCount;
	
	private Scene scene;
	
	private Pane mainPane;
	
	private TextArea codeTextArea;
	
	private TextArea inputArea;
	
	private TextArea outputArea;
	
	private Label inputLabel;
	
	private Label outputLabel;
	
	private Text currentCount;
	
	public DebugStage() {

	}

	public DebugStage(StageStyle style) {
		super(style);
	}
	
	private void insertStop() {
		if (count == 0) {
			codeTextArea.setText("(stop)" + program);
		} else if (count != maxCount) {
			int t = 0;
			for (int i = 0; i < maxCount; i++) {
				char p = program.charAt(i);
				if (p == '+' || p == '-'
	                    || p == '>'
	                    || p == '<'
	                    || p == ','
	                    || p == '.'
	                    || p == '['
	                    || p == ']') {
					t++;
					if (t == count) {
						codeTextArea.setText(program.substring(0, i + 1) + "(stop)" + program.substring(i + 1));
						return;
					}
				}
			}
		} else {
			codeTextArea.setText(program + "(stop)");
		}
	}
	 
	
	private void disableItemsInEnd() {
		backOneStepMenuItem.setDisable(false);
		backToBeginMenuItem.setDisable(false);
		runOneStepMenuItem.setDisable(true);
		runToEndMenuItem.setDisable(true);
	} 
	
	private void disableItemsInBegin() {
		backOneStepMenuItem.setDisable(true);
		backToBeginMenuItem.setDisable(true);
		runOneStepMenuItem.setDisable(false);
		runToEndMenuItem.setDisable(false);
	}
	
	private void enableAllItems() {
		backOneStepMenuItem.setDisable(false);
		backToBeginMenuItem.setDisable(false);
		runOneStepMenuItem.setDisable(false);
		runToEndMenuItem.setDisable(false);
	}
	
	public DebugStage(String fileName, String fileType, String rawProgram) {
		this();
		this.setTitle("Debug");
		this.fileName = fileName;
		this.fileType = fileType;
		if (fileType.equals(NewFileRequest.BRAIN_FUCK)) {
			program = Tools.processBFText(rawProgram);
		} else {
			program = Tools.processOokText(rawProgram);
		}
		maxCount = 0;
		for (int i = 0; i < program.length(); i++) {
			char p = program.charAt(i);
			if (p == '+' || p == '-'
                    || p == '>'
                    || p == '<'
                    || p == ','
                    || p == '.'
                    || p == '['
                    || p == ']') {
				maxCount++;
			}
		}
		init();
	}
	
	public void init() {
		if (program == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.show();
			this.close();
		}
		
		runMenu = new Menu("Run");
		backOneStepMenuItem = new MenuItem("back one step");
		backToBeginMenuItem = new MenuItem("back to begin");
		runOneStepMenuItem = new MenuItem("run one step");
		runToEndMenuItem = new MenuItem("run to end");
		runMenu.getItems().add(backOneStepMenuItem);
		runMenu.getItems().add(backToBeginMenuItem);
		runMenu.getItems().add(runOneStepMenuItem);
		runMenu.getItems().add(runToEndMenuItem);
		menuBar = new MenuBar();
		menuBar.getMenus().add(runMenu);
		
		currentCount = new Text("Current: " + count);
		currentCount.setLayoutX(480);
		currentCount.setLayoutY(16);
		
		codeTextArea = new TextArea();
		codeTextArea.setPrefHeight(240);
		codeTextArea.setPrefWidth(576);
		codeTextArea.setLayoutX(0);
		codeTextArea.setLayoutY(30);
		codeTextArea.setCursor(Cursor.TEXT);
		codeTextArea.setDisable(true);
		insertStop();
		
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
		outputLabel.setText("Output:");
		outputLabel.setPrefHeight(16);
		outputLabel.setLayoutX(296);
		outputLabel.setLayoutY(272);
		
		outputArea = new TextArea();
		outputArea.setDisable(true);
		outputArea.setPrefSize(280, 160);
		outputArea.setLayoutX(292);
		outputArea.setLayoutY(296);
		
		mainPane = new Pane();
		scene = new Scene(mainPane, 576, 464);
		mainPane.getChildren().add(codeTextArea);
		mainPane.getChildren().add(currentCount);
		mainPane.getChildren().add(inputArea);
		mainPane.getChildren().add(outputArea);
		mainPane.getChildren().add(inputLabel);
		mainPane.getChildren().add(outputLabel);
		mainPane.getChildren().add(menuBar);
		disableItemsInBegin();
		this.setScene(scene);
		this.setResizable(false);
		registerListener();
	}
	
	private void registerListener() {
		
		backOneStepMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				count -= 1;
				if (count == 0) {
					disableItemsInBegin();
				} else {
					enableAllItems();
				}
				insertStop();
				currentCount.setText("Current: " + count);
				String rawSource = program;
				String input = inputArea.getText();
				if (rawSource == null || rawSource.trim() == null || rawSource.trim().equals("")) {
					return;
				}
				Request request = new DebugRequest(Config.getUser(), rawSource, input, fileType, count);
				try {
					Response response = NetUtils.CommunicateWithServer(request);
					if (response instanceof DebugResponse) {
						DebugResponse debugResponse = (DebugResponse) response;
						String output = debugResponse.getOutput();
						outputArea.setText(output);
					} else {
						throw new Exception();
					}
				} catch (NetWorkException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "网络连接失败！");
					alert.setResizable(false);
					alert.show();
				} catch (InvaildRequsetException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "未知错误！");
					alert.setResizable(false);
					alert.show();
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "未知错误！");
					alert.setResizable(false);
					alert.show();
				}
			}
		});
		
		backToBeginMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				count = 0;
				insertStop();
				disableItemsInBegin();
				currentCount.setText("Current: " + count);
				outputArea.setText("");
			}
		});
		
		runOneStepMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				count += 1;
				if (count == maxCount) {
					disableItemsInEnd();
				} else {
					enableAllItems();
				}
				insertStop();
				currentCount.setText("Current: " + count);
				String rawSource = program;
				String input = inputArea.getText();
				if (rawSource == null || rawSource.trim() == null || rawSource.trim().equals("")) {
					return;
				}
				Request request = new DebugRequest(Config.getUser(), rawSource, input, fileType, count);
				try {
					Response response = NetUtils.CommunicateWithServer(request);
					if (response instanceof DebugResponse) {
						DebugResponse debugResponse = (DebugResponse) response;
						String output = debugResponse.getOutput();
						outputArea.setText(output);
					} else {
						throw new Exception();
					}
				} catch (NetWorkException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "网络连接失败！");
					alert.setResizable(false);
					alert.show();
				} catch (InvaildRequsetException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "未知错误！");
					alert.setResizable(false);
					alert.show();
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "未知错误！");
					alert.setResizable(false);
					alert.show();
				}
			}
		});
		
		runToEndMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				count = maxCount;
				disableItemsInEnd();
				insertStop();
				currentCount.setText("Current: " + count);
				String rawSource = program;
				String input = inputArea.getText();
				if (rawSource == null || rawSource.trim() == null || rawSource.trim().equals("")) {
					return;
				}
				Request request = new DebugRequest(Config.getUser(), rawSource, input, fileType, count);
				try {
					Response response = NetUtils.CommunicateWithServer(request);
					if (response instanceof DebugResponse) {
						DebugResponse debugResponse = (DebugResponse) response;
						String output = debugResponse.getOutput();
						outputArea.setText(output);
					} else {
						throw new Exception();
					}
				} catch (NetWorkException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "网络连接失败！");
					alert.setResizable(false);
					alert.show();
				} catch (InvaildRequsetException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "未知错误！");
					alert.setResizable(false);
					alert.show();
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR, "未知错误！");
					alert.setResizable(false);
					alert.show();
				}
			}
		});
		
	}
 
}
