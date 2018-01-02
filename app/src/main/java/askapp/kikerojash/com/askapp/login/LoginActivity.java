package askapp.kikerojash.com.askapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

import askapp.kikerojash.com.askapp.MainActivity;
import askapp.kikerojash.com.askapp.R;
import askapp.kikerojash.com.askapp.login.pageadapter.PageAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kike on 30/12/2017.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)ViewPager viewPager;

    private int [] layouts = {R.layout.first_slide,R.layout.second_slide};
    private PageAdapter pageAdapter;

    private LoginButton loginButtonFacebook;
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
        initView();
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


}
