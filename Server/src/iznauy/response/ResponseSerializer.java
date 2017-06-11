package iznauy.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by iznauy on 2017/6/7.
 */
public class ResponseSerializer implements JsonSerializer<Response> {
    @Override
    public JsonElement serialize(Response response, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject targetObject = new JsonObject();
        String responseType = response.getResponseType();
        String status = response.getStatus();
        targetObject.addProperty("status", status);
        targetObject.addProperty("responseType", responseType);
        if (responseType.equals(Response.LOGIN)) {
            //need to do nothing
        } else if (responseType.equals(Response.REGISTER)) {
            //need to do nothing
        } else if (responseType.equals(Response.EXECUTE)) {
            ExecuteResponse executeResponse = (ExecuteResponse) response;
            targetObject.addProperty("output", executeResponse.getOutput());
        } else if (responseType.equals(Response.DEFAULT)) {
            //need to do nothing
        } else if (responseType.equals(Response.NEW_FILE)) {
            //need to do nothing
        } else if (responseType.equals(Response.SAVE_FILE)) {
            //need to do nothing
        } else if (responseType.equals(Response.GET_FILE_LIST)) {
            GetFileListResponse getFileListResponse = (GetFileListResponse) response;
            String[] files = getFileListResponse.getFileList();
            JsonElement filesJson = jsonSerializationContext.serialize(files, String[].class);
            targetObject.add("files", filesJson);
        } else if (responseType.equals(Response.OPEN_FILE)) {
            OpenFileResponse openFileResponse = (OpenFileResponse) response;
            targetObject.addProperty("content", openFileResponse.getContent());
        } else if (responseType.equals(Response.GET_FILE_VERSION_LIST)) {
            GetFileVersionListResponse getFileVersionListResponse = (GetFileVersionListResponse) response;
            String[] versions = getFileVersionListResponse.getVersionList();
            JsonElement versionsJson = jsonSerializationContext.serialize(versions, String[].class);
            targetObject.add("versions", versionsJson);
        } else if (responseType.equals(Response.GET_SELECTED_VERSION)) {
            GetSelectedVersionResponse getSelectedVersionResponse = (GetSelectedVersionResponse) response;
            targetObject.addProperty("content", getSelectedVersionResponse.getContent());
        }
        return targetObject;
    }
}
