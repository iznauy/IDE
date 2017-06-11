package iznauy.request;

import iznauy.utils.User;

public class GetFileVersionListRequest extends Request {
	
	private String fileName;
	
	private String fileType;

	public GetFileVersionListRequest(String userName, String password, String fileName, String fileType) {
		super(userName, password);
		this.setFileName(fileName);
		this.setFileType(fileType);
		this.setRequestType(Request.GET_FILE_VERSION_LIST);
	}

	public GetFileVersionListRequest(User user, String fileName, String fileType) {
		this(user.getUserName(), user.getPassword(), fileName, fileType);
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

}
