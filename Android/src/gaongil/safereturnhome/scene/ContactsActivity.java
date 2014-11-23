package gaongil.safereturnhome.scene;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.ContactInfo;
import gaongil.safereturnhome.support.ContactsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class ContactsActivity extends Activity {
	
	private final String TAG = ContactsActivity.class.getSimpleName();
	private final ArrayList<ContactInfo> mPhoneList = new ArrayList<ContactInfo>();
	
	private Context mContext;
	private ContactsAdapter mContactsAdapter;
	private ListView mListView;
	private EditText mEdtSearch;
	private LinearLayout mLinearLayout;
	private Button mBtnOK;
	private RelativeLayout mRelativeLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		init();
		setupActionBar();
		setListener();
	}

	/************************************************************************
	 * Actionbar Code
	 */
	private void setupActionBar() {
		ActionBar actionBar = getActionBar();
		if (actionBar == null)
			return;
		
		actionBar.setLogo(R.drawable.ic_cancle);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color_blue)));
		actionBar.setHomeButtonEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.contact, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	            this.finish();
	            return true;
	        case R.id.contact_toggle_apply:
	        	// TODO Save Database and reaction to user
	        	Toast.makeText(this, "apply!", Toast.LENGTH_SHORT).show();
	        	return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

	/*
	 * Actionbar Code
	 ************************************************************************/
	
	private void setListener() {
		mBtnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getSelectedContacts();
			}
		});

		mEdtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				String text = mEdtSearch.getText().toString().toLowerCase(Locale.getDefault());
				mContactsAdapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

			@Override
			public void afterTextChanged(Editable arg0) {}
		});
	}

	private void init() {
		mContext = this;
		mRelativeLayout = (RelativeLayout) findViewById(R.id.contact_progressbar_relativelayout);
		mEdtSearch = (EditText) findViewById(R.id.contact_edt_search);
		mLinearLayout = (LinearLayout) findViewById(R.id.data_container);
		mBtnOK = (Button) findViewById(R.id.contact_btn_ok);
		
		addContactsInList();
	}

	private void getSelectedContacts() {
		StringBuffer sb = new StringBuffer();

		for (ContactInfo bean : this.mPhoneList) {
			if (bean.isSelected()) {
				sb.append(bean.getName());
				sb.append(" : ");
				sb.append(bean.getNumber()+"\n\n");
			}
		}

		String s = sb.toString().trim();

		if (TextUtils.isEmpty(s)) {
			Toast.makeText(mContext, "Select atleast one Contact",
					Toast.LENGTH_SHORT).show();
			
		} else {
			s = s.substring(0, s.length() - 1);
			Toast.makeText(mContext, "Selected Contacts : " + s,
					Toast.LENGTH_SHORT).show();

		}

	}

	private void addContactsInList() {
		showProgressBar();
	
		try {
			
			// get Contacts Cursor
			Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
	
			try {
				mPhoneList.clear();
				
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
				
			}
	
			String phoneName = null;
			String phoneNumber = null;
			String phoneImage = null;
			ContactInfo contactObject = null; 
			
			while (phones.moveToNext()) {
				
				// get Data From Contacts Cursor
				phoneName = phones.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				
				phoneNumber = phones.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				
				phoneImage = phones.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
	
				contactObject = new ContactInfo();
	
				contactObject.setName(phoneName);
				contactObject.setNumber(phoneNumber);
	
				mPhoneList.add(contactObject);
	
			}
			phones.close();
			
			//Lazy loading (Create ListView to show contacts, add linearLayout) 
			mListView = new ListView(mContext);
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
	
			mContactsAdapter = new ContactsAdapter(ContactsActivity.this, mPhoneList);
			mListView.setAdapter(mContactsAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	
					CheckBox checkBox = (CheckBox) view.findViewById(R.id.contact_checkbox);
					ContactInfo bean = mPhoneList.get(position);
					
					if (bean.isSelected()) {
						bean.setSelected(false);
						checkBox.setChecked(false);
						
					} else {
						bean.setSelected(true);
						checkBox.setChecked(true);
					}
	
				}
			});
	
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			
		}
	
		hideProgressbar();
	}

	private void showProgressBar() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mRelativeLayout.setVisibility(View.VISIBLE);
				mEdtSearch.setVisibility(View.GONE);
				mBtnOK.setVisibility(View.GONE);
			}
		});
	}

	private void hideProgressbar() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mRelativeLayout.setVisibility(View.GONE);
				mEdtSearch.setVisibility(View.VISIBLE);
				mBtnOK.setVisibility(View.VISIBLE);
			}
		});
	}
}
