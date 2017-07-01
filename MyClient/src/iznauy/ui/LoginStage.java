package iznauy.ui;

import iznauy.exception.InvaildRequsetException;
import iznauy.exception.NetWorkException;
import iznauy.request.LoginRequest;
import iznauy.request.Request;
import iznauy.response.LoginResponse;
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

public class LoginStage extends Stage {

	private TextField userName;

	private PasswordField passwordField;

	private Button login;

	private Button register;

	private Label wrongMessage;

	private Pane pane;

	public LoginStage() {

		initPane();
		registerListener();
		this.setScene(new Scene(pane, 400, 220));
		this.setTitle("Login");
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
		welcomeLabel.setText("Login");
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

		login = new Button();
		login.setText("Login");
		login.setPrefSize(60, 30);
		login.setLayoutX(300);
		login.setLayoutY(170);
		login.setTooltip(new Tooltip(login.getText()));
		pane.getChildren().add(login);

		register = new Button();
		register.setText("Register");
		register.setPrefSize(60, 30);
		register.setLayoutX(220);
		register.setLayoutY(170);
		register.setTooltip(new Tooltip(register.getText()));
		pane.getChildren().add(register);

		wrongMessage = new Label();
		wrongMessage.setLayoutX(40);
		wrongMessage.setLayoutY(150);
		wrongMessage.setPrefSize(200, 60);
		pane.getChildren().add(wrongMessage);

	}

	private void registerListener() {

		login.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String uid = userName.getText().trim();
				String password = passwordField.getText().trim();
				if (uid == null || uid.equals("")) {
					wrongMessage.setText("用户名不得为空");
					Config.playWarningSound();
				} else if (password == null || password.equals("")) {
					wrongMessage.setText("密码不得为空");
					Config.playWarningSound();
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
						Config.playWarningSound();
					} else {
						boolean regex = false;
						for (int i = 0; i < uid.length(); i++) {
							char charAt = uid.charAt(i);
							if (!((charAt >= 'A' && charAt <= 'Z') || (charAt >= '0' && charAt <= '9')
									|| (charAt >= 'a' && charAt <= 'z'))) {
								regex = true;
							}
						}
						if (regex) {
							wrongMessage.setText("存在非法字符！");
							Config.playWarningSound();
						} else {
							Request loginRequest = new LoginRequest(uid, password);
							try {
								Response response = NetUtils.CommunicateWithServer(loginRequest);
								if (!(response instanceof LoginResponse)) {
									throw new NetWorkException();
								}
								LoginResponse loginResponse = (LoginResponse) response;
								if (loginResponse.getStatus().equals(LoginResponse.NON_EXIST)) {
									wrongMessage.setText("账号不存在！");
									Config.playWarningSound();
								} else if (loginResponse.getStatus().equals(LoginResponse.WRONG_PASSWORD)) {
									wrongMessage.setText("密码错误！");
									Config.playWarningSound();
								} else if (loginResponse.getStatus().equals(Response.UNKNOWN_REASON)) {
									wrongMessage.setText("未知错误！");
									Config.playWarningSound();
								} else {
									wrongMessage.setText("登陆成功");
									Config.setUser(new User(uid, password));
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										e.printStackTrace();
									} finally {
										//转跳到主界面
										LoginStage.this.close();
										Config.getInitStage().close();
										new MainStage().show();
									}
								}
							} catch (NetWorkException | InvaildRequsetException e) {
								e.printStackTrace();
								wrongMessage.setText("网络连接失败！");
								Config.playWarningSound();
							}
						}
					}
				}
			}
		});

		register.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LoginStage.this.close();
				new RegisterStage().show();
			}
		});

	}

}
