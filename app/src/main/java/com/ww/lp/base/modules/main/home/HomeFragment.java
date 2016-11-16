package com.ww.lp.base.modules.main.home;

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

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeFragBinding binding;
    private HomeContract.Presenter mPresenter;
    int[] sampleImages = {
            R.drawable.va_home,
            R.drawable.va_home,
            R.drawable.va_home
    };



    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
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
        View root = inflater.inflate(R.layout.home_frag, container, false);
        binding = HomeFragBinding.bind(root);
        return root;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void updateCarouselView(final ArrayList<CarouselInfo> carouselList) {
        binding.homeCarouselView.setPageCount(carouselList.size());
        ViewListener viewListener = new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                View carouselView = LayoutInflater.from(getActivity()).inflate(R.layout.carousel_view, null);
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) carouselView.findViewById(R.id.carousel_img);
                simpleDraweeView.setImageURI(carouselList.get(position).getImgUrl());
                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                return carouselView;
            }
        };
        binding.homeCarouselView.setViewListener(viewListener);
    }
}
