package com.cc.web.entity.payloads;

public class FolderForm {

    private long parentId;
    private String name;

    private long folderId;

    private long fromId;
    private long toId;

    public FolderForm(long parentId, long folderId, String name, long fromId, long toId) {
        this.parentId = parentId;
        this.name = name;
        this.folderId = folderId;
        this.fromId = fromId;
        this.toId = toId;
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }


    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }
}
