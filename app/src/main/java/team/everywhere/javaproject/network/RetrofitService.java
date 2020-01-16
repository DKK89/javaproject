package team.everywhere.javaproject.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import team.everywhere.javaproject.BuildConfig;

public class RetrofitService {
    private Retrofit retrofit;
    private ApiService apiService;
    private static RetrofitService retrofitService = new RetrofitService();

    private RetrofitService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static RetrofitService getInstance() {
        return retrofitService;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
