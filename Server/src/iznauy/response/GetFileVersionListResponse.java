package iznauy.response;

/**
 * Created by iznauy on 2017/6/11.
 */
public class GetFileVersionListResponse extends Response {

    private String[] versionList;

    public GetFileVersionListResponse(String[] versionList) {
        super();
        this.setVersionList(versionList);
        this.setResponseType(Response.GET_FILE_VERSION_LIST);
    }

    public GetFileVersionListResponse(String[] versionList, String status) {
        this(versionList);
        this.setStatus(status);
    }


    public String[] getVersionList() {
        return versionList;
    }

    public void setVersionList(String[] versionList) {
        this.versionList = versionList;
    }
}
