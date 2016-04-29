/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\AndroidEclipseProject\\svn\\EsytoClient\\src\\com\\chinaums\\mpos\\service\\IUmsMposResultListener.aidl
 */
package com.chinaums.mpos.service;
public interface IUmsMposResultListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.chinaums.mpos.service.IUmsMposResultListener
{
private static final java.lang.String DESCRIPTOR = "com.chinaums.mpos.service.IUmsMposResultListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.chinaums.mpos.service.IUmsMposResultListener interface,
 * generating a proxy if needed.
 */
public static com.chinaums.mpos.service.IUmsMposResultListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.chinaums.mpos.service.IUmsMposResultListener))) {
return ((com.chinaums.mpos.service.IUmsMposResultListener)iin);
}
return new com.chinaums.mpos.service.IUmsMposResultListener.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_umsServiceResult:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.umsServiceResult(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.chinaums.mpos.service.IUmsMposResultListener
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void umsServiceResult(android.os.Bundle result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((result!=null)) {
_data.writeInt(1);
result.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_umsServiceResult, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_umsServiceResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void umsServiceResult(android.os.Bundle result) throws android.os.RemoteException;
}
