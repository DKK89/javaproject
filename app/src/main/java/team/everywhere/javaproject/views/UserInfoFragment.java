package team.everywhere.javaproject.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.everywhere.javaproject.R;
import team.everywhere.javaproject.adapters.UserInfoAdapter;
import team.everywhere.javaproject.databinding.FragmentUserinfoBinding;
import team.everywhere.javaproject.models.User;
import team.everywhere.javaproject.network.RetrofitService;

public class UserInfoFragment extends Fragment {
    private static final String TAG = "UserInfoFragment";
    FragmentUserinfoBinding binding;
    UserInfoAdapter adapter;
    ArrayList<User> userArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_userinfo, container, false);
        View view = binding.getRoot();

        adapter = new UserInfoAdapter(getContext(), userArrayList);
        binding.rvUserInfo.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvUserInfo.setAdapter(adapter);

        Call<JsonArray> call = RetrofitService.getInstance().getApiService().getUsers();
        Callback<JsonArray> callback = new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    for (int i = 0; i < response.body().size(); i++) {
                        userArrayList.add(gson.fromJson(response.body().get(i), User.class));
                    }
                    for (int i = 0; i < userArrayList.size(); i++) {
                        Log.d(TAG, "onResponse: user : " + userArrayList.get(i).getId());
                    }

                    adapter.notifyDataSetChanged();

                } else {
                    try {
                        Log.d(TAG, "onResponse: response : " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        };

        call.enqueue(callback);

        return view;
    }
}
