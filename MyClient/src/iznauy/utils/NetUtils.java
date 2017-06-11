package iznauy.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import iznauy.exception.InvaildRequsetException;
import iznauy.exception.NetWorkException;
import iznauy.request.Request;
import iznauy.response.Response;

/**
 * 提供网络连接
 * @author iznauy
 *
 */
public abstract class NetUtils {

	private static  Socket client;
	
	private static InputStream io;
	
	private static OutputStream out;
	
	public static boolean initNet() {
		try {
			client = new Socket(Config.getAddress(), Config.getPort());
			io = client.getInputStream();
			out = client.getOutputStream();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean sendRequest(Request request) {
		String requestJson = Config.getConfigedGson().toJson(request, Request.class);
		try {
			out.write(requestJson.getBytes());
			client.shutdownOutput();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static Response getResponse() {
		ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
		byte[] buffer = new byte[Config.getBufferSize()];
 		try {
			int len = 0;
			while ((len = io.read(buffer)) != -1) {
				byteBuff.write(buffer, 0, len);
			}
			String responseJson = new String(byteBuff.toByteArray());
			return Config.getConfigedGson().fromJson(responseJson, Response.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Response CommunicateWithServer(Request request) throws NetWorkException, InvaildRequsetException {
		if (request == null) {
			throw new InvaildRequsetException();
		}
 		if (!initNet()) {
			throw new NetWorkException();
		}
		if (!sendRequest(request)) {
			throw new NetWorkException();
		}
		Response response = getResponse();
		if (response == null) {
			throw new NetWorkException();
		}
		return response;
	}

}
