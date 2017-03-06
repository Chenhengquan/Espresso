package io.github.marktony.espresso.mvp.packagedetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.marktony.espresso.R;
import io.github.marktony.espresso.component.Timeline;
import io.github.marktony.espresso.data.PackageStatus;
import io.github.marktony.espresso.interfaze.OnRecyclerViewItemClickListener;

/**
 * Created by lizhaotailang on 2017/2/12.
 */

public class PackageStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final Context context;

    @NonNull
    private final LayoutInflater inflater;

    @NonNull
    private List<PackageStatus> list;

    @Nullable
    private OnRecyclerViewItemClickListener listener;

    public static final int TYPE_NORMAL = 0x00;
    public static final int TYPE_START = 0x01;
    public static final int TYPE_FINISH = 0x02;
    public static final int TYPE_SINGLE = 0x03;

    public PackageStatusAdapter(@NonNull Context context, List<PackageStatus> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PackageStatusViewHolder(inflater.inflate(R.layout.package_status_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PackageStatus item = list.get(position);
        PackageStatusViewHolder viewHolder = (PackageStatusViewHolder) holder;

        if (getItemViewType(position) == TYPE_SINGLE) {
            viewHolder.timeLine.setStartLine(null);
            viewHolder.timeLine.setFinishLine(null);
        } else if (getItemViewType(position) == TYPE_START) {
            viewHolder.timeLine.setStartLine(null);
        } else if (getItemViewType(position) == TYPE_FINISH) {
            viewHolder.timeLine.setFinishLine(null);
        }

        viewHolder.textViewTime.setText(item.getTime());
        viewHolder.textViewLocation.setText(item.getContext());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && position == list.size() - 1) {
            return TYPE_SINGLE;
        } else if (position == 0) {
            return TYPE_START;
        } else if (position == list.size() - 1) {
            return TYPE_FINISH;
        }
        return TYPE_NORMAL;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void updateData(@NonNull List<PackageStatus> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class PackageStatusViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private AppCompatTextView textViewLocation;
        private AppCompatTextView textViewTime;
        private Timeline timeLine;

        private OnRecyclerViewItemClickListener listener;

        public PackageStatusViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            textViewLocation = (AppCompatTextView) itemView.findViewById(R.id.textViewLocation);
            textViewTime = (AppCompatTextView) itemView.findViewById(R.id.textViewTime);
            timeLine = (Timeline) itemView.findViewById(R.id.timeLine);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (this.listener != null) {
                listener.OnItemClick(v, getLayoutPosition());
            }
        }
    }

}
