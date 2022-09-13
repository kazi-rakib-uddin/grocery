package com.example.erashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.Model.SearchModel;
import com.example.erashop.R;
import com.example.erashop.databinding.CustomSearchResultBinding;
import com.example.erashop.databinding.SinglePopulerItemBinding;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    ArrayList<SearchModel> searchModels = new ArrayList<>();
    Context context;

    public SearchAdapter(ArrayList<SearchModel> searchModels, Context context) {
        this.context = context;
        this.searchModels = searchModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        CustomSearchResultBinding binding = CustomSearchResultBinding.inflate(inflater,parent,false);
        return new SearchAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.searchItemName.setText(searchModels.get(position).getName());
        holder.binding.searchItemOGPrice.setText("₹"+searchModels.get(position).getOG_price());
        holder.binding.searchItemPrice.setText("₹"+searchModels.get(position).getPrice());
        holder.binding.searchImage.setImageResource(Integer.parseInt(searchModels.get(position).getImage()));

        holder.binding.IncDec.setVisibility(View.GONE);

        holder.binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.IncDecText.setText("1");
                holder.binding.addBtn.setVisibility(View.GONE);
                holder.binding.IncDec.setVisibility(View.VISIBLE);
                IncreaseDecrease(holder);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProduct.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomSearchResultBinding binding;


        public ViewHolder(@NonNull CustomSearchResultBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
//            search_image = itemView.findViewById(R.id.search_image);
//            search_item_name = itemView.findViewById(R.id.search_item_name);
//            search_item_price = itemView.findViewById(R.id.search_item_price);
//            search_item_OG_price = itemView.findViewById(R.id.search_item_OG_price);


        }
    }

    private void IncreaseDecrease(ViewHolder holder) {
        final int[] count = {1};

        holder.binding.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] += 1;
                holder.binding.IncDecText.setText(""+ count[0]);
            }
        });

        holder.binding.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count[0] == 1){
                    holder.binding.addBtn.setVisibility(View.VISIBLE);
                    holder.binding.IncDec.setVisibility(View.GONE);
                }else{
                    count[0] -= 1;
                    holder.binding.IncDecText.setText(""+ count[0]);
                }
            }
        });

    }
}
