package com.training.financialtraining.push;

/**
 * Created by shxioyang on 2018/7/25.
 */

public class GeTuiPushBean {
    private String title;
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
   /* *//**
     * title : title
     * content : desc
     * payload : {"type":1}
     *//*

    private String title;
    private String content;
    private PayloadBean payload;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        *//**
         * type : 1
         *//*

        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }*/

}