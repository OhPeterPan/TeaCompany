package com.example.administrator.chadaodiancompany.bean;

public class CommonResponse<T> {
    public int code;
    public T datas;

    @Override
    public String toString() {
        return code + "::" + datas.toString();
    }
}
