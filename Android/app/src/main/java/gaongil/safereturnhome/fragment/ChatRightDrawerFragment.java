package gaongil.safereturnhome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gaongil.safereturnhome.R;

/**
 * Created by yoon on 15. 1. 28..
 */
public class ChatRightDrawerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_chat_right, container, false);
        return view;
    }
}
