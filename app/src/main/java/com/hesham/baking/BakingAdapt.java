package com.hesham.baking;

import timber.log.Timber;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

public class BakingAdapt extends RecyclerView.Adapter<BakingAdapt.BakeViewHolder> {
    @BindView(R.id.bake_img)
    ImageView bake_image;
    @BindView(R.id.bake_text)
    TextView bake_text;

    private List<Baking> bakingItems;

    private final ListItemClickListener mOnCLickListener;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public BakingAdapt(List<Baking> bakeItems, ListItemClickListener listener) {
        bakingItems = bakeItems;
        mOnCLickListener = listener;
    }

    @Override
    public BakeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.bake_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        BakeViewHolder viewHolder = new BakeViewHolder(view);

        ButterKnife.bind(this, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BakeViewHolder holder, int position) {
        Timber.d("#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (bakingItems == null) return 0;

        return bakingItems.size();
    }

    class BakeViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public BakeViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        void bind(int index) {
            bake_text.setText(bakingItems.get(index).getName());

            if(index == 0) bake_image.setImageResource(R.drawable.nutella);
            else if(index == 1) bake_image.setImageResource(R.drawable.brownies);
            else if(index == 2) bake_image.setImageResource(R.drawable.yellowcake);
            else if(index == 3) bake_image.setImageResource(R.drawable.cheesecake);
            else bake_image.setImageResource(R.drawable.cheesecake);
        }

        @Override
        public void onClick(View view)
        {
            int clickedPosition = getAdapterPosition();
            mOnCLickListener.onListItemClick(clickedPosition);
        }
    }

    public void setData(List<Baking> baking) {
        bakingItems = baking;
        notifyDataSetChanged();
    }
}
