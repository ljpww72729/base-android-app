package com.ww.lp.base.modules.team.developer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.databinding.DeveloperFragBinding;
import com.ww.lp.base.utils.SPUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class DeveloperFragment extends BaseFragment implements DeveloperContract.View {

    public static DeveloperFragment newInstance() {

        Bundle args = new Bundle();

        DeveloperFragment fragment = new DeveloperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private DeveloperFragBinding binding;
    private DeveloperContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.developer_frag, false);
        binding = DeveloperFragBinding.bind(root);
        binding.shenhe.setText(getString(R.string.shenhe, (String)SPUtils.get(CustomApplication.self(), SPUtils.ADMIN_EMAIL, "")));
        return root;
    }

    @Override
    public void setPresenter(@NonNull DeveloperContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
