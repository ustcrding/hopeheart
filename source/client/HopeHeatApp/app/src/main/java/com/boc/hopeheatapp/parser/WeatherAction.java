package com.boc.hopeheatapp.parser;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherAction extends BaseAction {
    public  static final String SUB_KEY = "sub";

    public  static final String AIRDATA_KEY = "airData";
    public  static final String AIRQUALITY_KEY = "airQuality";
    public  static final String CITY_KEY = "city";
    public  static final String DATE_KEY = "date";
    public  static final String DATELONG_KEY = "dateLong";
    public  static final String HUMIDITY_KEY = "humidity";
    public  static final String LASTUPDATETIME_KEY = "lastUpdateTime";
    public  static final String PM25_KEY = "pm25";
    public  static final String TEMP_KEY = "temp";
    public  static final String TEMPRANGE_KEY = "temprange";
    public  static final String WEATHER_KEY = "weather";
    public  static final String WEATHERTYPE_KEY = "weatherType";
    public  static final String WIND_KEY = "wind";
    public  static final String WINDLEVEL_KEY = "windLevel";
    public List<Sub> sub;

    public WeatherAction() {

    }

    public WeatherAction(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject obj = new JSONObject(json);
                from(obj);

                if (obj.has(SUB_KEY)) {
                    JSONArray array = obj.getJSONArray(SUB_KEY);
                    if (array != null && array.length() > 0) {
                        sub = new ArrayList<Sub>();
                        for (int i = 0 ; i < array.length() ; ++i){
                            JSONObject cur = array.getJSONObject(i);
                            sub.add(new Sub(cur));
                        }
                    }
                }

            } catch (JSONException e) {
            }
        }
    }

    @Override
    protected int getType() {
        //return ACTION_WEATHER;
        return 0;
    }

    @Override
    protected JSONObject getDataJson() {
        try {
            JSONObject obj = new JSONObject();
            if (sub != null && sub.size() > 0) {
                JSONArray array = new JSONArray();
                for (int i = 0 ; i < sub.size(); ++i) {
                    array.put(sub.get(i).to());
                }
                obj.put(SUB_KEY, array);
            }
            return to(obj);
        } catch (JSONException e) {

        }
        return null;
    }


    public static class Sub {
        public  int airData;
        public  String airQuality;
        public  String city;
        public  String date;
        public  long dateLong;
        public  String humidity;
        public  String lastUpdateTime;
        public  String pm25;
        public  int temp;
        public  String temprange;
        public  String weather;
        public  int weatherType;
        public  String wind;
        public  int windLevel;

        public Sub() {

        }

        public Sub(JSONObject obj) {
            try {
                from(obj);
            } catch (JSONException e) {

            }
        }

        public void from(JSONObject obj) throws JSONException{
            if (obj != null) {
                if (obj.has(AIRDATA_KEY)) {
                    airData = obj.getInt(AIRDATA_KEY);
                }
                if (obj.has(AIRQUALITY_KEY)) {
                    airQuality = obj.getString(AIRQUALITY_KEY);
                }
                if (obj.has(CITY_KEY)) {
                    city = obj.getString(CITY_KEY);
                }
                if (obj.has(DATE_KEY)) {
                    date = obj.getString(DATE_KEY);
                }
                if (obj.has(DATELONG_KEY)) {
                    dateLong = obj.getLong(DATELONG_KEY);
                }
                if (obj.has(HUMIDITY_KEY)) {
                    humidity = obj.getString(HUMIDITY_KEY);
                }
                if (obj.has(LASTUPDATETIME_KEY)) {
                    lastUpdateTime = obj.getString(LASTUPDATETIME_KEY);
                }
                if (obj.has(PM25_KEY)) {
                    pm25 = obj.getString(PM25_KEY);
                }
                if (obj.has(TEMP_KEY)) {
                    temp = obj.getInt(TEMP_KEY);
                }
                if (obj.has(TEMPRANGE_KEY)) {
                    temprange = obj.getString(TEMPRANGE_KEY);
                }
                if (obj.has(WEATHER_KEY)) {
                    weather = obj.getString(WEATHER_KEY);
                }
                if (obj.has(WEATHERTYPE_KEY)) {
                    weatherType = obj.getInt(WEATHERTYPE_KEY);
                }
                if (obj.has(WIND_KEY)) {
                    wind = obj.getString(WIND_KEY);
                }
                if (obj.has(WINDLEVEL_KEY)) {
                    windLevel = obj.getInt(WINDLEVEL_KEY);
                }
            }
        }

        public JSONObject to(){
            JSONObject obj = new JSONObject();
            try {
                obj.put(AIRDATA_KEY, airData);
                obj.put(AIRQUALITY_KEY, TextUtils.isEmpty(airQuality) ? "" : airQuality);
                obj.put(CITY_KEY, TextUtils.isEmpty(city) ? "" : city);
                obj.put(DATE_KEY, TextUtils.isEmpty(date) ? "" : date);
                obj.put(DATELONG_KEY, dateLong);
                obj.put(HUMIDITY_KEY, TextUtils.isEmpty(humidity) ? "" : humidity);
                obj.put(LASTUPDATETIME_KEY, TextUtils.isEmpty(lastUpdateTime) ? "" : lastUpdateTime);
                obj.put(PM25_KEY, TextUtils.isEmpty(pm25) ? "" : pm25);
                obj.put(TEMP_KEY, temp);
                obj.put(TEMPRANGE_KEY, TextUtils.isEmpty(temprange) ? "" : temprange);
                obj.put(WEATHER_KEY, TextUtils.isEmpty(weather) ? "" : weather);
                obj.put(WEATHERTYPE_KEY, weatherType);
                obj.put(WIND_KEY, TextUtils.isEmpty(wind) ? "" : wind);
                obj.put(WINDLEVEL_KEY, windLevel);

                return obj;
            } catch (JSONException e) {
            }
            return null;
        }
    }
}
