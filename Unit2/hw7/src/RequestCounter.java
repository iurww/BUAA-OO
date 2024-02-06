public class RequestCounter {
    private int cnt;

    public RequestCounter() {
        cnt = 0;
    }

    public synchronized void release() {
        cnt++;
        notifyAll();
    }

    public synchronized void acquire() {
        while (true) {
            if (cnt > 0) {
                cnt -= 1;
                break;
            }
            else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}