package com.duranio.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("bdSt41WQFZOxDbL9vPoIyaLKtFLu6TmgqDoouysC")
                // if defined
                .clientKey("rc8TprYbBpTmz7q6XlBHHIUMhWHhQ7KPwqsQXdH3")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
