package askapp.kikerojash.com.askapp.base;

/**
 * Created by CCIE on 14/09/2017.
 */

public interface BasePresenter <T extends BaseView> {

    void attachView(T view);

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onBackPressed();
}
