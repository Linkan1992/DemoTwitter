package com.demotwitter.linkan.demotwitter.utils;

public final class AppConstants {

    private AppConstants() {
        // This utility class is not publicly instantiable
    }

    public static final String DB_NAME = "demo_twitter.db";

    public static final String PREF_NAME = "demo_twitter_pref";

    public static final Integer FEED_COUNT_PER_PAGE = 20;

    public static final String DATE_TIME_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final String STATUS_CODE_SUCCESS = "success";

    public static final String CONSUMER_KEY = "UXW5oXdCE4QYqQF1hHVBUzBXd";

    public static final String CONSUMER_SECRET = "qtt3rDMO6rr6dV2sHOn0WkisezHp4kG9l4OzrAqRiFx2u3YSgf";

    public static class Extras {

        public static final String USERNAME = "userName";

        public static final String USERID = "userId";

        public static final String AVATAR_URL = "avatarUrl";

        public static final String OAUTH_TOKEN = "oauth_token";

        public static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";

        public static final String ISLOGGED_IN = "is_logged_in";

    }

}
