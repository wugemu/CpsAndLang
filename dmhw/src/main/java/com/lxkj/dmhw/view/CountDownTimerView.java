package com.lxkj.dmhw.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lxkj.dmhw.R;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时控件
 */
@SuppressLint("HandlerLeak")
public class CountDownTimerView extends LinearLayout {


	private TextView tv_hour_decade;

	private TextView tv_hour_unit;

	private TextView tv_min_decade;

	private TextView tv_min_unit;

	private TextView tv_sec_decade;

	private TextView tv_sec_unit;

	private Context context;

	private int hour_decade;
	private int hour_unit;
	private int min_decade;
	private int min_unit;
	private int sec_decade;
	private int sec_unit;
	private Timer timer;
    private countTimerTask countTimerTask;
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			countDown();
		};
	};

	public CountDownTimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_countdowntimer, this);
		tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour_decade);
		tv_hour_unit = (TextView) view.findViewById(R.id.tv_hour_unit);
		tv_min_decade = (TextView) view.findViewById(R.id.tv_min_decade);
		tv_min_unit = (TextView) view.findViewById(R.id.tv_min_unit);
		tv_sec_decade = (TextView) view.findViewById(R.id.tv_sec_decade);
		tv_sec_unit = (TextView) view.findViewById(R.id.tv_sec_unit);

	}

	/**
	 *
	 * @Description: 开始计时
	 * @param
	 * @return void
	 * @throws
	 */
	public void start() {
		if (timer == null) {
			timer = new Timer();
			countTimerTask=new countTimerTask();
			timer.schedule(countTimerTask,0,1000);
		}
	}

	/**
	 * TimerTask类是一个抽象类
	 */
	public class countTimerTask extends TimerTask {
		@Override
		public void run() {
			handler.sendEmptyMessage(0);
		}

	}
	/**
	 *
	 * @Description: 停止计时
	 * @param
	 * @return void
	 * @throws
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (countTimerTask!= null) {
			countTimerTask.cancel();
			countTimerTask = null;
		}
	}

	/**
	 * @throws Exception
	 *
	 * @Description: 设置倒计时的时长
	 * @param
	 * @return void
	 * @throws
	 */
	public void setTime(String shour, String smin, String ssec) {
		int hour = Integer.parseInt(shour);
		int min = Integer.parseInt(smin);
		int sec = Integer.parseInt(ssec);
		if (hour >= 60 || min >= 60 || sec >= 60 || hour < 0 || min < 0
				|| sec < 0) {
			throw new RuntimeException("Time format is error,please check out your code");
		}

		hour_decade = hour / 10;
		hour_unit = hour - hour_decade * 10;

		min_decade = min / 10;
		min_unit = min - min_decade * 10;

		sec_decade = sec / 10;
		sec_unit = sec - sec_decade * 10;

		tv_hour_decade.setText(hour_decade + "");
		tv_hour_unit.setText(hour_unit + "");
		tv_min_decade.setText(min_decade + "");
		tv_min_unit.setText(min_unit + "");
		tv_sec_decade.setText(sec_decade + "");
		tv_sec_unit.setText(sec_unit + "");

	}

	/**
	 *
	 * @Description: 倒计时
	 * @param
	 * @return boolean
	 * @throws
	 */
	private void countDown() {

		if (isCarry4Unit(tv_sec_unit)) {
			if (isCarry4Decade(tv_sec_decade)) {

				if (isCarry4Unit(tv_min_unit)) {
					if (isCarry4Decade(tv_min_decade)) {

						if (isCarry4Unit(tv_hour_unit)) {
							if (isCarry4Decade(tv_hour_decade)) {
								tv_sec_unit.setText("0");
								tv_sec_decade.setText("0");
								tv_min_unit.setText("0");
								tv_min_decade.setText("0");
								tv_hour_unit.setText("0");
								tv_hour_decade.setText("0");
//								Toast.makeText(context, "时间到了",
//										Toast.LENGTH_SHORT).show();
								stop();
							}
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * @Description: 变化十位，并判断是否需要进位
	 * @param
	 * @return boolean
	 * @throws
	 */
	private boolean isCarry4Decade(TextView tv) {

		int time = Integer.valueOf(tv.getText().toString());
		time = time - 1;
		if (time <0) {
			time = 5;
			tv.setText(time + "");
			return true;
		} else {
			tv.setText(time + "");
			return false;
		}

	}

	/**
	 *
	 * @Description: 变化个位，并判断是否需要进位
	 * @param
	 * @return boolean
	 * @throws
	 */
	private boolean isCarry4Unit(TextView tv) {

		int time = Integer.valueOf(tv.getText().toString());
		time = time - 1;
		if (time <0) {
			time = 9;
			tv.setText(time + "");
			return true;
		} else {
			tv.setText(time + "");
			return false;
		}

	}
}
