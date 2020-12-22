package com.example.demo0610;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.demo0610.zxing.activity.CaptureActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;

public class MainActivity extends FragmentActivity {

    MapFragment mmapfragment;
    DataFragment mdatafragment;

    public static String U="U";
    String username;

    private Context mContext;
    private final int MY_NOTIFICATION_ID = 11151990;
    private BroadcastReceiver mRefreshReceiver;
    public static final String DATA_REFRESHED_ACTION = "course.labs.notificationslabnew.DATA_REFRESHED";
    NotificationManager notifyManager;
    int max =36;
    String maxC="桂花园";
    SharedPreferences sp;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //恢复原有的样式,必须这个位置
        setTheme(R.style.AppTheme);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }

        notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //头像
        file_exist("username");     //TODO: 修改
        file_download("filename");     //TODO: 文件名？

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_count();

        final TextView untv = findViewById(R.id.username);
        Intent intent = getIntent();
        if(intent!=null)
        {
            String U = intent.getStringExtra(LoginActivity.U);
            username = U;
            untv.setText(U);
//            demo s=(demo)intent.getSerializableExtra(MainActivity.C);
//            setTitle("名字是："+ s.getUserName());
        }

        context = getApplicationContext();
        sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        String name = sp.getString("username","");
//        String password=sp.getString("password","");
//        String remem =sp.getString("remember","");
//        String auto =sp.getString("autologin","");
        untv.setText(name);

        final TextView infotv1 = findViewById(R.id.flipper1);
        infotv1.setText("当前"+maxC+"餐厅\n的人流量最高");
        final TextView infotv2 = findViewById(R.id.flipper2);
        infotv2.setText("目前共"+max+"人");

        ImageButton scan = findViewById(R.id.scan);
        RequestOptions option1 = new RequestOptions()//圆形图片
                .override(200,200)
                .fitCenter()
                .centerCrop();
        Glide.with(this)
                .load(R.drawable.scan)
                .apply(option1)
                .into(scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQrCode();
            }
        });

        final ImageButton user_protrait = findViewById(R.id.user_portrait);
        RequestOptions option2 = new RequestOptions()//圆形图片
                .circleCrop();
        Glide.with(this)
                .load(R.mipmap.test1)
                .apply(option2)
                .into(user_protrait);
        user_protrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserActivity.class);
                intent.putExtra("U",username);
                startActivity(intent);
            }
        });

//        Intent intent = getIntent();
//        String fruitname = intent.getStringExtra(FRUIT_NAME);
//        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);

        //设置toolbar
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //设置导航按钮
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

//        mCollapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
//        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
//        mCollapsingToolbarLayout.setTitle(qing);

        //加载图片，设置文字
//        ImageView image_toolbar = findViewById(R.id.image_toolbar);
//        Glide.with(this).load(R.drawable.test).into(image_toolbar);

//      String fruitContent = generateFruitContent(fruitname);
//      fruitContentText.setText(fruitContent);

//        ImageView user_portrait = findViewById(R.id.user_portrait);
//        RequestOptions options = new RequestOptions()//圆形图片
//                .circleCrop();
//        Glide.with(this).load(R.drawable.connor).apply(options).into(user_portrait);
//
//        FloatingActionButton fab = findViewById(R.id.camera);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, " ", Toast.LENGTH_SHORT).show();
//            }
//        });

        final Button btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mmapfragment = new MapFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager .beginTransaction()   // 开启一个事务
                        .replace(R.id.container_map, mmapfragment,"f1")
                        .addToBackStack("true")
                        .commit();
            }
        });
//                 指定tag的好处是后续我们可以通过
//                 Fragment1 frag = getSupportFragmentManager().findFragmentByTag("f1")
//                 从FragmentManager中查找Fragment对象

        final Button btn_data = findViewById(R.id.btn_data);
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceholderFragment  mdatafragment = new PlaceholderFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager .beginTransaction()   // 开启一个事务
                        .replace(R.id.container_data, mdatafragment,"f1")
                        .addToBackStack("true")
                        .commit();
            }
        });

