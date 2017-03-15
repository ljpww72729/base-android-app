package com.ww.lp.base.modules.main.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class AboutFragment extends BaseFragment implements AboutContract.View{

    public static AboutFragment newInstance() {

        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TempFragBinding binding;
    private AboutContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container,savedInstanceState, R.layout.temp_frag, false);
        binding = TempFragBinding.bind(root);
        TempInfo tempInfo = new TempInfo();
        tempInfo.setInfo("Fragment test info.");
        binding.setTempInfo(tempInfo);
        binding.tempTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull AboutContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
