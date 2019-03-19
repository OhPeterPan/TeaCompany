package com.example.administrator.chadaodiancompany.util;


import com.example.administrator.chadaodiancompany.bean.HistoryBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/3/19 0019.
 * 维护数据库
 */

public class DatabaseUtil {
    /**
     * 删除数据库中所有的表
     */
    public static void delAllDatabase() {
        DataSupport.deleteAll(HistoryBean.class);
/*        DataSupport.deleteAll(StoreListObj.class);
        DataSupport.deleteAll(StoreAllBean.class);
        DataSupport.deleteAll(CircleHistoryBean.class);*/
    }
}
