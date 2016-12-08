package com.ww.lp.base.modules.main.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.SPUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MoreFragment extends BaseFragment implements MoreContract.View{

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
        View root = onCreateView(inflater, container,savedInstanceState, R.layout.more_frag, false);
        binding = MoreFragBinding.bind(root);
        binding.createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                intent.putExtra(OrderListActivity.ORDER_FLAG, OrderListActivity.RELEASE);
                intent.putExtra(Constants.TITLE, "发布订单");
                startActivity(intent);

            }
        });
        binding.acceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                intent.putExtra(OrderListActivity.ORDER_FLAG, OrderListActivity.ACCEPT);
                intent.putExtra(Constants.TITLE, "承接订单");
                startActivity(intent);
            }
        });
        binding.userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty((String)SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""))){
                    //未登录
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    //已登录
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull MoreContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
