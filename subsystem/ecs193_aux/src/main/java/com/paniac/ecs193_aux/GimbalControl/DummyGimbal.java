package com.paniac.ecs193_aux.GimbalControl;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by khjtony on 3/11/17.
 */

class GIMBALMSG{
    private String name;
    private String X;
    private String Y;
    private String TEMP;
    private String TimeStamp;

    private void _updateTimeStamp(){
        this.TimeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
    }

    private void _updateGimbal(){
        this.X = new SimpleDateFormat("ss").format(new Date());
        this.Y = String.valueOf(60 - Integer.valueOf(new SimpleDateFormat("ss").format(new Date())));
    }

    private  void _updateTemp(){
        this.TEMP = new SimpleDateFormat("ss").format(new Date());
    }

    GIMBALMSG(String name){
        this.name = name;
        this.TimeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
    }

    String toGson(){
        this._updateTimeStamp();
        this._updateGimbal();
        this._updateTemp();
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}


public class DummyGimbal {

    private static final String EXCHANGE_NAME = "DummyGimbal";

    public static void main(String[] args){
        String hostname = "localhost";
        String username = "guest";
        String password = "guest";
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("hostname (localhost) : ");
            hostname = sc.nextLine();
            hostname = hostname.equals("")?"localhost":hostname;

            System.out.print("Username (guest) : ");
            username = sc.nextLine();
            username = username.equals("")?"guest":username;

            System.out.print("Password (guest) : ");
            password = sc.nextLine();
            password = password.equals("")?"guest":password;

            System.out.println("Will connect to "+username+"@"+hostname);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        try {
            int counter  = 300;
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(hostname);
            factory.setUsername(username);
            factory.setPassword(password);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            com.paniac.ecs193_aux.GimbalControl.GIMBALMSG _msg = new com.paniac.ecs193_aux.GimbalControl.GIMBALMSG("DummyGimbal");

            while(counter > 0){
                channel.basicPublish(EXCHANGE_NAME, "", null, _msg.toGson().getBytes());
                System.out.println(_msg.toGson());
                counter --;
                Thread.sleep(50);
            }

            channel.close();
            connection.close();
        }catch(Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }
}
