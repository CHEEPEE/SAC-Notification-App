package com.app.notification.activity.sac.sacactivitynotificationstudent;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keji's Lab on 19/01/2018.
 */

public class PostFeedRecyclerViewAdapter extends RecyclerView.Adapter<PostFeedRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<PostFeedModel> postFeedModels;
    private Context context;
    private ArrayList<Boolean> getNull;
    private int Comment_Number;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,
                author,
                bodyContent,
                commentNumber,
                timeDate,
                lblComments,
                btnSend,
                postTitle,btnDone;
        public EditText inputMessage;
        public ImageView edit;
        public RecyclerView commentList;


        public MyViewHolder(View view){
            super(view);

            edit = (ImageView) view.findViewById(R.id.options);
            lblComments = (TextView) view.findViewById(R.id.lblComments);
            author = (TextView) view.findViewById(R.id.username);
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PostFeedModel postFeedModel = postFeedModels.get(position);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getUid().equals(postFeedModel.getmAuthorID())){
            holder.edit.setVisibility(View.VISIBLE);
        }else {
            holder.edit.setVisibility(View.INVISIBLE);
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setCancelable(true);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.edit_post);
                final TextInputEditText titleInput = (TextInputEditText) dialog.findViewById(R.id.inputTitle);
                final TextInputEditText message = (TextInputEditText) dialog.findViewById(R.id.inputMessage);
                TextView btnDelete = (TextView) dialog.findViewById(R.id.btnDelete);
                titleInput.setText(postFeedModel.getPostTitle());
                message.setText(postFeedModel.getContent());
                holder.btnDone = (TextView) dialog.findViewById(R.id.btnDone);
                holder.btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = postFeedModel.getKey();
                        CreatePostMapModel createPostMapModel = new CreatePostMapModel("Admin",titleInput.getText().toString(),message.getText().toString(),"Empty","null",key,mAuth.getCurrentUser().getUid(),UtilsTools.getDateToStrig());
                        Map<String,Object> postValue = createPostMapModel.toMap();
                        Map<String,Object> childUpdates = new HashMap<>();
                        childUpdates.put(key,postValue);
                        FirebaseDatabase.getInstance().getReference().child("OpenForumPost").updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(context,"Error Posting", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("OpenForumPost").child(postFeedModel.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                            }
                        });
                    }
                });


                dialog.show();
            }
        });

        mdatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        holder.author.setText(postFeedModel.getAuthor());
        holder.timeDate.setText(postFeedModel.getmTimeStamp());
        holder.title.setText(postFeedModel.getPostTitle());
        holder.bodyContent.setText(postFeedModel.getContent());
        holder.lblComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        final ArrayList<CommentDataModel> commentDataModels = new ArrayList<>();
                final Dialog commentDialog = new Dialog(context);
                commentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                commentDialog.setCancelable(true);
                commentDialog.show();
                commentDialog.setContentView(R.layout.dialog_comments);
                holder.postTitle = (TextView) commentDialog.findViewById(R.id.postTitle);
                holder.btnSend = (TextView) commentDialog.findViewById(R.id.btnSubmit);
                holder.inputMessage = (EditText) commentDialog.findViewById(R.id.inputMessage);
                holder.commentList = (RecyclerView) commentDialog.findViewById(R.id.commentList);
                holder.postTitle.setText(postFeedModel.getPostTitle());
                holder.inputMessage.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!s.equals("")){
                            holder.btnSend.setVisibility(View.VISIBLE);
                        }else {
                            holder.btnSend.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                holder.btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addComment(holder.inputMessage,position);
                    }
                });

                final CommentsPostFeedRecyclerViewAdapter commentsPostFeedRecyclerViewAdapter = new CommentsPostFeedRecyclerViewAdapter(context,commentDataModels);
                holder.commentList.setLayoutManager(new LinearLayoutManager(context));
                holder.commentList.setAdapter(commentsPostFeedRecyclerViewAdapter);

                mdatabase.child("OpenForumPostComments").child(postFeedModel.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        commentDataModels.clear();
                         for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                             CommentMapModel commentMapModel = dataSnapshot1.getValue(CommentMapModel.class);
                             CommentDataModel commentDataModel = new CommentDataModel();
                             commentDataModel.setAuthor(commentMapModel.author);
                             commentDataModel.setMsg(commentMapModel.message);
                             commentDataModel.setTimeStamp(commentMapModel.timeStamp);
                             commentDataModels.add(commentDataModel);
                             commentsPostFeedRecyclerViewAdapter.notifyDataSetChanged();
                         }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return postFeedModels.size();
    }


    private void addComment(final EditText comment,final int position){
  String key = mdatabase.push().getKey();
  CommentMapModel commentMapModel = new CommentMapModel(mAuth.getCurrentUser().getDisplayName(),comment.getText().toString(),UtilsTools.getDateToStrig());
  Map<String,Object> commentValue = commentMapModel.toMap();
  Map<String,Object> childupdates = new HashMap<>();
  childupdates.put(key,commentValue);
  mdatabase.child("OpenForumPostComments").child(postFeedModels.get(position).getKey()).updateChildren(childupdates).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {
          comment.setText("");
      }
  });

    }
}


