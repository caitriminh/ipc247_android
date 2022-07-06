package com.example.quyetthang.model.kho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_SaleOrder {
    @SerializedName("idQuote")
    @Expose
    private int idQuote;

    @SerializedName("quoteNum")
    @Expose
    private String quoteNum;

    public int getIdQuote() {
        return idQuote;
    }

    public void setIdQuote(int idQuote) {
        this.idQuote = idQuote;
    }

    public String getQuoteNum() {
        return quoteNum;
    }

    public void setQuoteNum(String quoteNum) {
        this.quoteNum = quoteNum;
    }
}
