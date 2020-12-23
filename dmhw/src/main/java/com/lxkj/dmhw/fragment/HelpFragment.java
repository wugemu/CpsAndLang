package com.lxkj.dmhw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.activity.ServiceofMyActivity;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.AnswersActivity;
import com.lxkj.dmhw.activity.FeedbackActivity;
import com.lxkj.dmhw.activity.ProblemActivity;
import com.lxkj.dmhw.bean.ClassifyHelp;
import com.lxkj.dmhw.bean.Service;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 帮助中心页
 * Created by Zyhant on 2018/1/16.
 */
public class HelpFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.fragment_help_app)
    LinearLayout fragmentHelpApp;
    @BindView(R.id.fragment_help_one_text)
    TextView fragmentHelpOneText;
    @BindView(R.id.fragment_help_one)
    LinearLayout fragmentHelpOne;
    @BindView(R.id.fragment_help_two_text)
    TextView fragmentHelpTwoText;
    @BindView(R.id.fragment_help_two)
    LinearLayout fragmentHelpTwo;
    @BindView(R.id.fragment_help_three_text)
    TextView fragmentHelpThreeText;
    @BindView(R.id.fragment_help_three)
    LinearLayout fragmentHelpThree;
    @BindView(R.id.fragment_help_four_text)
    TextView fragmentHelpFourText;
    @BindView(R.id.fragment_help_four)
    LinearLayout fragmentHelpFour;
    @BindView(R.id.fragment_help_integral)
    LinearLayout fragmentHelpIntegral;
    @BindView(R.id.fragment_help_five_text)
    TextView fragmentHelpFiveText;
    @BindView(R.id.fragment_help_five)
    LinearLayout fragmentHelpFive;
    @BindView(R.id.fragment_help_six_text)
    TextView fragmentHelpSixText;
    @BindView(R.id.fragment_help_six)
    LinearLayout fragmentHelpSix;
    @BindView(R.id.fragment_help_seven_text)
    TextView fragmentHelpSevenText;
    @BindView(R.id.fragment_help_seven)
    LinearLayout fragmentHelpSeven;
    @BindView(R.id.fragment_help_eight_text)
    TextView fragmentHelpEightText;
    @BindView(R.id.fragment_help_eight)
    LinearLayout fragmentHelpEight;
    @BindView(R.id.fragment_help_use)
    LinearLayout fragmentHelpUse;
    @BindView(R.id.fragment_help_nine_text)
    TextView fragmentHelpNineText;
    @BindView(R.id.fragment_help_nine)
    LinearLayout fragmentHelpNine;
    @BindView(R.id.fragment_help_ten_text)
    TextView fragmentHelpTenText;
    @BindView(R.id.fragment_help_ten)
    LinearLayout fragmentHelpTen;
    @BindView(R.id.fragment_help_eleven_text)
    TextView fragmentHelpElevenText;
    @BindView(R.id.fragment_help_eleven)
    LinearLayout fragmentHelpEleven;
    @BindView(R.id.fragment_help_twelve_text)
    TextView fragmentHelpTwelveText;
    @BindView(R.id.fragment_help_twelve)
    LinearLayout fragmentHelpTwelve;
    @BindView(R.id.fragment_help_extension)
    LinearLayout fragmentHelpExtension;
    @BindView(R.id.fragment_help_thirteen_text)
    TextView fragmentHelpThirteenText;
    @BindView(R.id.fragment_help_thirteen)
    LinearLayout fragmentHelpThirteen;
    @BindView(R.id.fragment_help_fourteen_text)
    TextView fragmentHelpFourteenText;
    @BindView(R.id.fragment_help_fourteen)
    LinearLayout fragmentHelpFourteen;
    @BindView(R.id.fragment_help_fifteen_text)
    TextView fragmentHelpFifteenText;
    @BindView(R.id.fragment_help_fifteen)
    LinearLayout fragmentHelpFifteen;
    @BindView(R.id.fragment_help_sixteen_text)
    TextView fragmentHelpSixteenText;
    @BindView(R.id.fragment_help_sixteen)
    LinearLayout fragmentHelpSixteen;
    @BindView(R.id.fragment_help_withdrawals)
    LinearLayout fragmentHelpWithdrawals;
    @BindView(R.id.fragment_help_seventeen_text)
    TextView fragmentHelpSeventeenText;
    @BindView(R.id.fragment_help_seventeen)
    LinearLayout fragmentHelpSeventeen;
    @BindView(R.id.fragment_help_eighteen_text)
    TextView fragmentHelpEighteenText;
    @BindView(R.id.fragment_help_eighteen)
    LinearLayout fragmentHelpEighteen;
    @BindView(R.id.fragment_help_nineteen_text)
    TextView fragmentHelpNineteenText;
    @BindView(R.id.fragment_help_nineteen)
    LinearLayout fragmentHelpNineteen;
    @BindView(R.id.fragment_help_twenty_text)
    TextView fragmentHelpTwentyText;
    @BindView(R.id.fragment_help_twenty)
    LinearLayout fragmentHelpTwenty;
    @BindView(R.id.fragment_help_service)
    LinearLayout fragmentHelpService;
    @BindView(R.id.fragment_help_feedback)
    LinearLayout fragmentHelpFeedback;
    @BindView(R.id.fragment_help_app_layout)
    LinearLayout fragmentHelpAppLayout;
    @BindView(R.id.fragment_help_integral_layout)
    LinearLayout fragmentHelpIntegralLayout;
    @BindView(R.id.fragment_help_use_layout)
    LinearLayout fragmentHelpUseLayout;
    @BindView(R.id.fragment_help_extension_layout)
    LinearLayout fragmentHelpExtensionLayout;
    @BindView(R.id.fragment_help_withdrawals_layout)
    LinearLayout fragmentHelpWithdrawalsLayout;
    private ArrayList<ClassifyHelp> list;
    private ClassifyHelp helpOne;
    private ClassifyHelp helpTwo;
    private ClassifyHelp helpThree;
    private ClassifyHelp helpFour;
    private ClassifyHelp helpFive;

    public static HelpFragment getInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 获取帮助列表
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetHelp", HttpCommon.GetHelp);
    }

    @Override
    public void onCustomized() {

    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.GetHelpSuccess) {
            list = (ArrayList<ClassifyHelp>) message.obj;
            helpList();
        }
        if (message.what == LogicActions.GetUpServiceSuccess) {
            Service service = (Service) message.obj;
            if (service.getWxcode().equals("")) {
                toast("暂无客服");
            } else {
//                ServiceDialog dialog = new ServiceDialog(getActivity());
//                dialog.showDialog(service.getWxcode(), service.getWxqrcode());
             Intent  intent =new Intent(getActivity(), ServiceofMyActivity.class);
             startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.back, R.id.fragment_help_app, R.id.fragment_help_integral, R.id.fragment_help_use, R.id.fragment_help_extension,
            R.id.fragment_help_withdrawals, R.id.fragment_help_service, R.id.fragment_help_feedback})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:// 返回
                isFinish();
                break;
            case R.id.fragment_help_app:// APP简介
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("title", "APP简介");
                intent.putExtra("number", 1);
                break;
            case R.id.fragment_help_integral:// 积分问题
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("title", "积分问题");
                intent.putExtra("number", 2);
                break;
            case R.id.fragment_help_use:// 使用教程
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("title", "使用教程");
                intent.putExtra("number", 3);
                break;
            case R.id.fragment_help_extension:// 推广问题
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("title", "推广问题");
                intent.putExtra("number", 4);
                break;
            case R.id.fragment_help_withdrawals:// 提现问题
                intent = new Intent(getActivity(), ProblemActivity.class);
                intent.putExtra("title", "提现问题");
                intent.putExtra("number", 5);
                break;
            case R.id.fragment_help_service:// 在线客服
                // 获取上级客服
                 intent =new Intent(getActivity(), ServiceofMyActivity.class);
