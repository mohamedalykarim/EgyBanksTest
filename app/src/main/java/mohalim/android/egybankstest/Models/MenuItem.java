package mohalim.android.egybankstest.Models;

public class MenuItem {
    int iconResource, title;

    public MenuItem(int iconResource, int title) {
        this.iconResource = iconResource;
        this.title = title;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}
