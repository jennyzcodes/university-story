package com.example.universitylife.Bookstore;

interface IBookstore {
    interface IBookstoreView{
        void displayWarning(String message);
        void updateMoneyDisplay(int newAmount);
    }

}