package com.example.exam2oakesja;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {

	private String mName;
	private int mHour;
	private int mMinute;

	public Friend(String name, int hour, int minute) {
		mName = name;
		mHour = hour;
		mMinute = minute;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public int getHour() {
		return mHour;
	}

	public void setHour(int hour) {
		this.mHour = hour;
	}

	public int getMinute() {
		return mMinute;
	}

	public void setMinute(int minute) {
		this.mMinute = minute;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
		dest.writeInt(mHour);
		dest.writeInt(mMinute);
	}

	public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
		public Friend createFromParcel(Parcel in) {
			return new Friend(in);
		}

		public Friend[] newArray(int size) {
			return new Friend[size];
		}
	};
	
	public Friend(Parcel in){
		mName = in.readString();
		mHour = in.readInt();
		mMinute = in.readInt();
	}
	
	@Override
	public String toString() {
		return mName + " " + mHour + ":" + mMinute;
	}
}

