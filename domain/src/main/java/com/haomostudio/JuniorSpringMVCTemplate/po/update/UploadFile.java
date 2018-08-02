package com.haomostudio.JuniorSpringMVCTemplate.po.update;

import java.io.Serializable;


public class UploadFile implements Serializable {
    private String saveName ;

    private String fileName ;

    private String visitName ;

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    @Override
    public String toString() {
        return "UploadFile{" +
                "saveName='" + saveName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", visitName='" + visitName + '\'' +
                '}';
    }
}
