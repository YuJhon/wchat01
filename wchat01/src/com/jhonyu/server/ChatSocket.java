package com.jhonyu.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.jhonyu.vo.ConvertVO;
import com.jhonyu.vo.Message;


/**
 * <p>功能描述: webSocket</p>
 * 
 * @className ChatSocket
 * @author jiangyu
 * @date 2016年3月30日 下午11:37:21
 * @version V1.0
 */
@ServerEndpoint("/chatSocket")
public class ChatSocket
{
    private static Set<ChatSocket> sockets = new HashSet<ChatSocket>();

    private static List<String> names = new ArrayList<String>();

    private Session session;

    private String username;

    private Gson gson = new Gson();

    /**
     * <p> 功能描述：进入</p>
     * 
     * @author jiangyu
     * @date 2016年3月30日 下午11:48:03
     * @param session
     * @version v1.0
     * @since V1.0
     */
    @OnOpen
    public void open(Session session)
    {
        this.session = session;
        sockets.add(this);
        String queryString = session.getQueryString();
        this.username = queryString.substring(queryString.indexOf("=") + 1);
        names.add(this.username);

        Message message = new Message();
        message.setAlert(this.username + "进入聊天室！！");
        message.setNames(names);

        broadCast(sockets, gson.toJson(message));
    }

    /**
     * <p> 功能描述：监听发送的消息</p>
     * 
     * @author jiangyu
     * @date 2016年3月30日 下午11:48:37
     * @param session
     * @param msg
     * @version v1.0
     * @since V1.0
     */
    @SuppressWarnings("deprecation")
    @OnMessage
    public void message(Session session, String msg)
    {
        /** 群聊 **/
        ConvertVO vo = gson.fromJson(msg, ConvertVO.class);
        if (Integer.valueOf(1).equals(vo.getChatType()))
        {
            Message message = new Message();
            message.setSendMsg(vo.getMessage());
            message.setFrom(this.username);
            message.setDate(new Date().toLocaleString());

            broadCast(sockets, gson.toJson(message));
        }
        else
        {
            /** 单聊 **/
            String to = vo.getTo();
            Message message = new Message();
            message.setSendMsg("<font color='red'>【私聊】：" + vo.getMessage() + "</font></br>");
            message.setFrom(this.username);
            message.setDate(new Date().toLocaleString());
            singleChat(sockets, gson.toJson(message), to);
        }

    }

    @OnClose
    public void close(Session session)
    {
        sockets.remove(this);
        names.remove(this.username);

        Message message = new Message();
        message.setAlert(this.username + "退出聊天室！！");
        message.setNames(names);

        broadCast(sockets, gson.toJson(message));
    }

    /**
     * <p> 功能描述：广播</p>
     * 
     * @author jiangyu
     * @date 2016年3月30日 下午11:47:17
     * @param ss
     * @param msg
     * @version v1.0
     * @since V1.0
     */
    public void broadCast(Set<ChatSocket> ss, String msg)
    {
        for (ChatSocket chatSocket : ss)
        {
            try
            {
                chatSocket.session.getBasicRemote().sendText(msg);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p> 功能描述：单聊</p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午1:28:26
     * @param ss
     * @param msg
     * @param to
     * @version v1.0
     * @since V1.0
     */
    public void singleChat(Set<ChatSocket> ss, String msg, String to)
    {
        if (null == to)
        {
            broadCast(ss, msg);
        }
        else
        {
            for (ChatSocket chatSocket : ss)
            {
                if (chatSocket.username.equals(to))
                {
                    try
                    {
                        chatSocket.session.getBasicRemote().sendText(msg);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
