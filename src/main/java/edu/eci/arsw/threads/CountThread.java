/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread {
    private int numberA;
    private int numberB;

    public CountThread(int numberA, int numberB){
        this.numberA = numberA;
        this.numberB = numberB;
    }

    @Override
    public void run(){
        for (int i = numberA; i <= numberB; i++){
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}