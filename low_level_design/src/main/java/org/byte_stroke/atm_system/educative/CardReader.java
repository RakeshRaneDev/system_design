package org.byte_stroke.atm_system.educative;

public class CardReader {
    public boolean readCard(AtmCard card){
        if(card==null){
            System.out.println("[ATM]: no card inserted.");
            return false;
        }
        System.out.println("[ATM]: Reading card: "+ card.getCardNumber());
        return true;
    }
}
