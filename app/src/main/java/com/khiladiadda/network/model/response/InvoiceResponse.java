package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class InvoiceResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private InvoiceDetails response;

    public InvoiceDetails getResponse() {
        return response;
    }

    public void setResponse(InvoiceDetails response) {
        this.response = response;
    }

}
