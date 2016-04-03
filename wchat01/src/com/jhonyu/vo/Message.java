package com.jhonyu.vo;


import java.util.List;


/**
 * <p>功能描述: TODO</p>
 * 
 * @className Message
 * @author jiangyu
 * @date 2016年3月30日 下午11:42:44
 * @version V1.0
 */
public class Message
{
    private String alert;

    private List<String> names;

    private String sendMsg;

    private String from;

    private String date;

    public Message()
    {}

    public String getAlert()
    {
        return alert;
    }

    public void setAlert(String alert)
    {
        this.alert = alert;
    }

    public List<String> getNames()
    {
        return names;
    }

    public void setNames(List<String> names)
    {
        this.names = names;
    }

    public String getSendMsg()
    {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg)
    {
        this.sendMsg = sendMsg;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String toJson()
    {
        return "<font color='red'>【私聊】："+this.sendMsg+"</font></br>";
    }
}
