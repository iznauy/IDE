package iznauy.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import iznauy.request.Request;
import iznauy.request.RequestSerializer;
import iznauy.response.Response;
import iznauy.response.ResponseDeserializer;
import javafx.stage.Stage;

/**
 * 主要的配置信息
 * @author iznauy
 *
 */
public abstract class Config {
	
	private static Stage initStage;

	private static User user;
	
	private static String address = "127.0.0.1";
	
	private static int port = 23334;
	
	private static Gson configedGson;
	
	private static int BufferSize = 1024;
	
	private static String presentFileName = null;
	
	private static String presentFileType = null;
	
	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Config.user = user;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		Config.address = address;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		Config.port = port;
	}

	public static Gson getConfigedGson() {
		if (Config.configedGson == null) {
			Config.configedGson = new GsonBuilder()
					.registerTypeAdapter(Request.class, new RequestSerializer())
					.registerTypeAdapter(Response.class, new ResponseDeserializer())
					.create();
		} 
		return Config.configedGson;
	}

	public static void setConfigedGson(Gson configedGson) {
		Config.configedGson = configedGson;
	}

	public static int getBufferSize() {
		return BufferSize;
	}

	public static void setBufferSize(int bufferSize) {
		BufferSize = bufferSize;
	}

	public static String getPresentFileName() {
		return presentFileName;
	}

	public static void setPresentFileName(String presentFileName) {
		Config.presentFileName = presentFileName;
	}

	public static String getPresentFileType() {
		return presentFileType;
	}

	public static void setPresentFileType(String presentFileType) {
		Config.presentFileType = presentFileType;
	}

	public static Stage getInitStage() {
		return initStage;
	}

	public static void setInitStage(Stage initStage) {
		Config.initStage = initStage;
	}

}
