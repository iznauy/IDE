package iznauy.request;

import iznauy.utils.User;

public class GetSelectedVersionRequest extends Request {
	
	private String version;

	private String fileName;
	
	private String fileType;

	public GetSelectedVersionRequest(String userName, String password, String fileName, String fileType, String version) {
		super(userName, password);
		this.setFileName(fileName);
		this.setFileType(fileType);
		this.setRequestType(Request.GET_SELECTED_VERSION_LIST);
		this.version = version;
	}

	public GetSelectedVersionRequest(User user, String fileName, String fileType, String version) {
		this(user.getUserName(), user.getPassword(), fileName, fileType, version);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
