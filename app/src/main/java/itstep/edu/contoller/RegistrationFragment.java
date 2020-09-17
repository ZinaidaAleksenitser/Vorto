package itstep.edu.contoller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import itstep.edu.MainActivity;
import itstep.edu.R;
import itstep.edu.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment
{
    private EditText etName, etPhone, etPass;
    private Button btnRegister;
    private Socket socket;
    private User user;


    public RegistrationFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_registration, container, false);
        etName = v.findViewById(R.id.etNameRF);
        etPhone = v.findViewById(R.id.etPhoneRF);
        etPass = v.findViewById(R.id.etPassRF);
        btnRegister = v.findViewById(R.id.bntRegisterRF);
        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask();
                registerAsyncTask.execute();
                //TODO in case of a failure open the registration fragment one more time
            }
        });

        return v;
    }

    public class RegisterAsyncTask extends AsyncTask<Void,String,Void>
    {


        @Override
        protected void onPreExecute()
        {
            socket = ((MainActivity)getActivity()).getSocket();
            user = new User(etName.getText().toString(),
                    etPhone.getText().toString(),
                    etPass.getText().toString());
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            String request = "registerUser";
            try
            {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                Scanner scanner = new Scanner(socket.getInputStream());
                Gson gson = new Gson();
                printWriter.write(request+"\n");
                printWriter.flush();
                printWriter.write(gson.toJson(user) +"\n");
                printWriter.flush();
                String idUser = scanner.nextLine();
                publishProgress(idUser + " id received ");
                user.setId(Integer.parseInt(idUser));


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            Toast.makeText(getActivity(),values[0],Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
           ((MainActivity)getActivity()).setUser(user);
            if(user.getId() != -1)
            {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new MainScreenFragment())
                        .commit();
            }
            else  Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

}
