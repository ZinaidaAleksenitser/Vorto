package itstep.edu.contoller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import itstep.edu.R;
import itstep.edu.model.User;

import java.util.ArrayList;

public class ContactsAdapter extends BaseAdapter
{

    private Context context;
    private ArrayList<User> contacts;
    private LayoutInflater inflater;

    public ContactsAdapter(Context context, ArrayList<User> contacts)
    {
        this.context = context;
        this.contacts = contacts;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount()
    {
        return contacts.size();
    }

    @Override
    public Object getItem(int position)
    {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_contact,parent,false);

        }

        User contact = getContact(position);

        ((TextView) convertView.findViewById(R.id.tvContactItem)).setText(contact.getName());
        //Log.d("MYTAG", "this is the name I am recieving " + contact.getName());
        return convertView;
    }

    User getContact (int position )

    {
        return (User) getItem(position);
    }
}
