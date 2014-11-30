package gaongil.safereturnhome.scene;

import java.util.ArrayList;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.ContactInfo;
import gaongil.safereturnhome.support.Constant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GroupActivity extends Activity implements OnClickListener {

	EditText mEdtGroupName, mEdtDisplayMemberList;
	Button mBtnAddMemberToGroup, mBtnCreate, mBtnCancle;
	ArrayList<ContactInfo> mSelectedContactList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);

		init();
	}

	private void init() {

		// init EditText
		mEdtGroupName = (EditText) findViewById(R.id.group_edt_groupname);
		mEdtDisplayMemberList = (EditText) findViewById(R.id.group_edt_display_groupmember);

		// init Button
		mBtnAddMemberToGroup = (Button) findViewById(R.id.group_btn_addmember);
		mBtnCreate = (Button) findViewById(R.id.group_btn_create);
		mBtnCancle = (Button) findViewById(R.id.group_btn_cancle);
		
		// Add Click Event Setting to Button
		mBtnAddMemberToGroup.setOnClickListener(this);
		mBtnCreate.setOnClickListener(this);
		mBtnCancle.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == Constant.REQUEST_CODE_GROUPTOCONTACT) {
			if (resultCode == RESULT_OK) {
				mSelectedContactList = data.getParcelableArrayListExtra(Constant.INTENT_GROUP_SELECTED_CONTACTLIST);
				
				for (ContactInfo contactInfo : mSelectedContactList) {
					mEdtDisplayMemberList.append(contactInfo.getName()+Constant.TEXT_SEPERATOR_COMMA);
				}
				
				//TODO SaveData in Memory.
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.group_btn_addmember:
			startActivityForResult(new Intent(GroupActivity.this, ContactsActivity.class), Constant.REQUEST_CODE_GROUPTOCONTACT);
			break;
		case R.id.group_btn_create:
			break;
		case R.id.group_btn_cancle:
			GroupActivity.this.finish();
			break;
		}
	}
}
