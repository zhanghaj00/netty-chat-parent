package io.ganguo.chat.client;

import io.ganguo.chat.route.biz.entity.Message;

import java.util.Observable;

/**
 * Created by James on 2015/11/15 0015.
 */



public class MessageObservable extends Observable {


    private Message message ;

    public void setMessage(Message message){

        super.setChanged();
        super.notifyObservers();
        this.message = message;
    }

    //内部静态类方式实现单利

    private static class MessageObHolder{
        private static final MessageObservable INSTANCE = new MessageObservable();
    }

    private MessageObservable(){}

    public static final MessageObservable getInstance(){
        return MessageObHolder.INSTANCE;
    }
}
