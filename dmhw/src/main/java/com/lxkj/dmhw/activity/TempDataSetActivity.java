package com.lxkj.dmhw.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.MyTeamTodayInfo;
import com.lxkj.dmhw.bean.MyTeamTotalInfo;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class TempDataSetActivity extends BaseActivity implements GalleryFinal.OnHanlderResultCallback {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;

    @BindView(R.id.user_layout)
    LinearLayout user_layout;
    @BindView(R.id.user_center_tv)
    TextView user_center_tv;

    @BindView(R.id.profit_total_layout)
    LinearLayout profit_total_layout;
    @BindView(R.id.profit_total_tv)
    TextView profit_total_tv;

    @BindView(R.id.profit_taobao_layout)
    LinearLayout profit_taobao_layout;
    @BindView(R.id.profit_taobao_tv)
    TextView profit_taobao_tv;
    @BindView(R.id.profit_more_layout)
    LinearLayout profit_more_layout;
    @BindView(R.id.team_layout)
    LinearLayout team_layout;

    @BindView(R.id.profit_more_tv)
    TextView profit_more_tv;
    @BindView(R.id.team_tv)
    TextView team_tv;


    @BindView(R.id.upload_image)
    TextView upload_image;

    @BindView(R.id.commit_info)
    TextView commit_info;

    @BindView(R.id.commit_total)
    TextView commit_total;

    @BindView(R.id.commit_taobao_detail)
    TextView commit_taobao_detail;

    @BindView(R.id.commit_more_detail)
    TextView commit_more_detail;

    @BindView(R.id.commit_team_detail)
    TextView commit_team_detail;


    @BindView(R.id.nick_name_edit)
    EditText nick_name_edit;
    @BindView(R.id.invite_code_edit)
    EditText invite_code_edit;
    @BindView(R.id.can_withdraw_edit)
    EditText can_withdraw_edit;
    @BindView(R.id.today_money_edit)
    EditText today_money_edit;
    @BindView(R.id.month_money_edit)
    EditText month_money_edit;
    @BindView(R.id.last_money_edit)
    EditText last_money_edit;

    @BindView(R.id.user_image)
    ImageView user_image;


    @BindView(R.id.rb_layout)
    RadioGroup rb_layout;
    @BindView(R.id.rb_01)
    RadioButton rb_01;
    @BindView(R.id.rb_02)
    RadioButton rb_02;
    @BindView(R.id.rb_03)
    RadioButton rb_03;
    @BindView(R.id.rb_04)
    RadioButton rb_04;


    @BindView(R.id.taobao_canwithdraw_edit)
    EditText taobao_canwithdraw_edit;
    @BindView(R.id.taobao_total_edit)
    EditText taobao_total_edit;
    @BindView(R.id.morepl_canwithdraw_edit)
    EditText morepl_canwithdraw_edit;
    @BindView(R.id.morepl_total_edit)
    EditText morepl_total_edit;

    @BindView(R.id.taobao_detail_canwithdraw_edit)
    EditText taobao_detail_canwithdraw_edit;
    @BindView(R.id.taobao_detail_total_edit)
    EditText taobao_detail_total_edit;
    @BindView(R.id.has_withdraw_edit)
    EditText has_withdraw_edit;
    @BindView(R.id.nocal_edit)
    EditText nocal_edit;
    @BindView(R.id.jiesuan_edit)
    EditText jiesuan_edit;
    @BindView(R.id.fukuan_num_edit)
    EditText fukuan_num_edit;
    @BindView(R.id.yugu_money_edit)
    EditText yugu_money_edit;
    @BindView(R.id.team_fukuan_num_edit)
    EditText team_fukuan_num_edit;
    @BindView(R.id.team_yugu_money_edit)
    EditText team_yugu_money_edit;

    @BindView(R.id.more_detail_canwithdraw_edit)
    EditText more_detail_canwithdraw_edit;
    @BindView(R.id.more_detail_total_edit)
    EditText more_detail_total_edit;
    @BindView(R.id.more_has_withdraw_edit)
    EditText more_has_withdraw_edit;
    @BindView(R.id.more_nocal_edit)
    EditText more_nocal_edit;
    @BindView(R.id.more_jiesuan_edit)
    EditText more_jiesuan_edit;
    @BindView(R.id.more_fukuan_num_edit)
    EditText more_fukuan_num_edit;
    @BindView(R.id.more_yugu_money_edit)
    EditText more_yugu_money_edit;
    @BindView(R.id.more_team_fukuan_num_edit)
    EditText more_team_fukuan_num_edit;
    @BindView(R.id.more_team_yugu_money_edit)
    EditText more_team_yugu_money_edit;


    @BindView(R.id.team_today_one_edit)
    EditText team_today_one_edit;
    @BindView(R.id.team_today_two_edit)
    EditText team_today_two_edit;
    @BindView(R.id.team_today_three_edit)
    EditText team_today_three_edit;
    @BindView(R.id.team_total_num_edit)
    EditText team_total_num_edit;

    @BindView(R.id.team_yestoday_one_edit)
    EditText team_yestoday_one_edit;
    @BindView(R.id.team_yestoday_one_money_edit)
    EditText team_yestoday_one_money_edit;
    @BindView(R.id.team_yestoday_two_edit)
    EditText team_yestoday_two_edit;
    @BindView(R.id.team_yestoday_two_money_edit)
    EditText team_yestoday_two_money_edit;
    @BindView(R.id.team_yestoday_three_edit)
    EditText team_yestoday_three_edit;
    @BindView(R.id.team_yestoday_three_money_edit)
    EditText team_yestoday_three_money_edit;
    @BindView(R.id.team_yestoday_four_edit)
    EditText team_yestoday_four_edit;
    @BindView(R.id.team_yestoday_four_money_edit)
    EditText team_yestoday_four_money_edit;
    @BindView(R.id.team_yestoday_five_edit)
    EditText team_yestoday_five_edit;
    @BindView(R.id.team_yestoday_five_money_edit)
    EditText team_yestoday_five_money_edit;
    @BindView(R.id.team_yestoday_six_edit)
    EditText team_yestoday_six_edit;
    @BindView(R.id.team_yestoday_six_money_edit)
    EditText team_yestoday_six_money_edit;



    String nickName="";
    String inviteCode="";
    String canWithdraw="";
    String todayMoney="";
    String monthMoney="";
    String lastMoney="";

    String taobaoCandraw="";
    String taobaoTotal="";
    String morePlCanWithdraw="";
    String morePlTotal="";


    String taoDetailCandraw="";
    String taoDetailTotal="";
    String taoDetailHasdraw="";
    String taoDetailNoCal="";
    String taoDetailjiesuan="";
    String taoDetailMyNum="";
    String taoDetailMyYugu="";
    String taoDetailTeamNum="";
    String taoDetailTeamYugu="";

    String moreDetailCandraw="";
    String moreDetailTotal="";
    String moreDetailHasdraw="";
    String moreDetailNoCal="";
    String moreDetailjiesuan="";
    String moreDetailMyNum="";
    String moreDetailMyYugu="";
    String moreDetailTeamNum="";
    String moreDetailTeamYugu="";


    String teamtoday01="";
    String teamtoday02="";
    String teamtoday03="";
    String teamtotal="";
    String teamYes01="";
    String teamYesMoney01="";
    String teamYes02="";
    String teamYesMoney02="";
    String teamYes03="";
    String teamYesMoney03="";
    String teamYes04="";
    String teamYesMoney04="";
    String teamYes05="";
    String teamYesMoney05="";
    String teamYes06="";
    String teamYesMoney06="";




    private int REQUEST_IMAGE = 999;

    public static boolean isCanEdit=false;
    public static boolean isProfitTotalCanEdit=false;
    public static boolean isTaoCanEdit=false;
    public static boolean isMoreCanEdit=false;
    public static boolean isTeamCanEdit=false;

    private String userImage = "";//Base64 头像
    public static HashMap<String,String> userCenterData=null;//数据
    public static String userType="";

    public static HashMap<String,String> ProfitTotalData=null;//收益总览数据

    public static HashMap<String,String> ProfitTaobaoDetailData=null;//淘宝平台收益数据

    public static HashMap<String,String> ProfitMoreDetailData=null;//其他平台收益数据

    public static String teamToalNum="";
    public static ArrayList<MyTeamTodayInfo> TeamTodayDataList=null;
    public static ArrayList<MyTeamTotalInfo> TeamTotalDataList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempdata_set);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight > 0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        rb_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_01:
                        userType="2";
                        break;
                    case R.id.rb_02:
                        userType="4";
                        break;
                    case R.id.rb_03:
                        userType="5";
                        break;
                    case R.id.rb_04:
                        userType="1";
                        break;
                }
                setNewLyoutByUserType(userType);
            }
        });

        if (isCanEdit){
            if(!userCenterData.get("userImage").equals("")){
                Utils.displayImageCircularWithBitmap(this, Utils.stringtoBitmap(userCenterData.get("userImage")), user_image);
            }
            nick_name_edit.setText(userCenterData.get("nickName"));
            invite_code_edit.setText(userCenterData.get("inviteCode"));
            can_withdraw_edit.setText(userCenterData.get("canWithdraw"));
            today_money_edit.setText(userCenterData.get("todayMoney"));
            month_money_edit.setText(userCenterData.get("monthMoney"));
            last_money_edit.setText(userCenterData.get("lastMoney"));
            switch (userCenterData.get("userType")){
                case "1"://运营商
                    rb_04.setChecked(true);
                    break;
                case "2"://会员
                    rb_01.setChecked(true);
                    break;
                case "4"://店主
                    rb_02.setChecked(true);
                    break;
                case "5"://合伙人
                    rb_03.setChecked(true);
                    break;
            }
            setNewLyoutByUserType(userType);
        }

      if (isProfitTotalCanEdit){
          taobao_canwithdraw_edit.setText(ProfitTotalData.get("taobaowithdraw"));
          taobao_total_edit.setText(ProfitTotalData.get("taobaototal"));
          morepl_canwithdraw_edit.setText(ProfitTotalData.get("moreplwithdraw"));
          morepl_total_edit.setText(ProfitTotalData.get("morepltotal"));
      }

      if (isTaoCanEdit){
          taobao_detail_canwithdraw_edit.setText(ProfitTaobaoDetailData.get("taoDetailCandraw"));
          taobao_detail_total_edit.setText(ProfitTaobaoDetailData.get("taoDetailTotal"));
          has_withdraw_edit.setText(ProfitTaobaoDetailData.get("taoDetailHasdraw"));
          nocal_edit.setText(ProfitTaobaoDetailData.get("taoDetailNoCal"));
          jiesuan_edit.setText(ProfitTaobaoDetailData.get("taoDetailjiesuan"));
          fukuan_num_edit.setText(ProfitTaobaoDetailData.get("taoDetailMyNum"));
          yugu_money_edit.setText(ProfitTaobaoDetailData.get("taoDetailMyYugu"));
          team_fukuan_num_edit.setText(ProfitTaobaoDetailData.get("taoDetailTeamNum"));
          team_yugu_money_edit.setText(ProfitTaobaoDetailData.get("taoDetailTeamYugu"));
      }

        if (isMoreCanEdit){
            more_detail_canwithdraw_edit.setText(ProfitMoreDetailData.get("moreDetailCandraw"));
            more_detail_total_edit.setText(ProfitMoreDetailData.get("moreDetailTotal"));
            more_has_withdraw_edit.setText(ProfitMoreDetailData.get("moreDetailHasdraw"));
            more_nocal_edit.setText(ProfitMoreDetailData.get("moreDetailNoCal"));
            more_jiesuan_edit.setText(ProfitMoreDetailData.get("moreDetailjiesuan"));
            more_fukuan_num_edit.setText(ProfitMoreDetailData.get("moreDetailMyNum"));
            more_yugu_money_edit.setText(ProfitMoreDetailData.get("moreDetailMyYugu"));
            more_team_fukuan_num_edit.setText(ProfitMoreDetailData.get("moreDetailTeamNum"));
            more_team_yugu_money_edit.setText(ProfitMoreDetailData.get("moreDetailTeamYugu"));
        }
        if (isTeamCanEdit){
            team_today_one_edit.setText(TeamTodayDataList.get(0).getCnt());
            team_today_two_edit.setText(TeamTodayDataList.get(1).getCnt());
            team_today_three_edit.setText(TeamTodayDataList.get(2).getCnt());
            team_total_num_edit.setText(teamToalNum);
            team_yestoday_one_edit.setText(TeamTotalDataList.get(0).getTeam());
            team_yestoday_one_money_edit.setText(TeamTotalDataList.get(0).getContribution());
            team_yestoday_two_edit.setText(TeamTotalDataList.get(1).getTeam());
            team_yestoday_two_money_edit.setText(TeamTotalDataList.get(1).getContribution());
            team_yestoday_three_edit.setText(TeamTotalDataList.get(2).getTeam());
            team_yestoday_three_money_edit.setText(TeamTotalDataList.get(2).getContribution());
            team_yestoday_four_edit.setText(TeamTotalDataList.get(3).getTeam());
            team_yestoday_four_money_edit.setText(TeamTotalDataList.get(3).getContribution());
            if (userType.equals("1")){
                team_yestoday_five_edit.setText(TeamTotalDataList.get(4).getTeam());
                team_yestoday_five_money_edit.setText(TeamTotalDataList.get(4).getContribution());
                team_yestoday_six_edit.setText(TeamTotalDataList.get(5).getTeam());
                team_yestoday_six_money_edit.setText(TeamTotalDataList.get(5).getContribution());
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

    }

    @OnClick({R.id.back,R.id.upload_image,R.id.commit_info,R.id.commit_total,R.id.commit_taobao_detail,R.id.user_center_tv,R.id.profit_total_tv,R.id.profit_taobao_tv,R.id.profit_more_tv,R.id.commit_more_detail,R.id.commit_team_detail,R.id.team_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.user_center_tv:
                if (user_layout.getVisibility()==View.VISIBLE){
                    user_layout.setVisibility(View.GONE);
                }else{
                    user_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.profit_total_tv:
                if (profit_total_layout.getVisibility()==View.VISIBLE){
                    profit_total_layout.setVisibility(View.GONE);
                }else{
                    profit_total_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.profit_taobao_tv:
                if (profit_taobao_layout.getVisibility()==View.VISIBLE){
                    profit_taobao_layout.setVisibility(View.GONE);
                }else{
                    profit_taobao_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.profit_more_tv:
                if (profit_more_layout.getVisibility()==View.VISIBLE){
                    profit_more_layout.setVisibility(View.GONE);
                }else{
                    profit_more_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.team_tv:
                if (team_layout.getVisibility()==View.VISIBLE){
                    team_layout.setVisibility(View.GONE);
                }else{
                    team_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.upload_image:
                GalleryFinal.openGallerySingle(REQUEST_IMAGE, this);
                break;
            case R.id.commit_info:
             nickName=nick_name_edit.getText().toString();
             inviteCode=invite_code_edit.getText().toString();
             canWithdraw=can_withdraw_edit.getText().toString();
             todayMoney=today_money_edit.getText().toString();
             monthMoney=month_money_edit.getText().toString();
             lastMoney=last_money_edit.getText().toString();
                userCenterData=new HashMap();
                userCenterData.put("userImage",userImage);
                userCenterData.put("userType",userType);
                userCenterData.put("nickName",nickName);
                userCenterData.put("inviteCode",inviteCode);
                userCenterData.put("canWithdraw",canWithdraw);
                userCenterData.put("todayMoney",todayMoney);
                userCenterData.put("monthMoney",monthMoney);
                userCenterData.put("lastMoney",lastMoney);
                isCanEdit=true; //进入可编辑状态
             //发通知改变个人中心数据显示
             InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("TempDataSetActivity"), false, 0);
             toast("生成成功，到个人中心查看");
              break;

            case R.id.commit_total:
                taobaoCandraw=taobao_canwithdraw_edit.getText().toString();
                taobaoTotal=taobao_total_edit.getText().toString();
                morePlCanWithdraw=morepl_canwithdraw_edit.getText().toString();
                morePlTotal=morepl_total_edit.getText().toString();
                ProfitTotalData=new HashMap();
                ProfitTotalData.put("taobaowithdraw",taobaoCandraw);
                ProfitTotalData.put("taobaototal",taobaoTotal);
                ProfitTotalData.put("moreplwithdraw",morePlCanWithdraw);
                ProfitTotalData.put("morepltotal",morePlTotal);
                isProfitTotalCanEdit=true;
                toast("生成成功，到我的收益查看");
                break;

            case R.id.commit_taobao_detail:
                 taoDetailCandraw=taobao_detail_canwithdraw_edit.getText().toString();
                 taoDetailTotal=taobao_detail_total_edit.getText().toString();
                 taoDetailHasdraw=has_withdraw_edit.getText().toString();
                 taoDetailNoCal=nocal_edit.getText().toString();
                 taoDetailjiesuan=jiesuan_edit.getText().toString();
                 taoDetailMyNum=fukuan_num_edit.getText().toString();
                 taoDetailMyYugu=yugu_money_edit.getText().toString();
                 taoDetailTeamNum=team_fukuan_num_edit.getText().toString();
                 taoDetailTeamYugu=team_yugu_money_edit.getText().toString();
                ProfitTaobaoDetailData=new HashMap<>();
                ProfitTaobaoDetailData.put("taoDetailCandraw",taoDetailCandraw);
                ProfitTaobaoDetailData.put("taoDetailTotal",taoDetailTotal);
                ProfitTaobaoDetailData.put("taoDetailHasdraw",taoDetailHasdraw);
                ProfitTaobaoDetailData.put("taoDetailNoCal",taoDetailNoCal);
                ProfitTaobaoDetailData.put("taoDetailjiesuan",taoDetailjiesuan);
                ProfitTaobaoDetailData.put("taoDetailMyNum",taoDetailMyNum);
                ProfitTaobaoDetailData.put("taoDetailMyYugu",taoDetailMyYugu);
                ProfitTaobaoDetailData.put("taoDetailTeamNum",taoDetailTeamNum);
                ProfitTaobaoDetailData.put("taoDetailTeamYugu",taoDetailTeamYugu);
                isTaoCanEdit=true;
                toast("生成成功，到淘宝平台收益查看");
                break;
            case R.id.commit_more_detail:
                moreDetailCandraw=more_detail_canwithdraw_edit.getText().toString();
                moreDetailTotal=more_detail_total_edit.getText().toString();
                moreDetailHasdraw=more_has_withdraw_edit.getText().toString();
                moreDetailNoCal=more_nocal_edit.getText().toString();
                moreDetailjiesuan=more_jiesuan_edit.getText().toString();
                moreDetailMyNum=more_fukuan_num_edit.getText().toString();
                moreDetailMyYugu=more_yugu_money_edit.getText().toString();
                moreDetailTeamNum=more_team_fukuan_num_edit.getText().toString();
                moreDetailTeamYugu=more_team_yugu_money_edit.getText().toString();
                ProfitMoreDetailData=new HashMap<>();
                ProfitMoreDetailData.put("moreDetailCandraw",moreDetailCandraw);
                ProfitMoreDetailData.put("moreDetailTotal",moreDetailTotal);
                ProfitMoreDetailData.put("moreDetailHasdraw",moreDetailHasdraw);
                ProfitMoreDetailData.put("moreDetailNoCal",moreDetailNoCal);
                ProfitMoreDetailData.put("moreDetailjiesuan",moreDetailjiesuan);
                ProfitMoreDetailData.put("moreDetailMyNum",moreDetailMyNum);
                ProfitMoreDetailData.put("moreDetailMyYugu",moreDetailMyYugu);
                ProfitMoreDetailData.put("moreDetailTeamNum",moreDetailTeamNum);
                ProfitMoreDetailData.put("moreDetailTeamYugu",moreDetailTeamYugu);
                isMoreCanEdit=true;
                toast("生成成功，到平台收益查看");
                break;
            case R.id.commit_team_detail:
                teamtoday01=team_today_one_edit.getText().toString();
                teamtoday02=team_today_two_edit.getText().toString();
                teamtoday03=team_today_three_edit.getText().toString();
                teamtotal=team_total_num_edit.getText().toString();
                teamYes01=team_yestoday_one_edit.getText().toString();
                teamYesMoney01=team_yestoday_one_money_edit.getText().toString();
                teamYes02=team_yestoday_two_edit.getText().toString();
                teamYesMoney02=team_yestoday_two_money_edit.getText().toString();
                teamYes03=team_yestoday_three_edit.getText().toString();
                teamYesMoney03=team_yestoday_three_money_edit.getText().toString();
                teamYes04=team_yestoday_four_edit.getText().toString();
                teamYesMoney04=team_yestoday_four_money_edit.getText().toString();
                teamYes05=team_yestoday_five_edit.getText().toString();
                teamYesMoney05=team_yestoday_five_money_edit.getText().toString();
                teamYes06=team_yestoday_six_edit.getText().toString();
                teamYesMoney06=team_yestoday_six_money_edit.getText().toString();


                MyTeamTodayInfo teamTodayInfo01=new MyTeamTodayInfo();
                teamTodayInfo01.setTitle("直属会员");
                teamTodayInfo01.setCnt(teamtoday01);
                MyTeamTodayInfo teamTodayInfo02=new MyTeamTodayInfo();
                if (userType.equals("1")){
                    teamTodayInfo02.setTitle("所有会员");
                }else{
                    teamTodayInfo02.setTitle("二级会员");
                }

                teamTodayInfo02.setCnt(teamtoday02);
                MyTeamTodayInfo teamTodayInfo03=new MyTeamTodayInfo();
                if (userType.equals("1")){
                    teamTodayInfo03.setTitle("所有店主");
                }else{
                    teamTodayInfo03.setTitle("直属店主");
                }
                teamTodayInfo03.setCnt(teamtoday03);
                TeamTodayDataList=new ArrayList<>();
                TeamTodayDataList.add(teamTodayInfo01);
                TeamTodayDataList.add(teamTodayInfo02);
                TeamTodayDataList.add(teamTodayInfo03);

                teamToalNum=teamtotal;
                TeamTotalDataList=new ArrayList<>();
                MyTeamTotalInfo myTeamTotalInfo01=new MyTeamTotalInfo();
                myTeamTotalInfo01.setTitle("直属会员");
                myTeamTotalInfo01.setTeam(teamYes01);
                myTeamTotalInfo01.setContributiontitle("本月贡献");
                myTeamTotalInfo01.setContribution(teamYesMoney01);
                TeamTotalDataList.add(myTeamTotalInfo01);

                MyTeamTotalInfo myTeamTotalInfo02=new MyTeamTotalInfo();
                myTeamTotalInfo02.setTitle("非直属会员");
                myTeamTotalInfo02.setTeam(teamYes02);
                myTeamTotalInfo02.setContributiontitle("本月贡献");
                myTeamTotalInfo02.setContribution(teamYesMoney02);
                TeamTotalDataList.add(myTeamTotalInfo02);


                MyTeamTotalInfo myTeamTotalInfo03=new MyTeamTotalInfo();
                myTeamTotalInfo03.setTitle("直属店主");
                myTeamTotalInfo03.setTeam(teamYes03);
                myTeamTotalInfo03.setContributiontitle("本月贡献");
                myTeamTotalInfo03.setContribution(teamYesMoney03);
                TeamTotalDataList.add(myTeamTotalInfo03);


                MyTeamTotalInfo myTeamTotalInfo04=new MyTeamTotalInfo();
                myTeamTotalInfo04.setTitle("直属店主团队会员");
                myTeamTotalInfo04.setTeam(teamYes04);
                myTeamTotalInfo04.setContributiontitle("本月贡献");
                myTeamTotalInfo04.setContribution(teamYesMoney04);
                TeamTotalDataList.add(myTeamTotalInfo04);


                if (userType.equals("1")) {
                    MyTeamTotalInfo myTeamTotalInfo05 = new MyTeamTotalInfo();
                    myTeamTotalInfo05.setTitle("非直属店主");
                    myTeamTotalInfo05.setTeam(teamYes05);
                    myTeamTotalInfo05.setContributiontitle("本月贡献");
                    myTeamTotalInfo05.setContribution(teamYesMoney05);
                    TeamTotalDataList.add(myTeamTotalInfo05);


                    MyTeamTotalInfo myTeamTotalInfo06 = new MyTeamTotalInfo();
                    myTeamTotalInfo06.setTitle("非直属店主团队会员");
                    myTeamTotalInfo06.setTeam(teamYes06);
                    myTeamTotalInfo06.setContributiontitle("本月贡献");
                    myTeamTotalInfo06.setContribution(teamYesMoney06);
                    TeamTotalDataList.add(myTeamTotalInfo06);
                }

                isTeamCanEdit=true;
                toast("生成成功，到我的团队查看");
                break;

        }
    }

    @Override
    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
        String data = resultList.get(0).getPhotoPath();
        userImage=Utils.getImageStr(data);
        if (!userImage.equals("")){
            Utils.displayImageCircularWithBitmap(this, Utils.stringtoBitmap(userImage), user_image);
        }
    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {

    }


    private void  setNewLyoutByUserType(String type){
        if (!type.equals("")){
            team_yestoday_one_edit.setVisibility(View.VISIBLE);
            team_yestoday_one_money_edit.setVisibility(View.VISIBLE);
            team_yestoday_two_edit.setVisibility(View.VISIBLE);
            team_yestoday_two_money_edit.setVisibility(View.VISIBLE);
             team_yestoday_three_edit.setVisibility(View.VISIBLE);
             team_yestoday_three_money_edit.setVisibility(View.VISIBLE);
             team_yestoday_four_edit.setVisibility(View.VISIBLE);
             team_yestoday_four_money_edit.setVisibility(View.VISIBLE);
             team_yestoday_five_edit.setVisibility(View.VISIBLE);
             team_yestoday_five_money_edit.setVisibility(View.VISIBLE);
            team_yestoday_six_edit.setVisibility(View.VISIBLE);
             team_yestoday_six_money_edit.setVisibility(View.VISIBLE);
            switch (type){
                case "1"://运营商
                    team_today_two_edit.setHint("所有会员");
                    team_today_three_edit.setHint("所有店主");
                    break;
                case "2"://会员
                case "4"://店主
                case "5"://合伙人
                    team_today_two_edit.setHint("二级会员");
                    team_today_three_edit.setHint("直属店主");
                    team_yestoday_five_edit.setVisibility(View.GONE);
                    team_yestoday_five_money_edit.setVisibility(View.GONE);
                    team_yestoday_six_edit.setVisibility(View.GONE);
                    team_yestoday_six_money_edit.setVisibility(View.GONE);
                    break;
            }
        }

    }
}

