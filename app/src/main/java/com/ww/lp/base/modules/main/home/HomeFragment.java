package com.ww.lp.base.modules.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.synnapps.carouselview.ViewListener;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.databinding.HomeFragBinding;
import com.ww.lp.base.entity.CarouselInfo;
import com.ww.lp.base.modules.webview.NormalWVActvity;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeFragBinding binding;
    private HomeContract.Presenter mPresenter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.home_frag, false);
        binding = HomeFragBinding.bind(root);
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void updateCarouselView(final ArrayList<CarouselInfo> carouselList) {
        ViewListener viewListener = new ViewListener() {
            @Override
            public View setViewForPosition(final int position) {
                View carouselView = LayoutInflater.from(getActivity()).inflate(R.layout.carousel_view, null);
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) carouselView.findViewById(R.id.carousel_img);
                simpleDraweeView.setImageURI(carouselList.get(position).getImg());
                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), NormalWVActvity.class);
                        intent.putExtra(NormalWVActvity.LOADURL, carouselList.get(position).getUrl());
                        startActivity(intent);
                    }
                });

                return carouselView;
            }
        };
        binding.homeCarouselView.setViewListener(viewListener);
        //setPageCount方法的调用需要放到setViewListener之后
        binding.homeCarouselView.setPageCount(carouselList.size());

    }
}
