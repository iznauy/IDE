package iznauy.utils;

import iznauy.request.Request;
import iznauy.response.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by iznauy on 2017/6/7.
 */
public abstract class NetUtils {

    public static boolean sendResponse(Socket client, Response response) {
        String responseJson = Config.getConfigedGson().toJson(response, Response.class);
        try {
            OutputStream out = client.getOutputStream();
            out.write(responseJson.getBytes());
            client.shutdownOutput();
            System.out.println("发送回复成功");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Request getRequest(Socket client) {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        byte[] buffer = new byte[Config.getBufferSize()];
        try {
            InputStream io = client.getInputStream();
            int len = 0;
            while ((len = io.read(buffer)) != -1) {
                byteBuff.write(buffer, 0, len);
            }
            String requestJson = new String(byteBuff.toByteArray());
            System.out.println("in getRequest:");
            System.out.println(requestJson);
            return Config.getConfigedGson().fromJson(requestJson, Request.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
