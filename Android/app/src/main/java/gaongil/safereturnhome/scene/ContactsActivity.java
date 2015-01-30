package gaongil.safereturnhome.scene;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.adapter.ContactsAdapter;
import gaongil.safereturnhome.model.ContactInfo;
import gaongil.safereturnhome.support.Constant;

@EActivity(R.layout.activity_contacts)
public class ContactsActivity extends Activity {

    private final ArrayList<ContactInfo> mPhoneList = new ArrayList<ContactInfo>();

    private ContactsAdapter contactsAdapter;
    private ListView mListView;

    @ViewById(R.id.contact_edt_search)
    EditText mEdtSearch;

    @ViewById(R.id.contact_data_container)
    LinearLayout mLinearLayout;

    @ViewById(R.id.contact_progressbar_relativelayout)
    RelativeLayout mRelativeLayout;

    @AfterViews
    void init() {
        addContactsInList();
    }

    @Click(R.id.contact_toolbar_left_toggle)
    void leftToggle() {
        this.finish();
    }

    @Click(R.id.contact_toolbar_right_toggle)
    void rightToggle() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constant.INTENT_GROUP_SELECTED_CONTACTLIST, getSelectedContacts());
        setResult(RESULT_OK, intent);

        this.finish();
    }

    @TextChange(R.id.contact_edt_search)
    void searchTextChange() {
        // When user changed the Text
        String text = mEdtSearch.getText().toString().toLowerCase(Locale.getDefault());
        contactsAdapter.filter(text);
    }

    private void addContactsInList() {
        showProgressBar();

        try {

            // get Contacts Cursor
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            try {
                mPhoneList.clear();

            } catch (Exception e) {
                Log.e(Constant.TAG, e.getMessage());
                e.printStackTrace();

            }

            String phoneName = null;
            String phoneNumber = null;
            ContactInfo contactObject = null;

            while (phones.moveToNext()) {

                // get Data From Contacts Cursor
                phoneName = phones.getString(phones
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                phoneNumber = phones.getString(phones
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactObject = new ContactInfo(phoneName, phoneNumber);
                mPhoneList.add(contactObject);

            }
            phones.close();

            //Lazy loading (Create ListView to show contacts, add linearLayout)
            mListView = new ListView(this);
            mListView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLinearLayout.addView(mListView);
                }
            });

            Collections.sort(mPhoneList,
                    new Comparator<ContactInfo>() {

                        @Override
                        public int compare(ContactInfo lhs, ContactInfo rhs) {
                            return lhs.getName().compareTo(rhs.getName());
                        }
                    });

            contactsAdapter = new ContactsAdapter(ContactsActivity.this, mPhoneList);
            mListView.setAdapter(contactsAdapter);
            mListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.contact_checkbox);
                    ContactInfo selectedContactInfo = mPhoneList.get(position);

                    if (selectedContactInfo.isSelected()) {
                        selectedContactInfo.setSelected(false);
                        checkBox.setChecked(false);

                    } else {
                        selectedContactInfo.setSelected(true);
                        checkBox.setChecked(true);
                    }

                }
            });

        } catch (Exception e) {
            Log.e(Constant.TAG, e.getMessage());
            e.printStackTrace();

        }

        hideProgressbar();
    }

    private ArrayList<ContactInfo> getSelectedContacts() {

        ArrayList<ContactInfo> selectedList = new ArrayList<ContactInfo>();

        for (ContactInfo contactInfo : this.mPhoneList) {
            if (contactInfo.isSelected())
                selectedList.add(contactInfo);
        }

        return selectedList;
    }


    @UiThread
    void showProgressBar() {
        mRelativeLayout.setVisibility(View.VISIBLE);
        mEdtSearch.setVisibility(View.GONE);
    }

    @UiThread
    void hideProgressbar() {
        mRelativeLayout.setVisibility(View.GONE);
        mEdtSearch.setVisibility(View.VISIBLE);
    }
}