//        final Button btn_setting = findViewById(R.id.btn_setting);
//        btn_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Setting!", Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }   // onCreate---------------------------------------------------------------------------------------------------

    protected void onResume() {
        super.onResume();

        // TODO:
        // Register the BroadcastReceiver to receive a
        // DATA_REFRESHED_ACTION broadcast
//        IntentFilter intentFilter = new IntentFilter(DATA_REFRESHED_ACTION);
//        registerReceiver(mRefreshReceiver,intentFilter);


    }

    protected void onPause() {
        super.onPause();

        String times;
        long t = System.currentTimeMillis();
        String time = String.valueOf(t/1000)+"000";

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setLenient(false);
        times = sdf.format(new Date(Long.parseLong(time)));

        if (max>1) {
            sendNotification();
        }
        if (times.equals("12:00:00")){
            sendNotification();
        }
    }

    private void get_count(){//获取当天每个食堂当前人流量
        String times;
        long t = System.currentTimeMillis();
        String time = String.valueOf(t/1000)+"000";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        times = sdf.format(new Date(Long.parseLong(time)));
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/getcount")
                .addParams("time",times)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        JSONObject obj;
                        JSONArray jsonArray = JSONObject.parseArray(response);
                        obj = jsonArray.getJSONObject(0);
                        String count1 = "0";
                        count1 = obj.getString("dinghall1");//这里是每个食堂的当前人流量
                        String count2 = obj.getString("dinghall2");
                        String count3 = obj.getString("dinghall3");

//                        Log.i("count1-----------------",count1);
//                        Log.i("count2-----------------",count2);
//                        Log.i("count3-----------------",count3);
//                        Toast.makeText(MainActivity.this,count1,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this,count2,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this,count3,Toast.LENGTH_SHORT).show();
                        int int1 = Integer.parseInt(count1);
                        int int2 = Integer.parseInt(count2);
                        int int3 = Integer.parseInt(count3);

                        int1 = 0;
                        int2 = 1;
                        int3 = 2;
                        if (max<=int1){
                            max=int1;
                            maxC="桂花园";
                        }
                        if(max<=int2){
                            max=int2;
                            maxC="玫瑰园";
                        }
                        if(max<=int3){
                            max=int3;
                            maxC="紫薇阁";
                        }
                    }
                });
    }

    private void sendNotification() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //获取NotificationManager实例
