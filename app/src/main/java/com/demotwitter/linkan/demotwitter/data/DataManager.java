package com.demotwitter.linkan.demotwitter.data;


import com.demotwitter.linkan.demotwitter.data.local.db.DbHelper;
import com.demotwitter.linkan.demotwitter.data.local.pref.PrefHelper;
import com.demotwitter.linkan.demotwitter.data.remote.ApiHelper;

public interface DataManager extends DbHelper, ApiHelper, PrefHelper {


}
