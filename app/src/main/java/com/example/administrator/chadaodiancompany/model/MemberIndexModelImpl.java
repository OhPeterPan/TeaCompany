package com.example.administrator.chadaodiancompany.model;

import com.example.administrator.chadaodiancompany.netCallback.IMemberIndexCallback;
import com.example.administrator.chadaodiancompany.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

public class MemberIndexModelImpl implements IMemberIndexModel {
    @Override
    public void sendNetMemberList(String key, String c_name, String sort, String curPage, final IMemberIndexCallback callback) {
        OkGo.<String>get(NetUtils.BASE_URL + NetUtils.MEMBER_INDEX_URL)
                .tag("MEMBER_INDEX_URL")
                .params("key", key)
                .params("c_name", c_name)
                .params("curpage", curPage)
                .params("sort", sort)
                .params("page", "10")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.memberResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetDelMember(String key, String memberId, final IMemberIndexCallback callback) {
        OkGo.<String>post(NetUtils.BASE_URL + NetUtils.MEMBER_DEL_URL)
                .tag("MEMBER_INDEX_URL")
                .params("key", key)
                .params("member_id", memberId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.delMemberResult(response.body());
                    }
                });
    }

    @Override
    public void sendNetOutExcel(String key, String type, String destFileName, final IMemberIndexCallback callback) {
        OkGo.<File>get(NetUtils.BASE_URL + NetUtils.MEMBER_OUT_URL)
                .tag("MEMBER_INDEX_URL")
                .params("key", key)
                .execute(new FileCallback(destFileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        callback.downloadFileExcel(response.body());
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        callback.getResultError(response.getException());
                    }
                });
    }

    @Override
    public void cancelTag() {
        OkGo.getInstance().cancelTag("MEMBER_INDEX_URL");
    }
}
