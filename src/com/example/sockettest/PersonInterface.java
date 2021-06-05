/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sockettest;

import java.util.List;

/**
 *
 * @author BASILYJOK3R
 */
public interface PersonInterface {
    public String getName();
    public String getId();
    public void setName(String Name);
    public void setId(String Id);
    public List<Classroom> getJoinedClasses();
}
