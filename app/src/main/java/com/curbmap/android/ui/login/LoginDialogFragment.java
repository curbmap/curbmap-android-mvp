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

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curbmap.android.R;
import com.curbmap.android.ui.main.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/* Todo: https://github.com/codepath/android_guides/wiki/Working-with-the-Soft-Keyboard
 * Move View Up on soft keyboard visible
 * */
public class LoginDialogFragment extends DialogFragment{

    public static final String TAG = "LoginDialogFragment";

    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";


    MainViewModel mainViewModel;
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


   /* public static LoginDialogFragment create(@Nullable String username, @Nullable String password){
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        Bundle args = new Bundle();

        if (password != null) {
            args.putString(PASSWORD, password);
        } else {
            args.putString(PASSWORD, "");
        }
        if (username != null) {
            args.putString(USERNAME, username);
        } else {
            args.putString(USERNAME, "");
        }

        loginDialogFragment.setArguments(args);
        return loginDialogFragment;
    }*/

    public static LoginDialogFragment create() {
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        return loginDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.layout_login_button_submit)
    @VisibleForTesting
    void doSubmitForLogin(){


        Log.d(TAG, "Username: "+ editTextUsername.getText() + " Password: "+ editTextPassword.getText());
        mainViewModel.doLogin(editTextUsername.getText().toString(), editTextPassword.getText().toString());
    }


}
