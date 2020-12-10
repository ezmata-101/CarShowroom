package sample;
public class Update implements Runnable{
    double percentage;
    double totalLength;
    double currentProgress;
    Thread thread;
    boolean isRunning;
    public Update(){
        thread = new Thread(this);
    }
    public Update(double totalLength){
        this.totalLength = totalLength;
        this.percentage = 0.0;
        thread = new Thread(this);
    }
    public void updateCurrentProgress(double currentProgress){
        this.currentProgress = currentProgress;
        setPercentage(currentProgress/totalLength*100.00);
    }
    public void addToProgress(double value){
        updateCurrentProgress(currentProgress + value);
    }
    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    public void closeThread(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Updater Thread Closed!");
        isRunning = false;
    }

    public void startThread(){
        thread.start();
    }

    @Override
    public void run() {
        isRunning = true;
        while(isRunning){
            StringBuilder string = new StringBuilder(140);
            string.append('\r');
            string.append(String.format("Transfer Completed: %.2f %%, ", percentage));
            System.out.print(string);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}