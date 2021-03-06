package com.example.radud.androidhardwarestore.sync;

import com.example.radud.androidhardwarestore.model.CartItem;
import com.example.radud.androidhardwarestore.model.Member;
import com.example.radud.androidhardwarestore.model.Order;
import com.example.radud.androidhardwarestore.model.PriceItem;
import com.example.radud.androidhardwarestore.model.Product;
import com.example.radud.androidhardwarestore.model.ProductCategory;
import com.example.radud.androidhardwarestore.model.Rating;
import com.example.radud.androidhardwarestore.model.Result;
import com.example.radud.androidhardwarestore.model.ShoppingCart;
import com.example.radud.androidhardwarestore.sync.requests.AddRatingHolder;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by profiralexandr on 10/11/15.
 */
public interface HardwareStoreApi {

    @POST("/member/shoppingCart/add/{id}")
    void addCartItem(@Path("id") int memberId, @Body CartItem cartItem, Callback<Result> callback);

    @POST("/product/rating/add/{id}")
    void addRating(@Path("id") int productId, @Body AddRatingHolder holder, Callback<Result> callback);

    @GET("/member/get/{id}")
    void getMember(@Path("id") int memberId, Callback<Member> member);

    @GET("/product/category/get/{id}")
    void getProducts(@Path("id") int categoryId, Callback<Result<List<Product>>> products);

    @GET("/product/get/{id}")
    void getProduct(@Path("id") int productId, Callback<Result<Product>> product);

    @GET("/product/rating/get/{id}")
    void getRatings(@Path("id") int productId, Callback<Result<List<Rating>>> ratings);

    @GET("/product/priceItem/get/{id}")
    void getPriceItems(@Path("id") int productId, Callback<Result<List<PriceItem>>> priceItems);

    @GET("/product/get")
    void getProducts(Callback<Result<List<Product>>> products);

    @GET("/product/category/get")
    void getCategories(Callback<Result<List<ProductCategory>>> categories);

    @FormUrlEncoded
    @POST("/member/login")
    void login(@Field("username") String username, @Field("password") String password, Callback<Result<Member>> callback);

    @POST("/member/register")
    void register(@Body Member member, Callback<Result<Member>> callback);

    @GET("/member/shoppingCart/get/{id}")
    void getShoppingCart(@Path("id") int memberId, Callback<Result<ShoppingCart>> resultCallback);

    @GET("/member/orders/get/{id}")
    void getOrders(@Path("id") int memberId, Callback<Result<List<Order>>> resultCallback);
}
