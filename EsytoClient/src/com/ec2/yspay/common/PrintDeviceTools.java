/*
 * 类文件名:  PrintDeviceTools.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年10月21日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.common;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import bluetooth.sdk.BluetoothManager;

import com.ec2.yspay.DeviceConnectActivity;
import com.ec2.yspay.common.Bluetooth.ServerOrCilent;
import com.ec2.yspay.entity.PrintOne;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年10月21日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PrintDeviceTools
{
    private static final String TAG = "print";
    private BluetoothManager m_BluetoothManager=new BluetoothManager(); 
    private ServerThread startServerThread = null;
    private clientThread clientConnectThread = null;
    private static Context mContext;
    private static PrintDeviceTools deviceTools=null;
    private String printMsg;
    private PrintDeviceTools(){
    }
    public synchronized static PrintDeviceTools getInstance(Context context){ 
        mContext = context;
        if(deviceTools == null){  
            deviceTools = new PrintDeviceTools();  
        }  
        return deviceTools;
    }  
    public void start(){
        if(Bluetooth.serviceOrCilent==ServerOrCilent.CILENT)
        {
            String address = Bluetooth.BlueToothAddress;
            if(!address.equals("null"))
            {
                m_BluetoothManager.setServerAddress(address);   
                clientConnectThread = new clientThread();
                clientConnectThread.start();
                Bluetooth.isOpen = true;
            }
            else
            {
                ToastUtils.show(mContext, "address is null !");
            }
        }
        else if(Bluetooth.serviceOrCilent==ServerOrCilent.SERVICE)
        {                   
            startServerThread = new ServerThread();
            startServerThread.start();
            Bluetooth.isOpen = true;
        }
    }
  //开启客户端
    private class clientThread extends Thread {         
        public void run() {
            try {
                //连接
                Message msg2 = new Message();
                msg2.obj = "请稍候，正在连接服务器:"+Bluetooth.BlueToothAddress;
                msg2.what = 0;
                LinkDetectedHandler.sendMessage(msg2);
                
                //创建一个Socket连接：只需要服务器在注册时的UUID号
                m_BluetoothManager.ConnectServer();
                
                //socket = device.createRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
                //socket.connect();
                
                Message msg = new Message();
                msg.obj = "已经连接上服务端！可以发送信息。";
                msg.what = 0;
                LinkDetectedHandler.sendMessage(msg);
                
            } 
            catch (IOException e) 
            {
                Log.e("connect", "", e);
                Message msg = new Message();
                msg.obj = "连接服务端异常！断开连接重新试一试。";
                msg.what = 0;
                LinkDetectedHandler.sendMessage(msg);
            } 
        }
    };
  //开启服务器
    private class ServerThread extends Thread { 
        public void run() {
                    
            try {
                m_BluetoothManager.ListenAsServer(); 
                
                Log.d("server", "wait cilent connect...");
                
                Message msg = new Message();
                msg.obj = "请稍候，正在等待客户端的连接...";
                msg.what = 0;
                LinkDetectedHandler.sendMessage(msg);
                
                m_BluetoothManager.Accept();
                
                Log.d("server", "accept success !");
                
                Message msg2 = new Message();
                String info = "客户端已经连接上！可以发送信息。";
                msg2.obj = info;
                msg.what = 0;
                LinkDetectedHandler.sendMessage(msg2);
                //启动接受数据
                m_BluetoothManager.StartReceive();
                 
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };
    private Handler LinkDetectedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ToastUtils.show(mContext, (String)msg.obj);
            Log.i(TAG, (String)msg.obj);
//            if(msg.what==1)
//            {
//                list.add(new deviceListItem((String)msg.obj, true));
//            }
//            else
//            {
//                list.add(new deviceListItem((String)msg.obj, false));
//            }
//            mAdapter.notifyDataSetChanged();
//            mListView.setSelection(list.size() - 1);
        }
        
    }; 
    
    /* 停止服务器 */
    public void shutdownServer() {
        new Thread() {
            public void run() {
                if(startServerThread != null)
                {
                    startServerThread.interrupt();
                    startServerThread = null;
                }
                                
                try 
                {
                    m_BluetoothManager.shutdownServer(); 
                }
                catch (IOException e) 
                {
                    Log.e("server", "mserverSocket.close()", e);
                }
            };
        }.start();
    }
    /* 停止客户端连接 */
    private void shutdownClient() {
        new Thread() {
            public void run() {
                if(clientConnectThread!=null)
                {
                    clientConnectThread.interrupt();
                    clientConnectThread= null;
                }
                
                m_BluetoothManager.shutdownClient();
                
            };
        }.start();
    }
    public void chosePrintDevice(){
        if (Bluetooth.serviceOrCilent == ServerOrCilent.CILENT) 
        {
            shutdownClient();
        }
        else if (Bluetooth.serviceOrCilent == ServerOrCilent.SERVICE) 
        {
            shutdownServer();
        }
        Bluetooth.isOpen = false;
        Bluetooth.serviceOrCilent=ServerOrCilent.NONE;
        ToastUtils.show(mContext, "已断开连接！");
    }
    public void sendResetCommand(final int resetCommand)
    {
        //超时100ms后，发送还原指令
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() { 
                try {
                    switch(resetCommand)
                    {
                    case 0:
                        m_BluetoothManager.SetPrintModel(0,0);
                        m_BluetoothManager.SetPrintRotate(0);
                        m_BluetoothManager.SetWhiteModel(0);
                        break;
                    case 1:
                        m_BluetoothManager.SetPrintModel(0,0);
                        break;
                    case 2:
                        m_BluetoothManager.SetPrintRotate(0);
                        break;
                    case 3:
                        m_BluetoothManager.SetWhiteModel(0);
                        break;
                    
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            }
        }, 100);
    }
    private Handler sendHandler = new Handler(){

        /**
         * 重载方法
         * @param msg
         */
        @Override
        public void handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            printMsg = (String)msg.obj;
            sendCommand(msg.what);
        }
        
    };
    //发送命令
    public void sendCommand(int commandIndex) 
    {
        if (!m_BluetoothManager.IsConnected()) 
        {

            ToastUtils.show(mContext, "请先连接打印机！");
            return;
        }
        String msg="无效命令";
        try 
        {
            switch(commandIndex+1)
            {
                case 0://查询打印机状态
                    {
                        msg ="查询打印机状态";
                        m_BluetoothManager.CheckState();
                    
//                      if(m_BluetoothManager.IsPrinterReady())
//                      {
//                          msg ="查询打印机状态:准备就绪";
//                      }
//                      else
//                      {
//                          msg ="查询打印机状态:异常";
//                      }
                    }
                    break;
                case 1://发送字符串  
                    {
                        msg="发送字符串";
                        sendCommand(10); 
                        sendResetCommand(0); 
                    }
                    break;
                case 2://打印一维码
                    {
                        msg ="打印一维码";
                        String tempStr = "43 4F 44 45 31 32 38 CC F5 C2 EB 0A 1D 6B 49 0A 7B 42 4E 6F 2E 7B 43 0C 22 38 0A";
                        
                        m_BluetoothManager.PrintbarcodeA(tempStr);
                    }
                    break;
                case 3://打印二维码
                    {
                        msg ="打印二维码";
                        String tempStr = "13 51 00 18 15 02 FE A3 F8 82 3A 08 BA D2 E8 BA 02 E8 BA 1A E8 82 FA 08 FE AB F8 00 48 00 A3 21 28 5C 56 D8 0A DC D0 CD F5 60 B3 D9 38 00 E4 80 FE A6 A0 82 28 F0 BA 67 F0 BA 17 80 BA 9C F8 82 16 40 FE FF A8 00 0A";
                        m_BluetoothManager.PrintbarcodeB(printMsg);
                    }
                    break;
                case 4: //设置打印模式
                    {
                        msg ="倍高倍宽 ";
                        m_BluetoothManager.SetPrintModel(1, 1);
                        sendCommand(10); 
                        sendResetCommand(1); 
                    }
                    break;
                case 5://旋转90度
                    {
                        msg =" 旋转90度";
                        m_BluetoothManager.SetPrintRotate(1);
                        sendCommand(10); 
                        sendResetCommand(2); 
                    }
                    break;
                case 6://反白模式
                    {
                        msg ="反白模式";
                        m_BluetoothManager.SetWhiteModel(1);
                        sendCommand(10); 
                        sendResetCommand(3); 
                    }
                    break; 
                case 7: //断开连接
                    {
                        msg ="断开连接";
                        m_BluetoothManager.PortClose();
                    }
                    break;  
                case 10:
                case 11://发送字符串  
                    {
                        msg ="发送字符串";
                        //String tempStr ="C0 B6 D1 C0 3a 50 72 69 6e 74 ";
                        //     tempStr+="0A";
                        //m_BluetoothManager.SendData(tempStr);
                        m_BluetoothManager.PrintData(printMsg);
                    }
                    break;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            msg="发送命令异常"+e.getMessage();
            ToastUtils.show(mContext, msg);
        }
        
    }
    public boolean isConnected(){
        return m_BluetoothManager.IsConnected();
    }
    public void printNormal(String str){
//        printMsg = str;
        Message msg = new Message();
        msg.what = 0;
        msg.obj = str;
        sendHandler.sendMessageDelayed(msg, 200);
//        try
//        {
////            sendResetCommand(0); 
////            m_BluetoothManager.PrintData(str);
////            sendResetCommand(0); 
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            String msg="发送命令异常"+e.getMessage();
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        }
        
    }
    public void printBig(String str){
        try
        {
            printMsg = str;
            m_BluetoothManager.SetPrintModel(1, 1);
            m_BluetoothManager.PrintData(str);
            sendResetCommand(1); 
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            String msg="发送命令异常"+e.getMessage();
            ToastUtils.show(mContext, msg);
        }
    }
    public void printCode(String tempStr){
        Message msg = new Message();
        msg.what = 2;
        msg.obj = tempStr;
        sendHandler.sendMessageDelayed(msg, 200);
//        try
//        {
//            m_BluetoothManager.PrintbarcodeB(tempStr);
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            String msg="发送命令异常"+e.getMessage();
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        }
    }
    public static void printLast(PrintOne printMsg,Context mContext){
        PrintDeviceTools tools = PrintDeviceTools.getInstance(mContext);
        if (!tools.isConnected()) 
        {
            ToastUtils.show(mContext, "请先连接打印机！");
            Intent intent = new Intent(mContext, DeviceConnectActivity.class);
            mContext.startActivity(intent);
        }else{
            tools.printBig("   收银凭据");
            tools.printNormal("\n商户名："+printMsg.getCompanyName());
            tools.printNormal("商户号："+printMsg.getCompanyCode());
//            tools.printNormal("分  店：明发商业广场");
            tools.printNormal("操作员："+printMsg.getUserName());
            tools.printNormal("...............................");
            tools.printNormal("订单编号："+printMsg.getOrderId());
            tools.printNormal("付款类型："+printMsg.getChannelType());
            tools.printNormal("交易单号："+printMsg.getTransactionId());
            tools.printNormal("订单金额："+printMsg.getAmount()+"元");
//            tools.printNormal("实付金额：560元");
//            tools.printNormal("找零：");
            tools.printNormal("...............................");
            String tempStr = "13 51 00 18 15 02 FE A3 F8 82 3A 08 BA D2 E8 BA 02 E8 BA 1A E8 82 FA 08 FE AB F8 00 48 00 A3 21 28 5C 56 D8 0A DC D0 CD F5 60 B3 D9 38 00 E4 80 FE A6 A0 82 28 F0 BA 67 F0 BA 17 80 BA 9C F8 82 16 40 FE FF A8 00 0A";
            tools.printCode(tempStr);
            tools.printNormal("\n\n\n");
        }
    }
}
