package com.example.billy.random_product;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    TextView textView;
    int randomNum=0;

    String Name;
    String id;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        AddControl();
        LoginManager.getInstance().logOut();





    }

    private void AddControl() {
        loginButton=findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this,"Successful.", Toast.LENGTH_LONG).show();
                ResultLogin();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this,"Login attempt canceled.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(MainActivity.this,"Login attempt failed.", Toast.LENGTH_LONG).show();
            }
        });

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
    private void ResultLogin() {
        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("json",response.getJSONObject().toString());
                try {
                    Name=object.getString("name").toString();
                    id=  Profile.getCurrentProfile().getId();

                    //img= Profile.getCurrentProfile().getProfilePictureUri(100,100);
                    //txtNameFB.setText(txtNameFB.getText()+": cac "+object.getString("name").toString());
                    //new LoadImage().execute(img.toString());//InsertInfo(img);
                    Toast.makeText(MainActivity.this,"You had login with " + Name +" id:"+id,Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
