package com.example.administrator.chadaodiancompany.viewImpl;

import java.io.File;

public interface IGoodAnalyseView extends IView {
    void getGoodAnalyseResult(String result);

    void downloadFileExcelSuccess(File file);
}
