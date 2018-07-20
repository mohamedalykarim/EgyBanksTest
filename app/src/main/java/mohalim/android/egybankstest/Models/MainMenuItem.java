package mohalim.android.egybankstest.Models;

public class MainMenuItem {
    int imageIcon;
    String title, subTitle;

    public MainMenuItem(int imageIcon, String title, String subTitle) {
        this.imageIcon = imageIcon;
        this.title = title;
        this.subTitle = subTitle;
    }

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
