/**
 * HomeContract.java
 * <p/>
 * A contract for the home screen.
 *
 * @package com.raj.task.contract
 * @version 1.0
 * @author Rajkumar.N
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.raj.task.contract;

import com.raj.task.data.model.Device;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter for the home screen.
 */
public interface HomeContract {

    interface View {
        /**
         * Sets progress indicator.
         *
         * @param indicator the indicator
         */
        void setProgressIndicator(boolean indicator);

        /**
         * Show the info toast message;
         *
         * @param message
         */
        void showInfoToast(String message);


        void showRetryToast(String message);

        /**
         * Updates the list with latest devices response.
         *
         * @param devices
         */
        void updateList(List<Device> devices);
    }

    /**
     * The interface User actions listener.
     */
    interface UserActionsListener {
        /**
         * Loads the device data from the server.
         */
        void loadDeviceData();
    }
}
