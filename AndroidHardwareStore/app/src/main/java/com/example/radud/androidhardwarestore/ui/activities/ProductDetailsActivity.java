package com.example.radud.androidhardwarestore.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.radud.androidhardwarestore.R;
import com.example.radud.androidhardwarestore.model.Product;
import com.example.radud.androidhardwarestore.model.Rating;
import com.example.radud.androidhardwarestore.model.Result;
import com.example.radud.androidhardwarestore.sync.ApiHelper;
import com.example.radud.androidhardwarestore.ui.adapters.ProductDetailsAdapter;
import com.example.radud.androidhardwarestore.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by radud on 03/01/2016.
 */
public class ProductDetailsActivity extends AppCompatActivity {

    public static final String PRODUCT_ID_KEY = "PRODUCT ID KEY";

    @Bind(R.id.apd_content_rv)
    RecyclerView mContentRV;

    private ProductDetailsAdapter mProductAdapter;

    private boolean mProductLoaded = false;
    private boolean mRatingsLoaded = false;
    private Product mProduct;
    private List<Rating> mRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        int id = -1;
        if (getIntent() != null) {
            id = getIntent().getIntExtra(PRODUCT_ID_KEY, -1);
        }
        loadProduct(id);
        loadRatings(id);
    }

    private void loadProduct(int id) {
        ApiHelper.getApi().getProduct(id, new Callback<Result<Product>>() {
            @Override
            public void success(Result<Product> productResult, Response response) {
                if (productResult.getHasErrors() || productResult.getResponse() == null) {
                    ToastUtils.showError(ProductDetailsActivity.this, productResult.getMessage());
                } else {
                    mProduct = productResult.getResponse();
                    setTitle(mProduct.getName());
                    mProductLoaded = true;
                    Log.e("tagtag", "product loaded");
                    checkIfLoaded();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void checkIfLoaded() {
        if (mProductLoaded && mRatingsLoaded) {
            setUpRV();
        }
    }

    private void loadRatings(int id) {
        ApiHelper.getApi().getRatings(id, new Callback<Result<List<Rating>>>() {
            @Override
            public void success(Result<List<Rating>> listResult, Response response) {
                if (!listResult.getHasErrors()) {
                    mRatings = listResult.getResponse();
                    mRatingsLoaded = true;
                    Log.e("tagtag", "ratings loaded");
                    checkIfLoaded();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void setUpRV() {
        mContentRV.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this));
        mProductAdapter = new ProductDetailsAdapter(mProduct, mRatings);
        mContentRV.setAdapter(mProductAdapter);
        mProductAdapter.notifyDataSetChanged();
    }
}
