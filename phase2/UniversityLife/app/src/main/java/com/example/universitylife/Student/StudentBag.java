package com.example.universitylife.Student;

class StudentBag {
    private int giftcards;
    private int[] items = new int[3];

    StudentBag(){
    }

    StudentBag(int giftcards, int[] items){
        this.giftcards = giftcards;
        this.items = items;
    }

    public boolean hasGiftcards(){
        return giftcards > 0;
    }

    public void purchaseItem(int itemIndex){
        items[itemIndex-1]++;
        giftcards--;
    }

    int getItem(int itemIndex){
        return items[itemIndex-1];
    }

    void useItem(int itemIndex){
        items[itemIndex-1]--;
    }

    void obtainGiftcards(int n){
        giftcards += n;
    }

    int getGiftcards(){
        return this.giftcards;
    }
}
