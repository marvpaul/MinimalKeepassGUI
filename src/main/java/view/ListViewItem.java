package view;

import javafx.scene.image.ImageView;

public class ListViewItem {
    final String text;
    final ImageView image;

    public ListViewItem(final String text, final String imageURL) {
        this.text = text;
        this.image = new ImageView(imageURL);
    }
}
