package br.com.unit.tec.unitplus.entity;

/**
 * Created by jon_j on 02/11/2015.
 */
public class GridItem {

    public static final long ITEM_HORARIOS = 1;
    public static final long ITEM_NOTAS = 2;

    private long id;
    private int image;
    private String label;

    public GridItem(long id) {
        this.id = id;
    }

    public GridItem(long id, int image, String label) {
        this.id = id;
        this.image = image;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
