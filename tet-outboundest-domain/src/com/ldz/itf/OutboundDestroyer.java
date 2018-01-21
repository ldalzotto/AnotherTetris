package com.ldz.itf;

import java.util.ArrayList;
import java.util.List;

public class OutboundDestroyer {

    private List<SingleDestroyer> singleDestroyers = new ArrayList<>();

    public List<SingleDestroyer> getSingleDestroyers() {
        return singleDestroyers;
    }

    public void setSingleDestroyers(List<SingleDestroyer> singleDestroyers) {
        this.singleDestroyers = singleDestroyers;
    }
}
