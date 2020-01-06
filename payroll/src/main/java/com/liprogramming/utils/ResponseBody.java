package com.liprogramming.utils;

import java.util.List;

public class ResponseBody<T> {
    String message;
    List<T> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> result) {
        this.data = result;
    }
}
