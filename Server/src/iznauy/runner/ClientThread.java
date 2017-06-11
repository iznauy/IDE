package iznauy.runner;

import iznauy.request.*;
import iznauy.response.*;

import iznauy.utils.*;

import java.io.*;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by iznauy on 2017/6/7.
 */
public class ClientThread implements Runnable {

    private Request request = null;

    private Socket client = null;

    public ClientThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        request = NetUtils.getRequest(client);
        if (request instanceof LoginRequest) {
            handleLoginRequest();
        } else if (request instanceof RegisterRequest) {
            handleRegisterRequest();
        } else if (request instanceof ExecuteRequest) {
            handleExecuteRequest();
        } else if (request instanceof NewFileRequest) {
            handleNewFileRequest();
        } else if (request instanceof SaveFileRequest) {
            handleSaveFileRequest();
        } else if (request instanceof OpenFileRequest) {
            handleOpenFileRequest();
        } else if (request instanceof GetFileListRequest) {
            handleGetFileListRequest();
        } else if (request instanceof GetFileVersionListRequest) {
            handleGetFileVersionListRequest();
        } else if (request instanceof GetSelectedVersionRequest) {
            handleGetSelectedVersionRequest();
        }
    }

    private void handleGetSelectedVersionRequest() {
        GetSelectedVersionRequest getSelectedVersionRequest = (GetSelectedVersionRequest) request;
        StringBuffer stringBuffer = new StringBuffer();
        String userName = getSelectedVersionRequest.getUserName();
        String fileType = getSelectedVersionRequest.getFileType();
        String fileVersion = getSelectedVersionRequest.getVersion();
        String fileName = getSelectedVersionRequest.getFileName();
        String path = null;
        if (fileType.equals(NewFileRequest.BRAIN_FUCK)) {
            path = userName + "/bf/" + fileName + "/" + fileVersion + ".txt";
        } else if (fileType.equals(NewFileRequest.OOK)) {
            path = userName + "/ook/" + fileName + "/" + fileVersion + ".txt";
        }
        File file = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuffer.append(temp);
            }
            NetUtils.sendResponse(client, new GetSelectedVersionResponse(stringBuffer.toString(), Response.SUCCESS));
            return;
        } catch (IOException e) {
            e.printStackTrace();
            NetUtils.sendResponse(client, new GetSelectedVersionResponse(null, Response.UNKNOWN_REASON));
            return;
        }
    }

    private void handleGetFileListRequest() {
        GetFileListRequest getFileListRequest = (GetFileListRequest) request;
        ArrayList<String> strings = new ArrayList<>();
        String userName = request.getUserName();
        String fileType = getFileListRequest.getFileType();
        File file = null;
        if (fileType.equals(NewFileRequest.BRAIN_FUCK)) {
            file = new File(userName + "/bf/nameSheet.txt");
        } else if (fileType.equals(NewFileRequest.OOK)) {
            file = new File(userName + "/ook/nameSheet.txt");
        }
        if (file.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    strings.add(temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
                NetUtils.sendResponse(client, new GetFileListResponse(null, Response.UNKNOWN_REASON));
                return;
            }
        }
        if (!strings.isEmpty()) {
            String[] files = new String[strings.size()];
            for (int i = 0; i < strings.size(); i++) {
                files[i] = strings.get(i);
            }
            NetUtils.sendResponse(client, new GetFileListResponse(files, Response.SUCCESS));
            return;
        } else {
            NetUtils.sendResponse(client, new GetFileListResponse(null, Response.SUCCESS));
            return;
        }
    }

    private void handleGetFileVersionListRequest() {
        GetFileVersionListRequest getFileVersionListRequest = (GetFileVersionListRequest) request;
        String userName = getFileVersionListRequest.getUserName();
        String fileName = getFileVersionListRequest.getFileName();
        String fileType = getFileVersionListRequest.getFileType();
        String filePath = null;
        if (fileType.equals(NewFileRequest.BRAIN_FUCK)) {
            filePath = userName + "/bf/" + fileName + "/version.txt";
        } else if (fileType.equals(NewFileRequest.OOK)) {
            filePath = userName + "/ook/" + fileName + "/version.txt";
        }
        try {
            File file = new File(filePath);
            ArrayList<String> strings = new ArrayList<>();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    strings.add(temp);
                }
            }
            String[] versions = new String[strings.size()];
            for (int i = 0; i < strings.size(); i++) {
                versions[i] = strings.get(i);
            }
            NetUtils.sendResponse(client, new GetFileVersionListResponse(versions));
        } catch (IOException e) {
            e.printStackTrace();
            NetUtils.sendResponse(client, new GetFileVersionListResponse(null, Response.UNKNOWN_REASON));
            return;
        }
    }

    private void handleOpenFileRequest() {
        OpenFileRequest openFileRequest = (OpenFileRequest) request;
        String fileName = openFileRequest.getFileName();
        String userName = openFileRequest.getUserName();
        String fileType = openFileRequest.getFileType();
        File targetFileCon = null;
        String filePath = null;
        if (fileType.equals(NewFileRequest.BRAIN_FUCK)) {
            filePath = userName + "/bf/" + fileName + "/";
            targetFileCon = new File(filePath);
        } else if (fileType.equals(NewFileRequest.OOK)) {
            filePath = userName + "/ook/" + fileName + "/";
            targetFileCon = new File(filePath);
        }
        System.out.print(filePath);
        File currentVersion = new File(filePath + "current.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(currentVersion))) {
            String version = bufferedReader.readLine();
            File file = new File(filePath + version + ".txt");
            StringBuffer stringBuffer = new StringBuffer();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))){
                String temp = null;
                while((temp = reader.readLine()) != null) {
                    stringBuffer.append(temp);
                }
            }
            NetUtils.sendResponse(client, new OpenFileResponse(stringBuffer.toString(), Response.SUCCESS));
            return;
        } catch (IOException e) {
            e.printStackTrace();
            NetUtils.sendResponse(client, new OpenFileResponse(null, Response.UNKNOWN_REASON));
            return;
        }
    }

    private void handleSaveFileRequest() {
        SaveFileRequest saveFileRequest = (SaveFileRequest) request;
        String userName = saveFileRequest.getUserName();
        String fileName = saveFileRequest.getFileName();
        String fileType = saveFileRequest.getFileType();
        String fileContent = saveFileRequest.getFileContent();
        String filePath;
        if (fileType.equals(NewFileRequest.BRAIN_FUCK)) {
            filePath = userName + "/bf/" + fileName + "/";
        } else {
            filePath = userName + "/ook/" + fileName + "/";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_MM_SS");
        String fileVersionName = simpleDateFormat.format(new Date());
        String versionSheet = filePath + "version.txt";
        String currentSheet = filePath + "current.txt";
        filePath = filePath + fileVersionName + ".txt";
        File file = new File(filePath);
        try {
            boolean successCreate = file.createNewFile();
            if (!successCreate) {
                throw new IOException();
            }
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                bufferedWriter.write(fileContent);
            }
            File current = new File(currentSheet);
            File version = new File(versionSheet);
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(current)))) {
                bufferedWriter.write(fileVersionName + System.lineSeparator());
            }
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(version, true)))) {
                bufferedWriter.write(fileVersionName + System.lineSeparator());
            }
            NetUtils.sendResponse(client, new SaveFileResponse());
        } catch (IOException e) {
            e.printStackTrace();
            NetUtils.sendResponse(client, new SaveFileResponse(SaveFileResponse.FAIL));
        }
    }

    private void handleNewFileRequest() {
        NewFileRequest newFileRequest = (NewFileRequest) request;
        String userName = newFileRequest.getUserName();
        String fileName = newFileRequest.getFileName();
        String fileType = newFileRequest.getFileType();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_MM_SS");
        String fileVersionName = simpleDateFormat.format(new Date());
        String filePath;
        String fileList;
        if (fileType.equals(NewFileRequest.BRAIN_FUCK)) {
            fileList = userName + "/bf/" + "nameSheet.txt";
            filePath = userName + "/bf/" + fileName + "/";
        } else {
            fileList = userName + "/ook/" + "nameSheet.txt";
            filePath = userName + "/ook/" + fileName + "/";
        }
        File outFile = new File(filePath);
        if (outFile.exists()) {
            NetUtils.sendResponse(client, new NewFileResponse(NewFileResponse.EXIST));
            return;
        }
        if (!outFile.mkdirs()) {
            NetUtils.sendResponse(client, new NewFileResponse(NewFileResponse.FAIL));
        } else {
            try {
                File originalFile = new File(outFile, fileVersionName + ".txt");
                originalFile.createNewFile();
                File versionSheet = new File(outFile, "version.txt");
                File currentVersion = new File(outFile, "current.txt");
                versionSheet.createNewFile();
                currentVersion.createNewFile();
                try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(versionSheet, true)))) {
                    bufferedWriter.write(fileVersionName + System.lineSeparator());
                }
                try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentVersion)))) {
                    bufferedWriter.write(fileVersionName + System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
                NetUtils.sendResponse(client, new NewFileResponse(NewFileResponse.FAIL));
            }
            File fileListFile = new File(fileList);
            if (!fileListFile.exists()) {
                try {
                    fileListFile.createNewFile();
                    try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileListFile, true)))) {
                        bufferedWriter.write(fileName + System.lineSeparator());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    outFile.delete();
                    if (fileListFile.exists()) {
                        fileListFile.delete();
                    }
                    NetUtils.sendResponse(client, new NewFileResponse(NewFileResponse.FAIL));
                }
            } else {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileListFile, true)))){
                    bufferedWriter.write(fileName + System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                    outFile.delete();
                    NetUtils.sendResponse(client, new NewFileResponse(NewFileResponse.FAIL));
                }
            }
            NetUtils.sendResponse(client, new NewFileResponse(NewFileResponse.OK));
        }
    }

    private void handleLoginRequest() {
        NetUtils.sendResponse(client, DataBase.login((LoginRequest) request));
    }

    private void handleRegisterRequest() {
        NetUtils.sendResponse(client, DataBase.register((RegisterRequest) request));
    }

    private void handleExecuteRequest() {
        ExecuteRequest executeRequest = (ExecuteRequest) request;
        String type = executeRequest.getType();
        String output = null;
        if (type.equals(NewFileRequest.BRAIN_FUCK)) {
            output = new BrainFuckInterpreter().executeProgram(executeRequest.getRawSource(), executeRequest.getInput());
        } else if (type.equals(NewFileRequest.OOK)) {
            output = new OokInterpreter().executeProgram(executeRequest.getRawSource(), executeRequest.getInput());
        }
        Response response = new ExecuteResponse(output);
        System.out.println(Config.getConfigedGson().toJson(response));
        System.out.println(NetUtils.sendResponse(client, response));
    }

}
