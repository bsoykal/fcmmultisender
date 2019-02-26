/*
 * Copyright (c) 2016 Inomera Research.
 */

package mobile.netmera.com.fcmmulti;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Emmar Kardeslik
 */
public final class PropertiesUtil {
  private PropertiesUtil() {
    //no instance
  }

  public static String netmeraApiKey = "";
  public static String gcmSenderId = "";
  public static String netmeraRestApiKey = "";
  public static String netmeraUrl = "";
  public static String transactionalPushRestUrl = "";

  public static void init(Context context, boolean isTest) {
    try {
      AssetManager assetManager = context.getAssets();
      InputStream inputStream = assetManager.open("property/nova.properties");
      Properties properties = new Properties();
      properties.load(inputStream);

      netmeraUrl = properties.getProperty("host");
      transactionalPushRestUrl = properties.getProperty("transactionalPushRestUrl");
      gcmSenderId = properties.getProperty(isTest ? "test_gcmSenderId" : "gcmSenderId");
      netmeraApiKey = properties.getProperty(isTest ? "test_netmeraApiKey" : "netmeraApiKey");
      netmeraRestApiKey = properties.getProperty("netmeraRestApiKey");
    } catch (Exception e) {
    }
  }

}
