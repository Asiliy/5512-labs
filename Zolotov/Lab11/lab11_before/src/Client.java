import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private DatagramSocket socket;
    private BufferedReader keyboard;
    private InetAddress ipAddress;
    private int port;

    private Client(String adr, int port) throws SocketException, UnknownHostException {
        ipAddress = InetAddress.getByName(adr);
        this.port = port;
        socket = new DatagramSocket();
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        Thread listener = new Thread(new FromServer());
        listener.start();
    }

    public static void main(String[] args) throws IOException {
        int port = 5665;
        new Client("localhost", port).run();
    }

    private void send(String s) throws IOException {
        byte[] message = s.getBytes();
        DatagramPacket socket = new DatagramPacket(message, message.length, ipAddress, port);
        this.socket.send(socket);
    }

    public void run() {
        System.out.print("@name ");
        String name = new Scanner(System.in).nextLine();
        try {
            send(name);
            while (true) {
                String line;
                line = keyboard.readLine();
                if (socket.isClosed())
                    break;
                send(line);
                if (line.equals("@quit")) {
                    socket.close();
                    break;
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    private String read() throws IOException {
        byte[] buffer = new byte[1000];
        DatagramPacket socket = new DatagramPacket(buffer, buffer.length);
        this.socket.receive(socket);
        return new String(socket.getData(), 0, socket.getLength());
    }

    private class FromServer implements Runnable {

        public void run() {
            try {
                String serverName = read();
                while (true) {
                    String line;
                    line = read();
                    if (line.equals("@quit")) {
                        System.out.println("server is quited");
                        break;
                    }
                    System.out.println(serverName + ": " + line);
                }
            } catch (IOException e) {
                socket.close();
            } finally {
                socket.close();
            }
        }
    }

}
