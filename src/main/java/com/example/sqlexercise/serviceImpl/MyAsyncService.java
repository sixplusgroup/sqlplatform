package com.example.sqlexercise.serviceImpl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MyAsyncService {

    @Async
    public void myAsyncMethod(){
        System.out.println("---> enter async method!");
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("---> end of async method!");
    }


}
