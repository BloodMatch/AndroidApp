package com.example.bloodmatch.model;

public class Blood {
    private String type,  rhesus;

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

    @Override
    public String toString() {
        return type.concat(rhesus);
    }
}
