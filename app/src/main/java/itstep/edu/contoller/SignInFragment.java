package itstep.edu.contoller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
public class SignInFragment extends Fragment implements View.OnClickListener
{

    private EditText etPhone, etPassword;
    private Button btnSignIn;
    private String password, phone;
    private User user;
    private Socket socket;
    TextView tvRegister;

    public SignInFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        etPhone = v.findViewById(R.id.etPhone);
        etPassword = v.findViewById(R.id.etPassword);
        btnSignIn = v.findViewById(R.id.btnSignIn);
        tvRegister = v.findViewById(R.id.tvRegister);
        btnSignIn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSignIn:
            phone = etPhone.getText().toString();
            password = etPassword.getText().toString();
            user = new User(phone, password);
            socket = ((MainActivity) getActivity()).getSocket();
            SignInAsyncTask signInAsyncTask = new SignInAsyncTask();
            signInAsyncTask.execute();
            break;

            case R.id.tvRegister:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new RegistrationFragment())
                        .commit();
                break;
        }
    }

    public class SignInAsyncTask extends AsyncTask<Void, String, Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            if (socket!=null)
            {
                String request = "authorizeUser";
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
                    publishProgress("user id " + idUser);
                    user.setId(Integer.parseInt(idUser));
                }
                catch (IOException e)
                {
                    e.printStackTrace();

                }
            }
            else
            {
               publishProgress("Connection to the server failed.");
                user.setId(-1);
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

            if(user.getId()!=-1)
            {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new ContactsFragment())
                        .commit();
            }
            else Toast.makeText(getActivity(), "Authorization failed.",Toast.LENGTH_SHORT).show();
        }
    }

}

