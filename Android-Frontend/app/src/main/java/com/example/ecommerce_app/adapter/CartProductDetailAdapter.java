package com.example.ecommerce_app.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce_app.R;
import com.example.ecommerce_app.application.MyApplication;
import com.example.ecommerce_app.model.CartItems;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductDetailAdapter extends RecyclerView.Adapter<CartProductDetailAdapter.CartViewHolder> {

    ApiInterface apiInterfaceProduct;
    Product product;

    Integer qty;
    CartProductDetailAdapter.CartProductDetailAdapterInterface adapterInterface;
    List<CartItems> cartItemsList=new ArrayList<>();
    Button buttonMinusCartProductListing;
    Button buttonPlusCartProductListing;
    ImageButton ibRemoveCartProductListing;
    TextView tvCartQuantityProductListing;
    TextView tvPriceCartProductListing;

    ImageView ivProductImageCartProductListing;
    TextView tvProductNameCartProductListing;
    TextView tvProductRAMCartProductListing;
    TextView tvStorageCartProductListing;
    TextView tvBatteryCartProductListing;

    public CartProductDetailAdapter(List<CartItems> cartItemsList, CartProductDetailAdapter.CartProductDetailAdapterInterface adapterInterface, ApiInterface apiInterfaceProduct) {
        this.cartItemsList = cartItemsList;
        this.adapterInterface = adapterInterface;
        this.apiInterfaceProduct = apiInterfaceProduct;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product_listing ,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(cartItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder{

        View view;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;

        }
        void bind(CartItems cartItems)
        {


            apiInterfaceProduct.getProductById(cartItems.getProductId()).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    product = response.body();
//
                    Log.i("Response",""+response.body());
                    setProductDetails(product);
                    tvCartQuantityProductListing= view.findViewById(R.id.tv_cart_quantity_product_listing);
                    tvCartQuantityProductListing.setText(String.valueOf(cartItems.getProductQuantity()));
                    tvPriceCartProductListing=view.findViewById(R.id.tv_cart_price_product_listing);
                    String productPriceCart= "Rs."+String.valueOf(cartItems.getProductPrice());
                    tvPriceCartProductListing.setText(productPriceCart);

                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Log.e("Response failed",""+t);

                }
            });

        }


        public void setProductDetails(Product product) {
            String productImgCart=product.getProductImg();
            ivProductImageCartProductListing= view.findViewById(R.id.iv_cart_image_product_listing);
            Glide.with(ivProductImageCartProductListing.getContext()).load(String.valueOf(productImgCart)).into(ivProductImageCartProductListing);
            // now text
            tvProductNameCartProductListing = view.findViewById(R.id.tv_cart_name_product_listing);
            String productNameCart = product.getProductName();
            tvProductNameCartProductListing.setText(productNameCart);

            tvProductRAMCartProductListing = view.findViewById(R.id.tv_cart_RAM_product_listing);
            String productRAMCart =product.getProductRAM();
            tvProductRAMCartProductListing.setText(productRAMCart);

            tvStorageCartProductListing =view.findViewById(R.id.tv_cart_storage_product_listing);
            String productStorageCart = product.getProductStorage();
            tvStorageCartProductListing.setText(productStorageCart);


            tvBatteryCartProductListing=view.findViewById(R.id.tv_cart_battery_product_listing);
            String  productBatteryCart=product.getProductBattery();
            tvBatteryCartProductListing.setText(productBatteryCart);


            buttonMinusCartProductListing= view.findViewById(R.id.button_minus_cart_product_listing);
            buttonPlusCartProductListing= view.findViewById(R.id.button_plus_cart_product_listing);
            ibRemoveCartProductListing= view.findViewById(R.id.ib_remove_cart_product_listing);




            buttonMinusCartProductListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textMinus = String.valueOf(tvCartQuantityProductListing.getText());
                    Integer textMinusValue = Integer.valueOf(textMinus);
                    if(textMinusValue==1) {
                        adapterInterface.onRemoveClickItem(getAdapterPosition());
                        Toast.makeText(view.getContext(), "Quantity can't be less than 0", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(getAdapterPosition()!=-1)
                        {
                            adapterInterface.onMinusClickItem(getAdapterPosition());

                        }
                     //   tvCartQuantityProductListing.setText(String.valueOf(textMinusValue-1));
                    }

                }
            });

            buttonPlusCartProductListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(getAdapterPosition()!=-1)
                    {
                        adapterInterface.onPlusClickItem(getAdapterPosition());

                    }

//                    tvCartQuantityProductListing.setText(String.valueOf(textPlusValue+1));

                }
            });


            ibRemoveCartProductListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=-1)
                    {
                        adapterInterface.onRemoveClickItem(getAdapterPosition());

                    }
                }
            });





        }
    }
    public interface CartProductDetailAdapterInterface {

        public void onMinusClickItem(int position);
        public void onPlusClickItem(int position);
        public void onRemoveClickItem(int position);


    }


}
