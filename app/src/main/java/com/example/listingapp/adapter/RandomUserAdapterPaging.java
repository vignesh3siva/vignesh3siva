package com.example.listingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listingapp.R;
import com.example.listingapp.app.App;
import com.example.listingapp.roomDB.model.RandomUserModel;
import com.squareup.picasso.Picasso;

public class RandomUserAdapterPaging extends PagedListAdapter<RandomUserModel, RandomUserAdapterPaging.ViewHolder> {
    public PagedList<RandomUserModel> data;
    public Activity activity;
    public Context context;

    private static DiffUtil.ItemCallback<RandomUserModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<RandomUserModel>() {
                // RandomUserModel details may have changed if reloaded from the database,
                // but ID is fixed.


                @Override
                public boolean areItemsTheSame(RandomUserModel oldConcert, RandomUserModel newConcert) {
                    return oldConcert.getId() == newConcert.getId();
                }

                @Override
                public boolean areContentsTheSame(RandomUserModel oldConcert,
                                                  RandomUserModel newConcert) {
                    return oldConcert.equals(newConcert);
                }
            };


    public  RandomUserAdapterPaging(Activity activity){
        super(DIFF_CALLBACK);
        this.activity=activity;
    }

    @NonNull
    @Override
    public RandomUserAdapterPaging.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        this.context=context;

        View contactView = inflater.inflate(R.layout.random_user_adapter_sin, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    public PagedList<RandomUserModel> getItems() {
        return getCurrentList();
    }

    public void setData(PagedList<RandomUserModel> data){
        this.data=data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RandomUserAdapterPaging.ViewHolder holder, int position) {


        Picasso.with(holder.itemView.getContext()).load(data.get(position).getLarge()).placeholder(holder.itemView.getContext().getResources().getDrawable(R.drawable.index)).into(holder.img_large_view_lf);
        holder.txt_large_view_lf.setText(""+data.get(position).getFirst()+" "+data.get(position).getLast());



    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public void getFilter(String charSequence) {


        String charString = charSequence.toString();
        new LivePagedListBuilder(App.get().getDB().randomUserDAO().searchFirstAndLastNamePaging("%" + charString + "%"), 50).build().observe((LifecycleOwner)context, new Observer<PagedList<RandomUserModel>>() {
            @Override
            public void onChanged(PagedList<RandomUserModel> t) {
                submitList(t);

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_large_view_lf;
        TextView txt_large_view_lf;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_large_view_lf=itemView.findViewById(R.id.img_large_view_lf);
            txt_large_view_lf=itemView.findViewById(R.id.txt_large_view_lf);
        }
    }
}
