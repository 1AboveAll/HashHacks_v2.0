package com.example.himanshurawat.shramdaan.PojoClass;

import java.util.Comparator;

/**
 * Created by Daksh Garg on 10/28/2017.
 */

public class DistanceSort implements Comparator<Distance> {
    @Override
    public int compare(Distance distance1, Distance distance2) {
        if(Double.parseDouble(distance1.distance)<Double.parseDouble(distance2.distance)) return -1;
        else if(Double.parseDouble(distance1.distance)>Double.parseDouble(distance2.distance)) return 1;
        return 0;
    }
}
