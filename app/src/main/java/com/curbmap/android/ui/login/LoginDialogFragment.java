/*
 * Copyright ${YEAR} ${PROJECT_NAME}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.curbmap.android.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.curbmap.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginDialogFragment extends DialogFragment{
        //implements Injectable, HasSupportFragmentInjector{

    public static final String TAG = "LoginDialogFragment";

    LoginViewModel loginViewModel;
    Unbinder unbinder;
    static int title;

    @BindView(R.id.layout_login_title_text_view)
    AppCompatTextView textViewUsername;
    @BindView(R.id.layout_login_username_input)
    AppCompatEditText editTextUsername;
    @BindView(R.id.layout_login_password_input)
    AppCompatEditText editTextPassword;
    @BindView(R.id.layout_login_button_submit)
    AppCompatButton buttonSubmit;




 /*   @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        title = getArguments().getInt("title");

        return new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.icon_account_circle)
                .setTitle(title)
                .create();
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        return view;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.layout_login_button_submit)
    @VisibleForTesting
    void doSubmitForLogin(){
        Log.d(TAG, "Username: "+ editTextUsername.getText() + " Password: "+ editTextPassword.getText());

        loginViewModel.submitLoginRequest(editTextUsername.getText().toString(), editTextPassword.getText().toString());
    }


}
