package com.example.demo0610;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DataFragment extends Fragment {




    public void onAttach(Context context) {
        super.onAttach(context);
//        mActivity = (Activity) context;
//        mParam = getArguments().getString(ARG_PARAM);  //获取参数
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_data, container, false);

        root.setClickable(true);





        return root;
    }


}
