package hx.counttimer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hx.bean.CountBean;

public class TimerService extends Service {

    private Map<String,CountBean> mapData = new HashMap<>();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    private class MyBinder extends Binder implements TimerBinder {

        @Override
        public Map<String,CountBean> getSecond() {
            startCounter(mapData);
            return mapData;
        }

        @Override
        public void startCount(String name,CountBean bean) {
            if(!mapData.containsKey(name)){
                CountBean countBean = new CountBean(name,false,20);
                mapData.put(name,countBean);
            }else{
                mapData.put(name,bean);
            }
            for (int i = 0;i<countDownTimerList.size();i++){
                countDownTimerList.get(i).cancel();
            }
            startCounter(mapData);
            startCounter(mapData);
            startCounter(mapData);
        }

    }
    private List<CountDownTimer> countDownTimerList = new ArrayList<>();
    private void startCounter(final Map<String,CountBean> secondMap) {
        Iterator iter = secondMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,CountBean> entry = (Map.Entry) iter.next();
            CountBean countBean =  entry.getValue();
            final String name = entry.getKey();
                if(countBean.isStartCount()){
                    CountDownTimer countDownTimer = new CountDownTimer((countBean.getCountTime() + 1) * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            CountBean countBean = new CountBean(name,true,(int) ((millisUntilFinished - 1000) / 1000));
                            mapData.put(name,countBean);
                        }
                        @Override
                        public void onFinish() {
                            CountBean countBean = new CountBean(name,false,0);
                            mapData.put(name,countBean);
                        }
                    }.start();
                    countDownTimerList.add(countDownTimer);
                }
            }
    }

}
