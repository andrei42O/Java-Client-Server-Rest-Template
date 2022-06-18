package objprotocol;

import model.User;
import objprotocol.Requests.LoginRequest;
import objprotocol.Requests.LogoutRequest;
import objprotocol.Responses.ExceptionResponse;
import objprotocol.Responses.LoginResponse;
import objprotocol.Responses.OKResponse;
import services.IObserver;
import services.IServices;
import services.ServiceException;
import utils.ServerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectWorker implements Runnable, IObserver {

    private IServices server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ObjectWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try{
                Object request = input.readObject();
                Object response = handleRequest((Request)request);
                if(response != null){
                    sendResponse((Response)response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break; // If there is an exception worker goes down O_O
            }
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Kill Client worker
        try{
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.printf("Error: %s\nClosing the worker%n", e.getMessage());
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("Sending response..");
        System.out.println(response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    private Object handleRequest(Request request) {
        if(request instanceof LoginRequest req){
            System.out.println("Login request received!");
            User user = req.getUser();
            try{
                user = server.login(user, this);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            if(user != null)
                return new LoginResponse(user);
            return new ExceptionResponse("Wrong combination of credentials");
        }
        if(request instanceof LogoutRequest req){
            System.out.println("Logout request received!");
            User user = req.getUser();
            try{
                server.logout(user, this);
            } catch (ServiceException e) {
                return new ExceptionResponse("The logout request couldn't be fulfilled!\nError: " + e.getMessage());
            }
            return new OKResponse();
        }
        return new ExceptionResponse("There is no such request available");
    }
}
