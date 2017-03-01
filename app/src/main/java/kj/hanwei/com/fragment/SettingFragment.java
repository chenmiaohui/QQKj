package kj.hanwei.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kj.hanwei.com.R;
import kj.hanwei.com.activity.MainActivity;
import kj.hanwei.com.tab.Constant;

/**
 * 设置
 * Created by 陈苗辉 on 2016/11/11.
 */
public class SettingFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, null);
        return view;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        MainActivity.currFragTag = Constant.FRAGMENT_FLAG_SETTING;

    }
}
