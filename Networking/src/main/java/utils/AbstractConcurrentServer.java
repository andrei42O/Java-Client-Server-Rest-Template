package utils;
import java.net.Socket;

public abstract class AbstractConcurrentServer extends AbstractServer{
    public AbstractConcurrentServer(int port) {
        super(port);
        System.out.println("Concurrent server starting...");
    }

    @Override
    protected void processRequest(Socket client) {
        Thread worker = createWorker(client);
        worker.start();
    }

    protected abstract Thread createWorker(Socket client);
}
