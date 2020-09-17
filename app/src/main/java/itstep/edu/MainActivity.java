package itstep.edu;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itstep.edu.contoller.ReceivingMessagesService;
import itstep.edu.contoller.SignInFragment;
import itstep.edu.model.User;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity
{

    private Socket socket;
    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask();
        connectionAsyncTask.execute();

        //startService(new Intent(this, ReceivingMessagesService.class));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new SignInFragment())
                .commit();
    }


    public class ConnectionAsyncTask extends AsyncTask<Void,String,Void> //TODO do I even need this async task here?
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                socket = new Socket("192.168.1.101",6566);
                publishProgress("Connected to the server");
            }
            catch (IOException e)
            {
                e.printStackTrace();
                publishProgress("Connection ot the server is failed");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            Toast.makeText(getBaseContext(),values[0],Toast.LENGTH_LONG)
                    .show();
        }
    }



}
