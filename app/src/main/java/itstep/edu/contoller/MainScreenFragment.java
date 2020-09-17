package itstep.edu.contoller;


import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itstep.edu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends Fragment
{

    Button btnContacts;

    public MainScreenFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_main_screen, container, false);
        btnContacts = v.findViewById(R.id.btnContacts);
        btnContacts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new ContactsFragment())
                        .commit();
            }
        });

        return v;
    }

}
