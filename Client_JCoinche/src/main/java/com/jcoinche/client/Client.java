package com.jcoinche.client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcoinche.model.Card;
import com.jcoinche.model.Greeting;
import com.jcoinche.model.Player;
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
    private List<Card> cards;
    private ProtoTask.Protocol task;
    private Card asset;
    private boolean prompt;

    public Client(String url, int port) throws Exception {
        {
            setPrompt(false);
            ListenableFuture<StompSession> f = this.connect(url, port);
            setStompSession(f.get());
            suscribeTo();
            setTask(ProtoTask.Protocol.WAIT);
        }
    }

    public void setPrompt(boolean prompt) {
        this.prompt = prompt;
    }

    public void setAsset(Card asset) {
        this.asset = asset;
    }

    public Card getAsset() {
        return asset;
    }

    public boolean getPrompt() {
        return prompt;
    }

    public ProtoTask.Protocol getTask() {
        return task;
    }

    public void setTask(ProtoTask.Protocol task) {
        this.task = task;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        displayCards();
    }

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

    /*
    *
    * Sub to /users/{id} to join a room and run Task
    * */
    public void subscribeUser() throws ExecutionException, InterruptedException {
        getStompSession().subscribe("/topic/users/" + getIdClient(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Greeting.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                if (payload instanceof Greeting) {
                    System.out.println("HandShake Success :" +((Greeting) payload).getContent());
                    runProgTask();
                }
            }
        });
    }

    /*
    *
    * Sub to /info/{id} to get info about the task to do
    * */
    public void subscribeInfos() throws ExecutionException, InterruptedException {
        getStompSession().subscribe("/topic/info/" + getIdClient(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ProtoTask.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                if (payload instanceof ProtoTask) {
                    System.out.println("TASK TO DO:"+((ProtoTask) payload).getTask());
                    setTask(((ProtoTask) payload).getTask());
                    if (((ProtoTask) payload).getTask() == ProtoTask.Protocol.TAKECARD) {
                        System.out.println("GO IN TAKE CARDS\n");
                        takeCards();
                    }
                    if (((ProtoTask) payload).getTask() == ProtoTask.Protocol.GETASSET) {
                        System.out.println("GO IN Get Asset\n");
                        takeAsset();
                    }

                }
            }
        });
    }

    /*
    *
    * Sub to /getAsset/{id} to get the asset
    * */
    public void subscribeGetAsset() throws ExecutionException, InterruptedException {
        getStompSession().subscribe("/topic/getAsset/"+getIdClient(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Card.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                //System.out.println("RECEIVED IN HANDLEFRAME");
                if (payload instanceof Card) {
                    System.out.println("WE RECEIVED A CARD IN GET ASSET");
                    setAsset((Card) payload);
                    try {
                        choseAsset(getAsset());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /*
    *
    * Sub to /takeCards/{id} to receive cards during distribution
    * */
    public void subscribePlayerCards() throws ExecutionException, InterruptedException {
        getStompSession().subscribe("/topic/takeCards/" + getIdClient(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Player.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                if (payload instanceof Player) {
                    System.out.println("Cards from handle :" + ((Player) payload).getCards());
                    setCards(((Player) payload).getCards());
                }
            }
        });
    }

    public ListenableFuture<StompSession> connect(String urlCo, int port) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://{host}:{port}/";
        return stompClient.connect(url, headers, new MyHandler(), urlCo, port);
    }

    public void greeting(StompSession stompSession, String name) {
        String jsonHello = "{\"name\" : \"" + name + "\" }";
        System.out.print("ID: "+getIdClient()+" Name entered :" + jsonHello);//debug
        stompSession.send("/app/jcoinche/greeting/" + getIdClient(), jsonHello.getBytes());
    }

    public void askForTask(StompSession stompSession) {
        if (getPrompt() == false) {
            System.out.print("ID is sending message:" + getIdClient() + "\n");//debug
            stompSession.send("/app/jcoinche/askForTask/" + getIdClient(), null);
        }
    }

    public void takeCards() {
        getStompSession().send("/app/jcoinche/takeCards/" + getIdClient(), null);
    }

    public void takeAsset() {
        getStompSession().send("/app/jcoinche/getAsset/" + getIdClient(), null);
    }

    public void choseAsset(Card asset) throws IOException {
        {
            setPrompt(true);
            String response = getInfosFromUser("Do you want the asset : " + asset.getType() + " - " + asset.getValue() + " ? Y/N");
            setPrompt(false);
            if (response.equals("Y") == true || response.equals("Yes") == true) {
                cards.add(getAsset());
                getStompSession().send("/app/jcoinche/takeAsset/" + getIdClient(), response);
            }
            else {
                getStompSession().send("/app/jcoinche/takeAsset/" + getIdClient(), response);
            }
        }
    }

    private class MyHandler extends StompSessionHandlerAdapter {
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            setIdClient(stompSession.getSessionId().toString());
            System.out.println("ID OF CONNECTION AFTER CONNECTED:"+getIdClient());
        }
    }

    static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException er) {
            return false;
        }
    }

    public String getInfosFromUser(String question) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        for (; ; ) {
            System.out.print(question + "\n");
            System.out.flush();
            String line = in.readLine();
            if (line == null) break;
            if (line.length() == 0) continue;
            return line;
        }
        return "";
    }

    public void runTask(Client client) {
        Timer time = new Timer(); // Instantiate Timer Object

        time.schedule(new CustomTask(client), 0, TimeUnit.SECONDS.toMillis(1));
    }

    public void suscribeTo() throws Exception {
        {
            subscribeUser();
        }
    }

    public void runProgTask() {
        //run TimerTask every second;
        try {
            subscribeInfos();
            subscribePlayerCards();
            subscribeGetAsset();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("RUN PROG TASK");
        this.runTask(this);
    }

    public void displayCards() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println("---> Card [" + i + "] = " + cards.get(i).getType() + " - " + cards.get(i).getValue());
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "localhost";
        int port = 8080;

        if (args.length > 1 && !args[1].isEmpty()) {
            url = args[1];
        }
        if (args.length > 0 && isInt(args[0])) {
            port = Integer.parseInt(args[0]);
        }
        Client client = new Client(url, port);
        client.greeting(client.getStompSession(), "guest");
        //Timer for QUIT Client
        Thread.sleep(180000);
    }
}