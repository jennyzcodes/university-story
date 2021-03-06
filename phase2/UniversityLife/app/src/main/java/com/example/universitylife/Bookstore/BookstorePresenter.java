package com.example.universitylife.Bookstore;

import com.example.universitylife.GameManager;
import com.example.universitylife.DataHandler.IData;

import java.util.List;

class BookstorePresenter {
    private IBookstore.IBookstoreView view;
    private Bookstore store;
    private GameManager gameManager;

    BookstorePresenter(IBookstore.IBookstoreView view, String username, IData dataHandler) {
        this.view = view;
        gameManager = new GameManager(username, dataHandler);
        store = new Bookstore(gameManager.getCurrentStudent());
    }

    void save() {
        gameManager.saveBeforeExit();
    }


    void validateBonusPurchase(int bonusIdNum) {
        if (store.studentHasGiftcards()) {
            store.buyItem(bonusIdNum);
        } else {
            view.displayWarning("You do not have enough giftcards for this purchase!");
        }
        view.updateGiftCardDisplay(store.getStudentGiftcards());
    }

    /**
     * Returns the number of gift cards the user currently has.
     */
    int getStudentGiftcards() {
        return store.getStudentGiftcards();
    }

    /**
     * Returns a list of bonuses that is available in the bookstore.
     */
    List<BonusItem> getBonusItemsList() {
        return store.getBonusItemList();
    }
}
