package com.skalyter.mytraveljournal.model;

public enum TripType {
    CITYBREAK(1, "City Break"),
    MOUNTAIN(2, "Mountains"),
    SEASIDE(3, "Seaside");

    private String value;
    private Integer key;

    TripType(Integer key, String value){
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Integer getKey() {
        return key;
    }

    public static TripType getType(int key){
        if(key ==1) {
            return TripType.CITYBREAK;
        }
        if(key == 2){
            return TripType.MOUNTAIN;
        }
        return TripType.SEASIDE;
    }
}
