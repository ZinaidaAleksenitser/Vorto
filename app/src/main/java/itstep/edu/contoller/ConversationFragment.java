package itstep.edu.contoller;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import itstep.edu.MainActivity;
import itstep.edu.R;
import itstep.edu.model.Message;
import itstep.edu.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment implements View.OnClickListener
{

    public final String my_tag = "MYTAG";

    private User interlocutor, user;
    private TextView tvInterlocutor;
    private ListView lvMyMessage;
    private Button btnMessageSent;
    private EditText etMyMessage;
    private ArrayList<Message> messagesFromUser;
    private ArrayList<Message> messagesToUser;
    private MessagesAdapter adapter;
    private Socket socket;
    private Message messageFrom;


    public ConversationFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_conversation, container, false);

        tvInterlocutor = v.findViewById(R.id.tvConversation);
        String interlocutorName = "", interlocutorPhone ="";

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            interlocutorName = bundle.getString("name");
            //Toast.makeText(getActivity(), "this is text " + userName, Toast.LENGTH_SHORT).show();
            interlocutorPhone = bundle.getString("phone");
        } //else Toast.makeText(getActivity(), "bundle is null", Toast.LENGTH_SHORT).show();
        tvInterlocutor.setText(tvInterlocutor.getText().toString() +interlocutorName);
        //users
        interlocutor = new User(interlocutorName, interlocutorPhone, null);



        user = ((MainActivity)getActivity()).getUser();
        //TODO catch exception if user is null
        lvMyMessage = v.findViewById(R.id.lvMyMessage);
        btnMessageSent = v.findViewById(R.id.btnMessageSend);
        etMyMessage = v.findViewById(R.id.etMyMessage);
        btnMessageSent.setOnClickListener(this);

        //messages from and to
        messagesFromUser = new ArrayList<>();
        messagesToUser = new ArrayList<>();

        //Async
        socket = ((MainActivity) getActivity()).getSocket();



        adapter = new MessagesAdapter(getActivity(), messagesFromUser);
        lvMyMessage.setDivider(null);
        lvMyMessage.setDividerHeight(0);
        lvMyMessage.setAdapter(adapter);

        //setting a service
       getActivity().startService(new Intent(getActivity(),ReceivingMessagesService.class)
               .putExtra("userId", user.getId()));//по факту я сервис в активити запускаю

        return v;
    }

    @Override
    public void onClick(View v)
    {

        String myText = etMyMessage.getText().toString();
        messageFrom = new Message(user.getId(), myText);

        messagesFromUser.add(messageFrom);
        etMyMessage.setText("");

        ConversationAsyncTask conversationAsyncTask = new ConversationAsyncTask();
        conversationAsyncTask.execute();

    }

    public class ConversationAsyncTask extends AsyncTask<Void, String, Void>
    {
        int idInterlocutor;
        PrintWriter printWriter =null;
        Scanner scanner = null;



        @Override
        protected Void doInBackground(Void... voids)
        {

            if(socket != null)
            {
                String request = "getIdByPhone";
                try
                {
                    printWriter = new PrintWriter(socket.getOutputStream());
                    scanner = new Scanner(socket.getInputStream());
                    Gson gson =  new Gson();
                    printWriter.write(request+"\n");
                    printWriter.flush();
                    printWriter.write(gson.toJson(interlocutor)+"\n");
                    printWriter.flush();
                    String idUser = scanner.nextLine();
                    interlocutor.setId(Integer.parseInt(idUser));
                    Log.d(my_tag, "Id received from the server (interlocutor) - "+idUser);



                 request ="messageSend";


                    messageFrom.setMessageTo(interlocutor.getId());
                    Log.d(my_tag, "Object messageFrom from: " + messageFrom.getMessageFrom() +
                            " message to :" +messageFrom.getMessageTo() +" text: " + messageFrom.getMessageText() );
                    printWriter.write(request+"\n");
                    printWriter.flush();
                    printWriter.write(gson.toJson(messageFrom) +"\n");
                    printWriter.flush();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }

  /*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {

        String userName = "", userPhone;
        //Bundle bundle = savedInstanceState.getArguments();
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            userName = bundle.getString("name");
            Toast.makeText(getActivity(), "this is text " + userName, Toast.LENGTH_SHORT).show();
            userPhone = bundle.getString("phone");
        } else Toast.makeText(getActivity(), "bundle is null", Toast.LENGTH_SHORT).show();
        textView.setText(textView.getText().toString() + " " +userName);
        super.onActivityCreated(savedInstanceState);
    }*/
}
