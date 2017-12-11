package xyz.imaginatrix.synapse.ui.list.items;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.data.models.Classification;

public class EntryClassificationItem extends AbstractItem<EntryClassificationItem, EntryClassificationItem.ViewHolder> {

    private Classification classification;

    public EntryClassificationItem(Classification classification) {
        this.classification = classification;
    }

    @Override public int getType() { return R.id.listing_classification_id; }

    @Override public int getLayoutRes() { return R.layout.item_entry_classification; }

    @Override public void bindView(ViewHolder viewHolder, List<Object> payloads) {
    	super.bindView(viewHolder, payloads);

        if (classification.getCategory() != null) {
            viewHolder.primaryText.setText(classification.getCategory().getName());
            viewHolder.secondaryText.setText(classification.getSubjectName());
        } else {
            viewHolder.primaryText.setText(classification.getSubjectName());
            viewHolder.secondaryText.setVisibility(View.GONE);
        }
    }

    //reset the view here (this is an optional method, but recommended)
    @Override public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.primaryText.setText(null);
        holder.secondaryText.setText(null);
    }

    //Init the viewHolder for this Item
    @Override public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_entry_classification_primary) TextView primaryText;
        @BindView(R.id.item_entry_classification_secondary) TextView secondaryText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}