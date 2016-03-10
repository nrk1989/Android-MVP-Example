/**
 * DevicesListAdapter.java
 * <p/>
 * An adapter for the devices list.
 *
 * @package com.raj.task.ui.adapter
 * @version 1.0
 * @author Rajkumar.N
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.raj.task.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.raj.task.R;
import com.raj.task.data.model.Device;

import java.util.List;

/**
 * An adapter for the devices list.
 */
public class DevicesListAdapter extends RecyclerView.Adapter<DevicesListAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Device> mDevices;


    public DevicesListAdapter(Context context, List<Device> devices) {
        mContext = context;
        mDevices = devices;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(mContext);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.list_margin);
        layoutParams.leftMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.list_margin);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(Color.parseColor("#f2f2f2"));
        textView.setBackgroundColor(Color.parseColor("#10acf6"));
        int padding = mContext.getResources().getDimensionPixelOffset(R.dimen.list_padding);
        textView.setPadding(padding, padding, padding, padding);
        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Device device = mDevices.get(position);
        holder.txtDevice.setText(mContext.getString(R.string.name) + device.getName() + "\n" + mContext.getString(R.string.model) + device.getModel() + "\n" + mContext.getString(R.string.device_type) + device.getDeviceType());
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }


    /**
     * The UI ViewHolder for device list item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDevice;

        public ViewHolder(View convertView) {
            super(convertView);
            txtDevice = (TextView) convertView;
        }
    }
}
