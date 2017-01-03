package cn.ipathology.dp.entityClass;

/**
 * Created by wdb on 2016/5/6.
 */
public class ImageBrowser {
    private String[] urls;
    private String[] titles;
    private int index ;

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
