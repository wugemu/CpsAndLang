package com.lxkj.dmhw.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.AreaBean;
import com.lxkj.dmhw.bean.self.CityBean;
import com.lxkj.dmhw.bean.self.ProvinceBean;
import com.lxkj.dmhw.widget.wheel.ArrayWheelAdapter;
import com.lxkj.dmhw.widget.wheel.DistrictModel;
import com.lxkj.dmhw.widget.wheel.OnWheelChangedListener;
import com.lxkj.dmhw.widget.wheel.SelectAddressInterface;
import com.lxkj.dmhw.widget.wheel.WheelView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WheelAddrOnline implements OnWheelChangedListener {

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();


    /**
     * 当前区的postion
     */
    protected int mCurrentDistrictNamePosition;
    /**
     * 当前省的postion
     */
    protected int mCurrentProviceNamePosition;
    /**
     * 当前市的postion
     */
    protected int mCurrentCityNamePosition;
    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName = "";
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName = "";
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";
    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    @BindView(R.id.id_province)
    WheelView mViewProvince;
    @BindView(R.id.id_city)
    WheelView mViewCity;
    @BindView(R.id.id_district)
    WheelView mViewDistrict;
    private Activity context;
    private Dialog overdialog;
    private SelectAddressInterface selectAdd;
    private int tmp1, tmp2, tmp3;
    private Handler handler;
    private int what;
    private List<ProvinceBean> provinceBeanList;
    public WheelAddrOnline(Activity context,List<ProvinceBean> provinceBeanList,
                               SelectAddressInterface selectAdd) {
        this.context = context;
        this.selectAdd = selectAdd;
        this.provinceBeanList=provinceBeanList;
        initView();
        setUpData();

    }

    private void initView() {
        // TODO Auto-generated constructor stub
        View overdiaView = View.inflate(context,
                R.layout.dialog_select_address, null);
        ButterKnife.bind(this, overdiaView);

        overdialog = new Dialog(context, R.style.dialog_lhp);
        Window window = overdialog.getWindow();
        window.setWindowAnimations(R.style.mystyle); // 添加动画
        overdialog.setContentView(overdiaView);
        overdialog.setCanceledOnTouchOutside(false);
        setUpListener();
    }

    public void showDialog() {

        if (overdialog != null) {
            if (mViewProvince != null) mViewProvince.setCurrentItem(mCurrentProviceNamePosition);
            if (mViewCity != null) mViewCity.setCurrentItem(mCurrentCityNamePosition);
            if (mViewDistrict != null) mViewDistrict.setCurrentItem(mCurrentDistrictNamePosition);
            overdialog.show();
            Window win = overdialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(lp);
        }else {
            overdialog.show();
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity && mCitisDatasMap.size() > 0) {
            updateAreas();
        } else if (wheel == mViewDistrict && mDistrictDatasMap.size() > 0) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentProviceName+mCurrentCityName)[newValue];
            tmp3 = newValue;
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {

        int pCurrent = mViewCity.getCurrentItem();
        tmp2 = pCurrent;
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentProviceName+mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mCurrentDistrictName = areas[0];
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context,
                areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        tmp1 = pCurrent;
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities != null) {
            mViewCity
                    .setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
            mViewCity.setCurrentItem(0);
            updateAreas();
        }

    }

    @OnClick({R.id.btn_confirm, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (selectAdd != null) {
                    selectAdd.setAreaString(mCurrentProviceName + " " + mCurrentCityName + " "
                            + mCurrentDistrictName);
                    selectAdd.setArea(mCurrentProviceName,mCurrentCityName,mCurrentDistrictName);
                    mCurrentProviceNamePosition = tmp1;
                    mCurrentCityNamePosition = tmp2;
                    mCurrentDistrictNamePosition = tmp3;
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = what;
                    msg.obj = mCurrentProviceName;
                    handler.sendMessage(msg);
                }

//			ToastUtil.show(context,mCurrentCityNamePosition+":"+mCurrentCityNamePosition+":"+mCurrentDistrictNamePosition);
                overdialog.cancel();
                break;

            case R.id.btn_cancel:
                overdialog.cancel();
                break;
            default:
                break;
        }
    }
//	@Override
//	public void onClick(View v) {
//
//	}

    private void showSelectedResult() {
        ToastUtil.show(context,"当前选中:" + mCurrentProviceName + "," + mCurrentCityName + ","
                + mCurrentDistrictName + "," + mCurrentZipCode);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context,
                mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
//		mBtnConfirm.setOnClickListener(this);
        // 添加onclick事件
//		mBtnCancel.setOnClickListener(this);

    }

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        // */ 初始化默认选中的省、市、区
        if (provinceBeanList == null) {
            return;
        }
        if(provinceBeanList.size()>0) {
            mCurrentProviceName = provinceBeanList.get(0).getProvince();
            List<CityBean> cityList = provinceBeanList.get(0).getSub();
            if (cityList != null && cityList.size() > 0) {
                mCurrentCityName = cityList.get(0).getCity();
                List<AreaBean> districtList = cityList.get(0)
                        .getSub();
                mCurrentDistrictName = districtList.get(0).getArea();
                mCurrentZipCode = districtList.get(0).getAreaId();
            }
        }
        mProvinceDatas = new String[provinceBeanList.size()];
        for (int i = 0; i < provinceBeanList.size(); i++) {
            // 遍历所有省的数据
            mProvinceDatas[i] = provinceBeanList.get(i).getProvince();
            List<CityBean> cityList = provinceBeanList.get(i).getSub();
            String[] cityNames = new String[cityList.size()];
            for (int j = 0; j < cityList.size(); j++) {
                // 遍历省下面的所有市的数据
                cityNames[j] = cityList.get(j).getCity();
                List<AreaBean> districtList = cityList.get(j)
                        .getSub();
                String[] distrinctNameArray = new String[districtList
                        .size()];
                DistrictModel[] distrinctArray = new DistrictModel[districtList
                        .size()];
                for (int k = 0; k < districtList.size(); k++) {
                    // 遍历市下面所有区/县的数据
                    DistrictModel districtModel = new DistrictModel(
                            districtList.get(k).getArea(), districtList
                            .get(k).getAreaId());
                    // 区/县对于的邮编，保存到mZipcodeDatasMap
                    mZipcodeDatasMap.put(districtList.get(k).getArea(),
                            districtList.get(k).getAreaId());
                    distrinctArray[k] = districtModel;
                    distrinctNameArray[k] = districtModel.getName();
                }
                // 市-区/县的数据，保存到mDistrictDatasMap
                mDistrictDatasMap.put(provinceBeanList.get(i).getProvince()+cityNames[j], distrinctNameArray);
            }
            // 省-市的数据，保存到mCitisDatasMap
            mCitisDatasMap.put(provinceBeanList.get(i).getProvince(), cityNames);
        }
    }
}
