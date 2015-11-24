package io.ganguo.chat.route.server.config;

/**
 * Created by James on 2015/11/23 0023.
 */
public enum  Constant {

    PORT1(9090),PORT(8989);

    private int mvalue = 9090;

    Constant(int value){
        mvalue = value;
    }

    public int getValue(){return  mvalue;}



    public static void main(String args[]){

        System.out.print(Constant.PORT1.getValue());
    }
}
