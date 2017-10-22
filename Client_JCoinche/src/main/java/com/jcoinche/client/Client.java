package com.jcoinche.client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcoinche.model.Greeting;
import com.jcoinche.model.ProtoTask;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Client {

    private static Logger logger = Logger.getLogger(Client.class);
    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    private String idClient;
    private StompSession stompSession;

    public String getIdClient() {
        return this.idClient;
    }

    public void setIdClient(String id) {
        this.idClient = id;
    }

    public StompSession getStompSession() {
        return stompSession;
    }

    public void setStompSession(StompSession stompSession) {
        this.stompSession = stompSession;
    }

    public void subscribeUser() throws ExecutionException, InterruptedException {
        getStompSession().subscribe("/topic/users/" + getIdClient(), new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                String response = new String((byte[]) o);
                System.out.print("Response = > " + response + "\n");
            }
        });
    }

    public void subscribeInfos() throws ExecutionException, InterruptedException {
        getStompSession().subscribe("/topic/info/" + getIdClient(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ProtoTask.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                //System.out.println("RECEIVED IN HANDLEFRAME");
                if (payload instanceof ProtoTask) {
                    System.out.println(((ProtoTask) payload).getTask());
                }
            }
        });
    }

    public ListenableFuture<StompSession> connect() {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://{host}:{port}/";
        return stompClient.connect(url, headers, new MyHandler(), "localhost", 8080);
    }

    public void greeting(StompSession stompSession, String name) {
        String jsonHello = "{\"name\" : \""+name+"\" }";
        System.out.print("Name entered :"+jsonHello);//debug
        stompSession.send("/app/jcoinche/greeting/"+getIdClient(), jsonHello.getBytes());
    }

    public void askForTask(StompSession stompSession) {
        System.out.print("ID is sending message:"+getIdClient()+"\n");//debug
        stompSession.send("/app/jcoinche/askForTask/"+getIdClient(), null);
    }

    private class MyHandler extends StompSessionHandlerAdapter {
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            setIdClient(stompSession.getSessionId().toString());
        }
    }

    static boolean isInt(String s)
    {
        try
        { int i = Integer.parseInt(s); return true; }

        catch(NumberFormatException er)
        { return false; }
    }

    public String getInfosFromUser(String question) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        for (;;) {
            System.out.print(question + "\n");
            System.out.flush();
            String line = in.readLine();
            if ( line == null ) break;
            if ( line.length() == 0 ) continue;
            return line;
        }
        return "";
    }

    public void runTask(Client client){
        Timer time = new Timer(); // Instantiate Timer Object

        time.schedule(new CustomTask(client), 0, TimeUnit.SECONDS.toMillis(2));
    }

    public static void main(String[] args) throws Exception {
        String url = "localhost";
        int port = 8080;
        Client client = new Client();

        if (args.length > 1 && !args[1].isEmpty()) {
            url = args[1];
        }
        if (args.length > 0 && isInt(args[0])) {
            port = Integer.parseInt(args[0]);
        }

        //ListenableFuture<StompSession> f = client.connect(port, url);
        ListenableFuture<StompSession> f = client.connect();
        client.setStompSession(f.get());

        logger.info("Subscribing to greeting topic using session " + client.getStompSession());
        client.subscribeUser();
        //client.subscribeInfos();
        client.subscribeInfos();
        String userName = client.getInfosFromUser("What is your name ?");
        client.greeting(client.getStompSession(), userName);

        TimeUnit.SECONDS.sleep(2);
        client.askForTask(client.getStompSession());
        //run TimerTask;
        client.runTask(client);
        Thread.sleep(180000);
    }
}