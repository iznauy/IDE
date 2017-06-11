package iznauy.response;

public class GetFileListResponse extends Response {

    private String[] fileList;

    public GetFileListResponse(String[] fileList) {
        super();
        this.setFileList(fileList);
        this.setResponseType(Response.GET_FILE_LIST);
    }

    public GetFileListResponse(String[] fileList, String status) {
        this(fileList);
        this.setStatus(status);
    }


    public String[] getFileList() {
        return fileList;
    }

    public void setFileList(String[] fileList) {
        this.fileList = fileList;
    }
}
