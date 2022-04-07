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

public class MainActivity extends AppCompatActivity {

    EditText sayi ;
    TextView sonuc , liste , joker , passgecme1, denemehak1 , timer;
    Random rastgele = new Random();
    Handler handler = new Handler(Looper.getMainLooper());
    List<Integer> girilensayilar = new ArrayList<Integer>();
    int tutulandeger  ,girilendeger ,sayac , hak  , localpuan , puan , jokerhak , gecmehak;
    long sure ;
    String girilensayilartxt;
    MediaPlayer mp ;
    CountDownTimer Timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sayi = (EditText) findViewById(R.id.numara);
        joker = (TextView) findViewById(R.id.jokerhak);
        denemehak1 = (TextView) findViewById(R.id.denemehak);
        passgecme1 = (TextView) findViewById(R.id.passgecme);
        sonuc = (TextView) findViewById(R.id.scour);
        liste = (TextView) findViewById(R.id.girilansayilar);
        timer = (TextView) findViewById(R.id.timer);
        tutulandeger = rastgele.nextInt(50) + 1;
        hak = 10;
        localpuan = 100 ;
        puan = 0 ;
        sayac = 0 ;
        jokerhak = 3;
        gecmehak = 3 ;
        yaz();
        timerstart();
    }

    public void tahmin (View view) {
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
                        }, 4000);
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
            Toast.makeText(MainActivity.this, "Lütfen bir sayı giriniz.", Toast.LENGTH_LONG).show();
        }
    }

    public void tur(){
        girilendeger = Integer.parseInt(sayi.getText().toString());
        girilensayilar.add(girilendeger) ;
        for (int i = 0 ; i <= sayac ; i++){
            girilensayilartxt += girilensayilar.get(i).toString() + ",";
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
                    sayi.setHint("1 - 50 arası sayı girin");
                }
            }, 3000);

            tutulandeger = rastgele.nextInt(50) + 1;
            Toast.makeText(MainActivity.this, "Tekrar dene", Toast.LENGTH_LONG).show();
            hak = 11 ;
            puan += localpuan;
            localpuan = 100 ;
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

        tutulandeger = rastgele.nextInt(50) + 1;
        puan = 0;
        jokerhak = 3 ;
        gecmehak = 3;
        hak = 10;
        girilensayilar.clear();
        sayac = 0 ;
        liste.setText("");
        localpuan = 100;
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

                tutulandeger = rastgele.nextInt(50) + 1;
                sayi.setText("");
                sayi.setHint("Joker hakı kullandınız.... ");
                puan += 100;
                hak = 10 ;
                localpuan = 100;
                girilensayilar.clear();
                sayac = 0 ;
                liste.setText("");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sayi.setHint("1 - 50 arası sayı girin");
                    }
                }, 3000);
            timerstart();
                yaz();
        }else {
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

                tutulandeger = rastgele.nextInt(50) + 1;
                sayi.setText("");
                sayi.setHint("Pass hakı kullandınız.... ");
                hak = 10 ;
                localpuan = 100;
                girilensayilar.clear();
                sayac = 0 ;
                liste.setText("");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sayi.setHint("1 - 50 arası sayı gırın");
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
        joker.setText("Hakınız : " + jokerhak);
        passgecme1.setText("Passınız : " + gecmehak);
        denemehak1.setText("deneme hakı : " + hak);
    }

    public  void timerstart(){

        Timer = new CountDownTimer(15000,1000) {

            @Override
            public void onTick(long l) {

                sure = l/1000 ;
                timer.setText("süreniz : " + sure );

                if (puan >= 500) {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.move);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        };
                    });

                    Intent i = new Intent(MainActivity.this, MainActivity2.class);
                    i.putExtra("puan", puan);
                    i.putExtra("jokerhak", jokerhak);
                    startActivity(i);
                    Timer.cancel();
                }
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

                Toast.makeText(MainActivity.this, "süreniz Bitti!", Toast.LENGTH_SHORT).show();
                sayi.setText("");
                sayi.setHint("süreniz Bitti! ");
            }
        };
        Timer.start();
    }
}