package com.example.tkb;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // dong nay themm
    DatabaseReference myData;
    TextView text_test;
    Button btn_1hoichuong;
    Button btn_2hoichuong;
    FirebaseDatabase database;
    //private static final long START_TIME_IN_MILLIS = 60000;
    TextView text_timer;
    DatabaseReference bao1hoi;
    DatabaseReference bao2hoi;
    DatabaseReference tatchuong;
    DatabaseReference chonmothoichuong;
    DatabaseReference chonhaihoichuong;
    DatabaseReference mothoichuong;
    DatabaseReference haihoichuong;
    Calendar calendarBao;
    //private CountDownTimer mCountDownTimer;
    //private boolean mTimerRunning;
    //private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    boolean status1=false;

    TextView txtchuong;
    LinearLayout ln1hoi5s, lnbtn12hoi,lmo,bt;
    CheckBox checktay;

    public static TextView txtGioTiepTheo, txtNghiLe;
    TimerTask timerTask;
    Timer timer;
    SimpleDateFormat sdfGiay = new SimpleDateFormat("ss");
    SimpleDateFormat sdfPhut = new SimpleDateFormat("mm");
    SimpleDateFormat sdfGio = new SimpleDateFormat("HH");

/*    SimpleDateFormat sdfngay = new SimpleDateFormat("d");
    SimpleDateFormat sdfthang = new SimpleDateFormat("M");
    SimpleDateFormat sdfnam = new SimpleDateFormat("yyyy");*/

    private Button btn1;

    private RadioButton radioButton1hoi;
    private RadioButton radioButton2hoi;
    private RadioButton radioButtontat;
    private RadioGroup radioGroupchuong;
    private RadioButton motchuongMO,haichuongMO,tatchuongMO;
    SharedPreferences sharedPreferences;

    AlarmManager alarmManager;
    Intent intent;
    PendingIntent pendingIntent;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        intent= new Intent(MainActivity.this, AlarmReceiver.class);
        calendarBao=calendarBao.getInstance();
        // dong nay them
        btn_1hoichuong = (Button) findViewById(R.id.btn1hoi);
        btn_2hoichuong = (Button) findViewById(R.id.btn3hoi);
        myData = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        chonmothoichuong=database.getReference("chonradio1hoi");
        chonhaihoichuong=database.getReference("chonradio2hoi");
        mothoichuong=database.getReference("mothoi");
        haihoichuong=database.getReference("haihoi");
        tatchuong=database.getReference("tatchuong");
        bao1hoi=database.getReference("bao1hoi");
        bao2hoi=database.getReference("bao2hoi");

        //txtchuong = findViewById(R.id.chuong);
        ln1hoi5s = findViewById(R.id.chuong1hoi5s);
        lnbtn12hoi = findViewById(R.id.btn12hoi);
        lmo = findViewById(R.id.lammo);
        bt = findViewById(R.id.binhthuong);
        ln1hoi5s.setVisibility(View.GONE);
        lnbtn12hoi.setVisibility(View.GONE);
        motchuongMO = findViewById(R.id.motchuongmo);
        haichuongMO = findViewById(R.id.haichuongmo);
        tatchuongMO = findViewById(R.id.tatchuongmo);

        this.radioButton1hoi = (RadioButton) this.findViewById(R.id.radioButton_1hoi);
        this.radioButton2hoi = (RadioButton) this.findViewById(R.id.radioButton_2hoi);
        this.radioButtontat = (RadioButton) this.findViewById(R.id.radioButton_tat);
        this.radioGroupchuong = (RadioGroup) this.findViewById(R.id.radioGroup_chuong);

        /*txtchuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtchuong.getText().equals("Chuông:")) {
                    radioGroup1hoi.setVisibility(View.GONE);
                    radioButton2hoi.setVisibility(View.GONE);
                    radioButtontat.setVisibility(View.GONE);
                    ln1hoi5s.setVisibility(View.VISIBLE);
                    lnbtn12hoi.setVisibility(View.VISIBLE);
                    txtchuong.setText("Bấm chuông bằng tay:");
                } else if (txtchuong.getText().equals("Bấm chuông bằng tay:")) {
                    radioGroup1hoi.setVisibility(View.VISIBLE);
                    radioButton2hoi.setVisibility(View.VISIBLE);
                    radioButtontat.setVisibility(View.VISIBLE);
                    ln1hoi5s.setVisibility(View.GONE);
                    lnbtn12hoi.setVisibility(View.GONE);
                    txtchuong.setText("Chuông:");
                }
            }
        });*/

        radioButton1hoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonmothoichuong.setValue(1);
                chonhaihoichuong.setValue(0);
                tatchuong.setValue(0);
                Toast.makeText(MainActivity.this,"Đã chọn 1 hồi chuông",Toast.LENGTH_LONG).show();
            }
        });

        radioButton2hoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonmothoichuong.setValue(0);
                chonhaihoichuong.setValue(1);
                tatchuong.setValue(0);
                Toast.makeText(MainActivity.this,"Đã chọn 2 hồi chuông",Toast.LENGTH_LONG).show();
            }
        });

        radioButtontat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tatchuong.setValue(1);
                chonmothoichuong.setValue(0);
                chonhaihoichuong.setValue(0);
                Toast.makeText(MainActivity.this,"Đã tắt chuông",Toast.LENGTH_LONG).show();
            }
        });

        btn_1hoichuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Timer
                new CountDownTimer(5000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        bao1hoi.setValue(1);
                    }

                    @Override
                    public void onFinish() {
                        bao1hoi.setValue(0);
                        Toast.makeText(MainActivity.this,"Xong 1 hồi chuông",Toast.LENGTH_LONG).show();
                    }
                }.start();
            }
        });

        btn_2hoichuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Timer
                new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        bao2hoi.setValue(1);
                    }

                    @Override
                    public void onFinish() {
                        bao2hoi.setValue(0);
                        Toast.makeText(MainActivity.this,"Xong 2 hồi chuông",Toast.LENGTH_LONG).show();
                    }
                }.start();
