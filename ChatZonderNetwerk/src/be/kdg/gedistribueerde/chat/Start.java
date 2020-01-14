package be.kdg.gedistribueerde.chat;

import be.kdg.gedistribueerde.chat.communication.NetworkAddress;

public class Start {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Client <contactsIP> <contactsPort>");
            System.exit(1);
        }
        int port = Integer.parseInt(args[1]);
        NetworkAddress chatServer = new NetworkAddress(args[0], port);


        ChatClientStub chatClient1 = new ChatClientStub(chatServer);
        chatClient1.run();

    }
}
