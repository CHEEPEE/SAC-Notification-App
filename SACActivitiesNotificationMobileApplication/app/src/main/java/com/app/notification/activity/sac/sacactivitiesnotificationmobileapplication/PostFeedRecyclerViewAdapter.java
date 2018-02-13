package com.app.notification.activity.sac.sacactivitiesnotificationmobileapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Keji's Lab on 19/01/2018.
 */

public class PostFeedRecyclerViewAdapter extends RecyclerView.Adapter<PostFeedRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<PostFeedModel> postFeedModels;
    private Context context;
    private ArrayList<Boolean> getNull;
    private int Comment_Number;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,author,bodyContent,commentNumber,timeDate;



        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.postHeader);
            bodyContent = (TextView) view.findViewById(R.id.postMessage);
            timeDate = (TextView) view.findViewById(R.id.lblTimeStamp);

        }
    }

    public PostFeedRecyclerViewAdapter(Context c, ArrayList<PostFeedModel> postFeedModels){
        this.postFeedModels = postFeedModels;
        this.context =c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_admin_news_feed_layout,parent,false);


        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PostFeedModel postFeedModel = postFeedModels.get(position);


        holder.timeDate.setText(postFeedModel.getmTimeStamp());
        holder.title.setText(postFeedModel.getPostTitle());
        holder.bodyContent.setText(postFeedModel.getContent());
    }

    @Override
    public int getItemCount() {
        return postFeedModels.size();
    }
}


