package iznauy.ui;

import java.util.ArrayList;
import java.util.Optional;

import iznauy.exception.InvaildRequsetException;
import iznauy.exception.NetWorkException;
import iznauy.request.ExecuteRequest;
import iznauy.request.GetFileListRequest;
import iznauy.request.GetFileVersionListRequest;
import iznauy.request.GetSelectedVersionRequest;
import iznauy.request.NewFileRequest;
import iznauy.request.OpenFileRequest;
import iznauy.request.Request;
import iznauy.request.SaveFileRequest;
import iznauy.response.ExecuteResponse;
import iznauy.response.GetFileListResponse;
import iznauy.response.GetFileVersionListResponse;
import iznauy.response.GetSelectedVersionResponse;
import iznauy.response.NewFileResponse;
import iznauy.response.OpenFileResponse;
import iznauy.response.Response;
import iznauy.response.SaveFileResponse;
import iznauy.utils.Config;
import iznauy.utils.NetUtils;
import iznauy.utils.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainStage extends Stage {
	
	private	MenuBar menuBar;
	
	private Menu fileMenu;
	
	private Menu runMenu;
	
	private Menu versionMenu;
	
	private Menu testMenu;
	
	private MenuItem newFile;
	
	private MenuItem saveFile;
	
	private MenuItem exitFile;
	
	private MenuItem openFile;
	
	private MenuItem execute;
	
	private MenuItem versionItem;
	
	private MenuItem undoItem;
	
	private MenuItem redoItem;
	
	private MenuItem debugItem;
	
	private MenuItem unitTestItem;
	
	private Scene scene;
	
	private Pane mainPane;
	
	private TextArea codeTextArea;
	
	private TextArea inputArea;
	
	private TextArea outputArea;
	
	private Text userName;
	
	private Label inputLabel;
	
	private Label outputLabel;
	
	private String fileType = ExecuteRequest.BRAIN_FUCK;
	
	private ArrayList<String> bufferVersions = new ArrayList<>();
	
	private ArrayList<String> redoBuffer = new ArrayList<>();
	
	private Stage debugStage = null;
	
	private Stage unitTestStage = null;
	
	private String buffer = null;
	
	public MainStage() {
		init();
		registerListeners();
		disableMainPane();
	}
	
	private void init() { 
		fileMenu = new Menu("File");
		runMenu = new Menu("Run");
		versionMenu = new Menu("Version");
		testMenu = new Menu("Test");
		newFile = new MenuItem("New");
		saveFile = new MenuItem("Save");
		exitFile = new MenuItem("Exit");
		openFile = new MenuItem("Open");
		execute = new MenuItem("Execute");
		undoItem = new MenuItem("Undo");
		redoItem = new MenuItem("Redo");
		debugItem = new MenuItem("Debug");
		unitTestItem = new MenuItem("Unit Test");
		fileMenu.getItems().add(newFile);
		fileMenu.getItems().add(openFile);
		fileMenu.getItems().add(saveFile);
		fileMenu.getItems().add(exitFile);
		fileMenu.getItems().add(undoItem);
		fileMenu.getItems().add(redoItem);
		runMenu.getItems().add(execute);
		testMenu.getItems().add(debugItem);
		testMenu.getItems().add(unitTestItem);
		versionItem = new MenuItem("version");
		versionMenu.getItems().add(versionItem);
		menuBar = new MenuBar(fileMenu, runMenu, versionMenu, testMenu);
		
		codeTextArea = new TextArea();
		codeTextArea.setPrefHeight(300);
		codeTextArea.setPrefWidth(720);
		codeTextArea.setLayoutX(0);
		codeTextArea.setLayoutY(30);
		codeTextArea.setCursor(Cursor.TEXT);
		
		userName = new Text();
		if (Config.getUser() == null) {
			userName.setText("尚未登录！");
		} else {
			userName.setText("Hello, " + Config.getUser().getUserName());
		}
		userName.setLayoutX(600);
		userName.setLayoutY(20);
		
		inputLabel = new Label();
		inputLabel.setText("Input:");
		inputLabel.setPrefHeight(20);
		inputLabel.setLayoutX(10);
		inputLabel.setLayoutY(340);
		
		inputArea = new TextArea();
		inputArea.setPrefSize(350, 200);
		inputArea.setLayoutX(5);
		inputArea.setLayoutY(370);
		inputArea.setCursor(Cursor.TEXT);
		
		outputLabel = new Label();
		outputLabel.setText("Output:");
		outputLabel.setPrefHeight(20);
		outputLabel.setLayoutX(370);
		outputLabel.setLayoutY(340);
		
		outputArea = new TextArea();
		outputArea.setDisable(true);
		outputArea.setPrefSize(350, 200);
		outputArea.setLayoutX(365);
		outputArea.setLayoutY(370);
		
		mainPane = new Pane();
		scene = new Scene(mainPane, 720, 580);
		mainPane.getChildren().add(menuBar);
		mainPane.getChildren().add(codeTextArea);
		mainPane.getChildren().add(userName);
		mainPane.getChildren().add(inputArea);
		mainPane.getChildren().add(outputArea);
		mainPane.getChildren().add(inputLabel);
		mainPane.getChildren().add(outputLabel);
		this.setScene(scene);
		this.setTitle("IDE");
		this.setResizable(false);
		
	}
	
	private void registerListeners() {
		
		execute.setOnAction(new EventHandler<ActionEvent>() {
			
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
		
		newFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				TextInputDialog textInputDialog = new TextInputDialog();
				textInputDialog.setHeaderText("创建一个新的文件");
				TextField nameTextField = textInputDialog.getEditor();
				nameTextField.setPromptText("输入文件名");
				textInputDialog.setTitle("New File");
				
				DialogPane dialogPane = textInputDialog.getDialogPane();
				ToggleGroup fileTypeGroup = new ToggleGroup();
				RadioButton ookButton = new RadioButton();
				RadioButton brainfuckButton = new RadioButton();
				ookButton.setLayoutX(20);
				ookButton.setLayoutY(100);
				
				brainfuckButton.setLayoutX(200);
				brainfuckButton.setLayoutY(100);
				brainfuckButton.setSelected(true);

				Text ookText = new Text("OoK");
				Text brainfuckText = new Text("BF");
				ookText.setLayoutX(50);
				ookText.setLayoutY(105);
				brainfuckText.setLayoutX(230);
				brainfuckText.setLayoutY(105);
				fileTypeGroup.getToggles().add(brainfuckButton);
				fileTypeGroup.getToggles().add(ookButton);
				dialogPane.getChildren().add(brainfuckButton);
				dialogPane.getChildren().add(ookButton);
				dialogPane.getChildren().add(ookText);
				dialogPane.getChildren().add(brainfuckText);
				
				dialogPane.setPrefSize(400, 270);

				Optional<String> result = textInputDialog.showAndWait();
				
				if (result.isPresent()) {
					if (nameTextField.getText() == null || nameTextField.getText().equals("")
							|| nameTextField.getText().trim().equals("")) {
						new Alert(AlertType.ERROR, "文件名不得为空！").show();
					} else {
						boolean unexpectedChar = false;
						for (int i = 0; i < nameTextField.getText().length(); i++) {
							char temp = nameTextField.getText().charAt(i);
							if (!((temp <= 'Z' && temp >= 'A') || (temp <= 'z' && temp >= 'a') || (temp >= '0' && temp <= '9'))) {
								unexpectedChar = true;
								break;
							}
						}
						if (unexpectedChar) {
							new Alert(AlertType.ERROR, "文件名含有不合法字符！").show();
						} else {
							Request request = null;
							if (brainfuckButton.isSelected()) {
								NewFileRequest newFileRequest = new NewFileRequest(Config.getUser(), nameTextField.getText().trim(), NewFileRequest.BRAIN_FUCK);
								request = newFileRequest;
							} else {
								NewFileRequest newFileRequest = new NewFileRequest(Config.getUser(), nameTextField.getText().trim(), NewFileRequest.OOK);
								request = newFileRequest;
							}
							try {
								Response response = NetUtils.CommunicateWithServer(request);
								if (!(response instanceof NewFileResponse)) {
									throw new NetWorkException();
								}
								NewFileResponse newFileResponse = (NewFileResponse) response;
								if (newFileResponse.getStatus().equals(NewFileResponse.EXIST)) {
									new Alert(AlertType.ERROR, "文件已存在！").show();
								} else if (newFileResponse.getStatus().equals(NewFileResponse.FAIL)) {
									new Alert(AlertType.ERROR, "服务走丢了").show();
								} else {
									new Alert(AlertType.INFORMATION, "文件创建成功！").show();
									disableMainPane();
									enableMainPane();
									Config.setPresentFileName(nameTextField.getText().trim());
									redoBuffer.clear();
									bufferVersions.clear();
									if (brainfuckButton.isSelected()) {
										Config.setPresentFileType(NewFileRequest.BRAIN_FUCK);
									} else {
										Config.setPresentFileType(NewFileRequest.OOK);
									}
								}
							} catch (NetWorkException | InvaildRequsetException e) {
								e.printStackTrace();
								new Alert(AlertType.ERROR, "网络连接失败！").show();
							}
						}
 					}
				} 
				
			}
		});
		
		exitFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				disableMainPane();
				Config.setPresentFileName(null);
				Config.setPresentFileType(null);
				
			}
		});
		
		saveFile.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String fileName = Config.getPresentFileName();
				String fileType = Config.getPresentFileType();
				String fileContent = codeTextArea.getText();
				User user = Config.getUser();
				SaveFileRequest saveFileRequest = new SaveFileRequest(user, fileName, fileType, fileContent);
				try {
					Response response = NetUtils.CommunicateWithServer(saveFileRequest);
					if (!(response instanceof SaveFileResponse)) {
						throw new NetWorkException();
					}
					SaveFileResponse saveFileResponse = (SaveFileResponse) response;
					if (saveFileResponse.getStatus().equals(SaveFileResponse.FAIL)) {
						new Alert(AlertType.ERROR, "服务器走丢了").show();
					} else {
						new Alert(AlertType.INFORMATION, "保存成功！").show();
					}
				} catch (NetWorkException | InvaildRequsetException e) {
					e.printStackTrace();
					new Alert(AlertType.ERROR, "网络连接失败！").show();
				}
			}
		});
		
		openFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("BF", "BF", "Ook");
				choiceDialog.setTitle("选择");
				choiceDialog.setHeaderText("选择文件类型");
				Optional<String> optional = choiceDialog.showAndWait();
				
				if (optional.isPresent()) {
					String selectedType = choiceDialog.getResult();
					Request askListRequest = null;
					if (selectedType.equals("BF")) {
						askListRequest = new GetFileListRequest(Config.getUser(), GetFileListRequest.BRAIN_FUCK);
					} else if (selectedType.equals("Ook")) {
						askListRequest = new GetFileListRequest(Config.getUser(), GetFileListRequest.OOK);
					}
					try {
						Response askListResponse = NetUtils.CommunicateWithServer(askListRequest);
						if (!(askListResponse instanceof GetFileListResponse)) {
							throw new NetWorkException();
						}
						GetFileListResponse getFileListResponse = (GetFileListResponse) askListResponse;
						if (getFileListResponse.getStatus().equals(Response.UNKNOWN_REASON)) {
							throw new NetWorkException();
						}
						String[] files = getFileListResponse.getFileList();
						if (files == null) {
							new Alert(AlertType.ERROR, "您尚未创建文件！").show();
							return;
						}

						ChoiceDialog<String> filesChoiceDialog = new ChoiceDialog<>(files[0], files);
						choiceDialog.setTitle("选择");
						choiceDialog.setHeaderText("选择文件");
						
						Optional<String> fileOptional = filesChoiceDialog.showAndWait();
						if (fileOptional.isPresent()) {
							String selectedFile = filesChoiceDialog.getSelectedItem();
							Request askFile = new OpenFileRequest(Config.getUser(), selectedFile, (((GetFileListRequest)askListRequest)).getFileType());
							Response askFileResponse = NetUtils.CommunicateWithServer(askFile);
							if (!(askFileResponse instanceof OpenFileResponse)) {
								throw new NetWorkException();
							}
							OpenFileResponse openFileResponse = (OpenFileResponse) askFileResponse;
							String content = openFileResponse.getContent();
							enableMainPane();
							codeTextArea.setText(content);
							Config.setPresentFileName(selectedFile);
							redoBuffer.clear();
							bufferVersions.clear();
							if (selectedType.equals("BF")) {
								Config.setPresentFileType(NewFileRequest.BRAIN_FUCK);
							} else if (selectedType.equals("Ook")) {
								Config.setPresentFileType(NewFileRequest.OOK);
							}
						}
					} catch (NetWorkException | InvaildRequsetException e) {
						e.printStackTrace();
						new Alert(AlertType.ERROR, "网络连接失败").show();
					}
				}
			}
		});
		
		versionItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (Config.getPresentFileName() == null) {
					new Alert(AlertType.ERROR, "当前文件为空！").show();
				} else {
					GetFileVersionListRequest getFileVersionListRequest = new GetFileVersionListRequest(Config.getUser(), Config.getPresentFileName(), Config.getPresentFileType());
					try {
						Response response = NetUtils.CommunicateWithServer(getFileVersionListRequest);
						if (!(response instanceof GetFileVersionListResponse)) {
							throw new NetWorkException();
						}
						GetFileVersionListResponse getFileVersionListResponse = (GetFileVersionListResponse) response;
						String[] versions = getFileVersionListResponse.getVersionList();
						if (getFileVersionListResponse.getStatus().equals(Response.UNKNOWN_REASON) || versions == null) {
							throw new NetWorkException();
						}
						
						ChoiceDialog<String> choiceVersionDialog = new ChoiceDialog<>(versions[0], versions);
						choiceVersionDialog.setTitle("选择");
						choiceVersionDialog.setHeaderText("选择版本");
						
						Optional<String> result = choiceVersionDialog.showAndWait();
						
						if (result.isPresent()) {
							String selectedVersion = choiceVersionDialog.getSelectedItem();
							Request request = new GetSelectedVersionRequest(Config.getUser(), Config.getPresentFileName(), Config.getPresentFileType(), selectedVersion);
							response = NetUtils.CommunicateWithServer(request);
							if (!(response instanceof GetSelectedVersionResponse)) {
								throw new NetWorkException();
							}
							GetSelectedVersionResponse getSelectedVersionResponse = (GetSelectedVersionResponse) response;
							String content = getSelectedVersionResponse.getContent();
							codeTextArea.setText(content);
						}
						
					} catch (NetWorkException | InvaildRequsetException e) {
						e.printStackTrace();
						new Alert(AlertType.ERROR, "网络连接失败").show();
					}
				}
			}
		});
		
		undoItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				redoItem.setDisable(false);
				String temp = codeTextArea.getText();
				buffer = bufferVersions.remove(bufferVersions.size() - 1);
				if (bufferVersions.size() == 0) {
					undoItem.setDisable(true);
				}
				redoBuffer.add(temp);
				if (redoBuffer.size() > 10) {
					redoBuffer.remove(0);
				}
				codeTextArea.setText(buffer);
			}
		});
		
		redoItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				undoItem.setDisable(false);
				String temp = codeTextArea.getText();
				buffer = redoBuffer.remove(redoBuffer.size() - 1);
				if (redoBuffer.size() == 0) {
					redoItem.setDisable(true);
				}
				bufferVersions.add(temp);
				if (bufferVersions.size() > 10) {
					bufferVersions.remove(0);
				}
				codeTextArea.setText(buffer);
			}
		});
		
		codeTextArea.setOnKeyPressed(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				String temp = codeTextArea.getText();
				if (bufferVersions.size() >= 10) {
					bufferVersions.remove(0);
				}
				bufferVersions.add(temp);
				undoItem.setDisable(false);
				redoItem.setDisable(true);
				redoBuffer.clear();
			}
		});
		
		debugItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Config.getPresentFileType().equals(NewFileRequest.BRAIN_FUCK)) {
					if (debugStage != null) {
						debugStage.close();
					}
					debugStage = new DebugStage(Config.getPresentFileName(), Config.getPresentFileType(), codeTextArea.getText());
					debugStage.show();
				} else {
					new Alert(AlertType.ERROR, "目前只支持BF！").show();
				}
			}
		});
		
		unitTestItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Config.getPresentFileType().equals(NewFileRequest.BRAIN_FUCK)) {
					if (unitTestStage != null) {
						unitTestStage.close();
					}
					unitTestStage = new UnitTestStage(Config.getPresentFileName(), Config.getPresentFileType(), codeTextArea.getText());
					unitTestStage.show();
				} else {
					new Alert(AlertType.ERROR, "目前只支持BF！").show();
				}
			}
		});
		
	}
	
	private void disableMainPane() {
		redoItem.setDisable(true);
		undoItem.setDisable(true);
		outputArea.setDisable(true);
		saveFile.setDisable(true);
		execute.setDisable(true);
		exitFile.setDisable(true);
		inputArea.setDisable(true);
		codeTextArea.setDisable(true);
		unitTestItem.setDisable(true);
		debugItem.setDisable(true);
		inputArea.setText("");
		codeTextArea.setText("");
		outputArea.setText("");
		redoBuffer.clear();
		bufferVersions.clear();
	}
	
	private void enableMainPane() {
		outputArea.setDisable(false);
		saveFile.setDisable(false);
		execute.setDisable(false);
		exitFile.setDisable(false);
		inputArea.setDisable(false);
		codeTextArea.setDisable(false);
		unitTestItem.setDisable(false);
		debugItem.setDisable(false);
		redoBuffer.clear();
		bufferVersions.clear();
	}

}
