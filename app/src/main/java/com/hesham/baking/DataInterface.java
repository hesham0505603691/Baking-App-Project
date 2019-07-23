package com.hesham.baking;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.Call;




public interface DataInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Baking>> getData();
}
