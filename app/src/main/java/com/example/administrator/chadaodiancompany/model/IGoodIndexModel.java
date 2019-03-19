package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IGoodIndexCallback;

public interface IGoodIndexModel {
    void sendNetGetGoodDetail(String key, String curPage, String stcId, String keyWord, IGoodIndexCallback callback);

    void sendNetGetGoodBan(String key, String goodCommonId, IGoodIndexCallback callback);

    void sendNetChangeGoodNumber(String key, String goodsCommonId, String number, IGoodIndexCallback callback);
}
