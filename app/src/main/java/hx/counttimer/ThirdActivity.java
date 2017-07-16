package hx.counttimer;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hx.bean.CountBean;

import static hx.counttimer.MainActivity.dataMap;

public class ThirdActivity extends AppCompatActivity {
    private ServiceConnection serviceConnection;
    private CountDownTimer countDownTimer;
    private TextView tv;
    private Button btn;
    private TimerBinder timerBinder;
    private CountBean initialCountBean;
    private CountBean countBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv = (TextView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);
        Intent intent = new Intent(this,TimerService.class);
        serviceConnection = MainActivity.conn;
        bindService(intent, serviceConnection,Context.BIND_AUTO_CREATE);
        timerBinder = MainActivity.binder;

        initialCountBean = dataMap.get(ThirdActivity.this.getLocalClassName());
        timerBinder.startCount(ThirdActivity.this.getLocalClassName(), initialCountBean);
        if(initialCountBean ==null){
            initialCountBean =  new CountBean(ThirdActivity.this.getLocalClassName(),false,20);
        }
        tv.setText(initialCountBean.getCountTime()+"");
        if(initialCountBean.isStartCount()){
            btn.setText("暂停计时");
            startCount();
        }else{
            if(initialCountBean.getCountTime()==0){
                btn.setText("重新计时");

            }else {
                btn.setText("开始计时");
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(initialCountBean.isStartCount()){
                    btn.setText(" 开始计时");
                    initialCountBean.setStartCount(false);
                }else{
                    if(initialCountBean.getCountTime()==0){
                        initialCountBean.setCountTime(20);
                    }
                    btn.setText("暂停计时");
                    initialCountBean.setStartCount(true);
                }
                dataMap.put(ThirdActivity.this.getLocalClassName(), countBean);
                timerBinder.startCount(ThirdActivity.this.getLocalClassName(), initialCountBean);
                startCount();
            }
        });
    }

    private void startCount(){
        countBean = dataMap.get(ThirdActivity.this.getLocalClassName());
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer((countBean.getCountTime() + 1) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(countBean.isStartCount()){
                    countBean.setCountTime((int) ((millisUntilFinished - 1000) / 1000));
                    initialCountBean = countBean;
                    dataMap.put(ThirdActivity.this.getLocalClassName(), countBean);
                    tv.setText((int) ((millisUntilFinished - 1000) / 1000) + "");
                }
            }
            @Override
            public void onFinish() {
                countBean.setCountTime(0);
                countBean.setStartCount(false);
                initialCountBean = countBean;
                dataMap.put(ThirdActivity.this.getLocalClassName(), countBean);
                btn.setText(" 重新计时");
            }
        }.start();
    }
    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        super.onDestroy();
    }
}
