package com.mb.countrylist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*country listing model class*/
public class CountryListModel implements Parcelable {

    @Expose
    @SerializedName("PhoneCode")
    private String PhoneCode;
    @Expose
    @SerializedName("Code")
    private String Code;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("ID")
    private int ID;

    public CountryListModel() {
    }

    protected CountryListModel(Parcel in) {
        PhoneCode = in.readString();
        Code = in.readString();
        Name = in.readString();
        ID = in.readInt();
    }

    public static final Creator<CountryListModel> CREATOR = new Creator<CountryListModel>() {
        @Override
        public CountryListModel createFromParcel(Parcel in) {
            return new CountryListModel(in);
        }

        @Override
        public CountryListModel[] newArray(int size) {
            return new CountryListModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PhoneCode);
        dest.writeString(Code);
        dest.writeString(Name);
        dest.writeInt(ID);
    }

    public String getPhoneCode() {
        return PhoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        PhoneCode = phoneCode;
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

    public static Creator<CountryListModel> getCREATOR() {
        return CREATOR;
    }
}
