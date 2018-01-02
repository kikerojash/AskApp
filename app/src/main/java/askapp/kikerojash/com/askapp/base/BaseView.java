package askapp.kikerojash.com.askapp.base;

/**
 * Created by CCIE on 14/09/2017.
 */

public interface BaseView <T extends BasePresenter>{
    void setPresenter(T presenter);
}
