package mohalim.android.egybankstest.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MainMenuItem implements Parcelable {
    int imageIcon;
    String title, subTitle;

    public MainMenuItem(int imageIcon, String title, String subTitle) {
        this.imageIcon = imageIcon;
        this.title = title;
        this.subTitle = subTitle;
    }

    protected MainMenuItem(Parcel in) {
        imageIcon = in.readInt();
        title = in.readString();
        subTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageIcon);
        dest.writeString(title);
        dest.writeString(subTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainMenuItem> CREATOR = new Creator<MainMenuItem>() {
        @Override
        public MainMenuItem createFromParcel(Parcel in) {
            return new MainMenuItem(in);
        }

        @Override
        public MainMenuItem[] newArray(int size) {
            return new MainMenuItem[size];
        }
    };

    public int getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(int imageIcon) {
        this.imageIcon = imageIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
