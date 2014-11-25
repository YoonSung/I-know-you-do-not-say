package gaongil.safereturnhome.support;

import java.util.ArrayList;

import gaongil.safereturnhome.model.UserStatus;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class StatusSpinnerAdapter extends ArrayAdapter<UserStatus>{

	private Context context;
	private int layoutResourceId;
	private ArrayList<UserStatus> statusList;

	public StatusSpinnerAdapter(Context context, int layoutResourceId, ArrayList<UserStatus> statusList) {
		super(context, layoutResourceId, statusList);
		
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.statusList = statusList;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater(); 
			
			row = inflater.inflate(layoutResourceId, parent, false);
		}
		
		//get view from inflater and setData
		
		
		return row;
	}	
}
