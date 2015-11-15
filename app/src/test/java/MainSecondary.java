import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.minimize.ahmedrizwan.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.Observables;

import org.junit.Before;
import org.junit.Test;

import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertTrue;

/**
 * Created by ahmedrizwan on 15/11/2015.
 */
public class MainSecondary {
    Observables mObservables;
    MyApi mMyApi;

    @Before
    public void setup() {
        mObservables = new Observables();
        final MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://localhost:8080/_ah/api/")
                .setGoogleClientRequestInitializer(abstractGoogleClientRequest ->
                        abstractGoogleClientRequest.setDisableGZipContent(true));
        mMyApi = builder.build();
    }

    @Test
    public void testJokeReturns() {
        String jokeString = "";
        try {
            jokeString = mObservables.getJokeObservable(mMyApi)
                    .subscribeOn(Schedulers.newThread())
                    .toBlocking()
                    .first();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Assert that a string is returned
        assertTrue(jokeString.length() > 0);
        System.out.println("Joke Returned : " + jokeString);
    }

}
