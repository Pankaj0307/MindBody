package com.mb.countrylist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*province listing model class*/
public class ProvinceListModel implements Parcelable {

    @Expose
    @SerializedName("CountryCode")
    private String CountryCode;
    @Expose
    @SerializedName("Code")
    private String Code;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("ID")
    private int ID;

    protected ProvinceListModel(Parcel in) {
        CountryCode = in.readString();
        Code = in.readString();
        Name = in.readString();
        ID = in.readInt();
    }

    public static final Creator<ProvinceListModel> CREATOR = new Creator<ProvinceListModel>() {
        @Override
        public ProvinceListModel createFromParcel(Parcel in) {
            return new ProvinceListModel(in);
        }

        @Override
        public ProvinceListModel[] newArray(int size) {
            return new ProvinceListModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CountryCode);
        dest.writeString(Code);
        dest.writeString(Name);
        dest.writeInt(ID);
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public static Creator<ProvinceListModel> getCREATOR() {
        return CREATOR;
    }
}
