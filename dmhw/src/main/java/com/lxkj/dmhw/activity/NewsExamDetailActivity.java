package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.NewsExam;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.dialog.NewsExamDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 运营推广详情页
 */
public class NewsExamDetailActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;

    @BindView(R.id.team_image)
    ImageView team_image;
    @BindView(R.id.team_name)
    TextView team_name;
    @BindView(R.id.team_recommend_code)
    TextView team_recommend_code;
    @BindView(R.id.apply_time)
    TextView apply_time;
    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.weixin_txt)
    TextView weixin_txt;
    @BindView(R.id.weixin_img)
    ImageView weixin_img;
    @BindView(R.id.weixin_arr)
    ImageView weixin_arr;
    @BindView(R.id.weixin_btn_layout)
    RelativeLayout weixin_btn_layout;
    @BindView(R.id.phone_btn_layout)
    RelativeLayout phone_btn_layout;


    @BindView(R.id.user_type_img)
    ImageView user_type_img;
    @BindView(R.id.user_type_txt)
    TextView user_type_txt;



    @BindView(R.id.money_01)
    TextView money_01;
    @BindView(R.id.money_02)
    TextView money_02;
    @BindView(R.id.money_03)
    TextView money_03;





    @BindView(R.id.team_num)
    TextView team_num;
    @BindView(R.id.team_01)
    TextView team_01;
    @BindView(R.id.team_02)
    TextView team_02;
    @BindView(R.id.team_03)
    TextView team_03;

    @BindView(R.id.sum_num)
    TextView sum_num;
    @BindView(R.id.sum_01)
    TextView sum_01;
    @BindView(R.id.sum_02)
    TextView sum_02;
    @BindView(R.id.sum_03)
    TextView sum_03;
    @BindView(R.id.sum_04)
    TextView sum_04;
    @BindView(R.id.sum_05)
    TextView sum_05;


    @BindView(R.id.order_num)
    TextView order_num;
    @BindView(R.id.order_01)
    TextView order_01;
    @BindView(R.id.order_02)
    TextView order_02;
    @BindView(R.id.order_03)
    TextView order_03;

    @BindView(R.id.exam_bottom_layout)
    LinearLayout exam_bottom_layout;


    private NewsExam info;

   private int state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_exam_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        info =(NewsExam)getIntent().getExtras().getSerializable("examItemInfo");
        state=getIntent().getIntExtra("state",0);
        if (info!=null) {
            Utils.displayImageCircular(this, info.getUserpicurl(), team_image);
            team_name.setText(info.getUsername());
            team_recommend_code.setText(info.getExtensionid());
            apply_time.setText(info.getReqtime());
            phone.setText(info.getUserphone());

          switch (info.getUsertype()){
              case "2":
                  user_type_img.setImageResource(R.mipmap.user_type_vip);
                  user_type_txt.setText("呆萌会员");
                  break;
              case "1":
                  user_type_img.setImageResource(R.mipmap.user_type_operators);
                  user_type_txt.setText("运营商");
                  break;
              case "4":
                  user_type_img.setImageResource(R.mipmap.user_type_shoper);
                  user_type_txt.setText("呆萌店主");
                  break;
              case "5":
                  user_type_img.setImageResource(R.mipmap.user_type_parner);
                  user_type_txt.setText("合伙人");
                  break;
          }

          if (info.getWxcode().equals("")){
            weixin_btn_layout.setBackgroundResource(R.mipmap.weixin_bg);
              weixin_btn_layout.setEnabled(false);
            weixin_arr.setVisibility(View.GONE);
            weixin_img.setVisibility(View.GONE);
            weixin_txt.setText("未添加微信");
            weixin_txt.setTextColor(0xFEF6F7FF);
          }else {
              weixin_btn_layout.setBackgroundResource(R.mipmap.phone_bg);
              weixin_btn_layout.setEnabled(true);
              weixin_arr.setVisibility(View.VISIBLE);
              weixin_img.setVisibility(View.VISIBLE);
              weixin_txt.setText(info.getWxcode());
              weixin_txt.setTextColor(0xFFFFFFFF);
          }

            money_01.setText(info.getCommisionsum());
            money_02.setText(info.getProfitsum());
            money_03.setText(info.getContributionsum());

            team_num.setText(info.getTeamcnt());
            team_01.setText(info.getAcnt());
            team_02.setText(info.getBcnt());
            team_03.setText(info.getNormcnt());


            sum_num.setText(info.getProfit());
            sum_01.setText(info.getTodayamount());
            sum_02.setText(info.getYesterdayamount());
            sum_03.setText(info.getSevenamount());
            sum_04.setText(info.getPreamount());
            sum_05.setText(info.getMonthamount());

            order_num.setText(info.getOrdercnt());
            order_01.setText(info.getTodayordercnt());
            order_02.setText(info.getPreordercnt());
            order_03.setText(info.getMonthordercnt());

            if (state==0){
              exam_bottom_layout.setVisibility(View.VISIBLE);
            }else{
              exam_bottom_layout.setVisibility(View.GONE);
            }
        }


    }


    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if(message.what==LogicActions.MerchantReqApproveSuccess){
            toast("操作成功");
            dismissDialog();
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("MerchantReqApproveStatus"), false, 0);
            finish();
        }
    }



    @OnClick({R.id.back,R.id.exam_refuse,R.id.exam_pass,R.id.weixin_btn_layout,R.id.phone_btn_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.weixin_btn_layout:
                if (!info.getWxcode().equals("")) {
                    Utils.copyText(weixin_txt.getText().toString());
                    ToastUtil.showWXToast(this,"");
                    getWechatApi();
                }
                break;
            case R.id.phone_btn_layout:
                //创建打电话的意图
                Intent intent = new Intent();
                //设置拨打电话的动作
                intent.setAction(Intent.ACTION_CALL);
                //设置拨打电话的号码
                intent.setData(Uri.parse("tel:" + phone.getText().toString()));
                //开启打电话的意图
                startActivity(intent);
                break;
            case R.id.exam_refuse:
                NewsExamDialog newsExamDialog1=new NewsExamDialog(this,"确定拒绝");
                newsExamDialog1.setSureClickListener(new NewsExamDialog.OnSureClickListener() {
                    @Override
                    public void onClick() {
                        showDialog();
                        paramMap.clear();
                        paramMap.put("msguserid", info.getMsguserid());
                        paramMap.put("state", "2");
                        NetworkRequest.getInstance().POST(handler, paramMap, "MerchantReqApprove", HttpCommon.MerchantReqApprove);
                    }
                });
                newsExamDialog1.show();
                break;
            case  R.id.exam_pass:
                NewsExamDialog newsExamDialog=new NewsExamDialog(this,"确定通过");
                newsExamDialog.setSureClickListener(new NewsExamDialog.OnSureClickListener() {
                    @Override
                    public void onClick() {
                        showDialog();
                        paramMap.clear();
                        paramMap.put("msguserid", info.getMsguserid());
                        paramMap.put("state", "1");
                        NetworkRequest.getInstance().POST(handler, paramMap, "MerchantReqApprove", HttpCommon.MerchantReqApprove);
                    }
                });
                newsExamDialog.show();
                break;
        }
    }
        /**
         * 跳转到微信
         */
        private void getWechatApi() {
            Utils.openWechat(this);
        }
}
