package eg.edu.guc.android.eshop.util;


import com.squareup.picasso.Downloader;

import retrofit.Callback;
import retrofit.http.PATCH;
import retrofit.http.Path;

public interface PrivateApiRoutes {
    @PATCH("/products/{product_id}/buy")
    void patchProductBuy(@Path("product_id") long productId,
                         Callback<Downloader.Response> callback);
}
