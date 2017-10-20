package com.example.billy.random_product;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    TextView textView;
    int randomNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=1;i<=6;i++)
            arrayList.add(i+"");



        button=findViewById(R.id.button);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random=new Random();
                textView.setText("Đang quay...");
                AnimationSet animSet = new AnimationSet(true);
                animSet.setInterpolator(new DecelerateInterpolator());
                animSet.setFillAfter(true);
                animSet.setFillEnabled(true);



                float angle=1080;

                randomNum = random.nextInt(6)+1;

                angle+=randomNum*60-30;

                final RotateAnimation animRotate = new RotateAnimation(0.0f, angle,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                animRotate.setDuration(3000);
                animRotate.setFillAfter(true);
                animSet.addAnimation(animRotate);

                imageView.startAnimation(animSet);




                CountDownTimer Timer = new CountDownTimer(3000, 3000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        textView.setText(randomNum+"");
                    }
                }.start();
            }
        });




    }
}
