package ca.on.conestogac.jeopardygameapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View currentUserFragView;
    private SharedPreferences sharedPref;
    private TextView name;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CurrentUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentUserFragment newInstance(String param1, String param2) {
        CurrentUserFragment fragment = new CurrentUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentUserFragView = inflater.inflate(R.layout.fragment_current_user, container, false);
        name = currentUserFragView.findViewById(R.id.textViewCurrentUser);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String userName = sharedPref.getString("userName", "Username");
        name.setText(userName);
        return currentUserFragView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}