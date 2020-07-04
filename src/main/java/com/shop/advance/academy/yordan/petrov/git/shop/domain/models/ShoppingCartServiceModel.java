package com.shop.advance.academy.yordan.petrov.git.shop.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartServiceModel {

    private Long id;
    private List<ItemServiceModel> addedItems =  new ArrayList<>();
    private UserServiceModel user;
    private LocalDateTime created;
    private LocalDateTime modified;

    public ShoppingCartServiceModel() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemServiceModel> getAddedItems() {
        return this.addedItems;
    }

    public void setAddedItems(List<ItemServiceModel> addedItems) {
        this.addedItems = addedItems;
    }

    public UserServiceModel getUser() {
        return this.user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return this.modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
}