//        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.drawable.sakura)
                //设置通知标题
                .setAutoCancel(true)
                .setContentTitle("情急令提醒")
                //设置通知内容
                .setContentText("当前"+maxC+"餐厅的人流量最高，请注意出行！")
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
                .setWhen(System.currentTimeMillis())
                .setContentIntent(mainPendingIntent);
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(3, builder.build());
    }



    // TODO:下载
    private void file_download(String filename){
        String name = filename;
        //String name = "59739640.jpg";
        OkHttpUtils
                .post()
                .url("http://10.0.2.2:8090/Web_Service/file_download")
                .addParams("filename",name)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),"59739640.jpg") {
                    @Override
                    public void inProgress(float progress, long total, int id) {
//                        progressBar1.setProgress((int)(100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.i("debug",Log.getStackTraceString(e));
                    }

                    @Override
                    public void onResponse(File response, int id) {
//                        Log.e(TAG, "onResponse :" + response.getAbsolutePath());

                    }
                });
    }

    // TODO:查看服务器是否存在图片的方法
    private void file_exist(String username)
    {
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/filedir")
                .addParams("username", username)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        //返回true表示服务器存在用户保存图片
                        //false表示不存在

                    }
                });
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void startQrCode(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){

            }
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},11003);
            return;
        }
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent,11002);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == 11002 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            //将扫描出的信息显示出来
            Toast.makeText(this, scanResult, Toast.LENGTH_SHORT).show();
            insert("root",Integer.parseInt(scanResult));
            //output_text.setText(scanResult);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11003:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(MainActivity.this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void insert(String username,int floor)
    {
        String return_value = null;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int m = calendar.get(Calendar.DAY_OF_WEEK);//今天是星期几


        long t = System.currentTimeMillis();
        String time = String.valueOf(t/1000)+"000";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return_value = sdf.format(new Date(Long.parseLong(time)));

        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/insert")
                .addParams("username", username)
                .addParams("time", return_value)
                .addParams("floor",String.valueOf(floor))
                .addParams("Date",String.valueOf(m))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void check_time(String username,int floor)
    {
        String time = null;
        long t = System.currentTimeMillis();
        String temp = String.valueOf(t/1000)+"000";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        time = sdf.format(new Date(Long.parseLong(temp)));
        OkHttpUtils
                .post()
                .url("http://47.97.173.230:8090/Android_Server/checktime")
                .addParams("username", username)
                .addParams("time", time)
                .addParams("floor",String.valueOf(floor))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("debug",Log.getStackTraceString(e));

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    }
                });
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class PlaceholderFragment extends Fragment {
        public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec",};//柱状图下标
        public String[] week = new String[7];//柱状图下标
        public String[] day = new String[7];//柱状图
        List weekcount_list = new ArrayList();//柱状图y坐标
        List datecount_list = new ArrayList();//折线图y坐标
        //public String[] week_list = new String[];
        public String[] time_count = new String[8];
        public Integer[] result = new Integer[8];//折线图y坐标

        String current_date;//折线图当前选定日期




        public int n =0;

        public final static String[] times = new String[]{"08:00:00","10:00:00","12:00:00","14:00:00","16:00:00","18:00:00","20:00:00","22:00:00"};//折线图下标

        public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};//折线图下标

        private LineChartView chartTop;
        private ColumnChartView chartBottom;

        private LineChartData lineData;
        private ColumnChartData columnData;
        private Line line = new Line();
        private String info ="2";

        private Spinner medalSpinner;
        private ArrayAdapter<String> adapter;
        private static final String[] sSpiner={"桂花园 1st","桂花园 2nd","桂花园 3rd","玫瑰园 1st",
                "玫瑰园 2nd"," 玫瑰园 3nd","紫薇阁 1st","紫薇阁 2nd","紫薇阁 3rd"};

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_line_column_dependency, container, false);

            // *** TOP LINE CHART ***
            chartTop = (LineChartView) rootView.findViewById(R.id.chart_top);

            // Generate and set data for line chart
            generateInitialLinevalue();
            line = lineData.getLines().get(0);

            // *** BOTTOM COLUMN CHART ***

            chartBottom = (ColumnChartView) rootView.findViewById(R.id.chart_bottom);

            getDate();
            getcount_week(info);
            //generateColumnvalue();
            //generateColumnData();


            medalSpinner = rootView.findViewById(R.id.medal_spinner);
            adapter = new ArrayAdapter<String>(getContext(),R.layout.spinnerlayout,sSpiner);
//          MapFragment.this,R.layout.spinnerlayout,sSpiner
            medalSpinner.setAdapter(adapter);
            medalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv = (TextView)view;
                    tv.setTextColor(getResources().getColor(R.color.result_view)); //设置颜色
                    tv.setTextSize(15.0f); //设置大小
                    tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL); //设置居中
//                    String info = adapter.getItem(position).toString();//获取i所在的文本
//                    info = adapter.getItem(position);
                    getcount_week(String.valueOf(position+1));
