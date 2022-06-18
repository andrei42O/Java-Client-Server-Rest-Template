package utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private final int port;
    private ServerSocket server = null;

    public AbstractServer(int port) {
        this.port = port;
    }

    public void start() throws ServerException{
        try{
            server = new ServerSocket(port);
            while(true){
                System.out.println("Waiting for clients...");
                Socket client = server.accept();
                System.out.println("Client connected");
                processRequest(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            stop();
        }
    }

    private void stop() throws ServerException{
        try{
            server.close();
        } catch (IOException e) {
            throw new ServerException("Error on server close: ", e);
        }
    }

    protected abstract void processRequest(Socket client);
}
