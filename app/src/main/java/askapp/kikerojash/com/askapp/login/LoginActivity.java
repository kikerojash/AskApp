package askapp.kikerojash.com.askapp.login;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

import askapp.kikerojash.com.askapp.main.MainActivity;
import askapp.kikerojash.com.askapp.R;
import askapp.kikerojash.com.askapp.login.pageadapter.PageAdapter;
import askapp.kikerojash.com.askapp.utils.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kike on 30/12/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private static String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.viewPager)ViewPager viewPager;
    @BindView(R.id.btnFaceb)LoginButton loginButtonFacebook;
    @BindView(R.id.dotsLayout)LinearLayout dotsLayout;
    //@BindView(R.id.Linearlayout)LinearLayout buttonSiguiente;
    private int [] layouts = {R.layout.first_slide,R.layout.second_slide};
    private PageAdapter pageAdapter;
    private ImageView imageViewDots[];

   // private LoginButton loginButtonFacebook;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        pageAdapter= new PageAdapter(layouts,this);
        viewPager.setAdapter(pageAdapter);
        callbackManager = CallbackManager.Factory.create();
        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        createDots(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position == layouts.length){
                    Log.d(TAG,"btnSTART");

                }else {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initView();

    }

    /*@OnClick(R.id.Linearlayout)
    public void onClickSiguiente(View view){
        int nextSlide = viewPager.getCurrentItem()+1;
        if(nextSlide<layouts.length){

            viewPager.setCurrentItem(nextSlide);
        }else {
            new PreferenceManager(this).writePreference();
        }
    }*/

    private void createDots(int currentItem){
        if (dotsLayout!=null)
            dotsLayout.removeAllViews();
        imageViewDots = new ImageView[layouts.length];
        for(int i = 0;i<layouts.length;i++){
            imageViewDots[i] = new ImageView(this);
            if (i == currentItem){
                Log.d(TAG,"IF");
                imageViewDots[i].setImageDrawable(getResources().getDrawable(R.drawable.active_dots));
            }else {
                Log.d(TAG,"ELSE");
                imageViewDots[i].setImageDrawable(getResources().getDrawable(R.drawable.default_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            dotsLayout.addView(imageViewDots[i],params);
        }
    }

    private void initView() {
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                gotoMainScreen();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Snackbar.make(viewPager,error.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void gotoMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadNextSlide(){
        int nextSlite= viewPager.getCurrentItem()+1;
        if(nextSlite<layouts.length){
            viewPager.setCurrentItem(nextSlite);
        }else{

        }
    }

}
