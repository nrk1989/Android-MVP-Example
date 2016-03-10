/**
 * Device.java
 * <p/>
 * A model that represents the device properties.
 *
 * @package com.raj.task.data.model
 * @version 1.0
 * @author Rajkumar.N
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.raj.task.data.model;

/**
 * A model that represents the device properties.
 */
public class Device {
    private String model;

    private String deviceType;

    private String name;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Device [model = " + model + ", deviceType = " + deviceType + ", name = " + name + "]";
    }
}