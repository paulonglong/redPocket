package com.example.redpacket.controller;

/**
 * @author Li Wenlong
 * @since 2021/4/19
 */
public class ThreadTest extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("1111");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ThreadTest().run();
        System.out.println("2222");
    }
}
