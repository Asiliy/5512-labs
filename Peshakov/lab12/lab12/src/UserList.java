import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class UserList {
    private ArrayList<ClientThread> currentUsers;
    private Timer timer;
    public UserList() {
        currentUsers = new ArrayList<>();
        timer = new Timer();
    }

    public synchronized boolean isOnline(String name) {
        for(ClientThread clientThread: currentUsers) {
            if(clientThread.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public synchronized ClientThread get(String name) {
        for(ClientThread user : currentUsers) {
            if(user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public long size() { return currentUsers.size(); }

    public synchronized void add(ClientThread user) {
        currentUsers.add(user);
    }

    public synchronized void remove(ClientThread user) {
        currentUsers.remove(user);
    }

    public ArrayList<ClientThread> getList() {
        return currentUsers;
    }

    public void setTimer(ClientThread user, Date date) {
        timer.schedule(new AlarmTimerTask(user, this), date);
    }
}
