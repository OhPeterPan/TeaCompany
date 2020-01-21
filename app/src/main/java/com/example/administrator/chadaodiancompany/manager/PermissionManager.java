package com.example.administrator.chadaodiancompany.manager;

import com.example.administrator.chadaodiancompany.util.UIUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class PermissionManager {
    private static PermissionManager manager;

    private PermissionManager() {

    }

    public static PermissionManager getInstance() {
        if (manager == null) {
            synchronized (PermissionManager.class) {
                if (manager == null) {
                    manager = new PermissionManager();
                }
            }
        }
        return manager;
    }

    public void requestPermission(final Runnable runnable, final String... permissions) {
        AndPermission.with(UIUtil.getContext())
                .runtime()
                .permission(permissions)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        runnable.run();
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                if (AndPermission.hasAlwaysDeniedPermission(UIUtil.getContext(), permissions)) {
                    // 打开权限设置页
                    AndPermission.with(UIUtil.getContext()).runtime().setting();
                    return;
                }
            }
        }).start();
    }
}
