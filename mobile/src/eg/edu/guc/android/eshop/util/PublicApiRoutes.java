package eg.edu.guc.android.eshop.util;


import java.util.List;

import eg.edu.guc.android.eshop.model.Product;
import eg.edu.guc.android.eshop.model.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface PublicApiRoutes {
    @POST("/sessions")
    @FormUrlEncoded
    void login(@Field("session[email]") String email,
               @Field("session[password]") String password,
               Callback<User> callback);
    @GET("/products")
    void getProducts(Callback<List<Product>> callback);
}
