package com.example.Guess_Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity3 extends AppCompatActivity {


    EditText sayi ;
    TextView sonuc , liste , jokerhak3 , passgecme3 , denemehak3 , timer;
    Random rastgele = new Random();
    List<Integer> girilensayilar = new ArrayList<Integer>();
    Handler handler = new Handler(Looper.getMainLooper());
    int tutulandeger  ,girilendeger , hak , sayac , localpuan , tutulanpuan , tutulanjoker , jokerhak , gecmehak, puan;
    long sure ;
    String girilensayilartxt;
    MediaPlayer mp ;
    CountDownTimer Timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sayi = (EditText) findViewById(R.id.numara);
        jokerhak3 = (TextView) findViewById(R.id.jokerhak);
        denemehak3 = (TextView) findViewById(R.id.denemehak);
        passgecme3 = (TextView) findViewById(R.id.passgecme);
        sonuc = (TextView) findViewById(R.id.scour);
        liste = (TextView) findViewById(R.id.girilansayilar);
        timer = (TextView) findViewById(R.id.timer);
        tutulandeger = rastgele.nextInt(100) + 1;
        Intent intent = getIntent();
        tutulanpuan = intent.getIntExtra("puan", 0);
        tutulanjoker = intent.getIntExtra("jokerhak", 0);
        hak = 5;
        localpuan = 50 ;
        puan = tutulanpuan ;
        jokerhak = tutulanjoker + 1;
        sayac = 0 ;
        gecmehak = 3 ;
        yaz();
        timerstart();
    }

    public  void reset(View view){
        Timer.cancel();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.yenilme);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            };
        });

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void tahmin (View view){
        Timer.cancel();
        try {
            if (sure > 0) {
                if (hak <= 0) {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.yenilme);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        };
                    });

                    sayi.setText("");
                    sayi.setHint("Hakınız bitti :( ");
                    /*duration text */
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sayi.setHint("yenileme botuna tıklayınız ");
                        }
                    }, 3000);
                } else {
                    tur();
                    hak--;
                    yaz();

                }
            }
            else {
                sayi.setText("");
                sayi.setHint("sürenizi bitti :( ");
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity3.this, "Lütfen bir sayı giriniz.", Toast.LENGTH_LONG).show();
        }
    }

    public void tur(){
        girilendeger = Integer.parseInt(sayi.getText().toString());
        girilensayilar.add(girilendeger) ;
        for (int i = 0 ; i <= sayac ; i++){
            girilensayilartxt+= girilensayilar.get(i).toString() + ",";
            liste.setText(girilensayilar.toString());
        }
        sayac++ ;

        if (tutulandeger == girilendeger) {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.kazan);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });

            sayi.setText("");
            sayi.setHint("Tebrikler Bildiniz ");
            girilensayilar.clear();
            sayac = 0 ;
            liste.setText("");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sayi.setHint("1 - 100 arası sayı girin");
                }
            }, 3000);

            tutulandeger = rastgele.nextInt(100) + 1;
            Toast.makeText(MainActivity3.this, "Tekrar dene...", Toast.LENGTH_LONG).show();
            hak = 5 ;
            puan += localpuan;
            localpuan = 50 ;
            Timer.cancel();
        } else if (girilendeger < tutulandeger) {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.deneme);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });

            sayi.setText("");
            sayi.setHint("sayıyı büyütmelisin ");

        } else if (girilendeger > tutulandeger) {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.deneme);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });

            sayi.setText("");
            sayi.setHint("sayıyı kücültmelisin ");
        }
        timerstart();
        localpuan -= 10;
    }

    public void yenile (View view){
        Timer.cancel();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.yenilme);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            };
        });

        tutulandeger = rastgele.nextInt(100) + 1;
        puan = tutulanpuan;
        jokerhak = tutulanjoker;
        hak = 5;
        gecmehak = 3 ;
        localpuan = 50;
        girilensayilar.clear();
        sayac = 0 ;
        liste.setText("");
        localpuan = 50;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sayi.setHint("Baştan başlayın ... ");
            }
        }, 3000);
        timerstart();
        yaz();
    }

    public void joker(View view){
        Timer.cancel();
        if(jokerhak>0){
            jokerhak--;
            mp = MediaPlayer.create(getApplicationContext(), R.raw.kazan);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });

            tutulandeger = rastgele.nextInt(100) + 1;
            sayi.setText("");
            sayi.setHint("Joker hakı kullandınız.... ");
            puan += 50;
            localpuan = 50 ;
            hak = 5;
            girilensayilar.clear();
            sayac = 0 ;
            liste.setText("");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sayi.setHint("1 - 100 arası sayı girin");
                }
            }, 3000);
            timerstart();
            yaz();
        } else{
            mp = MediaPlayer.create(getApplicationContext(), R.raw.pass);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });

            sayi.setText("");
            sayi.setHint("Joker hakınız bitti. ");
        }
    }

    public void gecme(View view){
        Timer.cancel();
        if(gecmehak>0){
            gecmehak--;
            mp = MediaPlayer.create(getApplicationContext(), R.raw.pass);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });

            tutulandeger = rastgele.nextInt(100) + 1;
            sayi.setText("");
            sayi.setHint("Pass hakı kullandınız.... ");
            hak = 5 ;
            localpuan = 50 ;
            girilensayilar.clear();
            sayac = 0 ;
            liste.setText("");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sayi.setHint("1 - 100 arasi sayi girin");
                }
            }, 3000);
            yaz();
            timerstart();
        }else {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.pass);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });

            sayi.setText("");
            sayi.setHint("Pass hakınız bitti. ");
        }
    }

    public void yaz(){
        sonuc.setText(String.valueOf(puan));
        jokerhak3.setText("Hakınız : " + jokerhak);
        passgecme3.setText("Passınız : " + gecmehak);
        denemehak3.setText("deneme hakı : " + hak);
    }

    public  void timerstart(){

        Timer = new CountDownTimer(15000,1000) {

            @Override
            public void onTick(long l) {

                sure = l/1000 ;
                timer.setText("süreniz : " + sure );
            }
            // Geriye sayım işlemi bittiğinde yapılacak işlem.
            @Override
            public void onFinish() {
                mp = MediaPlayer.create(getApplicationContext(), R.raw.yenilme);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    };
                });

                Toast.makeText(MainActivity3.this, "süreniz Bitti!", Toast.LENGTH_SHORT).show();
                sayi.setText("");
                sayi.setHint("süreniz Bitti! ");
            }
        };
        Timer.start();
    }

}