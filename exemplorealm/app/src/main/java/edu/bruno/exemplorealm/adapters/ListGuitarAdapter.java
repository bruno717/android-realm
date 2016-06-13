package edu.bruno.exemplorealm.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.bruno.exemplorealm.EditGuitarActivity;
import edu.bruno.exemplorealm.R;
import edu.bruno.exemplorealm.models.Guitar;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by bruno.oliveira on 6/10/16.
 */
public class ListGuitarAdapter extends RecyclerView.Adapter<ListGuitarAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private RealmResults<Guitar> mGuitars;

    public ListGuitarAdapter(Context context, RealmQuery<Guitar> guitars) {
        mGuitars = guitars.findAll().sort("id");
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.cell_list_guitar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Guitar guitar = mGuitars.get(position);
        holder.mTextViewName.setText(guitar.getName());
        holder.mTextViewColor.setText(guitar.getColor());
        holder.mButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        guitar.deleteFromRealm();
                    }
                });
                Realm.getDefaultInstance().addChangeListener(new RealmChangeListener<Realm>() {
                    @Override
                    public void onChange(Realm element) {
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Intent intent = new Intent(c, EditGuitarActivity.class);
                intent.putExtra(EditGuitarActivity.INTENT_KEY_EDIT_ID, guitar.getId());
                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGuitars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_name)
        TextView mTextViewName;
        @BindView(R.id.textview_color)
        TextView mTextViewColor;
        @BindView(R.id.button_remove)
        Button mButtonRemove;
        @BindView(R.id.button_edit)
        Button mButtonEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
