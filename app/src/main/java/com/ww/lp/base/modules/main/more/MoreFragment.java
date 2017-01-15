package com.ww.lp.base.modules.main.more;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.databinding.MoreFragBinding;
import com.ww.lp.base.modules.login.LoginActivity;
import com.ww.lp.base.modules.main.user.UserInfoActivity;
import com.ww.lp.base.modules.order.list.OrderListActivity;
import com.ww.lp.base.modules.team.detail.TeamDetailActivity;
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MoreFragment extends BaseFragment implements MoreContract.View {

    public static MoreFragment newInstance() {

        Bundle args = new Bundle();

        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private MoreFragBinding binding;
    private MoreContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        loginLogout();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.more_frag, false);
        binding = MoreFragBinding.bind(root);
        binding.createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""))) {
                    ToastUtils.toastShort("请先登录后再操作！");
                } else {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra(OrderListActivity.ORDER_FLAG, OrderListActivity.RELEASE);
                    intent.putExtra(OrderListActivity.ISONLYQUERYMYPUBLIS, OrderListActivity.PERSONAL);
                    intent.putExtra(Constants.TITLE, "已发布项目");
                    startActivity(intent);
                }

            }
        });
        binding.acceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""))) {
                    ToastUtils.toastShort("请先登录后再操作！");
                } else {
                    if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TEAM_ID, ""))) {
                        ToastUtils.toastShort("请先创建团队，如已创建请退出后重新登录再试！");
                    } else {
                        Intent intent = new Intent(getActivity(), OrderListActivity.class);
                        intent.putExtra(OrderListActivity.ORDER_FLAG, OrderListActivity.ACCEPT);
                        intent.putExtra(OrderListActivity.ISONLYQUERYMYPUBLIS, OrderListActivity.PERSONAL);
                        intent.putExtra(Constants.TITLE, "已承接项目");
                        startActivity(intent);
                    }
                }
            }
        });
        binding.userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""))) {
                    //未登录
                    ToastUtils.toastShort("请先登录后再操作！");
                } else {
                    //已登录
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        binding.myTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""))) {
                    ToastUtils.toastShort("请先登录后再操作！");
                } else {
                    if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TEAM_ID, ""))) {
                        ToastUtils.toastShort("请认证为开发者并创建团队！");
                    } else {
                        Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
                        intent.putExtra("teamId", (String) SPUtils.get(CustomApplication.self(), SPUtils.TEAM_ID, ""));
                        intent.putExtra("isOnlyQueryMyOwn", "1");
                        intent.putExtra("flag", "mine");
                        startActivity(intent);
                    }
                }
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定退出？");
                builder.setTitle("提示信息");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.clear(getActivity());
                        binding.login.setVisibility(View.VISIBLE);
                        binding.logout.setVisibility(View.GONE);
                        ToastUtils.toastShort("退出成功！");
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                builder.show();

            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull MoreContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void loginLogout() {
        if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""))) {
            binding.login.setVisibility(View.VISIBLE);
            binding.logout.setVisibility(View.GONE);
        } else {
            binding.login.setVisibility(View.GONE);
            binding.logout.setVisibility(View.VISIBLE);
        }
    }

}
