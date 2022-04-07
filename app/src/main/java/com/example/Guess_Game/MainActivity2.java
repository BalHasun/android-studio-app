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

public class MainActivity2 extends AppCompatActivity {

    EditText sayi ;
    TextView sonuc , liste , jokerhak2 , passgecme2 , denemehak2 , timer2;
    Random rastgele = new Random();
    List<Integer> girilensayilar = new ArrayList<Integer>();
    Handler handler = new Handler(Looper.getMainLooper());
    int tutulandeger  ,girilendeger , hak , sayac , localpuan , tutulanpuan , tutulanjoker , jokerhak , gecme, puan;
    long sure ;
    String girilensayilartxt;
    MediaPlayer mp ;
    CountDownTimer Timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sayi = (EditText) findViewById(R.id.numara);
        jokerhak2 = (TextView) findViewById(R.id.jokerhak);
        denemehak2 = (TextView) findViewById(R.id.denemehak);
        passgecme2 = (TextView) findViewById(R.id.passgecme);
        sonuc = (TextView) findViewById(R.id.scour);
        liste = (TextView) findViewById(R.id.girilansayilar);
        timer2 = (TextView) findViewById(R.id.timer);
        tutulandeger = rastgele.nextInt(75) + 1;
        Intent intent = getIntent();
        tutulanpuan = intent.getIntExtra("puan", 0);
        tutulanjoker = intent.getIntExtra("jokerhak", 0);
        hak = 7;
        localpuan = 70 ;
        puan = tutulanpuan ;
        jokerhak = tutulanjoker + 1;
        sayac = 0 ;
        gecme = 3 ;
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
            Toast.makeText(MainActivity2.this, "Lütfen bir sayı giriniz.", Toast.LENGTH_LONG).show();
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
                    sayi.setHint("1 - 75 arası sayı girin");
                }
            }, 3000);

            tutulandeger = rastgele.nextInt(75) + 1;
            Toast.makeText(MainActivity2.this, "Tekrar dene...", Toast.LENGTH_LONG).show();
            hak = 7 ;
            puan += localpuan;
            localpuan = 70 ;
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

        tutulandeger = rastgele.nextInt(75) + 1;
        puan = tutulanpuan;
        jokerhak = tutulanjoker;
        hak = 7;
        gecme = 3 ;
        localpuan = 70;
        girilensayilar.clear();
        sayac = 0 ;
        liste.setText("");
        localpuan = 70;
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

            tutulandeger = rastgele.nextInt(75) + 1;
            sayi.setText("");
            sayi.setHint("Joker hakı kullandınız.... ");
            puan += 70;
            localpuan = 70;
            hak = 7;
            girilensayilar.clear();
            sayac = 0 ;
            liste.setText("");
            handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sayi.setHint("1 - 75 arası sayı girin");
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
        if(gecme>0){
            gecme--;
            mp = MediaPlayer.create(getApplicationContext(), R.raw.pass);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });

            tutulandeger = rastgele.nextInt(75) + 1;
            sayi.setText("");
            sayi.setHint("Pass hakı kullandınız.... ");
            hak = 7 ;
            localpuan = 70;
            girilensayilar.clear();
            sayac = 0 ;
            liste.setText("");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sayi.setHint("1 - 75 arasi sayi girin");
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
        jokerhak2.setText("Hakınız : " + jokerhak);
        passgecme2.setText("Passınız : " + gecme);
        denemehak2.setText("deneme hakı : " + hak);


    }

    public  void timerstart(){
        Timer = new CountDownTimer(15000,1000) {

            @Override
            public void onTick(long l) {
                sure = l/1000 ;
                timer2.setText("süreniz : " + sure );

                if (puan >= 1000) {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.move);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        };
                    });

                    Intent i = new Intent(MainActivity2.this, MainActivity3.class);
                    i.putExtra("puan", puan);
                    i.putExtra("jokerhak", jokerhak);
                    startActivity(i);
                    Timer.cancel();
                }
            }

            @Override
            public void onFinish() {
                mp = MediaPlayer.create(getApplicationContext(), R.raw.yenilme);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    };
                });

                Toast.makeText(MainActivity2.this, "süreniz Bitti!", Toast.LENGTH_SHORT).show();
                sayi.setText("");
                sayi.setHint("süreniz Bitti! ");
            }
        };
        Timer.start();
    }
}