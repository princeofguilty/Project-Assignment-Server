/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sockettest;

import java.io.Serializable;

/**
 *
 * @author BASILYJOK3R
 */
public class Packet implements Serializable {
    String msg;

    public Packet(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
    
}
