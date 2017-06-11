package iznauy.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import iznauy.request.Request;
import iznauy.request.RequestDeserializer;
import iznauy.response.Response;
import iznauy.response.ResponseSerializer;

/**
 * Created by iznauy on 2017/6/7.
 */
public abstract class Config {

    private static int BufferSize = 1024;

    private static int port = 23334;

    private static Gson configedGson = null;

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Config.port = port;
    }

    public static Gson getConfigedGson() {
        if (Config.configedGson == null) {
            Config.configedGson = new GsonBuilder()
                    .registerTypeAdapter(Request.class, new RequestDeserializer())
                    .registerTypeAdapter(Response.class, new ResponseSerializer())
                    .create();
        }
        return configedGson;
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
}
