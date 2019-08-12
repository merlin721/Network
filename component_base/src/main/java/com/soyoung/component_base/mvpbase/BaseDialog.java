/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.soyoung.component_base.mvpbase;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.soyoung.component_base.R;

/**
  *BaseDialog
  *@author ：daiwenbo
  *@Time   ：2018/2/27
  *@e-mail ：daiwwenb@163.com
  *@description ：dialog 基类
  *
  */
public abstract class BaseDialog extends DialogFragment {
    protected BaseActivity mActivity;
    private View rootView;
    protected AbsDialogButtonClickListener onDialogButtonClickListener;

    public void setOnDialogButtonClickListener(AbsDialogButtonClickListener onDialogButtonClickListener) {
        this.onDialogButtonClickListener = onDialogButtonClickListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }


    protected abstract void setUp(View view, Bundle savedInstanceState);
    protected abstract int setLayoutId();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getContext(), R.style.customDatePickerDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        Window window = dialog.getWindow();

        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
        }
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }
    public void showMessage(String message){
        Toast.makeText(mActivity,message, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(setLayoutId(),container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view,savedInstanceState);
    }

    public interface OnDialogButtonClickListener{
        void onCancelListener();
        void onDetermineListener();
    }
    public static class AbsDialogButtonClickListener implements OnDialogButtonClickListener{
        @Override
        public void onCancelListener() {

        }

        @Override
        public void onDetermineListener() {

        }
    }

    public void show(FragmentManager fragmentManager, String tag) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    @Override
    public void onResume() {
        super.onResume();
        HideKeyboard(rootView);
    }

    public void dismissDialog() {
        HideKeyboard(rootView);
        if (mActivity!=null) {
            mActivity = null;
        }
        dismiss();
    }
    @Override
    public void dismiss(){
        if (mActivity!=null) {
            mActivity = null;
        }
        super.dismiss();
    }

    public static void HideKeyboard(View v) {
        if(null==v)return;
        InputMethodManager imm = (InputMethodManager) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
        }
    }
}