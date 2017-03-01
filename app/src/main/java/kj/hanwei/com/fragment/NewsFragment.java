package kj.hanwei.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kj.hanwei.com.R;

/**
 * 新闻
 * Created by 陈苗辉 on 2016/11/11.
 */
public class NewsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, null);
        return view;
    }
}
