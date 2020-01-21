package com.example.administrator.chadaodiancompany.netCallback;

import java.io.File;

public interface IGoodAnalyseCallback extends ICallback {
    void getGoodAnalyse(String result);

    void downloadFileExcel(File file);
}
