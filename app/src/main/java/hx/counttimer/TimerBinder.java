package hx.counttimer;

import java.util.Map;

import hx.bean.CountBean;

/**
 * Created by mirui on 2017/7/5.
 */

public interface TimerBinder {
    Map<String,CountBean> getSecond();
    void startCount(String name,CountBean countBean);
}
