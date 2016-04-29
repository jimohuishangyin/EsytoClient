package com.ec2.yspay.widget.popwindow;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.City;
import com.ec2.yspay.common.CityManager;
import com.ec2.yspay.common.Province;
import com.ec2.yspay.widget.ScrollerNumberPicker;
import com.ec2.yspay.widget.ScrollerNumberPicker.OnSelectListener;

/**
 * 城市列表级联弹窗效果
 * 
 * @ClassName: CityPopwindow
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 罗洪祥 luohx@esyto.com
 * @date 2016年4月12日 下午3:16:10
 *
 */
public class CityPopwindow extends Popwindow {
	private ScrollerNumberPicker provincePicker;
	private ScrollerNumberPicker cityPicker;
	private ArrayList<Province> mProvinceList;
	private CityManager mCityManager;
	private int tempProvinceIndex = -1;
	private String tUserProvince, tUserCity;
	int province = 0;
	private OnCitySelectedListener citySelectedListener;

	public CityPopwindow(Activity mContext) {
		super(mContext);
		mCityManager = new CityManager();
		mCityManager.readDataBase(mContext);
		mProvinceList = (ArrayList<Province>) mCityManager.getProvivceList();
	}

	public void setOnCitySelectedListener(
			OnCitySelectedListener citySelectedListener) {
		this.citySelectedListener = citySelectedListener;
	}

	public void showCityPopwindow(View parent) {
		showCityPopwindow(parent, "厦门", "福建");
	}

	/**
	 * 
	 * @Description: 调用此方法弹窗窗口
	 * @param @param parent 弹窗父控件
	 * @param @param city 默认城市
	 * @param @param province 默认省份
	 * @return void
	 * @author 罗洪祥
	 * @date 2016年4月12日 下午3:16:44
	 */
	public void showCityPopwindow(View parent, String city, String province) {
		final View outerView = LayoutInflater.from(mContext).inflate(
				R.layout.wheel_view_two, null);
		TextView tvSure = (TextView) outerView.findViewById(R.id.tv_sure);
		TextView tvCancel = (TextView) outerView.findViewById(R.id.tv_cancel);
		provincePicker = (ScrollerNumberPicker) outerView
				.findViewById(R.id.province);
		cityPicker = (ScrollerNumberPicker) outerView.findViewById(R.id.city);
		if (city.isEmpty() || province.isEmpty()) {
			city = "北京";
			province = "北京";
		}
		if ((province.substring(province.length() - 1, province.length()))
				.equals("省")
				|| (province
						.substring(province.length() - 1, province.length()))
						.equals("市")
				|| province.equals("内蒙古")
				|| province.equals("新疆")
				|| province.equals("西藏")
				|| province.equals("广西")) {
			city = "北京";
			province = "北京";
		}
		tUserProvince = province;
		tUserCity = city;

		provincePicker.setData(getProvince());
		int index = getIndexByProvinceName(province);
		provincePicker.setDefault(index);
		ArrayList<String> citys = getCity(mProvinceList.get(index)
				.getProvinceID());
		int cityIndex = getIndexByCityName(city, citys);
		cityPicker.setData(citys);
		cityPicker.setDefault(cityIndex);
		provincePicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void selecting(int id, String text) {

			}

			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null)
					return;
				if (tempProvinceIndex != id) {
					cityPicker.setData(getCity(id + 1));
					cityPicker.setDefault(0);
					tUserCity = getCity(id + 1).get(0);
				}
				tempProvinceIndex = id;
				tUserProvince = text;
			}
		});
		cityPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void selecting(int id, String text) {

			}

			@Override
			public void endSelect(int id, String text) {
				tUserCity = text;
			}
		});

		tvSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				// updatePersonalMsg();
				if (citySelectedListener != null)
					citySelectedListener.onResult(tUserCity, tUserProvince);
			}
		});
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		showPopupwindow(outerView, parent);
	}

	private ArrayList<String> getProvince() {
		ArrayList<String> str = new ArrayList<String>();
		for (int i = 0; i < mProvinceList.size(); i++) {
			str.add(mProvinceList.get(i).getName());
		}
		return str;
	}

	private ArrayList<String> getCity(int provinceid) {
		ArrayList<City> names = mCityManager.getProvinceCityNames(provinceid);
		final int size = names.size();
		ArrayList<String> str = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			str.add(names.get(i).getName());
		}
		return str;
	}

	public interface OnCitySelectedListener {
		public void onResult(String city, String province);
	}

	/**
	 * @Description: 通过省份中文找下角标
	 * @param @param provinceName
	 * @param @return
	 * @return int
	 * @author 罗洪祥
	 * @date 2016年4月12日 下午3:18:20
	 */
	private int getIndexByProvinceName(String provinceName) {
		for (int i = 0; i < mProvinceList.size(); i++) {
			if (mProvinceList.get(i).getName().equals(provinceName))
				return i;
		}
		return 0;
	}

	private int getIndexByCityName(String city, ArrayList<String> str) {
		for (int i = 0; i < str.size(); i++) {
			if (str.get(i).equals(city))
				return i;
		}
		return 0;
	}
}