//                status1=!status1;
//                if(status1==true) bao1hoi.setValue(1);
//                else bao1hoi.setValue(0);
            }
        });

        checktay = findViewById(R.id.checkboxtay);
        loadchuongtay();
        checktay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                   savechuongtay();
                    chonmothoichuong.setValue(0);
                    chonhaihoichuong.setValue(0);
                    tatchuong.setValue(0);
                }
                else{
                    savechuongtay();
                    chonmothoichuong.setValue(1);
                    chonhaihoichuong.setValue(0);
                    tatchuong.setValue(0);
                }
            }
        });
        btn1 = findViewById(R.id.btnsetting);

        loadChuong();

        this.radioGroupchuong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                xulyradio(group, checkedId); }
            private void xulyradio(RadioGroup group, int checkedId) {

                int checkedRadioId = group.getCheckedRadioButtonId();
                if (checkedRadioId == R.id.radioButton_1hoi) {
                    savechuong();
                    Toast.makeText(MainActivity.this, "Chọn 1 hồi chuông", Toast.LENGTH_SHORT).show();
                } else if (checkedRadioId == R.id.radioButton_2hoi) {
                    savechuong();
                    Toast.makeText(MainActivity.this, "Chọn 2 hồi chuông", Toast.LENGTH_SHORT).show();
                } else if (checkedRadioId == R.id.radioButton_tat) {
                    savechuong();
                    Toast.makeText(MainActivity.this, "Tắt chuông", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtGioTiepTheo = findViewById(R.id.giotieptheo);
        txtNghiLe = findViewById(R.id.txtNgayNghi);

        loadData();
        openReceiver();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });

        TextClock textClockthu = findViewById(R.id.textclockthu);
//      String formatdate = "E, d-M-yyyy, k:m: sa";
        String formatdatethu = "E";
        textClockthu.setFormat12Hour(formatdatethu);
        textClockthu.setFormat24Hour(formatdatethu);

        TextClock textClockngay = findViewById(R.id.textclockngay);
        String formatdatengay = "d-M-yyyy";
        textClockngay.setFormat12Hour(formatdatengay);
        textClockngay.setFormat24Hour(formatdatengay);

        TextClock textClockgio = findViewById(R.id.textclockgio);
        String formatdategio = "k:m:s";
        textClockgio.setFormat12Hour(formatdategio);
        textClockgio.setFormat24Hour(formatdategio);

        SimpleDateFormat simpleDateFormatgio = new SimpleDateFormat(formatdategio);
       /*SimpleDateFormat simpleDateFormatngay = new SimpleDateFormat(formatdatengay);*/

        Date today = Calendar.getInstance().getTime();

        String dategio = simpleDateFormatgio.format(today);
/*        String datengay = simpleDateFormatngay.format(today);

        Calendar calendar = Calendar.getInstance();
        String ngayhientai = sdfngay.format(calendar.getTime());
        String thanghientai = sdfthang.format(calendar.getTime());
        String namhientai = sdfnam.format(calendar.getTime());*/

/*
        int ingay = ngayhientai.compareTo("21");
        int ithang = thanghientai.compareTo("04");
        int inam = namhientai.compareTo("2019");
*/

        //txtGioTiepTheo.setText("i = "+i);
/*        if (ingay == 0 && ithang >= 0 && inam == 0) {
            txtGioTiepTheo.setText("dang nghi le");
        }*/

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //lấy giờ hệ thống:
                        Calendar calendar = Calendar.getInstance();
                        //txtGioTiepTheo.setText("Gio hien tai"+sdf.format(calendar.getTime()));
                        String gioHienTai = sdfGio.format(calendar.getTime());
                        String phutHienTai = sdfPhut.format(calendar.getTime());
                        String giayHienTai = sdfGiay.format(calendar.getTime());

                        if(gioHienTai.equals("08")&&phutHienTai.equals("40")&&giayHienTai.equals("00"))
                        {
                            countdown();
                        }
                        if(gioHienTai.equals("08")&&phutHienTai.equals("50")&&giayHienTai.equals("00"))
                        {
                            countdown();
                        }

                        int igio1 = gioHienTai.compareTo("07");
                        int iphut1 = phutHienTai.compareTo("00");

                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio1 == 0 && iphut1 >= 0) {
                            txtGioTiepTheo.setText("07" + ":50");
                        }

                        int igio2 = gioHienTai.compareTo("07");
                        int iphut2 = phutHienTai.compareTo("50");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio2 >= 0 && iphut2 >= 0) {
                            txtGioTiepTheo.setText("08" + ":40");
                        }
                        int igio21 = gioHienTai.compareTo("08");
                        int iphut21 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio21 == 0 && iphut21 >= 0) {
                            txtGioTiepTheo.setText("08" + ":40");
                        }

                        int igio3 = gioHienTai.compareTo("08");
                        int iphut3 = phutHienTai.compareTo("40");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio3 == 0 && iphut3 >= 0) {
                            txtGioTiepTheo.setText("08" + ":50");
                        }

                        int igio4 = gioHienTai.compareTo("08");
                        int iphut4 = phutHienTai.compareTo("50");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio4 == 0 && iphut4 >= 0) {
                            txtGioTiepTheo.setText("09" + ":40");
                        }
                        int igio41 = gioHienTai.compareTo("09");
                        int iphut41 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio41 >= 0 && iphut41 >= 0) {
                            txtGioTiepTheo.setText("09" + ":40");
                        }

                        int igio5 = gioHienTai.compareTo("09");
                        int iphut5 = phutHienTai.compareTo("40");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio5 >= 0 && iphut5 >= 0) {
                            txtGioTiepTheo.setText("10" + ":30");
                        }
                        int igio51 = gioHienTai.compareTo("10");
                        int iphut51 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio51 == 0 && iphut51 >= 0) {
                            txtGioTiepTheo.setText("10" + ":30");
                        }

                        int igio6 = gioHienTai.compareTo("10");
                        int iphut6 = phutHienTai.compareTo("30");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio6 == 0 && iphut6 >= 0) {
                            txtGioTiepTheo.setText("10" + ":40");
                        }

                        int igio7 = gioHienTai.compareTo("10");
                        int iphut7 = phutHienTai.compareTo("40");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio7 >= 0 && iphut7 >= 0) {
                            txtGioTiepTheo.setText("11" + ":30");
                        }

                        int igio71 = gioHienTai.compareTo("11");
                        int iphut71 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio71 == 0 && iphut71 >= 0) {
                            txtGioTiepTheo.setText("11" + ":30");
                        }

                        int igio8 = gioHienTai.compareTo("11");
                        int iphut8 = phutHienTai.compareTo("30");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio8 >= 0 && iphut8 >= 0) {
                            txtGioTiepTheo.setText("12" + ":30");
                        }
                        int igio81 = gioHienTai.compareTo("12");
                        int iphut81 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio81 == 0 && iphut81 >= 0) {
                            txtGioTiepTheo.setText("12" + ":30");
                        }

                        int igio9 = gioHienTai.compareTo("12");
                        int iphut9 = phutHienTai.compareTo("30");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio9 >= 0 && iphut9 >= 0) {
                            txtGioTiepTheo.setText("13" + ":20");
                        }
                        int igio91 = gioHienTai.compareTo("13");
                        int iphut91 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio91 == 0 && iphut91 >= 0) {
                            txtGioTiepTheo.setText("13" + ":20");
                        }

                        int igio10 = gioHienTai.compareTo("13");
                        int iphut10 = phutHienTai.compareTo("20");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio10 == 0 && iphut10 >= 0) {
                            txtGioTiepTheo.setText("14" + ":10");
                        }
                        int igio101 = gioHienTai.compareTo("14");
                        int iphut101 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio101 == 0 && iphut101 >= 0) {
                            txtGioTiepTheo.setText("14" + ":10");
                        }

                        int igio11 = gioHienTai.compareTo("14");
                        int iphut11 = phutHienTai.compareTo("10");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio11 == 0 && iphut11 >= 0) {
                            txtGioTiepTheo.setText("14" + ":20");
                        }

                        int igio12 = gioHienTai.compareTo("14");
                        int iphut12 = phutHienTai.compareTo("20");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio12 == 0 && iphut12 >= 0) {
                            txtGioTiepTheo.setText("15" + ":10");
                        }
                        int igio121 = gioHienTai.compareTo("15");
                        int iphut121 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio121 >= 0 && iphut121 >= 0) {
                            txtGioTiepTheo.setText("15" + ":10");
                        }

                        int igio13 = gioHienTai.compareTo("15");
                        int iphut13 = phutHienTai.compareTo("10");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio13 == 0 && iphut13 >= 0) {
                            txtGioTiepTheo.setText("16" + ":00");
                        }

                        int igio14 = gioHienTai.compareTo("16");
                        int iphut14 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio14 == 0 && iphut14 >= 0) {
                            txtGioTiepTheo.setText("16" + ":10");
                        }

                        int igio15 = gioHienTai.compareTo("16");
                        int iphut15 = phutHienTai.compareTo("10");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio15 == 0 && iphut15 >= 0) {
                            txtGioTiepTheo.setText("17" + ":00");
                        }

                        int igio16 = gioHienTai.compareTo("17");
                        int iphut16 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio16 == 0 && iphut16 >= 0) {
                            txtGioTiepTheo.setText("17" + ":50");
                        }

                        int igio17 = gioHienTai.compareTo("17");
                        int iphut17 = phutHienTai.compareTo("50");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio17 == 0 && iphut17 >= 0) {
                            txtGioTiepTheo.setText("7" + ":00");
                        }
                        int igio171 = gioHienTai.compareTo("18");
                        int iphut171 = phutHienTai.compareTo("00");
                        //txtGioTiepTheo.setText("i = "+i);
                        if (igio171 >= 0 && iphut171 >= 0) {
                            txtGioTiepTheo.setText("7" + ":00");
                        }

                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    private void openReceiver() {
        pendingIntent= PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        calendarBao.set(Calendar.HOUR_OF_DAY,Calendar.getInstance().getTime().getHours());
        calendarBao.set(Calendar.MINUTE,Calendar.getInstance().getTime().getMinutes()+2);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendarBao.getTimeInMillis(),pendingIntent);
    }

    public void loadData() {
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);

        if (sharedPreferences != null) {
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.radioauto);
            if (checkedRadioButtonId == R.id.radionghile) {
                txtGioTiepTheo.setVisibility(View.GONE);
                txtNghiLe.setVisibility(View.VISIBLE);
                txtNghiLe.setText("Đang trong thời gian nghỉ lễ");
            } else if (checkedRadioButtonId == R.id.radiothi) {
                txtGioTiepTheo.setVisibility(View.GONE);
                txtNghiLe.setVisibility(View.VISIBLE);
                txtNghiLe.setText("Đang trong thời gian thi");
            } else {
                txtGioTiepTheo.setVisibility(View.VISIBLE);
                txtNghiLe.setVisibility(View.GONE);
            }
        }
    }

    public void loadChuong()  {
        sharedPreferences= this.getSharedPreferences("chuong",MODE_PRIVATE);
        if(sharedPreferences != null) {
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.radioButton_1hoi);
            this.radioGroupchuong.check(checkedRadioButtonId);
        } else {
            this.radioGroupchuong.check(R.id.radioButton_1hoi);
            Toast.makeText(this," ",Toast.LENGTH_LONG).show();
        }
    }

    public void savechuong()  {
        // File chia sẻ sử dụng trong nội bộ ứng dụng, hoặc các ứng dụng được chia sẻ cùng User.
        SharedPreferences sharedPreferences= this.getSharedPreferences("chuong", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // ID của RadioButton đang được chọn.
        int checkedRadioButtonId = radioGroupchuong.getCheckedRadioButtonId();
        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);
        // Save.
        editor.apply();
    }

    /**
     * hàm lưu trạng thái
     */
    public void savechuongtay()
    {
        //tạo đối tượng getSharedPreferences
        sharedPreferences = this.getSharedPreferences("chuongtay", MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean checkedboxid=checktay.isChecked();
        editor.putBoolean("checked", checkedboxid);
        if(!checkedboxid)
        {
            ln1hoi5s.setVisibility(View.GONE);
            lnbtn12hoi.setVisibility(View.GONE);
            lmo.setVisibility(View.GONE);
            bt.setVisibility(View.VISIBLE);
        }
        else
        {
            motchuongMO.setEnabled(false);
            haichuongMO.setEnabled(false);
            tatchuongMO.setEnabled(false);
            ln1hoi5s.setVisibility(View.VISIBLE);
            lnbtn12hoi.setVisibility(View.VISIBLE);
            lmo.setVisibility(View.VISIBLE);
            bt.setVisibility(View.GONE);
        }
        //chấp nhận lưu xuống file
        editor.apply();
    }
    /**
     * hàm đọc trạng thái đã lưu trước đó
     */
    public void loadchuongtay()
    {
        sharedPreferences = this.getSharedPreferences("chuongtay", MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean checkedboxid=sharedPreferences.getBoolean("checked", false);
        if(checkedboxid)
        {
            ln1hoi5s.setVisibility(View.VISIBLE);
            lnbtn12hoi.setVisibility(View.VISIBLE);
            lmo.setVisibility(View.VISIBLE);
            bt.setVisibility(View.GONE);
            motchuongMO.setEnabled(false);
            haichuongMO.setEnabled(false);
            tatchuongMO.setEnabled(false);
        }
        else
        {
            ln1hoi5s.setVisibility(View.GONE);
            lnbtn12hoi.setVisibility(View.GONE);
            lmo.setVisibility(View.GONE);
            bt.setVisibility(View.VISIBLE);
        }
        checktay.setChecked(checkedboxid);
    }

    void countdown(){
        new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mothoichuong.setValue(1);
            }

            @Override
            public void onFinish() {
                mothoichuong.setValue(0);
                Toast.makeText(MainActivity.this,"Reo 5 giây",Toast.LENGTH_LONG).show();
            }
        }.start();
    }
}

    ///them dong code nay
//    private void startTimer(){
//        mCountDownTimer= new CountDownTimer(mTimeLeftInMillis,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mTimeLeftInMillis= millisUntilFinished;
//                updateCountDownText();
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();
//        mTimerRunning=true;
//
//    }
//    private void updateCountDownText()
//    {
//        int minutes = (int) (mTimeLeftInMillis /1000)/60;
//        int seconds = (int) (mTimeLeftInMillis /1000)%60;
//        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
//        text_timer.setText(timeLeftFormatted);
//    }