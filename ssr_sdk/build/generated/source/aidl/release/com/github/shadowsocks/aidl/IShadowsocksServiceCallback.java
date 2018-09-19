/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\VPN\\ssr_sdk\\src\\main\\aidl\\com\\github\\shadowsocks\\aidl\\IShadowsocksServiceCallback.aidl
 */
package com.github.shadowsocks.aidl;
public interface IShadowsocksServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.github.shadowsocks.aidl.IShadowsocksServiceCallback
{
private static final java.lang.String DESCRIPTOR = "com.github.shadowsocks.aidl.IShadowsocksServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.github.shadowsocks.aidl.IShadowsocksServiceCallback interface,
 * generating a proxy if needed.
 */
public static com.github.shadowsocks.aidl.IShadowsocksServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.github.shadowsocks.aidl.IShadowsocksServiceCallback))) {
return ((com.github.shadowsocks.aidl.IShadowsocksServiceCallback)iin);
}
return new com.github.shadowsocks.aidl.IShadowsocksServiceCallback.Stub.Proxy(obj);
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
case TRANSACTION_stateChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
this.stateChanged(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_trafficUpdated:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
long _arg1;
_arg1 = data.readLong();
long _arg2;
_arg2 = data.readLong();
long _arg3;
_arg3 = data.readLong();
this.trafficUpdated(_arg0, _arg1, _arg2, _arg3);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.github.shadowsocks.aidl.IShadowsocksServiceCallback
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
@Override public void stateChanged(int state, java.lang.String profileName, java.lang.String msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(state);
_data.writeString(profileName);
_data.writeString(msg);
mRemote.transact(Stub.TRANSACTION_stateChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void trafficUpdated(long txRate, long rxRate, long txTotal, long rxTotal) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(txRate);
_data.writeLong(rxRate);
_data.writeLong(txTotal);
_data.writeLong(rxTotal);
mRemote.transact(Stub.TRANSACTION_trafficUpdated, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_stateChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_trafficUpdated = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void stateChanged(int state, java.lang.String profileName, java.lang.String msg) throws android.os.RemoteException;
public void trafficUpdated(long txRate, long rxRate, long txTotal, long rxTotal) throws android.os.RemoteException;
}
