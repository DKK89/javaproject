package team.everywhere.javaproject.views;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.everywhere.javaproject.R;
import team.everywhere.javaproject.databinding.FragmentSignUpBinding;
import team.everywhere.javaproject.models.User;
import team.everywhere.javaproject.network.RetrofitService;

public class SignUpFragment extends Fragment {
    private static final String TAG = "SignUpFragment";
    FragmentSignUpBinding binding;

    private int age;

    private String userIdx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        View view = binding.getRoot();

        init();

        setListener();

        return view;
    }

    private void init() {

    }


    private void setListener() {
        binding.sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.tvAge.setText(String.valueOf(progress));
                age = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestApi();
            }
        });
    }

    public void requestApi() {
        String id = binding.etID.getText().toString();
        String password = binding.etPassword.getText().toString();
        String email = binding.etEmail.getText().toString();
        String name = binding.etName.getText().toString();
        String sex;

        if (binding.rbMan.isChecked()) {
            sex = "0";
        } else {
            sex = "1";
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "잘못된 이메일 형식입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(id, password, email, name, sex, age);

        Call<JsonObject> call = RetrofitService.getInstance().getApiService().signUp(user);
        Callback<JsonObject> callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());

                    getActivity().onBackPressed();
//                    getFragmentManager().popBackStack();
//                    getFragmentManager().beginTransaction().detach(SignUpFragment.this).commit();
                } else {
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        };
        call.enqueue(callback);
    }
}
