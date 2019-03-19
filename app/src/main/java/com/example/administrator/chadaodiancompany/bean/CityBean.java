package com.example.administrator.chadaodiancompany.bean;

import java.util.List;

public class CityBean {
	public int code;
	public AreaListDatasBean datas;

	public static class AreaListDatasBean {

		public List<AreaListBean> area_list;

		public static class AreaListBean {
			public String area_id;
			public String area_name;
		}
	}
}
