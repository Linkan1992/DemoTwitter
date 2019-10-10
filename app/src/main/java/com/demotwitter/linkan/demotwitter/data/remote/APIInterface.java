package com.demotwitter.linkan.demotwitter.data.remote;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

  @GET(ApiEndPoints.GetUserDetail)
  Call<User> show(@Query("user_id") long userId);


  @GET(ApiEndPoints.GetTimelineFeed)
  Call<List<Tweet>> fetchTimelinePost(@Query("count") Integer count,
                                      @Query("since_id") Long sinceId,
                                      @Query("max_id") Long maxId,
                                      @Query("trim_user") Boolean trimUser,
                                      @Query("exclude_replies") Boolean excludeReplies,
                                      @Query("contributor_details") Boolean contributeDetails,
                                      @Query("include_entities") Boolean includeEntities);

}
