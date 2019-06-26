package com.boc.hopeheatapp.http;

/**
 * Created by qsy on 2018/02/08.
 */

public class VoidResponse {
   public static final int OK = 0;
   private int code;
   private String message;

   public int getCode() {
      return code;
   }

   public void setCode(int code) {
      this.code = code;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
