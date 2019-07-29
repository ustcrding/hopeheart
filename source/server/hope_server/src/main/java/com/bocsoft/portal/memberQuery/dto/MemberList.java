package com.bocsoft.portal.memberQuery.dto;

import java.util.List;

public class MemberList {
    private int currentPage;
    private int totalPage;
    private String uploadNo;
    private String infoType;
    private String endFlag;
    private List<MemberCycle> memberList;

    public List<MemberCycle> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberCycle> memberList) {
        this.memberList = memberList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getUploadNo() {
        return uploadNo;
    }

    public void setUploadNo(String uplpadNo) {
        this.uploadNo = uploadNo;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(String endFlag) {
        this.endFlag = endFlag;
    }

    @Override
    public String toString() {
        return "MemberList{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", uploadNo='" + uploadNo + '\'' +
                ", infoType='" + infoType + '\'' +
                ", endFlag='" + endFlag + '\'' +
                ", memberList=" + memberList +
                '}';
    }
}
