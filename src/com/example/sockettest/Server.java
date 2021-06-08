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
    private static final int PORT = 9998;
    private static List<Classroom> clist=new ArrayList<Classroom>();


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
            //String trueString = "t";
            String falseString = "f";
            //Packet responceTrue=new Packet(trueString);
            Packet responceFalse=new Packet(falseString);
            ss = new ServerSocket(PORT);
            s = ss.accept();
            System.out.println("connection succeeded!!!");
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            Packet cmd=new Packet("t");
            Person p;
            //
            persons.add(new Person(1,"ahmed", "1", "1", "1"));
            //
            while(true)
            {
            try
            {
                    
                cmd=(Packet)objectInputStream.readObject();
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
                             String trueString = "t";
                             Packet responceTrue=new Packet(trueString);
                             objectOutputStream.writeObject(responceTrue);
                             System.out.println(cmd.msg);
                         }
                        else if (cmd.msg.contains("JOINCLASS"))
                         {
                            Classroom c = Classroom.findbyid(cmd.msg2, clist);
                            Person x = Person.findPersonById(cmd.person.getId(), persons);
                            Packet pac;
                            if(c!=null){
                            x.JoinClassroom(c);
                            c.addPerson(x);
                            pac = new Packet("t", x);
                            }
                            else{
                                pac = new Packet("f", cmd.person);
                            }
                            objectOutputStream.writeObject(pac);
                            
                         }
                        else if (cmd.msg.contains("CREATECLASSROOM"))
                         {
                            Classroom c = cmd.person.getJoinedClasses().get(0);
                            System.out.println(cmd.msg2);
                            Person ps = Person.findPersonById(cmd.msg2, persons);
                            ps.JoinClassroom(c);
                            c.addPerson(ps);
                            clist.add(c);
                            Packet pac = new Packet("t", ps);
                            objectOutputStream.writeObject(pac);
                         }
                        else if (cmd.msg.contains("addassignment"))
                         {
                            for (Classroom c:clist)
                            {
                                if (cmd.a.getAssignId().contains(c.getId()))
                                {
                                    c.addAssignment(cmd.a);
                                    Packet pac = new Packet("t", c);
                                    objectOutputStream.writeObject(pac);
                                }
                            }
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
//                                     next.JoinClassroom(new Classroom("a", "12", "asdfghjkl"));
                                     Packet lol=new Packet("t",next);
                                     System.out.println(lol.person.toString());
                                     objectOutputStream.writeObject(lol);
                                     objectOutputStream.flush();
                                    // responceTrue.Clear();
                                     status=true;
                                     break;
                                 }
                             }
                             if (status==false)
                                 objectOutputStream.writeObject(responceFalse);
                             }
                             
                         }
            }
                     catch(EOFException e ){
                         s.close();
                         ss.close();
                         System.err.println("connection ended");
                         ss = new ServerSocket(PORT);
                         s = ss.accept();
                        System.out.println("connection succeeded!!!");
                        objectInputStream = new ObjectInputStream(s.getInputStream());
                        objectOutputStream = new ObjectOutputStream(s.getOutputStream());
                     }
                     catch (Exception e) {
                            e.printStackTrace();
                     }
            }
    }
}