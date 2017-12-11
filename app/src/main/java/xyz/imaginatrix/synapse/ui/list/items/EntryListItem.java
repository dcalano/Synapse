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
import xyz.imaginatrix.synapse.data.models.Entry;

public class EntryListItem extends AbstractItem<EntryListItem, EntryListItem.ViewHolder> {

    private Entry entry;

    public EntryListItem(Entry entry) {
        this.entry = entry;
    }

    @Override public int getType() {
        return R.id.listing_card_id;
    }

    @Override public int getLayoutRes() {
        return R.layout.item_entry_card;
    }

    @Override public void bindView(ViewHolder viewHolder, List<Object> payloads) {
    	super.bindView(viewHolder, payloads);

        viewHolder.title.setText(entry.getTitle());

        if (entry.getClassifications() != null) {
            Classification primaryClassification = entry.getClassifications().get(0);
            if (primaryClassification.getCategory() != null) {
                viewHolder.category.setText(primaryClassification.getCategory().getName());
            } else {
                viewHolder.category.setText(primaryClassification.getSubjectName());
            }
        } else {
            viewHolder.category.setVisibility(View.GONE);
        }


    }

    //reset the view here (this is an optional method, but recommended)
    @Override public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.title.setText(null);
        holder.category.setText(null);
    }

    //Init the viewHolder for this Item
    @Override public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }


    public Entry getEntry() { return entry; }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_paper_title) TextView title;
        @BindView(R.id.item_paper_category) TextView category;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}