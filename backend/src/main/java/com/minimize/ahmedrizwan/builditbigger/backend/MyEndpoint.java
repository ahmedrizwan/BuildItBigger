/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.minimize.ahmedrizwan.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.minimize.Jokes;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.builditbigger.ahmedrizwan.minimize.com",
    ownerName = "backend.builditbigger.ahmedrizwan.minimize.com",
    packagePath=""
  )
)
public class MyEndpoint {
    Jokes mJokes = new Jokes();

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public JokeBean sayHi(@Named("name") String name) {
        JokeBean response = new JokeBean();
        response.setJoke("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "getJoke")
    public JokeBean getJoke(){
        JokeBean response = new JokeBean();
        response.setJoke(mJokes.getNextJoke());
        return response;
    }

}
