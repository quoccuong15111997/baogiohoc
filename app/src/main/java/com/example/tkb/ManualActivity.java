package com.example.tkb;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ManualActivity extends AppCompatActivity {

    public String chon;
    java.util.Calendar cal;
    Date datebatdau;

    SimpleDateFormat sdfGiay = new SimpleDateFormat("ss");
    SimpleDateFormat sdfPhut = new SimpleDateFormat("mm");
    SimpleDateFormat sdfGio = new SimpleDateFormat("HH");


    SimpleDateFormat sdfNgay1 = new SimpleDateFormat("dd");
    SimpleDateFormat sdfThang1 = new SimpleDateFormat("MM");
    SimpleDateFormat sdfNam1 = new SimpleDateFormat("yyyy");

    // dong nay themm
    DatabaseReference myData;
    FirebaseDatabase database;
    //private static final long START_TIME_IN_MILLIS = 60000;
    TextView text_timer;
    DatabaseReference lebatdau;
    DatabaseReference leketthuc;

    EditText chonngay1, chonngay2, chonngay3, chonngay4;
    EditText chongiongay1, chongiongay2;
    //ngày giờ bắt đầu, kết thúc lễ
    EditText edtNgayBatDauLe, edtGioBatDauLe;
    EditText edtNgayKetThucLe, edtGioKetThucLe;

    TextView giocai1, giocai2, giocai3, giocai4, giocai5, giocai6, giocai7, giocai8;
    TextView txtGioTiepTheo, txtNghiLe;

    Button macDinh, dieuChinh, chondate;
    ImageView iv_back;
    LinearLayout chinh1, chinh2, chinh3, chinh4, chinh5;
    LinearLayout md1, md2, md3, md4, md5, md6, md7, md8, md9;

    RadioButton radiobtnnghile;
    public static SharedPreferences sharedPreferences;

    private RadioButton radiobtnthi, radiobtnmacdinh, radiobtndieuchinh;
    private RadioGroup radioGroupcaidat, radioGroupthi;

    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radioGroupcaidat.clearCheck();
        radioGroupthi.clearCheck();
        chonngay1.setText("Chọn ngày");
        chongiongay1.setText("Chọn giờ");
        chonngay2.setText("Chọn ngày");
        chongiongay2.setText("Chọn giờ");
        chonngay3.setText("Chọn ngày");
        chonngay4.setText("Chọn ngày");
        giocai1.setText("Chọn giờ");
        giocai2.setText("Chọn giờ");
        giocai3.setText("Chọn giờ");
        giocai4.setText("Chọn giờ");
        giocai5.setText("Chọn giờ");
        giocai6.setText("Chọn giờ");
        giocai7.setText("Chọn giờ");
        giocai8.setText("Chọn giờ");
        MainActivity.txtGioTiepTheo.setVisibility(View.VISIBLE);
        MainActivity.txtNghiLe.setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        // dong nay them
        myData = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        lebatdau = database.getReference("nghilebatdau");
        leketthuc = database.getReference("nghileketthuc");

        txtGioTiepTheo = (TextView) findViewById(R.id.giotieptheo);
        txtNghiLe = (TextView) findViewById(R.id.txtNgayNghi);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radiobtnnghile = (RadioButton) this.findViewById(R.id.radionghile);
        this.radiobtnthi = (RadioButton) this.findViewById(R.id.radiothi);
        this.radioGroupcaidat = (RadioGroup) this.findViewById(R.id.radiogroupcaidat);


        radiobtnmacdinh = (RadioButton) this.findViewById(R.id.radiomacdinh);
        radiobtndieuchinh = (RadioButton) this.findViewById(R.id.radiodieuchinh);
        radioGroupthi = (RadioGroup) this.findViewById(R.id.radiogroupthi);


        radiobtnnghile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.txtGioTiepTheo.setVisibility(View.GONE);
                MainActivity.txtNghiLe.setVisibility(View.VISIBLE);
                MainActivity.txtNghiLe.setText("Đang trong thời gian nghỉ lễ");
            }
        });

        radiobtnthi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.txtGioTiepTheo.setVisibility(View.GONE);
                MainActivity.txtNghiLe.setVisibility(View.VISIBLE);
                MainActivity.txtNghiLe.setText("Đang trong thời gian thi");
            }
        });

        this.radioGroupthi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                xulyradio(group, checkedId);
            }

            private void xulyradio(RadioGroup group, int checkedId) {

                int checkedRadioId = group.getCheckedRadioButtonId();
                if (checkedRadioId == R.id.radiomacdinh) {
                    savethi();
                } else if (checkedRadioId == R.id.radiodieuchinh) {
                    savethi();
                }
            }
        });

        dieuChinh = findViewById(R.id.btndieuchinh);
        chinh1 = findViewById(R.id.dieuchinh1);
        chinh2 = findViewById(R.id.dieuchinh2);
        chinh3 = findViewById(R.id.dieuchinh3);
        chinh4 = findViewById(R.id.dieuchinh4);
        chinh5 = findViewById(R.id.dieuchinh5);

        macDinh = findViewById(R.id.btnmacdinh);
        md1 = findViewById(R.id.macdinh1);
        md2 = findViewById(R.id.macdinh2);
        md3 = findViewById(R.id.macdinh3);
        md4 = findViewById(R.id.macdinh4);
        md5 = findViewById(R.id.macdinh5);
        md6 = findViewById(R.id.macdinh6);
        md7 = findViewById(R.id.macdinh7);
        md8 = findViewById(R.id.macdinh8);
        md9 = findViewById(R.id.macdinh9);

        dieuChinh.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {
                visible = !visible;
                chinh1.setVisibility(visible ? View.VISIBLE : View.GONE);
                chinh2.setVisibility(visible ? View.VISIBLE : View.GONE);
                chinh3.setVisibility(visible ? View.VISIBLE : View.GONE);
                chinh4.setVisibility(visible ? View.VISIBLE : View.GONE);
                chinh5.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });

        macDinh.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {
                visible = !visible;
                md1.setVisibility(visible ? View.VISIBLE : View.GONE);
                md2.setVisibility(visible ? View.VISIBLE : View.GONE);
                md3.setVisibility(visible ? View.VISIBLE : View.GONE);
                md4.setVisibility(visible ? View.VISIBLE : View.GONE);
                md5.setVisibility(visible ? View.VISIBLE : View.GONE);
                md6.setVisibility(visible ? View.VISIBLE : View.GONE);
                md7.setVisibility(visible ? View.VISIBLE : View.GONE);
                md8.setVisibility(visible ? View.VISIBLE : View.GONE);
                md9.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });

        giocai1 = (TextView) findViewById(R.id.chongio1);
        giocai1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ChonGio1();
            }
        });

        giocai2 = (TextView) findViewById(R.id.chongio2);
        giocai2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ChonGio2();
            }
        });

        giocai3 = (TextView) findViewById(R.id.chongio3);
        giocai3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ChonGio3();
            }
        });

        giocai4 = (TextView) findViewById(R.id.chongio4);
        giocai4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ChonGio4();
            }
        });

        giocai5 = (TextView) findViewById(R.id.chongio5);
        giocai5.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ChonGio5();
            }
        });

        giocai6 = (TextView) findViewById(R.id.chongio6);
        giocai6.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ChonGio6();
            }
        });

        giocai7 = (TextView) findViewById(R.id.chongio7);
        giocai7.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ChonGio7();
            }
        });

        giocai8 = (TextView) findViewById(R.id.chongio8);
        giocai8.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ChonGio8();
            }
        });

        chonngay3 = (EditText) findViewById(R.id.batdauthi);
        chonngay3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                chonNgay3();
            }
        });

        chonngay4 = (EditText) findViewById(R.id.ketthucthi);
        chonngay4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                chonNgay4();
            }
        });

        TextClock textClock = findViewById(R.id.textclock);
        String formatdate = "E, d-M-yyyy, k:m:sa";
        textClock.setFormat12Hour(formatdate);
        textClock.setFormat24Hour(formatdate);

        loadData();
        loadthi();
        loaddc();
        //chọn ngày giờ lễ
        addControls();
        addEvents();
    }

    private void addEvents() {
        edtNgayBatDauLe.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                chonNgay(edtNgayBatDauLe);
            }
        });
        edtGioBatDauLe.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                chonGio(edtGioBatDauLe);
            }
        });
        edtNgayKetThucLe.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                chonNgay(edtNgayKetThucLe);
            }
        });
        edtGioKetThucLe.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                chonGio(edtGioKetThucLe);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void chonNgay(final EditText edt) {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {

                calendar.set(year1, month1, dayOfMonth1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void chonGio(final EditText edt) {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    private void addControls() {
        edtNgayBatDauLe=findViewById(R.id.edtNgayBatDauLe);
        edtGioBatDauLe=findViewById(R.id.edtGioBatDauLe);
        edtNgayKetThucLe=findViewById(R.id.edtNgayKetThucLe);
        edtGioKetThucLe=findViewById(R.id.edtGioKetThucLe);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void chonNgay3() {
        final Calendar calendar = Calendar.getInstance();
        int ngay3 = calendar.get(Calendar.DATE);
        int thang3 = calendar.get(Calendar.MONTH);
        int nam3 = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {

                calendar.set(year1, month1, dayOfMonth1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                chonngay3.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam3, thang3, ngay3);
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void chonNgay4() {
        final Calendar calendar = Calendar.getInstance();
        int ngay4 = calendar.get(Calendar.DATE);
        int thang4 = calendar.get(Calendar.MONTH);
        int nam4 = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth2) {

                calendar.set(year2, month2, dayOfMonth2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                chonngay4.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam4, thang4, ngay4);
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChonGio1() {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                giocai1.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChonGio2() {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                giocai2.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChonGio3() {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                giocai3.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChonGio4() {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                giocai4.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChonGio5() {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                giocai5.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChonGio6() {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                giocai6.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChonGio7() {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                giocai7.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChonGio8() {
        final Calendar calendar = Calendar.getInstance();
        final int gio1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int phut1 = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, (minute));
                giocai8.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, gio1, phut1, true);
        timePickerDialog.show();
    }

    public void loadData() {
        sharedPreferences = this.getSharedPreferences("data", MODE_PRIVATE);
        if (sharedPreferences != null) {
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.radioauto);
            this.radioGroupcaidat.check(checkedRadioButtonId);
            if (checkedRadioButtonId == R.id.radionghile) {
                MainActivity.txtGioTiepTheo.setVisibility(View.GONE);
                MainActivity.txtNghiLe.setVisibility(View.VISIBLE);
                MainActivity.txtNghiLe.setText("Đang trong thời gian nghỉ lễ");
                chonngay1.setText(sharedPreferences.getString("chonngay1", ""));
                chongiongay1.setText(sharedPreferences.getString("chongiongay1", ""));
                chonngay2.setText(sharedPreferences.getString("chonngay2", ""));
                chongiongay2.setText(sharedPreferences.getString("chongiongay2", ""));
            } else if (checkedRadioButtonId == R.id.radiothi) {
                MainActivity.txtGioTiepTheo.setVisibility(View.GONE);
                MainActivity.txtNghiLe.setVisibility(View.VISIBLE);
                MainActivity.txtNghiLe.setText("Đang trong thời gian thi");
                chonngay3.setText(sharedPreferences.getString("chonngay3", ""));
                chonngay4.setText(sharedPreferences.getString("chonngay4", ""));
            } else {
                MainActivity.txtGioTiepTheo.setVisibility(View.VISIBLE);
                MainActivity.txtNghiLe.setVisibility(View.GONE);
            }
            MainActivity.txtNghiLe.setText(sharedPreferences.getString("text", ""));
        } else {
            this.radioGroupcaidat.check(R.id.radioauto);
            Toast.makeText(this, " ", Toast.LENGTH_LONG).show();
        }
    }

    // Được gọi khi người dùng nhấn vào nút Save.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void doSave(View view) {
        savedc();
        // File chia sẻ sử dụng trong nội bộ ứng dụng, hoặc các ứng dụng được chia sẻ cùng User.
        SharedPreferences sharedPreferences = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // ID của RadioButton đang được chọn.
        int checkedRadioButtonId = radioGroupcaidat.getCheckedRadioButtonId();
        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);
        editor.putString("text", MainActivity.txtNghiLe.getText().toString());
        editor.putString("chonngay1", chonngay1.getText().toString());
        editor.putString("chongiongay1", chongiongay1.getText().toString());
        editor.putString("chonngay2", chonngay2.getText().toString());
        editor.putString("chongiongay2", chongiongay2.getText().toString());
        editor.putString("chonngay3", chonngay3.getText().toString());
        editor.putString("chonngay4", chonngay4.getText().toString());

/*        Intent intent = new Intent(ManualActivity.this, MainActivity.class);
        startActivity(intent);*/

        // Save.
        editor.apply();

        Toast.makeText(this, "Đã lưu", Toast.LENGTH_LONG).show();
    }

    public void loadthi() {
        sharedPreferences = this.getSharedPreferences("thihocki", MODE_PRIVATE);
        if (sharedPreferences != null) {
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.radiomacdinh);
            this.radioGroupthi.check(checkedRadioButtonId);
        } else {
            this.radioGroupthi.check(R.id.radiomacdinh);
            Toast.makeText(this, " ", Toast.LENGTH_LONG).show();
        }
    }

    public void savethi() {
        // File chia sẻ sử dụng trong nội bộ ứng dụng, hoặc các ứng dụng được chia sẻ cùng User.
        SharedPreferences sharedPreferences = this.getSharedPreferences("thihocki", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // ID của RadioButton đang được chọn.
        int checkedRadioButtonId = radioGroupthi.getCheckedRadioButtonId();
        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);
        // Save.
        editor.apply();
    }

    public void loaddc() {
        sharedPreferences = this.getSharedPreferences("loaddc", MODE_PRIVATE);
        if (sharedPreferences != null) {
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.radiodieuchinh);
            this.radioGroupthi.check(checkedRadioButtonId);
            if (checkedRadioButtonId == R.id.radiodieuchinh) {
                giocai1.setText(sharedPreferences.getString("giocai1", ""));
                giocai2.setText(sharedPreferences.getString("giocai2", ""));
                giocai3.setText(sharedPreferences.getString("giocai3", ""));
                giocai4.setText(sharedPreferences.getString("giocai4", ""));
                giocai5.setText(sharedPreferences.getString("giocai5", ""));
                giocai6.setText(sharedPreferences.getString("giocai6", ""));
                giocai7.setText(sharedPreferences.getString("giocai7", ""));
                giocai8.setText(sharedPreferences.getString("giocai8", ""));
            }
        } else {
            this.radioGroupthi.check(R.id.radiodieuchinh);
            Toast.makeText(this, " ", Toast.LENGTH_LONG).show();
        }
    }

    // Được gọi khi người dùng nhấn vào nút Save.
    public void savedc() {
        // File chia sẻ sử dụng trong nội bộ ứng dụng, hoặc các ứng dụng được chia sẻ cùng User.
        SharedPreferences sharedPreferences = this.getSharedPreferences("loaddc", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // ID của RadioButton đang được chọn.
        int checkedRadioButtonId = radioGroupthi.getCheckedRadioButtonId();
        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);
        editor.putString("giocai1", giocai1.getText().toString());
        editor.putString("giocai2", giocai2.getText().toString());
        editor.putString("giocai3", giocai3.getText().toString());
        editor.putString("giocai4", giocai4.getText().toString());
        editor.putString("giocai5", giocai5.getText().toString());
        editor.putString("giocai6", giocai6.getText().toString());
        editor.putString("giocai7", giocai7.getText().toString());
        editor.putString("giocai8", giocai8.getText().toString());

        editor.apply();
    }
}