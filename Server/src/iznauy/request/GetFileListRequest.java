package iznauy.request;

import iznauy.utils.User;

public class GetFileListRequest extends Request {

	private String fileType;

	public GetFileListRequest(String userName, String password, String fileType) {
		super(userName, password);
		this.setFileType(fileType);
		this.setRequestType(Request.GET_FILE_LIST);
	}

	public GetFileListRequest(User user, String fileType) {
		this(user.getUserName(), user.getPassword(), fileType);
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
