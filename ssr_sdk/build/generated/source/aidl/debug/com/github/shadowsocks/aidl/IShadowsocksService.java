/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\VPN\\ssr_sdk\\src\\main\\aidl\\com\\github\\shadowsocks\\aidl\\IShadowsocksService.aidl
 */
package com.github.shadowsocks.aidl;
public interface IShadowsocksService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.github.shadowsocks.aidl.IShadowsocksService
{
private static final java.lang.String DESCRIPTOR = "com.github.shadowsocks.aidl.IShadowsocksService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.github.shadowsocks.aidl.IShadowsocksService interface,
 * generating a proxy if needed.
 */
public static com.github.shadowsocks.aidl.IShadowsocksService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.github.shadowsocks.aidl.IShadowsocksService))) {
return ((com.github.shadowsocks.aidl.IShadowsocksService)iin);
}
return new com.github.shadowsocks.aidl.IShadowsocksService.Stub.Proxy(obj);
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
case TRANSACTION_getState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getProfileName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getProfileName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.github.shadowsocks.aidl.IShadowsocksServiceCallback _arg0;
_arg0 = com.github.shadowsocks.aidl.IShadowsocksServiceCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.github.shadowsocks.aidl.IShadowsocksServiceCallback _arg0;
_arg0 = com.github.shadowsocks.aidl.IShadowsocksServiceCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
return true;
}
case TRANSACTION_use:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.use(_arg0);
return true;
}
case TRANSACTION_useSync:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.useSync(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.github.shadowsocks.aidl.IShadowsocksService
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
@Override public int getState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getProfileName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getProfileName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void registerCallback(com.github.shadowsocks.aidl.IShadowsocksServiceCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void unregisterCallback(com.github.shadowsocks.aidl.IShadowsocksServiceCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void use(int profileId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(profileId);
mRemote.transact(Stub.TRANSACTION_use, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void useSync(int profileId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(profileId);
mRemote.transact(Stub.TRANSACTION_useSync, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getProfileName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_use = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_useSync = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
public int getState() throws android.os.RemoteException;
public java.lang.String getProfileName() throws android.os.RemoteException;
public void registerCallback(com.github.shadowsocks.aidl.IShadowsocksServiceCallback cb) throws android.os.RemoteException;
public void unregisterCallback(com.github.shadowsocks.aidl.IShadowsocksServiceCallback cb) throws android.os.RemoteException;
public void use(int profileId) throws android.os.RemoteException;
public void useSync(int profileId) throws android.os.RemoteException;
}
