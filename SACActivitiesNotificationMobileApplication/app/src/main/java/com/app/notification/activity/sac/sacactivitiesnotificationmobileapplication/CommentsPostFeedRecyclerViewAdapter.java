package com.app.notification.activity.sac.sacactivitiesnotificationmobileapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Keji's Lab on 19/01/2018.
 */

public class CommentsPostFeedRecyclerViewAdapter extends RecyclerView.Adapter<CommentsPostFeedRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<CommentDataModel> commentDataModels;
    private Context context;
    private ArrayList<Boolean> getNull;
    private int Comment_Number;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    public class MyViewHolder extends RecyclerView.ViewHolder{

       public TextView lblauthor,message,timestamp;


        public MyViewHolder(View view){
            super(view);
            lblauthor = (TextView) view.findViewById(R.id.username);
            message = (TextView) view.findViewById(R.id.message);
            timestamp = (TextView) view.findViewById(R.id.timestamp);




        }
    }

    public CommentsPostFeedRecyclerViewAdapter(Context c, ArrayList<CommentDataModel> commentDataModelArrayList){
        this.commentDataModels = commentDataModelArrayList;
        this.context =c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_row,parent,false);


        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        CommentDataModel commentDataModel = commentDataModels.get(position);
        holder.lblauthor.setText(commentDataModel.getAuthor());
        holder.message.setText(commentDataModel.getMsg());
        holder.timestamp.setText(commentDataModel.getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return commentDataModels.size();
    }
}


