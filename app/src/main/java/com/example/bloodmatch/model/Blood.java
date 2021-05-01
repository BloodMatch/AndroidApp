package com.example.bloodmatch.model;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Blood {
    private String type,  rhesus;
    private static HashMap<String , String[]> bloodGraph;

    static {
        bloodGraph = new HashMap<String , String[]>();
        bloodGraph.put("O-", null);
        bloodGraph.put("O+", new String[]{"O-"});
        bloodGraph.put("A-", new String[]{"O-"});
        bloodGraph.put("A+", new String[]{"A-"});
        bloodGraph.put("B-", new String[]{"O-"});
        bloodGraph.put("B+", new String[]{"B-"});
        bloodGraph.put("AB-", new String[]{"A-","B-"});
        bloodGraph.put("AB+", new String[]{"AB-","A+","B+"});
    }

    public Blood(){}

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    public String getRhesus() {
        return rhesus;
    }

    public boolean canGiveTo(Blood recipientBlood){ // (Donor, Recipient)
        if( this.getRhesus().equals("+") && recipientBlood.getRhesus().equals("-") )
                return false;

        if( this.getType().equals("O") ) // (O,*)
                return true;

        if( recipientBlood.getType().equals("AB") ) // (*,AB)
                return true;

        if( this.getType().equals( recipientBlood.getType() ) ) // (A,A) || (B,B)
                return true;

        return false;
    }

    public boolean canTakeFrom(Blood donorBlood){
        return donorBlood.canGiveTo(this);
    }

    public void setBloodGroup(String bloodGroup){
        int length = bloodGroup.length();
        this.setRhesus(Character.toString(bloodGroup.charAt(length -1)));
        this.setType(bloodGroup.substring(0, length -1));
    }

    public static ArrayList<String> listOfAllDonorsBlood(String myBlood){
        HashSet<String> bloods = new HashSet<String>();
        bloods.add(myBlood);
        bloods.addAll(listOfAllDonorsBloodRecursive(bloodGraph.get(myBlood)));
        return new ArrayList<String>(bloods);
    }

    private static HashSet<String> listOfAllDonorsBloodRecursive(String[] listBloods){
        if( listBloods == null)
            return null;

        HashSet<String> localBloods = new HashSet<String>();
        for (String blood : listBloods ) {
            localBloods.add(blood);
            localBloods.addAll( listOfAllDonorsBloodRecursive(bloodGraph.get(blood)) );
        }
        return localBloods;
    }

    @Override
    public String toString() {
        return type.concat(rhesus);
    }
}
