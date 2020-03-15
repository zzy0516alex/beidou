package com.example.beidou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mapdraw extends AppCompatActivity {

    //
    MapView mMapView = null;//地图视图
    BaiduMap mbaidumap;
    TextView binInfo;
    Const[] currentpoints=new Const[8];
    Const[] currentpoints1=new Const[8];
    Const[] currentpoints2=new Const[8];
    Const[] currentpoints3=new Const[8];
    Const[] getCurrentpoints=new Const[8];
    String bin_type="recyclable";
    Marker[] markerA=new Marker[8];
    Marker[] markerB=new Marker[8];
    Marker[] markerC=new Marker[8];
    Marker[] markerD=new Marker[8];
    Marker[] markerE=new Marker[8];
    int markerNum=0;
    //
    public SharedPreferences recycle;
    public SharedPreferences other;
    public SharedPreferences kitchen;
    public SharedPreferences hazardous;
    public LocationClient mLocationClient = null;
    public MyLocationListener myListener =  new MyLocationListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定位
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
        //使用BaiduMap  SDK
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_mapdraw);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);//获得地图视图的对象
        mbaidumap=mMapView.getMap();
        //隐藏baidulogo
        View child = mMapView.getChildAt(1);
        if(child!=null && (child instanceof ImageView || child instanceof ZoomControls))
        {
            child.setVisibility(View.INVISIBLE);
        }
        //
        for(int i=0;i<8;i++)
        {
            getCurrentpoints[i]=new Const();
        }
        initpoints1();
        initpoints2();
        initpoints3();
        initpoints4();
        //获取搜索模式
        Intent intent=getIntent();
        bin_type=intent.getStringExtra("Bintype");
        //显示模式
        binInfo=findViewById(R.id.binInfo);
        String text="搜索"+bin_type;
        binInfo.setText(text);
        //存储器
        recycle=getSharedPreferences("recyclable",MODE_PRIVATE);
        other=getSharedPreferences("other",MODE_PRIVATE);
        kitchen=getSharedPreferences("kitchen",MODE_PRIVATE);
        hazardous=getSharedPreferences("hazardous",MODE_PRIVATE);
        write_recyclable(currentpoints,8);
        write_kitchen(currentpoints1,8);
        write_other(currentpoints2,8);
        write_hazardous(currentpoints3,8);
        //clear_recyclable();
        switch (bin_type) {
            case "recyclable":
                getCurrentpoints = read_recyclable(8);break;
            case "other":
                getCurrentpoints = read_other(8);break;
            case "kitchen":
                getCurrentpoints = read_kitchen(8);break;
            case "hazardous":
                getCurrentpoints = read_hazardous(8);break;
            default:break;


        }
        mbaidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(int i=0;i<8;i++) {
                    if (marker == markerA[i]) {
                        LatLng point = markerA[i].getPosition();
                        Button button = new Button(getApplicationContext());
                        button.setText("查看详情");
                        button.setTextSize(15);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(Mapdraw.this,recyclable.class);
                                startActivity(intent);
                            }
                        });
                        InfoWindow mInfowindow = new InfoWindow(button, point, -140);
                        mbaidumap.showInfoWindow(mInfowindow);
                    }
                    else if(marker==markerB[i]){
                        LatLng point = markerB[i].getPosition();
                        Button button = new Button(getApplicationContext());
                        button.setText("查看详情");
                        button.setTextSize(15);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(Mapdraw.this,other.class);
                                startActivity(intent);
                            }
                        });
                        InfoWindow mInfowindow = new InfoWindow(button, point, -140);
                        mbaidumap.showInfoWindow(mInfowindow);
                    }
                    else if(marker==markerC[i]){
                        LatLng point = markerC[i].getPosition();
                        Button button = new Button(getApplicationContext());
                        button.setText("查看详情");
                        button.setTextSize(15);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(Mapdraw.this,kitchen.class);
                                startActivity(intent);
                            }
                        });
                        InfoWindow mInfowindow = new InfoWindow(button, point, -140);
                        mbaidumap.showInfoWindow(mInfowindow);
                    }
                    else if(marker==markerD[i]){
                        LatLng point = markerD[i].getPosition();
                        Button button = new Button(getApplicationContext());
                        button.setText("查看详情");
                        button.setTextSize(15);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(Mapdraw.this,hazardous.class);
                                startActivity(intent);
                            }
                        });
                        InfoWindow mInfowindow = new InfoWindow(button, point, -140);
                        mbaidumap.showInfoWindow(mInfowindow);
                    }
                    else if(marker==markerE[i]){
                        LatLng point = markerE[i].getPosition();
                        Button button = new Button(getApplicationContext());
                        button.setText("此垃圾桶已满，不建议前往");
                        button.setTextSize(10);
                        InfoWindow mInfowindow = new InfoWindow(button, point, -140);
                        mbaidumap.showInfoWindow(mInfowindow);
                    }
                }
                return false;
            }
        });

    }

    /*
       Activity关闭时将地图关闭
        */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    /*
    ActivityonResume时，将地图onResume。
     */
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    /*
    ActivityonPause时，将地图onPause。
     */
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    public void drawpoints(Const[] pointsin, String bin_type, int num){
        markerNum=0;
        switch (bin_type)
        {
            case "recyclable":
            {
                for(int i=0;i<num;i++)
                {
                    if(pointsin[i].status.equals("可用"))
                    {
                        addpoint_recyclable(pointsin[i].Latitude,pointsin[i].Longtitude);
                    }
                    else if(pointsin[i].status.equals("已满"))
                    {
                        addpoint_full(pointsin[i].Latitude,pointsin[i].Longtitude);
                    }
                }
                break;
            }
            case "other":
            {
                for(int i=0;i<num;i++)
                {
                    if(pointsin[i].status.equals("可用"))
                    {
                        addpoint_other(pointsin[i].Latitude,pointsin[i].Longtitude);
                        Log.d("draw","other");
                    }
                    else if(pointsin[i].status.equals("已满"))
                    {
                        addpoint_full(pointsin[i].Latitude,pointsin[i].Longtitude);
                        Log.d("draw","brocken");
                    }
                }
                break;
            }
            case "kitchen":
            {
                for(int i=0;i<num;i++)
                {
                    if(pointsin[i].status.equals("可用"))
                    {
                        addpoint_kitchen(pointsin[i].Latitude,pointsin[i].Longtitude);
                    }
                    else if(pointsin[i].status.equals("已满"))
                    {
                        addpoint_full(pointsin[i].Latitude,pointsin[i].Longtitude);
                    }
                }
                break;
            }
            case "hazardous":
            {
                for(int i=0;i<num;i++)
                {
                    if(pointsin[i].status.equals("可用"))
                    {
                        addpoint_hazardous(pointsin[i].Latitude,pointsin[i].Longtitude);
                    }
                    else if(pointsin[i].status.equals("已满"))
                    {
                        addpoint_full(pointsin[i].Latitude,pointsin[i].Longtitude);
                    }
                }
                break;
            }
            default:Log.d("switch","error");break;
        }
    }
    //绘制可回收垃圾桶
    public void addpoint_recyclable(double x,double y) {
//        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        LatLng point=new LatLng(x,y);
        //设置图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.recyclable);
        //加载图标
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        markerA[markerNum]=(Marker)mbaidumap.addOverlay(option);
        markerNum+=1;
    }
    //绘制其他垃圾桶
    public void addpoint_other(double x,double y) {
        LatLng point = new LatLng(x, y);
        //设置图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.other);
        //加载图标
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        markerB[markerNum] = (Marker) mbaidumap.addOverlay(option);
        markerNum += 1;
    }
    //绘制厨余垃圾桶
    public void addpoint_kitchen(double x,double y) {
        LatLng point = new LatLng(x, y);
        //设置图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.kitchen);
        //加载图标
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        markerC[markerNum] = (Marker) mbaidumap.addOverlay(option);
        markerNum += 1;
    }
    //绘制有害垃圾桶
    public void addpoint_hazardous(double x,double y) {
        LatLng point = new LatLng(x, y);
        //设置图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.hazardous);
        //加载图标
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        markerD[markerNum] = (Marker) mbaidumap.addOverlay(option);
        markerNum += 1;
    }

    public void addpoint_full(double x,double y) {
        LatLng point = new LatLng(x, y);
        //设置图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.full);
        //加载图标
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        markerE[markerNum] = (Marker) mbaidumap.addOverlay(option);
        markerNum += 1;
    }
    //绘制用户坐标
    public void addMypoint(double x,double y)
    {
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        LatLng point=new LatLng(x,y);
        //设置图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mypoint);
        //加载图标
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        options.add(option);
        //显示在地图上
        mbaidumap.addOverlays(options);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private Const[] read_recyclable(int num){
        Const[]get=new Const[num];
        for(int i=0;i<num;i++)
        {
            get[i]=new Const();
        }
        for(int j=0;j<num;j++)
        {
            if(recycle.getFloat("Lati"+j,1)!=1.0) {
                get[j].Latitude = (double)recycle.getFloat("Lati" + j, 1);
                get[j].Longtitude=(double)recycle.getFloat("Long"+j,1);
                get[j].status=recycle.getString("status"+j,"");
                Log.d("text","Lati="+get[j].Longtitude+";Long="+get[j].Longtitude+";status="+get[j].status);
            }
            else Log.d("text","empty");
        }
        return get;
    }
    private Const[] read_other(int num){
        Const[]get=new Const[num];
        for(int i=0;i<num;i++)
        {
            get[i]=new Const();
        }
        for(int j=0;j<num;j++)
        {
            if(other.getFloat("Lati"+j,1)!=1.0) {
                get[j].Latitude = (double)other.getFloat("Lati" + j, 1);
                get[j].Longtitude=(double)other.getFloat("Long"+j,1);
                get[j].status=other.getString("status"+j,"");
                Log.d("text","Lati="+get[j].Longtitude+";Long="+get[j].Longtitude+";status="+get[j].status);
            }
            else Log.d("text","empty");
        }
        return get;
    }
    private Const[] read_kitchen(int num){
        Const[]get=new Const[num];
        for(int i=0;i<num;i++)
        {
            get[i]=new Const();
        }
        for(int j=0;j<num;j++)
        {
            if(kitchen.getFloat("Lati"+j,1)!=1.0) {
                get[j].Latitude = (double)kitchen.getFloat("Lati" + j, 1);
                get[j].Longtitude=(double)kitchen.getFloat("Long"+j,1);
                get[j].status=kitchen.getString("status"+j,"");
                Log.d("text","Lati="+get[j].Longtitude+";Long="+get[j].Longtitude+";status="+get[j].status);
            }
            else Log.d("text","empty");
        }
        return get;
    }
    private Const[] read_hazardous(int num){
        Const[]get=new Const[num];
        for(int i=0;i<num;i++)
        {
            get[i]=new Const();
        }
        for(int j=0;j<num;j++)
        {
            if(hazardous.getFloat("Lati"+j,1)!=1.0) {
                get[j].Latitude = (double)hazardous.getFloat("Lati" + j, 1);
                get[j].Longtitude=(double)hazardous.getFloat("Long"+j,1);
                get[j].status=hazardous.getString("status"+j,"");
                Log.d("text","Lati="+get[j].Longtitude+";Long="+get[j].Longtitude+";status="+get[j].status);
            }
            else Log.d("text","empty");
        }
        return get;
    }
    private void write_recyclable(Const[] pointsin, int num)
    {
        SharedPreferences.Editor editor1=recycle.edit();
        for(int i=0;i<num;i++){
            editor1.putFloat("Lati"+i,(float)pointsin[i].Latitude).apply();
            editor1.putFloat("Long"+i,(float)pointsin[i].Longtitude).apply();
            editor1.putString("status"+i,pointsin[i].status).apply();
        }
    }
    private void write_other(Const[] pointsin, int num)
    {
        SharedPreferences.Editor editor1=other.edit();
        for(int i=0;i<num;i++){
            editor1.putFloat("Lati"+i,(float)pointsin[i].Latitude).apply();
            editor1.putFloat("Long"+i,(float)pointsin[i].Longtitude).apply();
            editor1.putString("status"+i,pointsin[i].status).apply();
        }
    }
    private void write_kitchen(Const[] pointsin, int num)
    {
        SharedPreferences.Editor editor1=kitchen.edit();
        for(int i=0;i<num;i++){
            editor1.putFloat("Lati"+i,(float)pointsin[i].Latitude).apply();
            editor1.putFloat("Long"+i,(float)pointsin[i].Longtitude).apply();
            editor1.putString("status"+i,pointsin[i].status).apply();
        }
    }
    private void write_hazardous(Const[] pointsin, int num)
    {
        SharedPreferences.Editor editor1=hazardous.edit();
        for(int i=0;i<num;i++){
            editor1.putFloat("Lati"+i,(float)pointsin[i].Latitude).apply();
            editor1.putFloat("Long"+i,(float)pointsin[i].Longtitude).apply();
            editor1.putString("status"+i,pointsin[i].status).apply();
        }
    }
    private void clear_recyclable(){
        if(recycle!=null) recycle.edit().clear().apply();
    }
    private void clear_other(){
        if(recycle!=null) other.edit().clear().apply();
    }
    private void clear_kitchen(){
        if(recycle!=null) kitchen.edit().clear().apply();
    }
    private void clear_hazardous(){
        if(recycle!=null) hazardous.edit().clear().apply();
    }

    private void initpoints1()
    {
        for(int i=0;i<8;i++)
        {
            currentpoints[i]=new Const();
        }
        currentpoints[0].Latitude=31.8974;
        currentpoints[0].Longtitude=118.8329;
        currentpoints[0].status="可用";
        currentpoints[1].Latitude=31.892;
        currentpoints[1].Longtitude=118.844;
        currentpoints[1].status="损坏";
        currentpoints[2].Latitude=31.8973;
        currentpoints[2].Longtitude=118.8327;
        currentpoints[2].status="已满";
        currentpoints[3].Latitude=31.8971;
        currentpoints[3].Longtitude=118.8325;
        currentpoints[3].status="可用";
        currentpoints[4].Latitude=31.8968;
        currentpoints[4].Longtitude=118.8332;
        currentpoints[4].status="可用";
        currentpoints[5].Latitude=31.8977;
        currentpoints[5].Longtitude=118.8325;
        currentpoints[5].status="可用";
        currentpoints[6].Latitude=31.8979;
        currentpoints[6].Longtitude=118.8323;
        currentpoints[6].status="可用";
        currentpoints[7].Latitude=31.8976;
        currentpoints[7].Longtitude=118.8335;
        currentpoints[7].status="已满";
    }
    private void initpoints2()
    {
        for(int i=0;i<8;i++)
        {
            currentpoints1[i]=new Const();
        }
        currentpoints1[0].Latitude=31.8977;
        currentpoints1[0].Longtitude=118.8329;
        currentpoints1[0].status="损坏";
        currentpoints1[1].Latitude=31.887;
        currentpoints1[1].Longtitude=118.822;
        currentpoints1[1].status="可用";
        currentpoints1[2].Latitude=31.8966;
        currentpoints1[2].Longtitude=118.8297;
        currentpoints1[2].status="已满";
        currentpoints1[3].Latitude=31.8978;
        currentpoints1[3].Longtitude=118.8330;
        currentpoints1[3].status="可用";
        currentpoints1[4].Latitude=31.8981;
        currentpoints1[4].Longtitude=118.8324;
        currentpoints1[4].status="已满";
        currentpoints1[5].Latitude=31.899;
        currentpoints1[5].Longtitude=118.8334;
        currentpoints1[5].status="可用";
        currentpoints1[6].Latitude=31.8970;
        currentpoints1[6].Longtitude=118.8341;
        currentpoints1[6].status="可用";
        currentpoints1[7].Latitude=31.8963;
        currentpoints1[7].Longtitude=118.8323;
        currentpoints1[7].status="已满";
    }
    private void initpoints3()
    {
        for(int i=0;i<8;i++)
        {
            currentpoints2[i]=new Const();
        }
        currentpoints2[0].Latitude=31.8971;
        currentpoints2[0].Longtitude=118.8336;
        currentpoints2[0].status="可用";
        currentpoints2[1].Latitude=31.8966;
        currentpoints2[1].Longtitude=118.822;
        currentpoints2[1].status="可用";
        currentpoints2[2].Latitude=31.8974;
        currentpoints2[2].Longtitude=118.8297;
        currentpoints2[2].status="已满";
        currentpoints2[3].Latitude=31.8976;
        currentpoints2[3].Longtitude=118.8330;
        currentpoints2[3].status="可用";
        currentpoints2[4].Latitude=31.8985;
        currentpoints2[4].Longtitude=118.8345;
        currentpoints2[4].status="损坏";
        currentpoints2[5].Latitude=31.8982;
        currentpoints2[5].Longtitude=118.8343;
        currentpoints2[5].status="可用";
        currentpoints2[6].Latitude=31.8964;
        currentpoints2[6].Longtitude=118.8341;
        currentpoints2[6].status="可用";
        currentpoints2[7].Latitude=31.8968;
        currentpoints2[7].Longtitude=118.8323;
        currentpoints2[7].status="可用";
    }
    private void initpoints4()
    {
        for(int i=0;i<8;i++)
        {
            currentpoints3[i]=new Const();
        }
        currentpoints3[0].Latitude=31.8991;
        currentpoints3[0].Longtitude=118.8343;
        currentpoints3[0].status="可用";
        currentpoints3[1].Latitude=31.8958;
        currentpoints3[1].Longtitude=118.8328;
        currentpoints3[1].status="可用";
        currentpoints3[2].Latitude=31.8974;
        currentpoints3[2].Longtitude=118.8297;
        currentpoints3[2].status="损坏";
        currentpoints3[3].Latitude=31.8983;
        currentpoints3[3].Longtitude=118.8334;
        currentpoints3[3].status="已满";
        currentpoints3[4].Latitude=31.8981;
        currentpoints3[4].Longtitude=118.8341;
        currentpoints3[4].status="可用";
        currentpoints3[5].Latitude=31.8986;
        currentpoints3[5].Longtitude=118.8333;
        currentpoints3[5].status="可用";
        currentpoints3[6].Latitude=31.8969;
        currentpoints3[6].Longtitude=118.8339;
        currentpoints3[6].status="可用";
        currentpoints3[7].Latitude=31.8977;
        currentpoints3[7].Longtitude=118.8323;
        currentpoints3[7].status="可用";
    }
    private double currentLongtitude=0;
    private double currentLatitude=0;
    private double pastLongtitude=0;
    private int counter=0;
    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeServerError) {
                Toast.makeText(Mapdraw.this,"服务器异常",Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                Toast.makeText(Mapdraw.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                Toast.makeText(Mapdraw.this,"请打开位置权限和启用手机位置信息",Toast.LENGTH_SHORT).show();
            } else {
                pastLongtitude=currentLongtitude;
                currentLongtitude = location.getLongitude();
                currentLatitude = location.getLatitude();
                Log.d("main2",""+currentLatitude+";"+currentLongtitude);
                if(pastLongtitude!=currentLongtitude)
                {
                    mbaidumap.clear();
                    addMypoint(currentLatitude,currentLongtitude);
                    drawpoints(getCurrentpoints,bin_type,8);
                }
                counter+=1;
                if(counter>=10) {
                    //设置自己的位置坐标
                    final LatLng myPoint = new LatLng(currentLatitude, currentLongtitude);
                    //将自己的坐标显示在地图中心
                    MapStatus mapStatus = new MapStatus.Builder().target(myPoint).zoom(20).build();
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                    mbaidumap.setMapStatus(mapStatusUpdate);
                    counter=0;
                }

            }
        }
    }
    class Const{
        public double Latitude;
        public double Longtitude;
        public String status;
    }
}
