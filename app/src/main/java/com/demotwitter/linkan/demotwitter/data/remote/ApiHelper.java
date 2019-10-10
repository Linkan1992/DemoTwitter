package com.demotwitter.linkan.demotwitter.data.remote;


import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import io.reactivex.Observable;

public interface ApiHelper {


  Observable<User> fetchUserDetail(long userId);

  Observable<List<Tweet>> fetchTimelineFeed(Integer count, Long sinceId, Long maxId, Boolean trimUser,
                                            Boolean excludeReplies, Boolean contributeDetails, Boolean includeEntities);

}
