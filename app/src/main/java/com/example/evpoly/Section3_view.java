package com.example.evpoly;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Section3_view extends AppCompatActivity {
    private static final String TAG = "Mainactivity";

    DBHelper3 dbHelper;
    SQLiteDatabase database;
    private Car car;
    private SQLiteDatabase db;

    //UI버튼
    ImageView cameraBTN;
    ImageView resetBTN;
    ImageView Car_image;
    TextView carNUM;
    TextView carINTIME;
    TextView carOUTTIME;

    //카메라 권한 요청
    final static int TAKE_PICTURE = 1;
    final static int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    String Car_num; //차량번호
    String In_time; //입차시간
    String Out_time; //출차시간


    Uri photoUri;  //사진파일 주소

    //알림창
    private AlarmManager alarmManager;
    private GregorianCalendar mCalender;

    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.section3_view);

        car=new Car();
        dbHelper=new DBHelper3(this);
        db=dbHelper.getWritableDatabase();

        cameraBTN = findViewById(R.id.cameraBTN);
        resetBTN = findViewById(R.id.resetBTN);
        Car_image = findViewById(R.id.Car_image);
        carNUM = findViewById(R.id.carNUM);
        carINTIME = findViewById(R.id.carINTIME);
        carOUTTIME = findViewById(R.id.carOUTTIME);

        String sql="SELECT * FROM "+DBHelper3.USER_TABLE_NAME;
        Cursor cursor =db.rawQuery(sql,null);
        cursor.moveToLast();

        carNUM.setText(cursor.getString(1));
        carINTIME.setText(cursor.getString(2));
        carOUTTIME.setText(cursor.getString(3));
        if(cursor.getString(4)==null){
            Car_image.setImageResource(R.drawable.empty);
        }
        else{
            Uri myUri = Uri.parse(cursor.getString(4));
            Car_image.setImageURI(myUri);
        }



        //카메라 권한 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(Section3_view.this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        //알림창 설정
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mCalender = new GregorianCalendar();

        //onClickLister 설정
        cameraBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cameraBTN:
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                        }

                        if (photoFile != null) {
                            photoUri = FileProvider.getUriForFile(Section3_view.this, "com.example.EVpoly.fileprovider", photoFile);
                            i.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(i, 0);
                        }
                        break;

                }
            }
        });

        resetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                switch (v.getId()) {
                    case R.id.resetBTN:
                        carNUM.setText("");
                        carINTIME.setText("");
                        carOUTTIME.setText("");
                        Car_image.setImageResource(R.drawable.empty);

                        car.setCar_num("");
                        car.setIn_time("");
                        car.setLeft_time("");
                        car.setImage(null);
                        dbHelper.insertCar(car);

                        break;

                }
            }
        });
    }

    // 카메라 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }
    //카메라 촬영 저장, 이미지뷰에 보여줌
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 카메라 촬영을 하면 이미지뷰에 사진 삽입
        if (requestCode == 0 && resultCode == RESULT_OK) {
            File file = new File(mCurrentPhotoPath);
            Bitmap bitmap;
            if (Build.VERSION.SDK_INT >= 29) {
                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                try {
                    bitmap = ImageDecoder.decodeBitmap(source);
                    if (bitmap != null)
                        Car_image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 이미지뷰에 파일경로의 사진을 가져와 출력
            Car_image.setImageURI(photoUri);

            Date date = new Date();
            In_time = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, 1);
            Out_time = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(cal.getTime());
            Car_num = "52가 3108";
            carINTIME.setText(In_time);
            carOUTTIME.setText(Out_time);
            carNUM.setText(Car_num);

            setAlarm();
            car.setCar_num(Car_num);
            car.setIn_time(In_time);
            car.setLeft_time(Out_time);
            car.setImage(photoUri.toString());
            dbHelper.insertCar(car);

        }
    }

    // 사진 저장 , 저장되면서 시간 체크, 타이머 동작
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "path. " + mCurrentPhotoPath);

        return image;
    }

    //시간 되면 푸시 알림
    private void setAlarm() {
        //AlarmReceiver에 값 전달
        Intent receiverIntent = new Intent(Section3_view.this, AlarmRecevier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Section3_view.this, 0, receiverIntent, 0);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, 10);
        alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(),pendingIntent);
    }

}
