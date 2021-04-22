package com.mb.countrylist.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mb.countrylist.R;
import com.mb.countrylist.util.Constants;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements Constants {

    public Dialog mProgressDialog;
    protected Activity activity;
    protected View view;

    protected abstract void initView();

    protected abstract void setupViewModel();

    protected abstract int getLayoutResource();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutResource(), container, false);
        }
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new Dialog(activity);
            mProgressDialog.setContentView(R.layout.progress_dialog);
            if (mProgressDialog.getWindow() != null)
                mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dimisssDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
