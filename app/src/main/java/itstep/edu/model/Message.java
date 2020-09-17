package itstep.edu.model;

public class Message
{
    private int id;
    private int messageFrom;
    private int messageTo;
    private boolean isSent;
    private String sendTime;
    private String messageText;

    public Message(int messageFrom, String messageText)
    {
        this.messageFrom = messageFrom;
        this.messageText = messageText;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getMessageFrom()
    {
        return messageFrom;
    }

    public void setMessageFrom(int messageFrom)
    {
        this.messageFrom = messageFrom;
    }

    public int getMessageTo()
    {
        return messageTo;
    }

    public void setMessageTo(int messageTo)
    {
        this.messageTo = messageTo;
    }

    public boolean isSent()
    {
        return isSent;
    }

    public void setSent(boolean sent)
    {
        isSent = sent;
    }

    public String getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }

    public String getMessageText()
    {
        return messageText;
    }

    public void setMessageText(String messageText)
    {
        this.messageText = messageText;
    }
}

