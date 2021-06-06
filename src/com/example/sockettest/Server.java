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
    private static final int PORT = 9992;

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
            String responceTrue = "t";
            String responceFalse = "f";
            Packet res=new Packet(responceTrue);
            Packet respos=new Packet(responceFalse);
            ss = new ServerSocket(PORT);
            s = ss.accept();
            System.out.println("connection sucedeed!!!");
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            Packet cmd=new Packet("t");
            Person p;
            while(true)
            {
            try
            {
                    
                cmd=((Packet)objectInputStream.readObject());
                         //objectOutputStream.writeObject(res);
                         //System.out.println(cmd.msg);
                         if ( cmd.msg.contains("register"))
                         {
                             p=cmd.person;
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
                             p=cmd.person;
                             boolean status=false;
                             for (Person next: persons)
                             {
                                 if (next.getUsername().compareTo(p.getUsername())==0 && next.getPassword().compareTo(p.getPassword())==0)
                                 {
                                     //test
//                                     Classroom c = new Classroom();
//                                     c.setName("test class");
//                                     c.setDescribtion("hello world");
//                                     c.setId("123x");
//                                     next.JoinClassroom(c);
//                                     Assignment as = new Assignment(new Teacher(), c, "mega", "test");
//                                     as.setAssignId("123");
//                                     c.addAssignment(as);
                                     //end test
//                                     res.Clear();
                                     res.person = new Person(next);
                                     objectOutputStream.writeObject(res);
                                     objectOutputStream.flush();
                                     res.Clear();
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