package com.example.Varsani.Clients.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.Clients.Models.FeedbackModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import java.util.List;

public class AdapterFeedback extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FeedbackModel> items;

    private Context ctx;
    ProgressDialog progressDialog;
    private SessionHandler session;
    private UserModel user;
    public static final String TAG = "Events";
    public AdapterFeedback(Context context, List<FeedbackModel> items) {
        this.items = items;
        ctx = context;
    }
    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_comment,txv_reply;
        public OriginalViewHolder(View v) {
            super(v);

            txv_comment = v.findViewById( R.id.txv_comment);
            txv_reply =v.findViewById(R.id.txv_reply);

            session=new SessionHandler(ctx);
            user=session.getUserDetails();

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_feedback, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final FeedbackModel p = items.get(position);
            view.txv_comment.setText(p.getComment());
            if(p.getReply().equals("0")){
                view.txv_reply.setVisibility(View.GONE);
            }else{
                view.txv_reply.setText(p.getReply());
            }
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}