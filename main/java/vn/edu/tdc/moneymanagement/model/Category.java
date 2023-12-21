package vn.edu.tdc.moneymanagement.model;

public class Category {
    //Dinh nghia thuoc tinh cua bang
    public final static String TABLE_NAME = "categories";
    public final static String ID = "_id";

    public final static String ICON = "icon";
    public final static String CONTENT = "content";

    private int id;
    private long icon;
    private String content;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getIcon() {
        return icon;
    }

    public void setIcon(long icon) {
        this.icon = icon;
    }

    //Constructor
    public Category(long icon, String content) {
        this.icon = icon;
        this.content = content;
    }

    public Category(int id, long icon, String content) {
        this.id = id;
        this.icon = icon;
        this.content = content;
    }

    public Category() {
    }

    //Methods
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", icon=" + icon +
                ", content='" + content + '\'' +
                '}';
    }
}
