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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ObjectProxy implements IServices {
    private String host;
    private int port;
    private IObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    @Override
    public User login(User user, IObserver observer) throws ServiceException {
        initializeConnection();
        sendRequest(new LoginRequest(user));
        Response response = readResponse();
        if(response instanceof LoginResponse res){
            this.client = observer;
            return res.getUser();
        }
        if(response instanceof ExceptionResponse res){
            closeConnection();
            throw new ServiceException(res.getMessage());
        }
        return null;
    }

    @Override
    public void logout(User user, IObserver observer) throws ServiceException {
        sendRequest(new LogoutRequest(user));
        Response response = readResponse();
        if(!(response instanceof OKResponse)){
            throw new ServiceException("The logout couldn't be done successfully!");
        }
        closeConnection();
    }

    private void closeConnection() {
        finished = true;
        try{
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response readResponse() {
        Response response = null;
        try{
            System.out.println("The queue: " + qresponses);
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendRequest(Request request) throws ServiceException{
        try{
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ServiceException("An error occurred while sending the request: ", e);
        }
    }

    private void initializeConnection() {
        try{
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private class ReaderThread implements Runnable {
        @Override
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                        try {
                            qresponses.put((UpdateResponse)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

    private void handleUpdate(UpdateResponse response) {
//        if(response instanceof WhateverResponse res){
//            Model model = res.getModel();
//            System.out.println("Update reponse received... Updating UI");
//            try{
//                client.handleWhateverUpdate(model);
//            }
//            catch(ServerException e){
//                e.printStackTrace();
//            }
//        }
    }
}
