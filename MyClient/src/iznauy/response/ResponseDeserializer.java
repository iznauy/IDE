package iznauy.response;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ResponseDeserializer implements JsonDeserializer<Response> {

	@Override
	public Response deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		JsonObject originalObject = arg0.getAsJsonObject();
		String responseType = originalObject.get("responseType").getAsString();
		String status = originalObject.get("status").getAsString();
		Response response = null;
		if (responseType.equals(Response.LOGIN)) {
			response = new LoginResponse(status);
		} else if (responseType.equals(Response.REGISTER)) {
			response = new RegisterResponse(status);
		} else if (responseType.equals(Response.EXECUTE)) {
			String output = originalObject.get("output").getAsString();
			response = new ExecuteResponse(output);
		} else if (responseType.equals(Response.DEFAULT)) {
			
		} else if (responseType.equals(Response.NEW_FILE)) {
			response = new NewFileResponse(status);
		} else if (responseType.equals(Response.SAVE_FILE)) {
			response = new SaveFileResponse(status);
		} else if (responseType.equals(Response.OPEN_FILE)) {
			String content = originalObject.get("content").getAsString();
			response = new OpenFileResponse(content);
		} else if (responseType.equals(Response.GET_FILE_VERSION_LIST)) {
			String[] versions = arg2.deserialize(originalObject.get("versions"), String[].class);
			response = new GetFileVersionListResponse(versions);
		} else if (responseType.equals(Response.GET_SELECTED_VERSION)) {
			String content = originalObject.get("content").getAsString();
			response = new GetSelectedVersionResponse(content);
		} else if (responseType.equals(Response.GET_FILE_LIST)) {
			String[] files = arg2.deserialize(originalObject.get("files"), String[].class);
			response = new GetFileListResponse(files);
		}
		return response;
	}

}
