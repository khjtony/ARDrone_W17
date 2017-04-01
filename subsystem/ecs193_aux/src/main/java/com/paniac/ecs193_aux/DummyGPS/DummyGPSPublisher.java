package com.paniac.ecs193_aux.DummyGPS;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by khjtony on 3/10/17.
 */

class GPSMSG{
    private String name;
    private String LAT;
    private String LON;
    private String TEMP;
    private String TimeStamp;

    private void _updateTimeStamp(){
        this.TimeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
    }

    private void _updateGPS(){
        this.LAT = String.valueOf(30 + 20 * Math.cos(Double.valueOf(new SimpleDateFormat("ss").format(new Date())))/60.0*Math.PI);
        this.LON = String.valueOf(120 + 20 * Math.sin(Double.valueOf(new SimpleDateFormat("ss").format(new Date())))/60.0*Math.PI);
    }

    private  void _updateTemp(){
        this.TEMP = new SimpleDateFormat("ss").format(new Date());
    }

    GPSMSG(String name){
        this.name = name;
        this.TimeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
    }

    String toJson(){
        this._updateTimeStamp();
        this._updateGPS();
        this._updateTemp();
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}


public class DummyGPSPublisher {
    private static final String EXCHANGE_NAME = "DummyGPS";

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

            GPSMSG _msg = new GPSMSG("DummyGPS");

            while(counter > 0){
                channel.basicPublish(EXCHANGE_NAME, "", null, _msg.toJson().getBytes());
                System.out.println(_msg.toJson());
                counter --;
                Thread.sleep(500);
            }

            channel.close();
            connection.close();
        }catch(Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }
}
