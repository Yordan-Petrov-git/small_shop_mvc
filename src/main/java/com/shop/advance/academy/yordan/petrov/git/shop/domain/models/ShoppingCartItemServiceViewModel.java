package com.shop.advance.academy.yordan.petrov.git.shop.domain.models;

public class ShoppingCartItemServiceViewModel {

    private ItemServiceViewModel item;
    private Integer itemCount;

    public ShoppingCartItemServiceViewModel() {
    }

    public ItemServiceViewModel getItem() {
        return this.item;
    }

    public void setItem(ItemServiceViewModel item) {
        this.item = item;
    }

    public Integer getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}
