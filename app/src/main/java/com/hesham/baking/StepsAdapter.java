package com.hesham.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    @BindView(R.id.step_text) TextView step_text;

    private final ListItemClickListener mOnCLickListener;
    private List<Baking.steps> mStepsList;

    public StepsAdapter(List<Baking.steps> StepsItems, StepsAdapter.ListItemClickListener listener) {
        mStepsList = StepsItems;
        mOnCLickListener = listener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Timber.d("#" + position);
        holder.bind(position);
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.steps_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        StepViewHolder viewHolder = new StepViewHolder(view);

        ButterKnife.bind(this, view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        if (mStepsList == null) return 0;

        return mStepsList.size();
    }

    public void setData(List<Baking.steps> StepsList) {
        mStepsList = StepsList;
        notifyDataSetChanged();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public StepViewHolder(View itemView) {
            super(itemView);

            //This will also cache the view items
            itemView.setOnClickListener(this);
        }

        void bind(int index) {
           step_text.setText(mStepsList.get(index).shortDescription);
        }

        @Override
        public void onClick(View view)
        {
            int clickedPosition = getAdapterPosition();
            mOnCLickListener.onListItemClick(clickedPosition);
        }

    }
}
