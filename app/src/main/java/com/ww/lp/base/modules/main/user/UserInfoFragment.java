package com.ww.lp.base.modules.main.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.data.user.UserInfo;
import com.ww.lp.base.databinding.UserInfoFragBinding;
import com.ww.lp.base.modules.main.user.password.ModifyPWActivity;
import com.ww.lp.base.modules.team.developer.DeveloperActivity;
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.SPUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * 登录 Created by LinkedME06 on 16/10/27.
 */

public class UserInfoFragment extends BaseFragment implements com.ww.lp.base.modules.main.user.UserInfoContract.View {

    private UserInfoFragBinding userInfoFragBinding;
    private UserInfoContract.Presenter mPresenter;

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.user_info_frag, false);
        userInfoFragBinding = UserInfoFragBinding.bind(root);
        setHasOptionsMenu(true);
        if ((int) SPUtils.get(CustomApplication.self(), SPUtils.ROLE, 0) == Constants.NORMAL) {
            userInfoFragBinding.btnCertification.setText("未认证[点击认证]");
            userInfoFragBinding.btnCertification.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        } else {
            userInfoFragBinding.btnCertification.setText("已认证");
            userInfoFragBinding.btnCertification.setTextColor(ContextCompat.getColor(getActivity(), R.color.accent));
        }
        int role = (int) SPUtils.get(CustomApplication.self(), SPUtils.ROLE, 0);
        switch (role) {
            case 1:
                userInfoFragBinding.myRole.setText("开发者");
                break;
            case 2:
                userInfoFragBinding.myRole.setText("管理员");
                break;
            default:
                userInfoFragBinding.myRole.setText("普通用户");
                break;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail((String) SPUtils.get(CustomApplication.self(), SPUtils.EMAIL, ""));
        userInfo.setPhoneNum((String) SPUtils.get(CustomApplication.self(), SPUtils.PHONENUM, ""));
        if (!TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.AVATAR_IMG, ""))) {
            userInfoFragBinding.avatarImg.setImageURI((String) SPUtils.get(CustomApplication.self(), SPUtils.AVATAR_IMG, ""));
        }
        userInfoFragBinding.setUserInfo(userInfo);
        userInfoFragBinding.cBtnCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int) SPUtils.get(CustomApplication.self(), SPUtils.ROLE, 0) == Constants.NORMAL) {
                    Intent intent = new Intent(getActivity(), DeveloperActivity.class);
                    startActivity(intent);
                }
            }
        });
        userInfoFragBinding.cUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 18/03/2017 上传照片

            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull com.ww.lp.base.modules.main.user.UserInfoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify_pwd:
                Intent intent = new Intent(getActivity(), ModifyPWActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
