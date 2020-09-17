package itstep.edu.contoller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import itstep.edu.R;
import itstep.edu.model.Message;
import itstep.edu.model.User;

import java.util.ArrayList;

public class MessagesAdapter extends BaseAdapter
{

    private Context context;
    private ArrayList<Message> messages;
    private LayoutInflater inflater;


    public MessagesAdapter(Context context, ArrayList<Message> messages)
    {
        this.context = context;
        this.messages = messages;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return messages.size();
    }

    @Override
    public Object getItem(int position)
    {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_message,parent,false);

        }

        Message message = getMessage(position);

        ((TextView) convertView.findViewById(R.id.tvMessageItem)).setText(message.getMessageText());
        return convertView;
    }

    Message getMessage (int position )

    {
        return (Message) getItem(position);
    }
}
