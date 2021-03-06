package pw.gike.gikeweibo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;


import java.util.List;

import pw.gike.gikeweibo.R;
import pw.gike.gikeweibo.bean.statuses.Status;
import pw.gike.gikeweibo.bean.statuses.Weibo;

public class WeiboAdapter extends RecyclerView.Adapter<WeiboAdapter.ViewHolder> implements View.OnClickListener{

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    private List<Status> mWeiboStatuses;

//    private Weibo mWeibo;

    private Context context;

    private LinearLayout lyComment;

    public interface CallbackListener <T> {
        public void callback(T data);
    }

    private CallbackListener callbackListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_status, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        // 为ItemView添加点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Status status = mWeiboStatuses.get(i);
        Glide.with(context)
                .load(status.getUser().getProfileImageUrl())

                .into(viewHolder.imHead);
        viewHolder.tvUsername.setText(status.getUser().getName());
        viewHolder.tvTime.setText(status.getCreatedAt());
        viewHolder.tvContent.setText(status.getText());
        viewHolder.btComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyComment.setVisibility(View.VISIBLE);
                callbackListener.callback(status.getId());
            }
        });
        viewHolder.itemView.setTag(i);
    }

    public void addList(List<Status> mWeiboStatuses) {
        this.mWeiboStatuses.addAll(mWeiboStatuses);
        notifyDataSetChanged();
    }

    public void refresh(List<Status> mWeiboStatuses) {
        this.mWeiboStatuses.clear();
        this.mWeiboStatuses.addAll(mWeiboStatuses);
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return mWeiboStatuses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imHead;
        TextView tvUsername;
        TextView tvTime;
        TextView tvContent;
        ImageButton btComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imHead = itemView.findViewById(R.id.im_head);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
            btComment = itemView.findViewById(R.id.bt_comment);

        }
    }

    public WeiboAdapter(Context context, LinearLayout lyComment, CallbackListener callbackListener,List<Status> mWeiboStatuses) {
        this.mWeiboStatuses = mWeiboStatuses;
        this.context = context;
        this.lyComment = lyComment;
        this.callbackListener = callbackListener;
    }

}