//                paramMap.clear();
//                paramMap.put("userid", login.getUserid());
//                NetworkRequest.getInstance().POST(handler, paramMap, "GetUpService", HttpCommon.GetUpService);
                break;
            case R.id.fragment_help_feedback:// 留言反馈
                intent = new Intent(getActivity(), FeedbackActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 设置问题列表
     */
    private void helpList() {
        for (ClassifyHelp help : list) {
            if (Objects.equals(help.getProblemtype(), "00")) {
                fragmentHelpAppLayout.setVisibility(View.VISIBLE);
                helpOne = help;
                if (help.getProblemdata().size() >= 1) {
                    fragmentHelpOneText.setText(help.getProblemdata().get(0).getProblemname());
                    fragmentHelpOne.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 2) {
                    fragmentHelpTwoText.setText(help.getProblemdata().get(1).getProblemname());
                    fragmentHelpTwo.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 3) {
                    fragmentHelpThreeText.setText(help.getProblemdata().get(2).getProblemname());
                    fragmentHelpThree.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 4) {
                    fragmentHelpFourText.setText(help.getProblemdata().get(3).getProblemname());
                    fragmentHelpFour.setOnClickListener(this);
                }
            } else if (Objects.equals(help.getProblemtype(), "01")) {
                fragmentHelpIntegralLayout.setVisibility(View.VISIBLE);
                helpTwo = help;
                if (help.getProblemdata().size() >= 1) {
                    fragmentHelpFiveText.setText(help.getProblemdata().get(0).getProblemname());
                    fragmentHelpFive.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 2) {
                    fragmentHelpSixText.setText(help.getProblemdata().get(1).getProblemname());
                    fragmentHelpSix.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 3) {
                    fragmentHelpSevenText.setText(help.getProblemdata().get(2).getProblemname());
                    fragmentHelpSeven.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 4) {
                    fragmentHelpEightText.setText(help.getProblemdata().get(3).getProblemname());
                    fragmentHelpEight.setOnClickListener(this);
                }
            } else if (Objects.equals(help.getProblemtype(), "02")) {
                fragmentHelpUseLayout.setVisibility(View.VISIBLE);
                helpThree = help;
                if (help.getProblemdata().size() >= 1) {
                    fragmentHelpNineText.setText(help.getProblemdata().get(0).getProblemname());
                    fragmentHelpNine.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 2) {
                    fragmentHelpTenText.setText(help.getProblemdata().get(1).getProblemname());
                    fragmentHelpTen.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 3) {
                    fragmentHelpElevenText.setText(help.getProblemdata().get(2).getProblemname());
                    fragmentHelpEleven.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 4) {
                    fragmentHelpTwelveText.setText(help.getProblemdata().get(3).getProblemname());
                    fragmentHelpTwelve.setOnClickListener(this);
                }
            } else if (Objects.equals(help.getProblemtype(), "03")) {
                fragmentHelpExtensionLayout.setVisibility(View.VISIBLE);
                helpFour = help;
                if (help.getProblemdata().size() >= 1) {
                    fragmentHelpThirteenText.setText(help.getProblemdata().get(0).getProblemname());
                    fragmentHelpThirteen.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 2) {
                    fragmentHelpFourteenText.setText(help.getProblemdata().get(1).getProblemname());
                    fragmentHelpFourteen.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 3) {
                    fragmentHelpFifteenText.setText(help.getProblemdata().get(2).getProblemname());
                    fragmentHelpFifteen.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 4) {
                    fragmentHelpSixteenText.setText(help.getProblemdata().get(3).getProblemname());
                    fragmentHelpSixteen.setOnClickListener(this);
                }
            } else if (Objects.equals(help.getProblemtype(), "04")) {
                fragmentHelpWithdrawalsLayout.setVisibility(View.VISIBLE);
                helpFive = help;
                if (help.getProblemdata().size() >= 1) {
                    fragmentHelpSeventeenText.setText(help.getProblemdata().get(0).getProblemname());
                    fragmentHelpSeventeen.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 2) {
                    fragmentHelpEighteenText.setText(help.getProblemdata().get(1).getProblemname());
                    fragmentHelpEighteen.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 3) {
                    fragmentHelpNineteenText.setText(help.getProblemdata().get(2).getProblemname());
                    fragmentHelpNineteen.setOnClickListener(this);
                }
                if (help.getProblemdata().size() >= 4) {
                    fragmentHelpTwentyText.setText(help.getProblemdata().get(3).getProblemname());
                    fragmentHelpTwenty.setOnClickListener(this);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.fragment_help_one:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpOne.getProblemdata().get(0).getProblemid());
                intent.putExtra("problem", helpOne.getProblemdata().get(0).getProblemname());
                intent.putExtra("classify", "00");
                break;
            case R.id.fragment_help_two:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpOne.getProblemdata().get(1).getProblemid());
                intent.putExtra("problem", helpOne.getProblemdata().get(1).getProblemname());
                intent.putExtra("classify", "00");
                break;
            case R.id.fragment_help_three:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpOne.getProblemdata().get(2).getProblemid());
                intent.putExtra("problem", helpOne.getProblemdata().get(2).getProblemname());
                intent.putExtra("classify", "00");
                break;
            case R.id.fragment_help_four:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpOne.getProblemdata().get(3).getProblemid());
                intent.putExtra("problem", helpOne.getProblemdata().get(3).getProblemname());
                intent.putExtra("classify", "00");
                break;
            case R.id.fragment_help_five:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpTwo.getProblemdata().get(0).getProblemid());
                intent.putExtra("problem", helpTwo.getProblemdata().get(0).getProblemname());
                intent.putExtra("classify", "01");
                break;
            case R.id.fragment_help_six:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpTwo.getProblemdata().get(1).getProblemid());
                intent.putExtra("problem", helpTwo.getProblemdata().get(1).getProblemname());
                intent.putExtra("classify", "01");
                break;
            case R.id.fragment_help_seven:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpTwo.getProblemdata().get(2).getProblemid());
                intent.putExtra("problem", helpTwo.getProblemdata().get(2).getProblemname());
                intent.putExtra("classify", "01");
                break;
            case R.id.fragment_help_eight:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpTwo.getProblemdata().get(3).getProblemid());
                intent.putExtra("problem", helpTwo.getProblemdata().get(3).getProblemname());
                intent.putExtra("classify", "01");
                break;
            case R.id.fragment_help_nine:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpThree.getProblemdata().get(0).getProblemid());
                intent.putExtra("problem", helpThree.getProblemdata().get(0).getProblemname());
                intent.putExtra("classify", "02");
                break;
            case R.id.fragment_help_ten:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpThree.getProblemdata().get(1).getProblemid());
                intent.putExtra("problem", helpThree.getProblemdata().get(1).getProblemname());
                intent.putExtra("classify", "02");
                break;
            case R.id.fragment_help_eleven:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpThree.getProblemdata().get(2).getProblemid());
                intent.putExtra("problem", helpThree.getProblemdata().get(2).getProblemname());
                intent.putExtra("classify", "02");
                break;
            case R.id.fragment_help_twelve:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpThree.getProblemdata().get(3).getProblemid());
                intent.putExtra("problem", helpThree.getProblemdata().get(3).getProblemname());
                intent.putExtra("classify", "02");
                break;
            case R.id.fragment_help_thirteen:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpFour.getProblemdata().get(0).getProblemid());
                intent.putExtra("problem", helpFour.getProblemdata().get(0).getProblemname());
                intent.putExtra("classify", "03");
                break;
            case R.id.fragment_help_fourteen:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpFour.getProblemdata().get(1).getProblemid());
                intent.putExtra("problem", helpFour.getProblemdata().get(1).getProblemname());
                intent.putExtra("classify", "03");
                break;
            case R.id.fragment_help_fifteen:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpFour.getProblemdata().get(2).getProblemid());
                intent.putExtra("problem", helpFour.getProblemdata().get(2).getProblemname());
                intent.putExtra("classify", "03");
                break;
            case R.id.fragment_help_sixteen:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpFour.getProblemdata().get(3).getProblemid());
                intent.putExtra("problem", helpFour.getProblemdata().get(3).getProblemname());
                intent.putExtra("classify", "03");
                break;
            case R.id.fragment_help_seventeen:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpFive.getProblemdata().get(0).getProblemid());
                intent.putExtra("problem", helpFive.getProblemdata().get(0).getProblemname());
                intent.putExtra("classify", "04");
                break;
            case R.id.fragment_help_eighteen:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpFive.getProblemdata().get(1).getProblemid());
                intent.putExtra("problem", helpFive.getProblemdata().get(1).getProblemname());
                intent.putExtra("classify", "04");
                break;
            case R.id.fragment_help_nineteen:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpFive.getProblemdata().get(2).getProblemid());
                intent.putExtra("problem", helpFive.getProblemdata().get(2).getProblemname());
                intent.putExtra("classify", "04");
                break;
            case R.id.fragment_help_twenty:
                intent = new Intent(getActivity(), AnswersActivity.class);
                intent.putExtra("id", helpFive.getProblemdata().get(3).getProblemid());
                intent.putExtra("problem", helpFive.getProblemdata().get(3).getProblemname());
                intent.putExtra("classify", "04");
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
