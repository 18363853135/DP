package cn.ipathology.dp.entityClass;

/**
 * Created by wdb on 2016/12/28.
 */

public class Share {


    /**
     * ShareTitle : 诊断报告
     * ShareDesc :
     * ShareLink : http://img.med100.cn/v2/1482736609.35_378.pdf
     * ShareImgUrl : http://img.med100.cn/null
     */

    private String ShareTitle;
    private String ShareDesc;
    private String ShareLink;
    private String ShareImgUrl;

    public String getShareTitle() {
        return ShareTitle;
    }

    public void setShareTitle(String ShareTitle) {
        this.ShareTitle = ShareTitle;
    }

    public String getShareDesc() {
        return ShareDesc;
    }

    public void setShareDesc(String ShareDesc) {
        this.ShareDesc = ShareDesc;
    }

    public String getShareLink() {
        return ShareLink;
    }

    public void setShareLink(String ShareLink) {
        this.ShareLink = ShareLink;
    }

    public String getShareImgUrl() {
        return ShareImgUrl;
    }

    public void setShareImgUrl(String ShareImgUrl) {
        this.ShareImgUrl = ShareImgUrl;
    }
}
