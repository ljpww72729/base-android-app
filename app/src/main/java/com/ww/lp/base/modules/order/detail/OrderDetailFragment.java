package com.ww.lp.base.modules.order.detail;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.synnapps.carouselview.ViewListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.components.alipay.PayResult;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.data.project.ProjectImg;
import com.ww.lp.base.data.project.ProjectInfo;
import com.ww.lp.base.databinding.OrderDetailFragBinding;
import com.ww.lp.base.modules.order.comment.CommentActivity;
import com.ww.lp.base.modules.order.list.OrderListActivity;
import com.ww.lp.base.modules.order.post.PostActivity;
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Map;

import rx.functions.Action1;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;
import static com.ww.lp.base.modules.order.detail.OrderDetailActivity.PROJECT_ID;
import static com.ww.lp.base.modules.order.detail.OrderDetailActivity.SHOW_PAY;
import static com.ww.lp.base.modules.order.list.OrderListActivity.ORDER_FLAG;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderDetailFragment extends BaseFragment implements OrderDetailContract.View {

    private OrderDetailFragBinding binding;
    private OrderDetailContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ProjectInfo> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<ProjectInfo> lpRecyclerViewAdapter;
    RxPermissions rxPermissions;
    private ProjectInfo projectInfo;
    private int role;

    public static OrderDetailFragment newInstance(Intent intent) {

        Bundle bundle = new Bundle();
        bundle.putString(PROJECT_ID, intent.getStringExtra(PROJECT_ID));
        bundle.putString(ORDER_FLAG, intent.getStringExtra(ORDER_FLAG));
        bundle.putBoolean("showPay", intent.getBooleanExtra(SHOW_PAY, true));
        bundle.putBoolean(OrderDetailActivity.IS_PERSONAL, intent.getBooleanExtra(OrderDetailActivity.IS_PERSONAL, false));
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        role = (int) SPUtils.get(CustomApplication.self(), SPUtils.ROLE, Constants.NORMAL);
        mPresenter.loadProjectInfo(getArguments().getString(PROJECT_ID));
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rxPermissions = new RxPermissions(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.order_detail_frag, false);
        binding = OrderDetailFragBinding.bind(root);
        mPresenter.loadProjectInfo(getArguments().getString(PROJECT_ID));
        binding.btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnStatus();
            }
        });
        binding.btnNotComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnComplete();
            }
        });
        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) { // Always true pre-M
                            // I can control the camera now
                            String number = binding.phone.getText().toString();
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + number));
                            startActivity(intent);
                        } else {
                            // Oups permission denied
                        }
                    }
                });
            }
        });
        return root;
    }

    public void clickBtnStatus() {
        switch (projectInfo.getStatus()) {
            case Constants.PUBLISH:
                //可以承接项目
                if (!getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL) && role == Constants.DEVELOPER) {
                    //承接项目
                    mPresenter.acceptProject(projectInfo.getProjectId());
                } else {
                    ToastUtils.toastLong("必须认证成为开发者才可以承接项目");
                }
                break;
            case Constants.ACCEPT:
                if (getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL)) {
                    if (OrderListActivity.RELEASE.equals(getArguments().getString(ORDER_FLAG))) {
                        //拒绝承接
                        mPresenter.editProject(projectInfo, Constants.PUBLISH);
                    } else if (OrderListActivity.ACCEPT.equals(getArguments().getString(ORDER_FLAG))  && role == Constants.DEVELOPER) {
                        //项目已完成 开发者才可以完成项目
                         mPresenter.editProject(projectInfo, Constants.UNPAYED);
                    }
                } else {
                    ToastUtils.toastLong("您无权进行该操作！");
                }
                break;
            case Constants.UNPAYED:
                if (getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL)) {
                    if (OrderListActivity.RELEASE.equals(getArguments().getString(ORDER_FLAG))) {
                        ((BaseActivity) getActivity()).showProgressDialogLP("正在打开支付宝...");
                        //支付
                        mPresenter.getSign(projectInfo.getProjectId());
                    }
                } else {
                    ToastUtils.toastLong("您无权进行该操作！");
                }
                break;
            case Constants.PAYED:
                if (getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL)) {
                    if (OrderListActivity.RELEASE.equals(getArguments().getString(ORDER_FLAG))) {
                        Intent intent = new Intent(getActivity(), CommentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("projectId", projectInfo.getProjectId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    ToastUtils.toastLong("只有项目发布者才可以进行评论");
                }
                break;
        }
    }

    public void clickBtnComplete() {
        switch (projectInfo.getStatus()) {
            case Constants.UNPAYED:
                mPresenter.editProject(projectInfo, Constants.ACCEPT);
                break;
            case Constants.PAYED:
                binding.status.setText(getString(R.string.payed));
                break;
        }
    }

    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void updateCarouselView(final ArrayList<ProjectImg> projectImgs) {
        ViewListener viewListener = new ViewListener() {
            @Override
            public View setViewForPosition(final int position) {
                View carouselView = LayoutInflater.from(getActivity()).inflate(R.layout.carousel_view, null);
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) carouselView.findViewById(R.id.carousel_img);
                simpleDraweeView.setImageURI(projectImgs.get(position).getImg());
//                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), NormalWVActvity.class);
//                        intent.putExtra(NormalWVActvity.LOADURL, carouselList.get(position).getUrl());
//                        startActivity(intent);
//                    }
//                });

                return carouselView;
            }
        };
        binding.homeCarouselView.setViewListener(viewListener);
        //setPageCount方法的调用需要放到setViewListener之后
        binding.homeCarouselView.setPageCount(projectImgs.size());

    }

    @Override
    public void showDetail(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
        binding.setProjectInfo(projectInfo);
        setStatus(projectInfo.getStatus());
        updateCarouselView(projectInfo.getProjectImgs());
    }

    public void setStatus(int status) {
        //个人发布，并且是已发布状态才可以删除和修改
        if (getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL)
                && OrderListActivity.RELEASE.equals(getArguments().getString(ORDER_FLAG))
                && status == Constants.PUBLISH) {
            setHasOptionsMenu(true);
        } else {
            setHasOptionsMenu(false);
        }
        binding.btnNotComplete.setVisibility(View.GONE);
        binding.btnStatus.setVisibility(View.GONE);
        switch (status) {
            case Constants.PUBLISH:
                binding.status.setText(getString(R.string.publish));
                if (!getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL)) {
                    binding.btnStatus.setVisibility(View.VISIBLE);
                    binding.btnStatus.setText(getString(R.string.chengjie));
                }
                break;
            case Constants.ACCEPT:
                binding.status.setText(getString(R.string.accept));
                if (getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL)) {
                    binding.btnStatus.setVisibility(View.VISIBLE);
                    if (OrderListActivity.RELEASE.equals(getArguments().getString(ORDER_FLAG))) {
                        binding.btnStatus.setText(getString(R.string.jujuechengjie));
                    } else if (OrderListActivity.ACCEPT.equals(getArguments().getString(ORDER_FLAG))) {
                        binding.btnStatus.setText(getString(R.string.yiwancheng));
                    }

                }
                break;
            case Constants.UNPAYED:
                binding.status.setText(getString(R.string.unpay));
                if (getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL)
                        && OrderListActivity.RELEASE.equals(getArguments().getString(ORDER_FLAG))) {
                    binding.btnNotComplete.setVisibility(View.VISIBLE);
                    binding.btnStatus.setVisibility(View.VISIBLE);
                    binding.btnStatus.setText(getString(R.string.zhifu));
                }
                break;
            case Constants.PAYED:
                binding.status.setText(getString(R.string.payed));
                if (getArguments().getBoolean(OrderDetailActivity.IS_PERSONAL)
                        && OrderListActivity.RELEASE.equals(getArguments().getString(ORDER_FLAG))) {
                    binding.btnStatus.setVisibility(View.VISIBLE);
                    binding.btnStatus.setText(getString(R.string.pingja));
                }
                break;
        }
    }

    @Override
    public void deleteSuccess(boolean result) {
        ((BaseActivity) getActivity()).removeProgressDialogLP();
        if (result) {
            getActivity().finish();
            ToastUtils.toastShort("删除成功！");
        } else {
            ToastUtils.toastShort("删除失败，请重试！");
        }

    }

    @Override
    public void acceptProjectResult(boolean result) {
        if (result) {
            mPresenter.loadProjectInfo(getArguments().getString(PROJECT_ID));
            ToastUtils.toastLong("项目承接成功！");
        }
    }

    @Override
    public void editSuccess(boolean result) {
        if (result) {
            mPresenter.loadProjectInfo(getArguments().getString(PROJECT_ID));
        }
    }

    @Override
    public void signResult(boolean result, String signStr) {
        ((BaseActivity) getActivity()).removeProgressDialogLP();
        if (result) {
            mPresenter.pay(getActivity(), signStr);
        } else {
            ToastUtils.toastLong("支付失败，请重试！");
        }
    }

    @Override
    public void aliPay(boolean result, Map<String, String> payMap) {
        if (result) {
            PayResult payResult = new PayResult(payMap);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                mPresenter.getPayResult(payResult.getResult());
//                ToastUtils.toastLong("支付成功");
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                ToastUtils.toastLong("支付失败");
                mPresenter.loadProjectInfo(getArguments().getString(PROJECT_ID));
            }
        } else {
            ToastUtils.toastLong("支付失败，请重新支付！");
        }

    }

    @Override
    public void payResult(boolean result) {
        if (result) {
            ToastUtils.toastShort("支付成功！");
        } else {
            ToastUtils.toastShort("支付失败！");
        }
        mPresenter.loadProjectInfo(getArguments().getString(PROJECT_ID));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.project_operate, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify:
                if (projectInfo.getStatus() == Constants.PUBLISH) {
                    Intent intent = new Intent(getActivity(), PostActivity.class);
                    intent.putExtra("projectInfo", projectInfo);
                    startActivity(intent);
                } else {
                    ToastUtils.toastLong("不可修改！");
                }
                return true;
            case R.id.delete:
                ((BaseActivity) getActivity()).showProgressDialogLP("正在删除...");
                mPresenter.deleteProject(projectInfo.getProjectId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
