package com.abin.lee.thrift.threadpool.service.impl;

import com.abin.lee.thrift.api.HelloService;
import com.google.common.primitives.Longs;
import org.apache.thrift.TException;

/**
 * Created by abin on 2017/2/23 13:12.
 * thrift-svr1
 * com.abin.lee.thrift.service.impl
 */
public class HelloServiceImpl implements HelloService.Iface {

    public String sayHello(String username) throws TException {
        return "hi  sayHello , "+username;
    }

    public String sayId(int id) throws TException {
        return "hi  sayId , "+id;
    }

    public long sayIdi(int id) throws TException {
        return Longs.tryParse(id + "500");
    }

    public double minus(double money) throws TException {
        return money+1;
    }
}
