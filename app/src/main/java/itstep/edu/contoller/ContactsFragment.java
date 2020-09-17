package itstep.edu.contoller;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import itstep.edu.MainActivity;
import itstep.edu.R;
import itstep.edu.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment implements Toolbar.OnMenuItemClickListener
{

    private ArrayList<User> contacts;
    private SQLiteController sqLiteConnector;
    private SQLiteDatabase database;
    private Cursor cursor;
    private EditText contactName, contactPhone;
    private String name, phone;
    private ListView listView;
    private ContactsAdapter adapter;


    public ContactsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_contacts, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setSubtitle("Contacts");
        toolbar.inflateMenu(R.menu.menu_contacts);
        toolbar.setOnMenuItemClickListener(this);
        listView = v.findViewById(R.id.lvContacts);
        contacts = new ArrayList<>();
        //reading contacts
        sqLiteConnector = new SQLiteController(getActivity(), "Contacts",1);
        database = sqLiteConnector.getWritableDatabase();
        cursor = database.rawQuery("Select * from Contacts", null);
        String name, phone;
        while (cursor.moveToNext())
        {
            name = cursor.getString(1);
            phone = cursor.getString(2);
            contacts.add(new User(name, phone, null));
        }


        adapter = new ContactsAdapter(getActivity(),  contacts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                User u = contacts.get(position);
                String str = u.getName();
                String strPhone = u.getPhone();
                //Toast.makeText(getActivity(), "name " + str +"\n"+strPhone,Toast.LENGTH_SHORT).show();

                //trying to set a bundle
                Fragment fragment = new ConversationFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", str);
                bundle.putString("phone", strPhone);
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
            }
        });


         return v;
    }



    @Override
    public boolean onMenuItemClick(MenuItem item)
    {

        name = "";
        switch (item.getItemId())
        {
            case R.id.mitemAddContact:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                LayoutInflater inflater= requireActivity().getLayoutInflater();
                final View myView = inflater.inflate(R.layout.dialog_contact, null);


                builder.setView(myView);

                builder.setMessage("Add contact?");


                builder.setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        contactPhone = myView.findViewById(R.id.adPhone);
                        contactName = myView.findViewById(R.id.adName);
                        name = contactName.getText().toString();
                        phone = contactPhone.getText().toString();
                        contacts.add(new User(name, phone, null));

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("_name", name);
                        contentValues.put("_phone", phone);
                        database.insert("Contacts", null, contentValues);
                       adapter.notifyDataSetChanged();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {


                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return false;
    }



    /*static class SQLiteConnector extends SQLiteOpenHelper
    {

        public SQLiteConnector(@Nullable Context context, @Nullable String name, int version)
        {
            super(context, name,null,  version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("Create table Contacts(_id INTEGER primary key autoincrement, " +
                    "_name varchar(20), _phone varchar(13))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }*/
}
