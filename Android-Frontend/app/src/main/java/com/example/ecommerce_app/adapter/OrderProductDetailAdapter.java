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
import com.example.ecommerce_app.model.CartItems;
import com.example.ecommerce_app.model.OrderItems;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductDetailAdapter extends RecyclerView.Adapter<OrderProductDetailAdapter.OrderViewHolder>{

    ApiInterface apiInterfaceProduct;
    Product product;

    Integer qty;
    OrderProductDetailAdapter.OrderProductDetailAdapterInterface adapterInterface;
    List<OrderItems> orderItemsList=new ArrayList<>();
    Button buttonViewDetailsOrderProductListing;

    TextView tvOrderQuantityProductListing;
    TextView tvPriceOrderProductListing;

    public OrderProductDetailAdapter(List<OrderItems> orderItemsList, OrderProductDetailAdapter.OrderProductDetailAdapterInterface adapterInterface, ApiInterface apiInterfaceProduct) {
        this.orderItemsList = orderItemsList;
        this.adapterInterface = adapterInterface;
        this.apiInterfaceProduct = apiInterfaceProduct;
    }

    @NonNull
    @Override
    public OrderProductDetailAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_product_listing ,parent,false);
        return new OrderProductDetailAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductDetailAdapter.OrderViewHolder holder, int position) {
        holder.bind(orderItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder{

        View view;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=-1)
                    {
                        adapterInterface.onClickItem(getAdapterPosition(), product);

                    }                }
            });

        }
        void bind(OrderItems orderItems)
        {


            apiInterfaceProduct.getProductById(orderItems.getProductId()).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    product = response.body();
                    tvOrderQuantityProductListing= view.findViewById(R.id.tv_order_quantity_product_listing);

                    Log.i("Response",""+response.body());

                    setProductDetails(product);

                    tvOrderQuantityProductListing.setText(String.valueOf(orderItems.getProductQuantity()));
                    tvPriceOrderProductListing=view.findViewById(R.id.tv_order_price_product_listing);
                    String productPriceOrder= "Rs."+ orderItems.getProductPrice();
                    tvPriceOrderProductListing.setText(productPriceOrder);

                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Log.e("Response failed",""+t);

                }
            });

        }


        public void setProductDetails(Product product) {
            String productImgOrder=product.getProductImg();
            ImageView ivProductImageOrderProductListing= view.findViewById(R.id.iv_order_image_product_listing);
            Glide.with(ivProductImageOrderProductListing.getContext()).load(String.valueOf(productImgOrder)).into(ivProductImageOrderProductListing);
            // now text
            TextView tvProductNameOrderProductListing = view.findViewById(R.id.tv_order_name_product_listing);
            String productNameOrder = product.getProductName();
            tvProductNameOrderProductListing.setText(productNameOrder);

            TextView tvProductRAMOrderProductListing = view.findViewById(R.id.tv_order_RAM_product_listing);
            String productRAMOrder =product.getProductRAM();
            tvProductRAMOrderProductListing.setText(productRAMOrder);

            TextView tvStorageOrderProductListing =view.findViewById(R.id.tv_order_storage_product_listing);
            String productStorageOrder = product.getProductStorage();
            tvStorageOrderProductListing.setText(productStorageOrder);


            TextView tvBatteryOrderProductListing=view.findViewById(R.id.tv_order_battery_product_listing);
            String  productBatteryOrder=product.getProductBattery();
            tvBatteryOrderProductListing.setText(productBatteryOrder);


//            buttonViewDetailsOrderProductListing= view.findViewById(R.id.button_view_details_order_product_listing);
//
//
//            Button buttonOrderProductListing = view.findViewById(R.id.button_view_details_order_product_listing);
//            buttonOrderProductListing.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(getAdapterPosition()!=-1)
//                    {
//                        adapterInterface.onClickItem(getAdapterPosition(), product);
//
//                    }
//                }
//            });




        }
    }
    public interface OrderProductDetailAdapterInterface {

        public void onClickItem(int position, Product product);


    }

}