//                    Toast.makeText(PlaceholderFragment.this,info,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            return rootView;
        }

        private void generateColumnData() {
            Log.e("body", String.valueOf(n));
            int numSubcolumns = 1;
            int numColumns = months.length;//柱状图

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;//每一条柱状图数据
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
                }

                axisValues.add(new AxisValue(i).setLabel(months[i]));//设置x轴

                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
            }

            columnData = new ColumnChartData(columns);
            // Log.e("body", String.valueOf(columns));

            columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

            chartBottom.setColumnChartData(columnData);

            // Set value touch listener that will trigger changes for chartTop.
            chartBottom.setOnValueTouchListener(new ValueTouchListener());

            // Set selection mode to keep selected month column highlighted.
            chartBottom.setValueSelectionEnabled(true);

            chartBottom.setZoomType(ZoomType.HORIZONTAL);
        }

        private void generateInitialLineData() {
            int numValues = 7;

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 0; i < numValues; ++i) {
                values.add(new PointValue(i, 0));
                axisValues.add(new AxisValue(i).setLabel(days[i]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

            List<Line> lines = new ArrayList<Line>();
            lines.add(line);

            lineData = new LineChartData(lines);
            lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

            chartTop.setLineChartData(lineData);

            // For build-up animation you have to disable viewport recalculation.
            chartTop.setViewportCalculationEnabled(false);

            // And set initial max viewport and current viewport- remember to set viewports after data.
            Viewport v = new Viewport(0, 110, 6, 0);
            chartTop.setMaximumViewport(v);
            chartTop.setCurrentViewport(v);

            chartTop.setZoomType(ZoomType.HORIZONTAL);
        }

        private void generateLineData(int color, float range) {
            // Cancel last animation if not finished.
            chartTop.cancelDataAnimation();

            // Modify data targets
            Line line = lineData.getLines().get(0);// For this example there is always only one line.
            line.setColor(color);
            for (PointValue value : line.getValues()) {
                // Change target only for Y value.
                value.setTarget(value.getX(), (float) Math.random() * range);
            }

            // Start new data animation with 300ms duration;
            chartTop.startDataAnimation(300);
        }
        private class ValueTouchListener implements ColumnChartOnValueSelectListener {

            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                String date = day[columnIndex];
                getLinevalue(date,info);
                chartTop.cancelDataAnimation();
                // Modify data targets
                //Line line = lineData.getLines().get(0);// For this example there is always only one line.
                line.setColor(value.getColor());
                //getLinevalue(date);
                // Start new data animation with 300ms duration;
                chartTop.startDataAnimation(300);
                //generateLinevalue(value.getColor(), 100);
                //Log.e("body", String.valueOf(columnIndex));
            }

            @Override
            public void onValueDeselected() {

                generateLineData(ChartUtils.COLOR_GREEN, 0);

            }
        }


        private void generateColumnvalue()
        {
            chartBottom.cancelDataAnimation();

            int numSubcolumns = 1;
            int numColumns = week.length;//柱状图

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;//每一条柱状图数据
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {

                    values.add(new SubcolumnValue(Integer.parseInt(weekcount_list.get(i).toString()), ChartUtils.pickColor()));
                }

                axisValues.add(new AxisValue(i).setLabel(week[i]));//设置x轴

                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
            }

            columnData = new ColumnChartData(columns);
            // Log.e("body", String.valueOf(columns));

            columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

            chartBottom.setColumnChartData(columnData);

            // Set value touch listener that will trigger changes for chartTop.
            chartBottom.setOnValueTouchListener(new ValueTouchListener());

            // Set selection mode to keep selected month column highlighted.
            chartBottom.setValueSelectionEnabled(true);

            chartBottom.setZoomType(ZoomType.HORIZONTAL);
            chartBottom.startDataAnimation(300);
        }

        private void getDate(){
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int m = calendar.get(Calendar.DAY_OF_WEEK);

            long t = System.currentTimeMillis();
            String time = String.valueOf(t/1000)+"000";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            for(int i = 0;i<7;i++)
            {
                int temp = m - i;
                if(temp <= 0)
                    temp += 7;
                week[6-i] = String.valueOf(temp);
                day[6-i] = sdf.format(new Date(date.getTime()- i * 24 * 60 * 60 * 1000));
            }
            for(int i =0;i<7;i++)
            {
                if(week[i].equals("1"))
                    week[i] = "星期日";
                else if(week[i].equals("2"))
                    week[i] = "星期一";
                else if(week[i].equals("3"))
                    week[i] = "星期二";
                else if(week[i].equals("4"))
                    week[i] = "星期三";
                else if(week[i].equals("5"))
                    week[i] = "星期四";
                else if(week[i].equals("6"))
                    week[i] = "星期五";
                else if(week[i].equals("7"))
                    week[i] = "星期六";
            }
        }

        private void getcount_week(String floor){//获取柱状图y轴数据
            //int floor = 2;

            OkHttpUtils
                    .post()
                    .url("http://47.97.173.230:8090/Android_Server/weekcount")
                    .addParams("floor", floor)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("debug", Log.getStackTraceString(e));

                        }
                        @Override
                        public void onResponse(String response, int id) {
                            //weekcount_list.add(1);
                            //return_value1 = Integer.parseInt(response);
//                                if(Integer.parseInt(response) == 0)
//                                    n++;
                            //Log.e("body", String.valueOf(n));
                            //Log.e("body", response);
                            function1(response);
                        }
                    });

        }

        private void function1(String response)
        {
            JSONObject obj;
            JSONArray jsonArray = JSONObject.parseArray(response);
            obj = jsonArray.getJSONObject(0);
            String one = obj.getString("one");
            String two = obj.getString("two");
            String three = obj.getString("three");
            String four = obj.getString("four");
            String five =obj.getString("five");
            String six = obj.getString("six");
            String seven = obj.getString("seven");

            time_count[0] = one;
            time_count[1] = two;
            time_count[2] = three;
            time_count[3] = four;
            time_count[4] = five;
            time_count[5] = six;
            time_count[6] = seven;

            weekcount_list.add(one);
            weekcount_list.add(two);
            weekcount_list.add(three);
            weekcount_list.add(four);
            weekcount_list.add(five);
            weekcount_list.add(six);
            weekcount_list.add(seven);

            generateColumnvalue();
            weekcount_list.clear();

        }

        //public int count2=0;
        private void function2(String response)
        {
            JSONObject obj;
            JSONArray jsonArray = JSONObject.parseArray(response);
            obj = jsonArray.getJSONObject(0);
            String one = obj.getString("one");
            String two = obj.getString("two");
            String three = obj.getString("three");
            String four = obj.getString("four");
            String five =obj.getString("five");
            String six = obj.getString("six");
            String seven = obj.getString("seven");
            String eight =obj.getString("eight");

            time_count[0] = one;
            time_count[1] = two;
            time_count[2] = three;
            time_count[3] = four;
            time_count[4] = five;
            time_count[5] = six;
            time_count[6] = seven;
            time_count[7] = eight;

            int i = 0;
            for (PointValue value : line.getValues()) {
                // Change target only for Y value.
                value.setTarget(value.getX(), Integer.parseInt(time_count[i]));
                i++;
            }
            /*if (count2<8){
                datecount_list.add(response);
                //Log.e(String.valueOf(count2), response);
                count2++;
            }
            if (count2==8){
                generateLinevalue(color,range,columnIndex);
            }*/

        }

        private void generateLinevalue(int color, float range) {
            // Cancel last animation if not finished.
            chartTop.cancelDataAnimation();
            // Modify data targets
            //Line line = lineData.getLines().get(0);// For this example there is always only one line.
            line.setColor(color);
            int i = 0;
            for (PointValue value : line.getValues()) {
                // Change target only for Y value.
                value.setTarget(value.getX(), Integer.parseInt(time_count[i]));
                i++;
            }

            // Start new data animation with 300ms duration;
            chartTop.startDataAnimation(300);
        }

        private void generateInitialLinevalue() {
            int numValues = 8;

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 0; i < numValues; ++i) {
                values.add(new PointValue(i, 0));
                axisValues.add(new AxisValue(i).setLabel(times[i]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

            List<Line> lines = new ArrayList<Line>();
            lines.add(line);

            lineData = new LineChartData(lines);
            lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

            chartTop.setLineChartData(lineData);

            // For build-up animation you have to disable viewport recalculation.
            chartTop.setViewportCalculationEnabled(false);

            // And set initial max viewport and current viewport- remember to set viewports after data.
            Viewport v = new Viewport(0, 110, 6, 0);
            chartTop.setMaximumViewport(v);
            chartTop.setCurrentViewport(v);

            chartTop.setZoomType(ZoomType.HORIZONTAL);
        }



        private void getLinevalue(String date,String floor){//获取折线图y轴数据
            //String date = day[columnIndex];
            String time = date;
            OkHttpUtils
                    .post()
                    .url("http://47.97.173.230:8090/Android_Server/timecount")
                    .addParams("time", time)
                    .addParams("floor", floor)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("debug", Log.getStackTraceString(e));

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            function2(response);
                        }
                    });

        }

    }



}
