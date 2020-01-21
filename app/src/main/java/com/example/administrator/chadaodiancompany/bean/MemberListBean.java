package com.example.administrator.chadaodiancompany.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberListBean implements Parcelable {
    public String member_id;
    public String member_name;
    public String c_name;
    public String grade_name;

    public MemberListBean() {
    }

    protected MemberListBean(Parcel in) {
        member_id = in.readString();
        member_name = in.readString();
        c_name = in.readString();
        grade_name = in.readString();
    }

    public static final Creator<MemberListBean> CREATOR = new Creator<MemberListBean>() {
        @Override
        public MemberListBean createFromParcel(Parcel in) {
            return new MemberListBean(in);
        }

        @Override
        public MemberListBean[] newArray(int size) {
            return new MemberListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(member_id);
        dest.writeString(member_name);
        dest.writeString(c_name);
        dest.writeString(grade_name);
    }
}
