package hx.counttimer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hx.adapter.CountAdapter;
import hx.bean.CountBean;

public class CountActivity extends AppCompatActivity {

    private List<CountBean> dataList = new ArrayList<>();
    private TimerBinder binder;
    private CountAdapter countAdapter;
    private ListView lv;
    private CountDownTimer countDownTimer;
    private CountDownTimer updateListTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        lv = (ListView) findViewById(R.id.lv);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        Log.v("","");
        countAdapter = new CountAdapter(dataList,CountActivity.this);
        lv.setAdapter(countAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startNewCount(i);
            }
        });
        Intent intent = new Intent(this,TimerService.class);
        startService(intent);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    private void startNewCount(int position){
//        binder.startCount(position);
//        startCounter(binder.getSecond());
    }
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            binder = (TimerBinder)service;
//            dataList = binder.getSecond();
            startCounter(dataList);
        }
    };
    private List<CountDownTimer> countDownTimerList = new ArrayList<>();
    private void startCounter(final List<CountBean> secondList) {
        for (int i = 0;i<secondList.size();i++){
            if(secondList.get(i).isStartCount()){
                final int finalI = i;
                countDownTimer = new CountDownTimer((secondList.get(finalI).getCountTime() + 1) * 1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
//                        CountBean countBean = new CountBean(true,(int) ((millisUntilFinished - 1000) / 1000));
//                        dataList.set(finalI,countBean);
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
                countDownTimerList.add(countDownTimer);
            }
        }

        updateListTimer = new CountDownTimer(200 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                countAdapter = new CountAdapter(dataList,CountActivity.this);
                lv.setAdapter(countAdapter);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        for (int i = 0;i<countDownTimerList.size();i++){
            countDownTimerList.get(i).cancel();
        }
        updateListTimer.cancel();
        super.onDestroy();
    }
}
