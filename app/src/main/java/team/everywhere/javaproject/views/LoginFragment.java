package team.everywhere.javaproject.views;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.everywhere.javaproject.R;
import team.everywhere.javaproject.databinding.FragmentLoginBinding;
import team.everywhere.javaproject.models.User;
import team.everywhere.javaproject.network.RetrofitService;
import team.everywhere.javaproject.utils.SaveSharedPreference;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    FragmentLoginBinding binding;
    OnLoginListener loginListener;

    public interface OnLoginListener {
        void onLogin(User user);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginListener) {
            loginListener = (OnLoginListener) getContext();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        View view = binding.getRoot();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        Bundle bundle = getArguments();
        Log.d(TAG, "onCreateView: bundle : " + bundle);

        init();

        setListener();

        return view;
    }


    private void init() {
        String autoLogin = SaveSharedPreference.getAutoLogin(getContext());
        if (autoLogin.contains("true")) {
            binding.btnCheckRemember.setChecked(true);

            String id = SaveSharedPreference.getUserEmail(getContext());
            String password = SaveSharedPreference.getUserPassword(getContext());
            binding.etId.setText(id);
            binding.etPassword.setText(password);

            checkLogin();
        }
    }


    private void setListener() {
        binding.btnSignUp.setOnClickListener(v -> {
            Log.d(TAG, "onClick: called");
            SignUpFragment fragment = new SignUpFragment();
            getFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).addToBackStack("SignUp").commit();

        });

        binding.btnLogin.setOnClickListener((v) -> {
            checkLogin();

        });

        binding.etId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }


    private void checkLogin() {
        String id = binding.etId.getText().toString();
        String password = binding.etPassword.getText().toString();

        boolean isAutoLogin = binding.btnCheckRemember.isChecked();

        User user = new User(id, password);
        Call<JsonObject> call = RetrofitService.getInstance().getApiService().signIn(user);
        Callback<JsonObject> callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (isAutoLogin) {
                        SaveSharedPreference.setAutoLogin(getContext(), "true");
                        SaveSharedPreference.setUserInfo(getContext(), id, password);
                    } else {
                        SaveSharedPreference.clearUserInfo(getContext());
                    }
                    JsonObject jsonObject = response.body().getAsJsonObject("result");
                    Gson gson = new Gson();
                    User user = gson.fromJson(jsonObject, User.class);

                    Log.d(TAG, "onResponse: userinfo : " + user.getId());

                    loginListener.onLogin(user);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: call : " + call);
                t.printStackTrace();
            }
        };
        call.enqueue(callback);
    }

    private void checkLogin2() {
        String id = binding.etId.getText().toString();
        String password = binding.etPassword.getText().toString();
        User user = new User(id, password);
        Call<JsonObject> call = RetrofitService.getInstance().getApiService().signIn(user);
        Callback<JsonObject> callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    Log.d(TAG, "onResponse: jsonObject : " + jsonObject);

                    getActivity().onBackPressed();
//                    getFragmentManager().beginTransaction().detach(LoginFragment.this).commit();
//                    getFragmentManager().popBackStack();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: call : " + call);
                t.printStackTrace();
            }
        };
        call.enqueue(callback);
    }
}
