package itstep.edu.model;

import java.util.ArrayList;

public class MessagesContainer
{
    private ArrayList<Message> messages;

    public MessagesContainer(ArrayList<Message> messages)
    {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages)
    {
        this.messages = messages;
    }

}
