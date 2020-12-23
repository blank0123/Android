package com.example.weather_lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String TAG = "Test";
    TextView tv_weather, tv_humidity, tv_temperature;
    TextView tv_cloud, tv_feelsLike, tv_windDir,tv_windSpeed;
    String cityID = null;
    String weather = null, temperature = null, humidity = null;
    String cloud = null, feelsLike = null, windDir = null,windSpeed = null;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //此处分别输入你申请的Username与Key
        HeConfig.init("HE2011301404151417", "4ed9a3c947294480888b8f6f65e12e58");
        //个人开发者需要切换到免费服务域名，默认使用中国付费节点服务域名会报错
//        HeConfig.switchToFreeServerNode();
        HeConfig.switchToDevService();
        tv_weather = findViewById(R.id.weather);
        tv_temperature = findViewById(R.id.temperature);
        tv_humidity = findViewById(R.id.humidity);
        tv_cloud =  findViewById(R.id.cloud);
        tv_feelsLike =  findViewById(R.id.feelsLike);
        tv_windDir =  findViewById(R.id.windDir);
        tv_windSpeed =  findViewById(R.id.windSpeed);
        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.spinner)); //设置颜色
                tv.setTextSize(15.0f); //设置大小
                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL); //设置居中
                getWether(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getWether(0);
    }

    private void getWether(int n){
        String city = "CN101010100";
        if (n==0){
            city = "CN101010100";   //北京
        }else if (n==1){
            city = "CN101020100";   //上海
        }else if (n==2){
            city = "CN101210101";   //杭州
        }else if (n==3){
            city = "CN101280101";   //广州
        }

        /**
         * 实况天气数据
         * @param location 所查询的地区，可通过该地区名称、ID、IP和经纬度进行查询经纬度格式：纬度,经度
         *                 （英文,分隔，十进制格式，北纬东经为正，南纬西经为负)
         * @param lang     (选填)多语言，可以不使用该参数，默认为简体中文
         * @param unit     (选填)单位选择，公制（m）或英制（i），默认为公制单位
         * @param listener 网络访问结果回调
         */
        QWeather.getWeatherNow(MainActivity.this, city, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }
            @Override
            public void onSuccess(WeatherNowBean weatherBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK.getCode().equalsIgnoreCase(weatherBean.getCode())) {
                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();

//                    tv_weather.setText(now.getHumidity());

                    String JsonNow = new Gson().toJson(weatherBean.getNow());
//                    json输出例子{"cloud":"91","dew":"3","feelsLike":"9","humidity":"52","icon":"104","obsTime":"20201130T1554+0800","precip":"0.0",
//                    "pressure":"1025","temp":"12","text":"阴","vis":"7","wind360":"315","windDir":"西北风","windScale":"3","windSpeed":"13"}

//                    String JsonBasic = new Gson().toJson(weatherBean.getBasic());
                    JSONObject jsonObject = null;
                    Log.i(TAG, "LifeStyle onSuccess:" + new Gson().toJson(weatherBean.getNow()));
                    Log.i(TAG, "-------------------:" + new Gson().toJson(weatherBean.getBasic()));
                    try {
                        jsonObject = new JSONObject(JsonNow);

                        weather = jsonObject.getString("text");
                        temperature = jsonObject.getString("temp");
                        humidity = jsonObject.getString("humidity");
                        cloud = jsonObject.getString("cloud");
                        feelsLike = jsonObject.getString("feelsLike");
                        windDir = jsonObject.getString("windDir");
                        windSpeed = jsonObject.getString("windSpeed");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //在此查看返回数据失败的原因
                    String status = weatherBean.getCode();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }


//                tv_location.setText("fxLink:"+city+"updateTime:"+district);
                tv_weather.setText("天气:   "+weather);
                tv_temperature.setText("气温:   "+temperature+"°C");
                tv_humidity.setText("湿度:   "+humidity);
                tv_cloud.setText("云量:   "+cloud);
                tv_feelsLike.setText("体感:   "+feelsLike+"°C");
                tv_windDir.setText("风向:   "+windDir);
                tv_windSpeed.setText("风速:   "+windSpeed);
            }
        });
    }



//    private void getWether() {
//        /**
//         * 实况天气
//         * 实况天气即为当前时间点的天气状况
//         * @param context  上下文
//         * @param listener  网络访问回调接口
//         * 通过getWeatherNow和监听器OnResultWeatherNowBeanListener来监听返回的数据
//         */
//        QWeather.getWeatherNow(MainActivity.this, new QWeather.OnResultWeatherNowListener() {
//            public static final String TAG = "HeWeather_getWeatherNow";
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "onError:", e);
//                System.out.println("Weather Now Error:" + new Gson());
//            }
//
//            @Override
//            public void onSuccess(WeatherNowBean weatherNowBean) {
//
//            }
//
//            @Override
//            public void onSuccess(Now dataObject) {
//                Log.i(TAG, " Weather Now onSuccess:" + new Gson().toJson(dataObject));
//                String weather = null, temperature = null, city = null, district = null, cid = null;
//                if (dataObject.getStatus().equals("ok")) {
//                    String JsonNow = new Gson().toJson(dataObject.getNow());
//                    String JsonBasic = new Gson().toJson(dataObject.getBasic());
//                    JSONObject jsonObject = null;
//                    JSONObject jsonObject1 = null;
//                    try {
//                        jsonObject = new JSONObject(JsonNow);
//                        jsonObject1 = new JSONObject(JsonBasic);
//                        city = jsonObject1.getString("parent_city");
//                        district = jsonObject1.getString("location");
//                        cid = jsonObject1.getString("cid");
//                        weather = jsonObject.getString("cond_txt");
//                        temperature = jsonObject.getString("tmp");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Mistakes...", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String temperature2 = temperature + "℃";
//                String city2 = city + "市 " + district;
//                cityID = cid;
//                tv_location.setText(city2);
//                tv_weather.setText(weather);
//                tv_temperature.setText(temperature2);
//                /**
//                 * 生活指数
//                 * 在这里只显示生活指数中的运动指数
//                 * @param context  上下文
//                 * @param listener  网络访问回调接口
//                 */
//                QWeather.getWeatherLifeStyle(MainActivity.this, new QWeather.OnResultWeatherLifeStyleBeanListener() {
//                    public static final String TAG2 = "HeWeather_getSuggesion";
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Log.i(TAG2, "ERROR IS:", throwable);
//                    }
//
//                    @Override
//                    public void onSuccess(Lifestyle lifestyle) {
//                        Log.i(TAG2, "LifeStyle onSuccess:" + new Gson().toJson(lifestyle));
//                        String sport = null;
//                        String suggestion = null;
//                        if (lifestyle.getStatus().equals("ok")) {
//                            String JsonLifestyle = new Gson().toJson(lifestyle.getLifestyle());
//                            JSONObject jsonObject = null;
//                            try {
//                                JSONArray jsonArray = new JSONArray(JsonLifestyle);
//                                sport = jsonArray.getString(3);
//                                jsonObject = new JSONObject(sport);
//                                suggestion = jsonObject.getString("txt");
//                                tv_suggestion.setText(suggestion);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            Toast.makeText(MainActivity.this, "Mistakes...", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                });
//            }
//        });
//    }


}