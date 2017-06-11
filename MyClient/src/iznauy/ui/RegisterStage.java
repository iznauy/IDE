package iznauy.ui;

import iznauy.exception.InvaildRequsetException;
import iznauy.exception.NetWorkException;
import iznauy.request.RegisterRequest;
import iznauy.response.RegisterResponse;
import iznauy.response.Response;
import iznauy.utils.Config;
import iznauy.utils.NetUtils;
import iznauy.utils.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterStage extends Stage {
	
private TextField userName;
	
	private PasswordField passwordField;
	
	private PasswordField surePasswordField;
	
	private Button register;
	
	private Label wrongMessage;
	
	private Pane pane;

	public RegisterStage() {
		initPane();
		registerListener();
		this.setScene(new Scene(pane, 400, 255));
		this.setTitle("Register");
		this.initStyle(StageStyle.UTILITY);
		this.setResizable(false);
		this.setOnCloseRequest(e -> Platform.exit());
	}
	
	public void initPane() {
		
		pane = new Pane();
		
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setPrefSize(400, 55);
		anchorPane.setStyle("-fx-background-color: #00dd0d");
		Label welcomeLabel = new Label();
		welcomeLabel.setAlignment(Pos.CENTER);
		welcomeLabel.setText("Register");
		welcomeLabel.setFont(new Font(24));
		welcomeLabel.setTextFill(Color.WHITE);
		AnchorPane.setTopAnchor(welcomeLabel, 0.0);
		AnchorPane.setBottomAnchor(welcomeLabel, 0.0);
		AnchorPane.setLeftAnchor(welcomeLabel, 0.0);
		AnchorPane.setRightAnchor(welcomeLabel, 0.0);
		anchorPane.getChildren().add(welcomeLabel);
		pane.getChildren().add(anchorPane);
		
		Label user = new Label();
		user.setText("Username:");
		user.setLayoutX(30);
		user.setLayoutY(75);
		pane.getChildren().add(user);
		
		Label pwd = new Label();
		pwd.setText("Password:");
		pwd.setLayoutX(30);
		pwd.setLayoutY(120);
		pane.getChildren().add(pwd);
		
		Label confirm = new Label();
		confirm.setText("Confirm:");
		confirm.setLayoutX(30);
		confirm.setLayoutY(165);
		pane.getChildren().add(confirm);
		
		userName = new TextField();
		userName.setPrefSize(250, 30);
		userName.setPromptText("用户名");
		userName.setLayoutX(120);
		userName.setLayoutY(70);
		userName.requestFocus();
		pane.getChildren().add(userName);
		
		passwordField = new PasswordField();
		passwordField.setPromptText("密码");
		passwordField.setPrefSize(250, 30);
		passwordField.setLayoutX(120);
		passwordField.setLayoutY(115);
		pane.getChildren().add(passwordField);
		
		surePasswordField = new PasswordField();
		surePasswordField.setPromptText("确认密码");
		surePasswordField.setPrefSize(250, 30);
		surePasswordField.setLayoutX(120);
		surePasswordField.setLayoutY(160);
		pane.getChildren().add(surePasswordField);
		
		register = new Button();
		register.setText("Register");
		register.setPrefSize(80, 30);
		register.setLayoutX(260);
		register.setLayoutY(200);
		register.setTooltip(new Tooltip(register.getText()));
		pane.getChildren().add(register);
		
		wrongMessage = new Label();
		wrongMessage.setLayoutX(40);
		wrongMessage.setLayoutY(190);
		wrongMessage.setPrefSize(200, 60);
		pane.getChildren().add(wrongMessage);
		
	}
	
	private void registerListener() {
		
		register.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String uid = userName.getText().trim();
				String password = passwordField.getText().trim();
				String confirmPasswd = surePasswordField.getText().trim();
				
				if (uid == null || uid.equals("")) {
					wrongMessage.setText("用户名不得为空");
				} else if (password == null || password.equals("") || confirmPasswd == null || confirmPasswd.equals("")) {
					wrongMessage.setText("密码不得为空");
				} else if (!password.equals(confirmPasswd)){
					wrongMessage.setText("两次输入的密码不一致");
				} else {
					boolean hasSpace = false;
					for (int i = 0; i < uid.length(); i++) {
						if (uid.charAt(i) == ' ') {
							hasSpace = true;
							break;
						}
					}
					if (hasSpace) {
						wrongMessage.setText("用户名不能含有空格");
					} else {
						boolean regex = false;
						for (int i = 0; i < uid.length(); i++) {
							char charAt = uid.charAt(i);
							if (!((charAt >= 'A' && charAt <= 'Z') || (charAt >= '0' && charAt <= '9') || (charAt >= 'a' && charAt <= 'z'))) {
								regex = true;
							}
						}
						if (regex) {
							wrongMessage.setText("存在非法字符！");
						} else {
							RegisterRequest registerRequest = new RegisterRequest(uid, password);
							try {
								Response response = NetUtils.CommunicateWithServer(registerRequest);
								if (!(response instanceof RegisterResponse)) {
									throw new NetWorkException();
								} 
								RegisterResponse registerResponse = (RegisterResponse) response;
								if (registerResponse.getStatus().equals(RegisterResponse.HAS_REGISTER)) {
									wrongMessage.setText("账号已存在！");
								} else if (registerResponse.getStatus().equals(Response.UNKNOWN_REASON)){
									wrongMessage.setText("未知错误！");
								} else {
									wrongMessage.setText("注册成功！");
									Config.setUser(new User(uid, password));
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									} finally {
										RegisterStage.this.close();
										Config.getInitStage().close();
										new MainStage().show();
									}
								}
							} catch (NetWorkException | InvaildRequsetException e) {
								e.printStackTrace();
								wrongMessage.setText("网络连接失败！");
							}
						}
					}
				}
			}
		});
		
	}
	

}
