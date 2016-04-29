/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\AndroidEclipseProject\\svn\\EsytoClient\\src\\com\\chinaums\\mpos\\service\\IUmsMposService.aidl
 */
package com.chinaums.mpos.service;
public interface IUmsMposService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.chinaums.mpos.service.IUmsMposService
{
private static final java.lang.String DESCRIPTOR = "com.chinaums.mpos.service.IUmsMposService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.chinaums.mpos.service.IUmsMposService interface,
 * generating a proxy if needed.
 */
public static com.chinaums.mpos.service.IUmsMposService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.chinaums.mpos.service.IUmsMposService))) {
return ((com.chinaums.mpos.service.IUmsMposService)iin);
}
return new com.chinaums.mpos.service.IUmsMposService.Stub.Proxy(obj);
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
case TRANSACTION_setDevice:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.setDevice(_arg0, _arg1);
return true;
}
case TRANSACTION_bookOrder:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.bookOrder(_arg0, _arg1);
return true;
}
case TRANSACTION_payOrder:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.payOrder(_arg0, _arg1);
return true;
}
case TRANSACTION_showTransactionInfoAndSign:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.showTransactionInfoAndSign(_arg0, _arg1);
return true;
}
case TRANSACTION_queryOrderInfo:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.queryOrderInfo(_arg0, _arg1);
return true;
}
case TRANSACTION_cancelTransaction:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.cancelTransaction(_arg0, _arg1);
return true;
}
case TRANSACTION_returnGoods:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.returnGoods(_arg0, _arg1);
return true;
}
case TRANSACTION_printBill:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.printBill(_arg0, _arg1);
return true;
}
case TRANSACTION_checkVersionUpdate:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.checkVersionUpdate(_arg0, _arg1);
return true;
}
case TRANSACTION_getDeviceId:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.getDeviceId(_arg0, _arg1);
return true;
}
case TRANSACTION_pay:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.pay(_arg0, _arg1);
return true;
}
case TRANSACTION_preAuth:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.preAuth(_arg0, _arg1);
return true;
}
case TRANSACTION_preAuthFin:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.preAuthFin(_arg0, _arg1);
return true;
}
case TRANSACTION_preAuthCancel:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.preAuthCancel(_arg0, _arg1);
return true;
}
case TRANSACTION_preAuthFinCancel:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.preAuthFinCancel(_arg0, _arg1);
return true;
}
case TRANSACTION_checkCurrentEnv:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.chinaums.mpos.service.IUmsMposResultListener _arg1;
_arg1 = com.chinaums.mpos.service.IUmsMposResultListener.Stub.asInterface(data.readStrongBinder());
this.checkCurrentEnv(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.chinaums.mpos.service.IUmsMposService
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
@Override public void setDevice(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setDevice, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void bookOrder(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_bookOrder, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void payOrder(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_payOrder, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void showTransactionInfoAndSign(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_showTransactionInfoAndSign, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void queryOrderInfo(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_queryOrderInfo, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void cancelTransaction(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_cancelTransaction, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void returnGoods(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_returnGoods, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void printBill(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_printBill, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void checkVersionUpdate(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_checkVersionUpdate, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void getDeviceId(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_getDeviceId, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void pay(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_pay, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void preAuth(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_preAuth, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void preAuthFin(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_preAuthFin, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void preAuthCancel(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_preAuthCancel, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void preAuthFinCancel(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_preAuthFinCancel, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void checkCurrentEnv(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((args!=null)) {
_data.writeInt(1);
args.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_checkCurrentEnv, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_setDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_bookOrder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_payOrder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_showTransactionInfoAndSign = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_queryOrderInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_cancelTransaction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_returnGoods = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_printBill = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_checkVersionUpdate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getDeviceId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_pay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_preAuth = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_preAuthFin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_preAuthCancel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_preAuthFinCancel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_checkCurrentEnv = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
}
public void setDevice(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void bookOrder(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void payOrder(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void showTransactionInfoAndSign(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void queryOrderInfo(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void cancelTransaction(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void returnGoods(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void printBill(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void checkVersionUpdate(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void getDeviceId(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void pay(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void preAuth(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void preAuthFin(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void preAuthCancel(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void preAuthFinCancel(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
public void checkCurrentEnv(android.os.Bundle args, com.chinaums.mpos.service.IUmsMposResultListener listener) throws android.os.RemoteException;
}
