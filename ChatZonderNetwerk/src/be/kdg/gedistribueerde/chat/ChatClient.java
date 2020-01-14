package be.kdg.gedistribueerde.chat;

import be.kdg.gedistribueerde.chat.communication.NetworkAddress;

public interface ChatClient {
    public void send(String message);

    public void receive(String message);

    public void register();

    public void unregister();

    public NetworkAddress getAddress();

    public String getName();
}
