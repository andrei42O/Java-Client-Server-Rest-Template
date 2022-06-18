package utils;

import objprotocol.ObjectWorker;
import services.IServices;

import java.net.Socket;

public class ObjectConcurrentServer extends AbstractConcurrentServer{
    private IServices server = null;

    public ObjectConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("ObjectProtocol Server running...");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ObjectWorker worker = new ObjectWorker(server, client);
        return new Thread(worker);
    }
}
