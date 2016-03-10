/**
 * HomePresenter.java
 * <p/>
 * A presenter for home screen.
 *
 * @package com.raj.task.presenter
 * @version 1.0
 * @author Rajkumar.N
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.raj.task.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raj.task.R;
import com.raj.task.app.AppConstants;
import com.raj.task.contract.HomeContract;
import com.raj.task.data.model.Device;
import com.raj.task.helper.RequestHelper;
import com.raj.task.util.Logger;
import com.raj.task.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;


/**
 * A presenter for home screen.
 */
public class HomePresenter implements HomeContract.UserActionsListener {

    private Context mContext;

    private static final String TAG = HomePresenter.class.getSimpleName();

    @NonNull
    private final HomeContract.View mHomeView;

    @NonNull
    private final RequestHelper requestHelper;

    private final RequestHelper.RequestCompletedListener requestCompletedListener = new RequestHelper.RequestCompletedListener() {
        @Override
        public void onRequestCompleted(String tag, boolean status, String response) {
            mHomeView.setProgressIndicator(false);

            // Handles the error part
            if (!status) {
                mHomeView.showRetryToast(mContext.getString(R.string.toast_something_went_wrong));
                return;
            }

            // Handles the response of the getting devices list.
            if (tag.equals(AppConstants.REQUEST_GET_DEVICE)) {
                try {
                    final JSONObject responseJson = new JSONObject(response);
                    Type collectionType = new TypeToken<List<Device>>() {
                    }.getType();
                    final List<Device> devices = new Gson().fromJson(responseJson.getJSONArray(AppConstants.DEVICES).toString(), collectionType);
                    mHomeView.updateList(devices);
                } catch (JSONException e) {
                    mHomeView.showRetryToast(mContext.getString(R.string.toast_something_went_wrong));
                    Logger.e(TAG, e.getMessage(), e);
                }
            }
        }
    };

    /**
     * Instantiates a new home presenter.
     *
     * @param homeView the login view
     */
    public HomePresenter(@NonNull Context context, @NonNull HomeContract.View homeView) {
        this.mContext = context;
        mHomeView = Utils.checkNotNull(homeView);
        requestHelper = new RequestHelper(requestCompletedListener);
    }


    @Override
    public void loadDeviceData() {
        if (Utils.isInternetAvailable(mContext)) {
            mHomeView.setProgressIndicator(true);
            // Execute the webservice call
            requestHelper.requestString(AppConstants.REQUEST_GET_DEVICE, AppConstants.URL_GET_DEVICE);
        } else {
            mHomeView.showInfoToast(mContext.getString(R.string.toast_internet_connection));
        }
    }
}
