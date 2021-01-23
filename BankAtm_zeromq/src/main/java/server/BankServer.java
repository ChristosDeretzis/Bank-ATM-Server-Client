package server;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class BankServer {

    public static void main(String[] argv) throws Exception {

        BankServerImplementation serverImplementation = new BankServerImplementation();

        try(ZContext context = new ZContext(1)){
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:5555");

            System.out.println("Ready to accept requests from client");


            while (!Thread.currentThread().isInterrupted()){
                byte[] request_bytes = socket.recv(0);
                String request = new String(request_bytes, ZMQ.CHARSET);

                System.out.println("Recieved from client: " + request);

                String response = serverImplementation.process_request(request);
                socket.send(response.getBytes(ZMQ.CHARSET), 0);

                Thread.sleep(1000);
            }
        }
    }
}
