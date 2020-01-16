package team.everywhere.javaproject.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import team.everywhere.javaproject.models.User;

public interface ApiService {

    @POST("user")
    Call<JsonObject> signUp(@Body User user);

//    @POST("user")
//    Call<User> signUp(@Body User user);


    @POST("user/signIn")
    Call<JsonObject> signIn(@Body User user);

    @GET("user")
    Call<JsonArray> getUsers();


    @GET("user")
    Call<JsonObject> getLogin(@Query("id") String id,
                              @Query("password") String password);

    @PUT("user/{idx}")
    Call<JsonObject> putLogin(@Path("idx") String idx);

}
