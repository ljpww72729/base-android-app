package com.ww.lp.base.modules.templates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.databinding.TempFragBinding;
import com.ww.lp.base.entity.TempInfo;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TempFragment extends BaseFragment implements TempContract.View{

    public static TempFragment newInstance() {

        Bundle args = new Bundle();

        TempFragment fragment = new TempFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TempFragBinding binding;
    private TempContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.temp_frag, container, false);
        binding = TempFragBinding.bind(root);
        TempInfo tempInfo = new TempInfo();
        tempInfo.setInfo("Fragment test info.");
        binding.setTempInfo(tempInfo);
        binding.tempTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.testClick(binding.getTempInfo());
            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull TempContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void showInfo(String info) {
        Snackbar.make(getView(), info, Snackbar.LENGTH_SHORT).show();
    }
}
