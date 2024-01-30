package io.github.carloshkayser.wordcount.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MyWebSocketClient extends WebSocketClient {

  public MyWebSocketClient(URI serverUri) {
      super(serverUri);
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
      System.out.println("Connected to WebSocket server");
  }

  @Override
  public void onMessage(String message) {
      System.out.println("Received message: " + message);
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
      System.out.println("Closed connection to WebSocket server");
  }

  @Override
  public void onError(Exception ex) {
      ex.printStackTrace();
  }

  // public static void main(String[] args) {
  //     try {
  //         MyWebSocketClient client = new MyWebSocketClient(new URI("ws://localhost:8080"));
  //         client.connect();
  //     } catch (URISyntaxException e) {
  //         e.printStackTrace();
  //     }
  // }
}