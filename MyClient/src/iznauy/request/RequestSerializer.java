package iznauy.request;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RequestSerializer implements JsonSerializer<Request> {

	@Override
	public JsonElement serialize(Request request, Type arg1, JsonSerializationContext arg2) {
		JsonObject targetObject = new JsonObject();
		targetObject.addProperty("userName", request.getUserName());
		targetObject.addProperty("password", request.getPassword());
		targetObject.addProperty("requestType", request.getRequestType());
		if (request instanceof ExecuteRequest) {
			ExecuteRequest executeRequest = (ExecuteRequest) request;
			targetObject.addProperty("rawSource", executeRequest.getRawSource());
			targetObject.addProperty("input", executeRequest.getInput());
			targetObject.addProperty("type", executeRequest.getType());
		} else if (request instanceof NewFileRequest) {
			NewFileRequest newFileRequest = (NewFileRequest) request;
			targetObject.addProperty("fileType", newFileRequest.getFileType());
			targetObject.addProperty("fileName", newFileRequest.getFileName());
		} else if (request instanceof SaveFileRequest) {
			SaveFileRequest saveFileRequest = (SaveFileRequest) request;
			targetObject.addProperty("fileType", saveFileRequest.getFileType());
			targetObject.addProperty("fileName", saveFileRequest.getFileName());
			targetObject.addProperty("fileContent", saveFileRequest.getFileContent());
		} else if (request instanceof OpenFileRequest) {
			OpenFileRequest openFileRequest = (OpenFileRequest) request;
			targetObject.addProperty("fileVersion", openFileRequest.getFileVersion());
			targetObject.addProperty("fileType", openFileRequest.getFileType());
			targetObject.addProperty("fileName", openFileRequest.getFileName());
		} else if (request instanceof GetFileListRequest) {
			GetFileListRequest getFileListRequest = (GetFileListRequest) request;
			targetObject.addProperty("fileType", getFileListRequest.getFileType());
		} else if (request instanceof GetFileVersionListRequest) {
			GetFileVersionListRequest getFileVersionListRequest = (GetFileVersionListRequest) request;
			targetObject.addProperty("fileName", getFileVersionListRequest.getFileName());
			targetObject.addProperty("fileType", getFileVersionListRequest.getFileType());
		} else if (request instanceof GetSelectedVersionRequest) {
			GetSelectedVersionRequest getSelectedVersionRequest = (GetSelectedVersionRequest) request;
			targetObject.addProperty("fileName", getSelectedVersionRequest.getFileName());
			targetObject.addProperty("fileType", getSelectedVersionRequest.getFileType());
			targetObject.addProperty("version", getSelectedVersionRequest.getVersion());
		}
		return targetObject;
	}

}
