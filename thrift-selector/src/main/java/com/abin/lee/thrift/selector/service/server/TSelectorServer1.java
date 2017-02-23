package com.abin.lee.thrift.selector.service.server;

import com.abin.lee.thrift.api.HelloService;
import com.abin.lee.thrift.selector.service.impl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by abin on 2017/2/23 13:20.
 * thrift-svr1
 * com.abin.lee.thrift.service.server
 */
public class TSelectorServer1 {

    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloService TSimpleServer start ....");
            // 非阻塞式的，配合TFramedTransport使用
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(SERVER_PORT);
            // 关联处理器与Service服务的实现
            TProcessor processor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());
            // 目前Thrift提供的最高级的模式，可并发处理客户端请求
            TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport);
            args.processor(processor);
            // 设置协议工厂，高效率的、密集的二进制编码格式进行数据传输协议
            args.protocolFactory(new TCompactProtocol.Factory());
            // 设置传输工厂，使用非阻塞方式，按块的大小进行传输，类似于Java中的NIO
            args.transportFactory(new TFramedTransport.Factory());
            // 设置处理器工厂,只返回一个单例实例
            args.processorFactory(new TProcessorFactory(processor));
            // 多个线程，主要负责客户端的IO处理
            args.selectorThreads(2);
            // 工作线程池
            ExecutorService pool = Executors.newFixedThreadPool(3);
            args.executorService(pool);
            TThreadedSelectorServer server = new TThreadedSelectorServer(args);
            System.out.println("Starting server on port " + SERVER_PORT + "......");
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TSelectorServer1 server = new TSelectorServer1();
        server.startServer();
    }




}
