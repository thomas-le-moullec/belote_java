package com.jcoinche.client;
import com.jcoinche.model.Player;
import com.jcoinche.model.Room;
import org.apache.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Client {

    private static Logger logger = Logger.getLogger(Client.class);
    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    private String idClient;

    public String getIdClient() {
        return this.idClient;
    }

    public void setIdClient(String id) {
        this.idClient = id;
    }

    public void subscribeUser(StompSession stompSession) throws ExecutionException, InterruptedException {
        stompSession.subscribe("/topic/users/" + getIdClient(), new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object o) {
                String response = new String((byte[]) o);
                System.out.print("Response = > " + response + "\n");
            }
        });
    }

    public ListenableFuture<StompSession> connect(int port, String urlConnection) {

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        String url = "ws://{host}:{port}/jcoinche/";
        return stompClient.connect(url, headers, new MyHandler(), urlConnection, port);
    }

    public void greeting(StompSession stompSession, String name) {
        String jsonHello = "{\"name\" : \""+name+"\" }";
        System.out.print("Name entered :"+jsonHello);//debug
        stompSession.send("/app/jcoinche/greeting/"+getIdClient(), jsonHello.getBytes());
    }

    public void askForTask(StompSession stompSession) {
        String jsonHello = "";
        stompSession.send("/app/jcoinche/askForTask/"+getIdClient(), jsonHello.getBytes());
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

    /*public static void createPlayer() {
        Player player = new Player();
        List<Room> rooms;
        System.out.println(rooms.length);
    }*/

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

        ListenableFuture<StompSession> f = client.connect(port, url);
        StompSession stompSession = f.get();

        logger.info("Subscribing to greeting topic using session " + stompSession);
        client.subscribeUser(stompSession);

        String userName = client.getInfosFromUser("What is your name ?");

        client.greeting(stompSession, userName);
        TimeUnit.SECONDS.sleep(1);
        client.askForTask(stompSession);
        //run TimerTask;


        Thread.sleep(180000);
    }
}