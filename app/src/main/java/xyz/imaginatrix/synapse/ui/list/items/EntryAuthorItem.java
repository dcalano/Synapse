package xyz.imaginatrix.synapse.ui.list.items;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.data.models.Author;
import xyz.imaginatrix.synapse.data.models.RealmString;

public class EntryAuthorItem extends AbstractItem<EntryAuthorItem, EntryAuthorItem.ViewHolder> {

    private Author author;

    public EntryAuthorItem(Author author) {
        this.author = author;
    }

    @Override public int getType() {
        return R.id.listing_author_id;
    }

    @Override public int getLayoutRes() {
        return R.layout.item_entry_author;
    }

    @Override public void bindView(ViewHolder viewHolder, List<Object> payloads) {
    	super.bindView(viewHolder, payloads);

        viewHolder.name.setText(author.getName());

        if (author.getAffiliations() != null && !author.getAffiliations().isEmpty()) {
            viewHolder.affiliation.setVisibility(View.VISIBLE);
            Log.d("DEBUG", "Author has affiliations");
            Log.d("DEBUG", author.getAffiliations().get(0).value + " is first affiliation");

            StringBuilder sb = new StringBuilder();
            for (RealmString affiliation : author.getAffiliations()) {
                if (author.getAffiliations().indexOf(affiliation) == 0) {
                    sb.append(affiliation.value);
                } else {
                    sb.append("\n").append(affiliation.value);
                }
            }

            viewHolder.affiliation.setText(sb.toString());
        } else {
            viewHolder.affiliation.setVisibility(View.GONE);
        }
    }

    //reset the view here (this is an optional method, but recommended)
    @Override public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.name.setText(null);
        holder.affiliation.setText(null);
    }

    //Init the viewHolder for this Item
    @Override public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_entry_author_name) TextView name;
        @BindView(R.id.item_entry_author_affiliation) TextView affiliation;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}