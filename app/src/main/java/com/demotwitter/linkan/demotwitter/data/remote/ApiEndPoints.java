package com.demotwitter.linkan.demotwitter.data.remote;

public final class ApiEndPoints {

    private ApiEndPoints() {
        // This class is not publicly instantiable
    }

    public static final String GetUserDetail = "/1.1/users/show.json";

    public static final String GetTimelineFeed = "/1.1/statuses/home_timeline.json";

}
