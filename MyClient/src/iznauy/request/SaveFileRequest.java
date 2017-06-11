package iznauy.request;

import iznauy.utils.User;

public class SaveFileRequest extends Request {
	
	private String fileName;
	
	private String fileType;
	
	private String fileContent;

	public SaveFileRequest(String userName, String password, String fileName, String fileType, String fileContent) {
		super(userName, password);
		this.setFileName(fileName);
		this.setFileType(fileType);
		this.setFileContent(fileContent);
		this.setRequestType(Request.SAVE_FILE);
	}

	public SaveFileRequest(User user, String fileName, String fileType, String fileContent) {
		this(user.getUserName(), user.getPassword(), fileName, fileType, fileContent);
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

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

}
