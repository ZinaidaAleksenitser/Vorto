package itstep.edu.contoller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import itstep.edu.MainActivity;
import itstep.edu.model.Message;
import itstep.edu.model.MessagesContainer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ReceivingMessagesService extends Service
{
    public final String my_tag = "MYTAG";
    Socket socket;
    int id;

    public ReceivingMessagesService()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
         id = intent.getIntExtra("userId", -1);

        //while()
        getNewMessages();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy()
    {
        Log.d(my_tag, "service destroyed");
        super.onDestroy();
    }

    public void getNewMessages()
    {

        {
            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        socket = new Socket("192.168.1.101", 6566);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    String request = "getNewMessages";

                    {
                        PrintWriter printWriter = null;
                        try
                        {
                            printWriter = new PrintWriter(socket.getOutputStream());
                            Scanner scanner = new Scanner(socket.getInputStream());
                            Gson gson = new Gson();
                            printWriter.write(request + "\n");
                            printWriter.flush();
                            printWriter.write(gson.toJson(id) + "\n");
                            printWriter.flush();
                            String array = scanner.nextLine();
                            MessagesContainer container = gson.fromJson(array, MessagesContainer.class);
                            ArrayList<Message> arr = container.getMessages();
                            for (Message m : arr)
                                Log.d(my_tag, m.getMessageText());


                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }


                        Log.d(my_tag, "the service is working");
                        //stopSelf();
                    }
                }

            });
            thread.start();
        }

    }
}
