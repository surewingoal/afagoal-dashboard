package com.afagoal.util;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by BaoCai on 18/2/11.
 * Description:
 */
@Data
public class PageData<T> implements Serializable {

    private List<T> rows ;

    private int total;

    public PageData(List<T> rows , int total){
       this.rows = rows;
       this.total = total;
    }

}
