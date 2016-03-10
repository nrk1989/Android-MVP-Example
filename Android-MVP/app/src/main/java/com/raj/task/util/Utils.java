/**
 * Utils.java
 * <p/>
 * An utility class holds the some useful functions.
 *
 * @package com.raj.task.util
 * @version 1.0
 * @author Rajkumar.N
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.raj.task.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * An utility class holds the some useful functions.
 */
public class Utils {
    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param <T>       the type parameter
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Returns the Internet Connection Available Status
     *
     * @param context - Context environment passed by this parameter
     * @return true if the Internet Connection is Available and false otherwise
     */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
}
