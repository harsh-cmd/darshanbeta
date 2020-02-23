package com.example.darshanbeta;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

class ScannerResult implements Serializable {
    private Set<String> ResultCount = new HashSet<>();

    public Set<String> getResultCount() {
        return ResultCount;
    }

    public void setResultCount(String result) {
        ResultCount.add(result);
    }
}
