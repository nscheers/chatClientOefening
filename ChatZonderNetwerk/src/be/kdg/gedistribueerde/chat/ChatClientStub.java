package be.kdg.gedistribueerde.chat;

import be.kdg.gedistribueerde.chat.communication.MessageManager;
import be.kdg.gedistribueerde.chat.communication.MethodCallMessage;
import be.kdg.gedistribueerde.chat.communication.NetworkAddress;


public class ChatClientStub implements ChatClient{
    private NetworkAddress chatServer;
    private TextReceiver textReceiver;
    private final MessageManager messageManager;

    public ChatClientStub(NetworkAddress chatServer) {
        this.chatServer = chatServer;

        messageManager = new MessageManager();
        textReceiver = new ChatFrame(this);
    }

    public String getName() {
        return null;
    }

    public NetworkAddress getAddress() {return messageManager.getMyAddress();}

    public void send(String message) {
        MethodCallMessage msg = new MethodCallMessage(messageManager.getMyAddress(), "send");
        msg.setParameter("message", message);
        messageManager.send(msg, chatServer);
    }
    public void replyName(NetworkAddress address) {
        MethodCallMessage msg = new MethodCallMessage(messageManager.getMyAddress(), "replyName");
        msg.setParameter("name", this.getName());
        messageManager.send(msg, address);
    }


    public void receive(String message) {
       textReceiver.receive(message);
    }

    public void setTextReceiver(TextReceiver textReceiver) {
        this.textReceiver = textReceiver;
    }

    public void unregister() {
        MethodCallMessage msg = new MethodCallMessage(messageManager.getMyAddress(), "unregister");
        messageManager.send(msg, chatServer);
    }

    public void register() {
        MethodCallMessage msg = new MethodCallMessage(messageManager.getMyAddress(), "register");
        messageManager.send(msg, chatServer);
    }

    public void run(){
        this.register();
        while (true) {
            MethodCallMessage request = messageManager.wReceive();
            if (request.getMethodName().equals("send")){
                this.receive(request.getParameter("message"));
            } else if (request.getMethodName().equals("getName")) {
                replyName(request.getOriginator());
            }
        }
    }
}
