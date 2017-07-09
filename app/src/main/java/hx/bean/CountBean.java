package hx.bean;

/**
 * Created by mirui on 2017/7/6.
 */

public class CountBean {
    public String activityName;
    public boolean startCount;
    public int countTime;

    public CountBean(String activityName, boolean startCount, int countTime) {
        this.activityName = activityName;
        this.startCount = startCount;
        this.countTime = countTime;
    }

    public boolean isStartCount() {
        return startCount;
    }

    public void setStartCount(boolean startCount) {
        this.startCount = startCount;
    }

    public int getCountTime() {
        return countTime;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
