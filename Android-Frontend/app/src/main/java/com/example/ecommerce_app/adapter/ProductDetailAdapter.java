package com.example.ecommerce_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce_app.R;
import com.example.ecommerce_app.model.Product;

import java.util.List;
import java.util.Objects;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.CustomViewHolder> {

    ProductDetailAdapterInterface adapterInterface;
    List<Product> productList;
    public ProductDetailAdapter(List<Product> productList, ProductDetailAdapterInterface adapterInterface) {
        this.productList = productList;
        this.adapterInterface = adapterInterface;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_listing,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        if (Objects.nonNull(productList)){
            return productList.size();
        }
        return 1;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != -1) {
                        adapterInterface.onClickItem(getAdapterPosition());
                    }
                }
            });
        }
        public void bind(Product product)
        {


            // for image

            String productImg=product.getProductImg();
            ImageView ivProductImageProductListing= view.findViewById(R.id.iv_product_image_product_listing);
            Glide.with(ivProductImageProductListing.getContext()).load(String.valueOf(productImg)).into(ivProductImageProductListing);
            // now text
            TextView tvProductNameProductListing = view.findViewById(R.id.tv_product_name_product_listing);
            String productName = product.getProductName();
            tvProductNameProductListing.setText(productName);

            TextView tvProductRAMProductListing = view.findViewById(R.id.tv_productRAM_product_listing);
            String productRAM =product.getProductRAM();
            tvProductRAMProductListing.setText(productRAM);

            TextView tvStorageProductListing =view.findViewById(R.id.tv_storage_product_listing);
            String productStorage = product.getProductStorage();
            tvStorageProductListing.setText(productStorage);

            TextView tvProcessorProductListing=view.findViewById(R.id.tv_processor_product_listing);
            String productProcessor=product.getProductProcessor();
            tvProcessorProductListing.setText(productProcessor);

            TextView tvBatteryProductListing=view.findViewById(R.id.tv_battery_product_listing);
            String  productBattery=product.getProductBattery();
            tvBatteryProductListing.setText(productBattery);


            Button buttonProductListing = view.findViewById(R.id.button_product_listing);
            buttonProductListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=-1)
                    {
                        adapterInterface.onClickItem(getAdapterPosition());

                    }
                }
            });

        }

    }

    public interface ProductDetailAdapterInterface {
        public void onClickItem(int position);
    }
}
