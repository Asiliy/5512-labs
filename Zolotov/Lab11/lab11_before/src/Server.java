import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Server {
    private DatagramSocket socket;
    private String name;
    private BufferedReader keyboard;
    private InetAddress ipAddress;
    private int port;

    public Server(int port) throws IOException {
        socket = new DatagramSocket(port);
    }

    public static void main(String[] args) throws IOException {
        int port = 5665;
        new Server(port).run();
    }

    private String read() throws IOException {
        byte[] buffer = new byte[1000];
        DatagramPacket socket = new DatagramPacket(buffer, buffer.length);
        this.socket.receive(socket);
        ipAddress = socket.getAddress();
        port = socket.getPort();
        return new String(socket.getData(), 0, socket.getLength());
    }

    public void run() {
        System.out.print("@name ");
        name = new Scanner(System.in).nextLine();
        try {
            String clientName = read();
            Thread sender = new Thread(new Sender());
            sender.start();
            while (true) {
                String line;
                line = read();
                if (line.equals("@quit")) {
                    System.out.println("client is quited");
                    break;
                }
                System.out.println(clientName + ": " + line);
            }
        } catch (IOException e) {
            socket.close();
        } finally {
            socket.close();
        }
    }

    private void send(String s) throws IOException {
        byte[] message = s.getBytes();
        DatagramPacket socket = new DatagramPacket(message, message.length, ipAddress, port);
        this.socket.send(socket);
    }

    private class Sender implements Runnable {

        Sender() {
            keyboard = new BufferedReader(new InputStreamReader(System.in));
        }

        public void run() {
            try {
                send(name);
                while (!socket.isClosed()) {
                    String line;
                    line = keyboard.readLine();
                    if (line != null && !socket.isClosed()) {
                        send(line);
                        if (line.equals("@quit")) {
                            socket.close();
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
