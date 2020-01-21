package com.example.administrator.chadaodiancompany.viewImpl;

import java.io.File;

public interface IMemberIndexView extends IView {
    void memberResultSucc(String result);

    void delMemberResultSucc(String result);

    void downloadFileExcelSucc(File file);
}
