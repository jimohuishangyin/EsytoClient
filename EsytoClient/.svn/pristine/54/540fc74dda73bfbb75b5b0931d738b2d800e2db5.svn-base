package com.ec2.yspay.widget;


import java.util.Set;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ec2.yspay.R;
import com.ec2.yspay.common.MyApplication;

public class DeviceListDialog extends Dialog {

    public DeviceListDialog(Context context) {
        super(context);
    }

    public DeviceListDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    public static class Builder {
        private Context context;
        public static String EXTRA_DEVICE_ADDRESS = "device_address";
        // Member fields
        private BluetoothAdapter mBtAdapter;
        private ArrayAdapter<String> mPairedDevicesArrayAdapter;
        private ArrayAdapter<String> mNewDevicesArrayAdapter;

        private ListView lv_paired_devices;
        private ListView lv_new_devices;
        private Button btn_scan;
        private OndeviceSelectListener ondeviceSelectListener = null;
        private DeviceListDialog dialog;
        public Builder(Context context) {
            this.context = context;
        }
        public void setOndeviceSelectListener(OndeviceSelectListener ondeviceSelectListener){
            this.ondeviceSelectListener = ondeviceSelectListener;
        }
        public DeviceListDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialog = new DeviceListDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_bluetoothdevice, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            mPairedDevicesArrayAdapter = new ArrayAdapter<String>(context,
                    R.layout.listitem_bluetoothname);
            mNewDevicesArrayAdapter = new ArrayAdapter<String>(context,
                    R.layout.listitem_bluetoothname);

            initView(layout);

            // Register for broadcasts when a device is discovered
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            context.registerReceiver(mReceiver, filter);

            // Register for broadcasts when discovery has finished
            filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            context.registerReceiver(mReceiver, filter);

            // Get the local Bluetooth adapter
            mBtAdapter = BluetoothAdapter.getDefaultAdapter();

            // Get a set of currently paired devices
            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

            // If there are paired devices, add each one to the ArrayAdapter
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if(MyApplication.getBuleTooth(context)==null){
                        mPairedDevicesArrayAdapter.add(device.getName() + "\n"
                                + device.getAddress());
                    }else {
                        if ((MyApplication.getBuleTooth(context)).equals(device.getAddress())){
                            mPairedDevicesArrayAdapter.add(device.getName() + "      (蓝牙打印机)"+"\n"
                                    + device.getAddress());
                        }else {
                            mPairedDevicesArrayAdapter.add(device.getName() + "\n"
                                    + device.getAddress());
                        }
                    }


                }
            } else {
                String noDevices = context.getResources().getText(R.string.none_paired)
                        .toString();
                mPairedDevicesArrayAdapter.add(noDevices);
            }



            dialog.setContentView(layout);
            return dialog;
        }
        private void initView(View layout) {
            // TODO Auto-generated method stub
            btn_scan = (Button) layout.findViewById(R.id.btn_scan);
            btn_scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    doDiscovery();
                    v.setVisibility(View.GONE);
                }
            });
            lv_paired_devices = (ListView) layout.findViewById(R.id.lv_paired_devices);
            lv_paired_devices.setAdapter(mPairedDevicesArrayAdapter);
            lv_paired_devices.setOnItemClickListener(mDeviceClickListener);
            lv_new_devices = (ListView) layout.findViewById(R.id.lv_new_devices);
            lv_new_devices.setAdapter(mNewDevicesArrayAdapter);
            lv_new_devices.setOnItemClickListener(mDeviceClickListener);
        }
        /**
         * Start device discover with the BluetoothAdapter
         */
        private void doDiscovery() {

            // Indicate scanning in the title
//            setProgressBarIndeterminateVisibility(false);

            // If we're already discovering, stop it
            if (mBtAdapter.isDiscovering()) {
                mBtAdapter.cancelDiscovery();
            }

            // Request discover from BluetoothAdapter
            mBtAdapter.startDiscovery();
        }

        // The on-click listener for all devices in the ListViews
        private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
                // Cancel discovery because it's costly and we're about to connect
                mBtAdapter.cancelDiscovery();

                // Get the device MAC address, which is the last 17 chars in the
                // View
                String info = ((TextView) v).getText().toString();
                String address = info.substring(info.length() - 17);

                // Create the result Intent and include the MAC address
                if(ondeviceSelectListener!=null){
                    ondeviceSelectListener.onResult(address);
                }
                dialog.dismiss();
            }
        };

        // The BroadcastReceiver that listens for discovered devices and
        // changes the title when discovery is finished
        private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent
                            .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // If it's already paired, skip it, because it's been listed
                    // already
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        mNewDevicesArrayAdapter.add(device.getName() + "\n"
                                + device.getAddress());
                    }
                    // When discovery is finished, change the Activity title
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                        .equals(action)) {
//                    setProgressBarIndeterminateVisibility(false);
                    if (mNewDevicesArrayAdapter.getCount() == 0) {
                        String noDevices = context.getResources().getText(
                                R.string.none_found).toString();
                        mNewDevicesArrayAdapter.add(noDevices);
                    }
                }
            }
        };

        public interface OndeviceSelectListener{
            public void onResult(String address);
        }
    }
}
