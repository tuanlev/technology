package com.example.grpc;

import org.springframework.web.bind.annotation.RestController;

import io.grpc.ManagedChannel;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TestController {
    @Autowired
    private GrpcChannelFactory channelFactory;
    private SimpleGrpc.SimpleBlockingStub simpleBlockingStub;

    @PostConstruct // Hàm này sẽ chạy sau khi Controller được tạo
    public void init() {
        // Tạo một channel tới server (chính nó) tên là "grpc"
        // (Tên "grpc" này bạn phải định nghĩa trong application.properties)
        ManagedChannel channel = channelFactory.createChannel("default-channel");
        this.simpleBlockingStub = SimpleGrpc.newBlockingStub(channel);
    }
    @GetMapping("/")
    public String getMethodName() {

        HelloRequest request = HelloRequest.newBuilder().setName("Hello World").build();
        HelloReply response = simpleBlockingStub.sayHello(request);
        return response.getMessage();
    
    }
    
}
