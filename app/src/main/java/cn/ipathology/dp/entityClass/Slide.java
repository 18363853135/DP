package cn.ipathology.dp.entityClass;

/**
 * Created by wdb on 2016/12/27.
 */

public class Slide {


    /**
     * waxblock_memo : 腹水
     * slide_url : sp:e8f4f4f0baafafedeff4e9e3e6e5eeece1eeaef4e5f3f4aeeee7eff2ebaeb3b6e5aef8f9fabab8b0b0b0afe6e9ece5b2afdfb2b0b1b6b1b2b0b5b1b5b0b1b3b0afb1aeede4f3f8
     * case_id : 8437
     * slide_text : 直接涂片
     * pathology_no : C2016000002
     * slide_id : 9707
     * token : "c0cccc24dd23ded67404f5e511c342b0"
     * view :  "1" : ""
     * count (自己添加判断时候为选中状态) 1 ? 0
     *
     */




    private String waxblock_memo;
    private String slide_url;
    private int case_id;
    private String slide_text;
    private String pathology_no;
    private int slide_id;
    private String token;
    private String view;
    private int count = 0;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWaxblock_memo() {
        return waxblock_memo;
    }

    public void setWaxblock_memo(String waxblock_memo) {
        this.waxblock_memo = waxblock_memo;
    }

    public String getSlide_url() {
        return slide_url;
    }

    public void setSlide_url(String slide_url) {
        this.slide_url = slide_url;
    }

    public int getCase_id() {
        return case_id;
    }

    public void setCase_id(int case_id) {
        this.case_id = case_id;
    }

    public String getSlide_text() {
        return slide_text;
    }

    public void setSlide_text(String slide_text) {
        this.slide_text = slide_text;
    }

    public String getPathology_no() {
        return pathology_no;
    }

    public void setPathology_no(String pathology_no) {
        this.pathology_no = pathology_no;
    }

    public int getSlide_id() {
        return slide_id;
    }

    public void setSlide_id(int slide_id) {
        this.slide_id = slide_id;
    }
}
