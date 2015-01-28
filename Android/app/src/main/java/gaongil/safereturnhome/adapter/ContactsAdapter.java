package gaongil.safereturnhome.adapter;
import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.ContactInfo;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

 
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
 
public class ContactsAdapter extends BaseAdapter {
 
    Context mContext;
    LayoutInflater mInflater;
    private List<ContactInfo> mainDataList = null;
    private ArrayList<ContactInfo> arraylist;
 
    public ContactsAdapter(Context context, List<ContactInfo> mainDataList) {
     
        mContext = context;
        this.mainDataList = mainDataList;
        mInflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ContactInfo>();
        this.arraylist.addAll(mainDataList);
    }
 
    static class ViewHolder {
        protected TextView name;
        protected TextView number;
        protected CheckBox check;
    }
 
    @Override
    public int getCount() {
        return mainDataList.size();
    }
 
    @Override
    public ContactInfo getItem(int position) {
        return mainDataList.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.contacts_list_row, null);
 
            holder.name = (TextView) view.findViewById(R.id.contact_txt_name);
            holder.number = (TextView) view.findViewById(R.id.contact_txt_phonenum);
 
            holder.check = (CheckBox) view.findViewById(R.id.contact_checkbox);
 
            view.setTag(holder);
            view.setTag(R.id.contact_txt_name, holder.name);
            view.setTag(R.id.contact_txt_phonenum, holder.number);
            view.setTag(R.id.contact_checkbox, holder.check);
 
            holder.check
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
 
                        @Override
                        public void onCheckedChanged(CompoundButton vw,
                                boolean isChecked) {
 
                            int getPosition = (Integer) vw.getTag();
                            mainDataList.get(getPosition).setSelected(
                                    vw.isChecked());
 
                        }
                    });
 
        } else {
            holder = (ViewHolder) view.getTag();
        }
 
        holder.check.setTag(position);
         
        holder.name.setText(mainDataList.get(position).getName());
        holder.number.setText(mainDataList.get(position).getNumber());
        holder.check.setChecked(mainDataList.get(position).isSelected());
 
        return view;
    }
 
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mainDataList.clear();
        if (charText.length() == 0) {
            mainDataList.addAll(arraylist);
        } else {
            for (ContactInfo wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mainDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
 
    public Bitmap getByteContactPhoto(String contactId) {
        Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(contactId));
        Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = mContext.getContentResolver().query(photoUri,
                        new String[] {Contacts.Photo.DATA15}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return BitmapFactory.decodeStream( new ByteArrayInputStream(data));
                }
            }
        } finally {
            cursor.close();
        }
 
        return null;
        }
     
}
