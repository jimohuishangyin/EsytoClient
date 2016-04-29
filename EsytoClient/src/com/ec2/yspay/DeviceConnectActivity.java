package com.ec2.yspay;

import java.util.ArrayList;
import java.util.Set;

import com.ec2.yspay.adapter.DeviceListAdapter;
import com.ec2.yspay.common.Bluetooth;
import com.ec2.yspay.common.Bluetooth.ServerOrCilent;
import com.ec2.yspay.common.PrintDeviceTools;

import bluetooth.sdk.BluetoothManager;
import bluetooth.sdk.ClassUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DeviceConnectActivity extends Activity
{
    private ListView mListView;
    private ArrayList<SiriListItem> list;
    private Button seachButton, closeButton,startAutoConnectButton;
    private DeviceListAdapter mAdapter;
    private Context mContext;
    private PrintDeviceTools printDeviceTools;
    
    /* 取得默认的蓝牙适配器 */
    private BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    
    
    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        if (!mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 3);
        }
    }   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_connect);
        mContext = this;
        printDeviceTools = PrintDeviceTools.getInstance(mContext);
        init();
    }    
    
    

    private void init() {          
        list = new ArrayList<SiriListItem>();

        mAdapter = new DeviceListAdapter(mContext, list);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setFastScrollEnabled(true);
        mListView.setOnItemClickListener(mDeviceClickListener);     

        // 注册Receiver来获取蓝牙设备相关的结果   
         
        IntentFilter intent = new IntentFilter();    
        intent.addAction(BluetoothManager.ACTION_PAIRING_REQUEST);  
        registerReceiver(mReceiver, intent);  
        
         // Register for broadcasts when a device is discovered
        IntentFilter discoveryFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, discoveryFilter);

        // Register for broadcasts when discovery has finished
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, foundFilter);
        
        // Get a set of currently paired devices

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                list.add(new SiriListItem(device.getName(), device.getAddress(), true));
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(list.size() - 1);
            }
        } else {
//            list.add(new SiriListItem("没有设备已经配对", true));
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(list.size() - 1);
        }
        
        seachButton = (Button)findViewById(R.id.start_seach);
        seachButton.setOnClickListener(seachButtonClickListener);
        
        
        startAutoConnectButton = (Button)findViewById(R.id.start_auto_connect);
        startAutoConnectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                printDeviceTools.chosePrintDevice();
                 // Get a set of currently paired devices
//                Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
//
//                // If there are paired devices, add each one to the ArrayAdapter
//                if (pairedDevices.size() > 0) 
//                {
//                    for (BluetoothDevice device : pairedDevices) 
//                    {
//                        if(device.getName()== BluetoothManager.GetPrinterName())
//                        {
//                            Bluetooth.BlueToothAddress = device.getAddress();
//
//                            
//                            Bluetooth.serviceOrCilent=ServerOrCilent.CILENT;
//                            Bluetooth.mTabHost.setCurrentTab(1);  
//                            bFound =true;
//                            break;
//                        } 
//                    }
//                } 
//                
//                if(!bFound || (Bluetooth.BlueToothAddress==null || Bluetooth.BlueToothAddress== "null"))
//                {
//                    Toast.makeText(mContext, "自动连接失败，请确认打印机已成功匹配过！", Toast.LENGTH_SHORT).show();
//                } 
            }
        });
        
        closeButton = (Button)findViewById(R.id.close_bluetooth);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) { 
                mBtAdapter.disable();
            }
        });
        
    }
    /**
     * 重载方法
     */
    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        this.unregisterReceiver(mReceiver);
    }
     private OnClickListener seachButtonClickListener = new OnClickListener() {
         @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(mBtAdapter.isDiscovering()) 
                {
                    mBtAdapter.cancelDiscovery();
                    seachButton.setText("重新搜索");
                }
                else
                {
                    list.clear();
                    mAdapter.notifyDataSetChanged();
                    
                    Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
                    if (pairedDevices.size() > 0) {
                            for (BluetoothDevice device : pairedDevices) {
                                list.add(new SiriListItem(device.getName(),device.getAddress(), true));
                                mAdapter.notifyDataSetChanged();
                                mListView.setSelection(list.size() - 1);
                            }
                    } else {
                            list.add(new SiriListItem("No devices have been paired", true));
                            mAdapter.notifyDataSetChanged();
                            mListView.setSelection(list.size() - 1);
                     }                  
                    /* 开始搜索 */
                    mBtAdapter.startDiscovery();
                    seachButton.setText("停止搜索");
                }                
            }
        };
        
    // The on-click listener for all devices in the ListViews
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect     
            
            SiriListItem item = list.get(arg2);                
            Bluetooth.BlueToothAddress = item.address;
            
             AlertDialog.Builder StopDialog =new AlertDialog.Builder(mContext);//定义一个弹出框对象
             StopDialog.setTitle("连接");//标题          
             StopDialog.setMessage(item.message);
             StopDialog.setPositiveButton("连接", new DialogInterface.OnClickListener() {  
             public void onClick(DialogInterface dialog, int which) {  
                 // TODO Auto-generated method stub   
                 mBtAdapter.cancelDiscovery();
                 seachButton.setText("重新搜索"); 
                 Bluetooth.serviceOrCilent=ServerOrCilent.CILENT;
                 printDeviceTools.start();
                 
//                 Bluetooth.mTabHost.setCurrentTab(1);   
             }  
             });
             StopDialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {                       
                 public void onClick(DialogInterface dialog, int which) {  
                     Bluetooth.BlueToothAddress = null;
                 }
             });
             StopDialog.show();                            
        }
    };  
    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) 
            {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) 
                {
                    list.add(new SiriListItem(device.getName(),device.getAddress(), false));
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(list.size() - 1);
                }
            // When discovery is finished, change the Activity title
            } 
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) 
            {
                setProgressBarIndeterminateVisibility(false);
                if (mListView.getCount() == 0) 
                {
                    list.add(new SiriListItem("没有发现蓝牙设备", false));
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(list.size() - 1);
                }
                seachButton.setText("重新搜索");
            }
            else if (action.equals(BluetoothManager.ACTION_PAIRING_REQUEST))  
            {   
                BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);  
       
                // byte[] pinBytes = BluetoothDevice.convertPinToBytes("1234");  
                // device.setPin(pinBytes);  
              
                try  
                {  
                    ClassUtils.setPin(btDevice.getClass(), btDevice, BluetoothManager.GetPrinterPassword()); // 手机和蓝牙采集器配对  
                    ClassUtils.createBond(btDevice.getClass(), btDevice);  
                    ClassUtils.cancelPairingUserInput(btDevice.getClass(), btDevice);  
                }  
                catch (Exception e)  
                {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
        }
    };  
    
    public class SiriListItem {
        public String name;
        public String address;
        public String message;
        public boolean isSiri;
        public SiriListItem(String name,String address, boolean siri) {
            
            this.name = name;
            this.address = address;
            this.message = name  + "\n" + address;
            isSiri = siri;
        }
        
        public SiriListItem(String message, boolean siri) {
            this.name = "";
            this.address = "";
            this.message = message;
            isSiri = siri;
        }
    }
}
