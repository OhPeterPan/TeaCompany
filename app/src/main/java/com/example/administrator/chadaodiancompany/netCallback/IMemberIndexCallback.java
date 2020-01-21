package com.example.administrator.chadaodiancompany.netCallback;

import java.io.File;

public interface IMemberIndexCallback extends ICallback {
    void memberResult(String result);

    void delMemberResult(String result);

    void downloadFileExcel(File file);
}
