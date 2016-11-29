package com.ww.lp.base.modules.order.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BR;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.components.rvrl.SingleItemClickListener;
import com.ww.lp.base.data.ProjectInfo;
import com.ww.lp.base.databinding.OrderListBinding;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderListFragment extends BaseFragment implements OrderListContract.View {

    private LinearLayoutManager mLayoutManager;
    private ArrayList<ProjectInfo> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<ProjectInfo> lpRecyclerViewAdapter;

    public static OrderListFragment newInstance(String flag) {

        Bundle args = new Bundle();
        args.putString(OrderListActivity.ORDER_FLAG, flag);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private OrderListBinding binding;
    private OrderListContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.order_list, false);
        binding = OrderListBinding.bind(root);
        binding.lpRv.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.lpRv.setLayoutManager(mLayoutManager);
        lpRecyclerViewAdapter = new LPRecyclerViewAdapter<>(mRVData, R.layout.project_info_item, BR.lp_rv_item);
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);
        binding.lpRv.addOnItemTouchListener(new SingleItemClickListener(binding.lpRv, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
//                intent.putExtra(OrderDetailActivity.PROJECT_ID, mRVData.get(position).getProjectId());
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        mPresenter.loadOrderList(getArguments().getString(OrderListActivity.ORDER_FLAG));
        return root;
    }

    @Override
    public void setPresenter(@NonNull OrderListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void updateOrdertList(ArrayList<ProjectInfo> arrayList) {
        // TODO: 16/11/26 数据是否这样更新有待优化
        mRVData.clear();
        mRVData.addAll(arrayList);
        lpRecyclerViewAdapter.notifyDataSetChanged();
    }
}
