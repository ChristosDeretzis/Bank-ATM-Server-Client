package Client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class BankClient {
    private ZMQ.Socket socket;
    private ZContext context;
    public BankClient() {
            context = new ZContext(1);
            //  Socket to talk to server
            System.out.println("Connecting to bank server");
    }

    public String getResultFromServer(String request) {
        //  Socket to talk to server
        socket = context.createSocket(SocketType.REQ);
        socket.connect("tcp://localhost:5555");

        // Send message to server
        socket.send(request.getBytes(ZMQ.CHARSET), 0);

        // Receive from server
        byte[] response_bytes = socket.recv(0);
        String response = new String(response_bytes, ZMQ.CHARSET);
        return response;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BankClient bankClient = new BankClient();
        BankClientImplementation bankClientImplementation = new BankClientImplementation(bankClient);
        bankClientImplementation.run_client();
    }


}
