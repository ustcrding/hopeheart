package com.boc.hopeheatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author dwl
 * @date 2019/7/4.
 */
public class TutorHistoryEntity {
    @Expose
    @SerializedName("pageNumber")
    private int pageNumber;

    @Expose
    @SerializedName("totalPage")
    private int totalPage;

    @Expose
    @SerializedName("pageSize")
    private int pageSize;


    @Expose
    @SerializedName("list")
    private List<Result> results;


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        @Expose
        @SerializedName("victim_name")
        private String victimName;

        @Expose
        @SerializedName("victimGender")
        private String victimGender;

        @Expose
        @SerializedName("victim_test_id")
        private String victimTestId;

        @Expose
        @SerializedName("victim_id")
        private String victimId;

        @Expose
        @SerializedName("tests_level")
        private String testsLevel;

        @Expose
        @SerializedName("testioin_date")
        private String testioinDate;

        @Expose
        @SerializedName("address_code")
        private String addressCode;

        public String getAddressCode() {
            return addressCode;
        }

        public void setAddressCode(String addressCode) {
            this.addressCode = addressCode;
        }

        public String getVictimName() {
            return victimName;
        }

        public void setVictimName(String victimName) {
            this.victimName = victimName;
        }

        public String getVictimGender() {
            return victimGender;
        }

        public void setVictimGender(String victimGender) {
            this.victimGender = victimGender;
        }

        public String getVictimTestId() {
            return victimTestId;
        }

        public void setVictimTestId(String victimTestId) {
            this.victimTestId = victimTestId;
        }

        public String getTestsLevel() {
            return testsLevel;
        }

        public void setTestsLevel(String testsLevel) {
            this.testsLevel = testsLevel;
        }

        public String getVictimId() {
            return victimId;
        }

        public void setVictimId(String victimId) {
            this.victimId = victimId;
        }

        public String getTestioinDate() {
            return testioinDate;
        }

        public void setTestioinDate(String testioinDate) {
            this.testioinDate = testioinDate;
        }
    }
}
