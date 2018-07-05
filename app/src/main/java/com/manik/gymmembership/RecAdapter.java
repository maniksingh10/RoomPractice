package com.manik.gymmembership;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.manik.gymmembership.AppDatabase.Member;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.provider.Settings.System.DATE_FORMAT;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MemberViewHolder>{

    private List<Member> mTaskEntries;
    private Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault());
    final private ItemClickListener mItemClickListener;

    public RecAdapter(DisplayMembers mContext, ItemClickListener listenerr ) {
        this.mContext = mContext;
        mItemClickListener = listenerr;
    }




    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.member_details ,parent,false);

        return new MemberViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {

        Member member = mTaskEntries.get(position);
        String name = member.getName();
        String branch =member.getBranch();
        String date = dateFormat.format(member.getJoindate());
        String mid = String.valueOf(member.getId());
        String gender= member.getGender();

        holder.tdate.setText(date);
        holder.tid.setText(mid);
        holder.tname.setText(name);
        holder.tbranch.setText(branch);
        holder.tgender.setText(gender);

    }


    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<Member> getMembersEntries() {
        return mTaskEntries;
    }

    public void setTasks(List<Member> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }


    public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tid, tname, tgender, tbranch, tdate;

        public MemberViewHolder(View itemView) {
            super(itemView);

            tid=itemView.findViewById(R.id.tvmemberid);
            tname=itemView.findViewById(R.id.tvname);
            tgender=itemView.findViewById(R.id.tvgender);
            tdate=itemView.findViewById(R.id.tvjoindate);
            tbranch=itemView.findViewById(R.id.tvbranch);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }


}
