package com.jhonyu.vo;

/**
 * <p>功能描述:消息的转换体</p>
 * 
 * @className ConvertVO
 * @author jiangyu
 * @date 2016年4月3日 下午12:07:43
 * @version V1.0
 */
public class ConvertVO
{
    private Integer chatType;

    private String message;

    private String to;

    public Integer getChatType()
    {
        return chatType;
    }

    public void setChatType(Integer chatType)
    {
        this.chatType = chatType;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public ConvertVO()
    {}

}
