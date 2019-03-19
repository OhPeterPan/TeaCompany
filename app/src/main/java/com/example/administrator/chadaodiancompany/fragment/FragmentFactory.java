package com.example.administrator.chadaodiancompany.fragment;

import android.support.v4.app.Fragment;

import com.example.administrator.chadaodiancompany.fragment.home.MsgFragment;
import com.example.administrator.chadaodiancompany.fragment.home.NewHomeFragment;
import com.example.administrator.chadaodiancompany.fragment.home.OrderFragment;
import com.example.administrator.chadaodiancompany.fragment.home.StatisticsFragment;

import java.util.concurrent.ConcurrentHashMap;

public class FragmentFactory {
    private static ConcurrentHashMap<Integer, Fragment> hashMap = new ConcurrentHashMap<>();

    public static void putFragment(int index, Fragment fragment) {
        if (hashMap == null) hashMap = new ConcurrentHashMap<>();
        hashMap.put(index, fragment);
    }

    public static Fragment getFragment(int index) {
        if (hashMap == null) hashMap = new ConcurrentHashMap<>();
        Fragment fragment = hashMap.get(index);
        if (fragment == null) {
            switch (index) {
                case 0:
//                    fragment = new HomeFragment();
                    fragment = new NewHomeFragment();
                    break;
                case 1:
                    fragment = new OrderFragment();
                    break;

                case 2:
                    fragment = new StatisticsFragment();
                    break;
                case 3:
                    fragment = new MsgFragment();
                    break;
            }
            hashMap.put(index, fragment);
        }
        return fragment;
    }
}
