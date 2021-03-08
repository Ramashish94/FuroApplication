package com.app.furoapp.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("yoga");
        cricket.add("yoga");
        cricket.add("yoga");
        cricket.add("yoga");
        cricket.add("yoga ");

        List<String> football = new ArrayList<String>();
        football.add("yoga");
        football.add("yoga");
        football.add("yoga");
        football.add("yoga");
        football.add("yoga");

        List<String> basketball = new ArrayList<String>();
        basketball.add("yoga ");
        basketball.add("yoga");
        basketball.add("yoga");
        basketball.add("yoga");
        basketball.add("yoga");

        expandableListDetail.put("FLEXIBILITY CHALLENGE", cricket);
        expandableListDetail.put("STRENGTH CHALLENGE", football);
        expandableListDetail.put("ENDURANCE CHALLENGE", basketball);
        return expandableListDetail;
    }
}

