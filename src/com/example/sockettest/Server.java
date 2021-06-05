/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sockettest;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BASILYJOK3R
 */
public class Server {

    private static ServerSocket ss;
    private static Socket s;
    static PrintWriter writer;
    BufferedReader br;
    static Person din;
    public static List<Person> persons=new ArrayList<Person>();
    private static final int PORT = 9991;

   /* public String loginReg(Socket s)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String cmd = br.readLine();
            writer = new PrintWriter(s.getOutputStream());
            writer.println(resp);
            writer.flush();
            writer.close();
            return cmd;
    }*/
    
    public static void main(String[] args) throws IOException, ClassNotFoundException{
            String resp = "t";
            String respo = "f";
            Packet res=new Packet(resp);
            Packet respos=new Packet(respo);
            ss = new ServerSocket(PORT);
            s = ss.accept();
            System.out.println("connection sucedeed!!!");
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            Packet cmd=null;
            Person p;
            while(true)
            {
            try
            {
                    cmd=(Packet)objectInputStream.readObject();
                         //objectOutputStream.writeObject(res);
                         //System.out.println(cmd.msg);
                         if ( cmd.msg.contains("register"))
                         {
                             p=(Person)cmd;
                             System.out.println(p.toString());
                             persons.add(p);
                         }
                         else if (cmd.msg.contains("connect"))
                         {
                             objectOutputStream.writeObject(res);
                             System.out.println(cmd.msg);
                         }
                         else if(cmd.msg.contains("login"))
                         {
                             if (persons.isEmpty()==false)
                             {
                             p=(Person)cmd;
                             boolean status=false;
                             for (Person next: persons)
                             {
                                 if (next.getUsername().compareTo(p.getUsername())==0 && next.getPassword().compareTo(p.getPassword())==0)
                                 {
                                     objectOutputStream.writeObject(res);
                                     status=true;
                                     break;
                                 }
                             }
                             if (status==false)
                                 objectOutputStream.writeObject(respos);
                             }
                             
                         }
            }
                     catch ( Exception e) {
            e.printStackTrace();
                     }
            }
    }
}