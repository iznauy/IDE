package iznauy.request;

import iznauy.utils.User;

public class OpenFileRequest extends Request {

	private String fileName;
	
	private String fileType;
	
	private String fileVersion = Request.DEFAULT;

	public OpenFileRequest(String userName, String password, String fileName, String fileType) {
		super(userName, password);
		this.setFileName(fileName);
		this.setFileType(fileType);
		this.setRequestType(Request.OPEN_FILE);
	}

	public OpenFileRequest(User user, String fileName, String fileType) {
		this(user.getUserName(), user.getPassword(), fileName, fileType);
	}
	
	public OpenFileRequest(String userName, String password, String fileName, String fileType, String fileVersion) {
		super(userName, password);
		this.setFileName(fileName);
		this.setFileType(fileType);
		this.setFileVersion(fileVersion);
		this.setRequestType(Request.OPEN_FILE);
	}

	public OpenFileRequest(User user, String fileName, String fileType, String fileVersion) {
		this(user.getUserName(), user.getPassword(), fileName, fileType, fileVersion);
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

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

}