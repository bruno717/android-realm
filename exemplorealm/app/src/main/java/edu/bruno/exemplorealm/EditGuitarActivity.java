package edu.bruno.exemplorealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.bruno.exemplorealm.models.Guitar;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by bruno.oliveira on 6/10/16.
 */
public class EditGuitarActivity extends AppCompatActivity {

    public static final String INTENT_KEY_EDIT_ID = "INTENT_KEY_EDIT_ID";

    @BindView(R.id.edittext_name)
    EditText mEditTextName;
    @BindView(R.id.edittext_color)
    EditText mEditTextColor;

    private Realm mRealm = Realm.getDefaultInstance();
    private Guitar mGuitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guitar);
        ButterKnife.bind(this);

        Long id = getIntent().getLongExtra(INTENT_KEY_EDIT_ID, 0L);
        Realm realm = Realm.getDefaultInstance();
        mGuitar = realm.where(Guitar.class).equalTo("id", id).findFirst();
        mEditTextName.setText(mGuitar.getName());
        mEditTextColor.setText(mGuitar.getColor());

    }

    @OnClick(R.id.button_save)
    public void onClickEdit(View v) {

        mRealm.beginTransaction();
        mGuitar.setName(mEditTextName.getText().toString());
        mGuitar.setColor(mEditTextColor.getText().toString());
        mRealm.commitTransaction();

        mRealm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                onBackPressed();
                mRealm.removeAllChangeListeners();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
