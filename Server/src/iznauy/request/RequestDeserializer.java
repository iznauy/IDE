package iznauy.request;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by iznauy on 2017/6/7.
 */
public class RequestDeserializer implements JsonDeserializer<Request> {
    @Override
    public Request deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Request request = null;
        JsonObject targetObject = jsonElement.getAsJsonObject();
        String userName = targetObject.get("userName").getAsString();
        String password = targetObject.get("password").getAsString();
        String requestType = targetObject.get("requestType").getAsString();
        if (requestType.equals(Request.LOGIN)) {
            request = new LoginRequest(userName, password);
        } else if (requestType.equals(Request.REGISTER)) {
            request = new RegisterRequest(userName, password);
        } else if (requestType.equals(Request.EXECUTE)) {
            String rawSource = targetObject.get("rawSource").getAsString();
            String input = targetObject.get("input").getAsString();
            String exeType = targetObject.get("type").getAsString();
            request = new ExecuteRequest(userName, password, rawSource, input, exeType);
        } else if (requestType.equals(Request.DEFAULT)) {
            System.out.println("默认");
        } else if (requestType.equals(Request.NEW_FILE)) {
            String fileType = targetObject.get("fileType").getAsString();
            String fileName = targetObject.get("fileName").getAsString();
            request = new NewFileRequest(userName, password, fileName, fileType);
        } else if (requestType.equals(Request.OPEN_FILE)) {
            String fileVersion = targetObject.get("fileVersion").getAsString();
            String fileType = targetObject.get("fileType").getAsString();
            String fileName = targetObject.get("fileName").getAsString();
            request = new OpenFileRequest(userName, password, fileName, fileType, fileVersion);
        } else if (requestType.equals(Request.SAVE_FILE)) {
            String fileType = targetObject.get("fileType").getAsString();
            String fileName = targetObject.get("fileName").getAsString();
            String fileContent = targetObject.get("fileContent").getAsString();
            request = new SaveFileRequest(userName, password, fileName, fileType, fileContent);
        } else if (requestType.equals(Request.GET_FILE_LIST)) {
            String fileType = targetObject.get("fileType").getAsString();
            request = new GetFileListRequest(userName, password, fileType);
        } else if (requestType.equals(Request.GET_FILE_VERSION_LIST)) {
            String fileType = targetObject.get("fileType").getAsString();
            String fileName = targetObject.get("fileName").getAsString();
            request = new GetFileVersionListRequest(userName, password, fileName, fileType);
        } else if (requestType.equals(Request.GET_SELECTED_VERSION_LIST)) {
            String fileType = targetObject.get("fileType").getAsString();
            String fileName = targetObject.get("fileName").getAsString();
            String version = targetObject.get("version").getAsString();
            request = new GetSelectedVersionRequest(userName, password, fileName, fileType, version);
        } else if (requestType.equals(Request.DEBUG)) {
            String rawSource = targetObject.get("rawSource").getAsString();
            String input = targetObject.get("input").getAsString();
            String exeType = targetObject.get("type").getAsString();
            int count = targetObject.get("count").getAsInt();
            request = new DebugRequest(userName, password, rawSource, input, exeType, count);
        }
        return request;
    }
}
