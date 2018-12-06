package com.aojing.redstore.media.pojo;

import java.util.Date;

public class MediaInfo {
    private Integer id;

    private String entityId;

    private String goodsId;

    private String menuId;

    private String absolutePath;

    private String relativePath;

    private Integer type;

    private String name;

    private String nocount;

    private String lllustrate;

    private String leadingOfficical;

    private String location;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId == null ? null : entityId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath == null ? null : absolutePath.trim();
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath == null ? null : relativePath.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNocount() {
        return nocount;
    }

    public void setNocount(String nocount) {
        this.nocount = nocount == null ? null : nocount.trim();
    }

    public String getLllustrate() {
        return lllustrate;
    }

    public void setLllustrate(String lllustrate) {
        this.lllustrate = lllustrate == null ? null : lllustrate.trim();
    }

    public String getLeadingOfficical() {
        return leadingOfficical;
    }

    public void setLeadingOfficical(String leadingOfficical) {
        this.leadingOfficical = leadingOfficical == null ? null : leadingOfficical.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}