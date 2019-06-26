package com.boc.hopeheatapp.model;


import java.util.List;

public class SemanticData {
    public String parseResult;
    public List<Result> queryResult;

    public class Result {
        public String image;
        public String file;
        public String multfile;
        public String title;
        public String type;
        public String userid;
        public String daytime;
        public String content;

    }
}
