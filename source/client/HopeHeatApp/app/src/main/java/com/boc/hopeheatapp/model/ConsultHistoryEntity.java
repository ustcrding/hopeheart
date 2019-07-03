package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用户信息实体类
 *
 * @author dwl
 * @date 2018/2/23.
 */
public class ConsultHistoryEntity {

    @Expose
    @SerializedName("firstPage")
    private boolean firstPage;

    @Expose
    @SerializedName("lastPage")
    private boolean lastPage;

    @Expose
    @SerializedName("pageNumber")
    private int pageNumber;

    @Expose
    @SerializedName("pageSize")
    private int pageSize;

    @Expose
    @SerializedName("totalPage")
    private int totalPage;

    @Expose
    @SerializedName("totalRow")
    private int totalRow;

    @Expose
    @SerializedName("list")
    private List<Result> results;

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        @Expose
        @SerializedName("addressCode")
        private String addressCode;

        @Expose
        @SerializedName("counseling")
        private String counseling;

        @Expose
        @SerializedName("testioinDate")
        private String testioinDate;

        @Expose
        @SerializedName("testsLevel")
        private String testsLevel;

        @Expose
        @SerializedName("victimtestId")
        private String victimtestId;

        public String getAddressCode() {
            return addressCode;
        }

        public void setAddressCode(String addressCode) {
            this.addressCode = addressCode;
        }

        public String getCounseling() {
            return counseling;
        }

        public void setCounseling(String counseling) {
            this.counseling = counseling;
        }

        public String getTestioinDate() {
            return testioinDate;
        }

        public void setTestioinDate(String testioinDate) {
            this.testioinDate = testioinDate;
        }

        public String getTestsLevel() {
            return testsLevel;
        }

        public void setTestsLevel(String testsLevel) {
            this.testsLevel = testsLevel;
        }

        public String getVictimtestId() {
            return victimtestId;
        }

        public void setVictimtestId(String victimtestId) {
            this.victimtestId = victimtestId;
        }
    }
}
