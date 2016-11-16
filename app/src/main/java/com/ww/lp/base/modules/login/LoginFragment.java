package com.ww.lp.base.modules.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.databinding.LoginFragBinding;
import com.ww.lp.base.entity.UserInfo;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/10/27.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View{

    private LoginFragBinding loginFragBinding;
    private LoginContract.Presenter mPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

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
        View root = inflater.inflate(R.layout.login_frag, container, false);
        loginFragBinding = LoginFragBinding.bind(root);
        UserInfo userInfo = new UserInfo();
        userInfo.setName("test110");
        userInfo.setPassword("12345678");
        loginFragBinding.setUserInfo(userInfo);
//        loginFragBinding.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.login(loginFragBinding.getStudent());
//            }
//        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void success() {
        getActivity().finish();
    }
}
