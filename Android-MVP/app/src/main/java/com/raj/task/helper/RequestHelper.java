/**
 * RequestHelper.java
 * <p/>
 * An helper for request the string from the webservice.
 *
 * @package com.raj.task.helper
 * @version 1.0
 * @author Rajkumar.N
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.raj.task.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.raj.task.app.AppConstants;
import com.raj.task.util.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An helper for request the string from the webservice.
 */
public class RequestHelper {

    private static final String TAG = RequestHelper.class.getSimpleName();

    private static final int CONNECTION_TIMEOUT = 3000;


    private RequestCompletedListener requestCompletedListener;

    @NonNull
    private final ExecutorService executorService;

    /**
     * The request completed listener.
     */
    public interface RequestCompletedListener {
        /**
         * A callback triggers when the request has been completed.
         *
         * @param tag      the string refers the tag name for the request.
         * @param status   the boolean refers the status of the response.
         * @param response the string refers the response from the webservice.
         */
        void onRequestCompleted(String tag, boolean status, String response);
    }

    /**
     * Instantiates a new Request helper.
     *
     * @param requestCompletedListener the request completed listener
     */
    public RequestHelper(RequestCompletedListener requestCompletedListener) {
        this.requestCompletedListener = requestCompletedListener;
        executorService = Executors.newFixedThreadPool(5);
    }

    /**
     * Request/Get the string from the given url.
     *
     * @param tag the string refers the tag name of the request.
     * @param url the string refers the webservice url.
     */
    public void requestString(final String tag, final String url) {

        executorService.submit(new Thread() {
            @Override
            public void run() {
                try {
                    postMessage(tag, requestWebService(url));
                } catch (Throwable throwable) {
                    Logger.e(TAG, throwable.getMessage(), throwable);
                }

            }
        });
    }

    /**
     * Post the message to handler from the current running thread.
     *
     * @param tag     the string refers the tag name.
     * @param message the string refers the message to be post.
     */
    private void postMessage(String tag, String message) {
        Message msgObj = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.THREAD_TAG, tag);
        bundle.putString(AppConstants.THREAD_MESSAGE, message);
        msgObj.setData(bundle);
        handler.sendMessage(msgObj);
    }

    /**
     * Handler to handle message from the thread.
     */
    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            final String response = msg.getData().getString(AppConstants.THREAD_MESSAGE);
            requestCompletedListener.onRequestCompleted(msg.getData().getString(AppConstants.THREAD_TAG), TextUtils.isEmpty(response) ? false : true, response);
        }
    };


    /**
     * Request the webservice for the given url.
     *
     * @param serviceUrl the string refers the webservice url.
     * @return the string refers the response content.
     */
    private String requestWebService(String serviceUrl) {

        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
            }

            // create JSON object from content
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            return getResponseTexts(in);

        } catch (MalformedURLException e) {
            Logger.e(TAG, e.getMessage(), e);
        } catch (SocketTimeoutException e) {
            Logger.e(TAG, e.getMessage(), e);
        } catch (IOException e) {
            Logger.e(TAG, e.getMessage(), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    /**
     * Gets the response from the url.
     *
     * @param inputStream
     * @return the string refers the response from the url.
     */
    private static String getResponseText(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\A").next();
    }

    /**
     * Gets the response from the url.
     *
     * @param inputStream
     * @return the string refers the response from the url.
     */
    private static String getResponseTexts(InputStream inputStream) {
        StringBuffer response = new StringBuffer();
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferReader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            bufferReader.close();
        } catch (IOException e) {
            Logger.e(TAG, e.getMessage(), e);
        }
        return response.toString();
    }


}
