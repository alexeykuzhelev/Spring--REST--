package ru.bitmaster.paymentserver.critery;

import java.util.ArrayList;
import java.util.List;

public abstract class ACritery {
    /**
     * Список идентификаторов
     */
    private List<Long> ids = new ArrayList<>();

    /**
     * Номер страницы
     */
    private int pageNum;

    public ACritery() {

    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public ACritery(List<Long> ids) {
        if(ids!=null) {
            this.ids = ids;
        }
    }

}
