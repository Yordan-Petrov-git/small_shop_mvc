package com.shop.advance.academy.yordan.petrov.git.shop.data.models;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class model for .
 *
 * @author Yordan Petrov
 * @version 1.0.0.0
 * @since Jul 8, 2020.
 */
@Entity
@Table(name = "item_item_count_pairs")
public class ItemCountPair extends BaseEntity {

    private Item item;
    private Integer itemCount;


    public ItemCountPair(Item item, Integer itemCount) {
        this.item = item;
        this.itemCount = itemCount;
    }

    /**
     * Constructor
     */
    public ItemCountPair() {
    }

    /**
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY
            , cascade = {CascadeType.DETACH})
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    public Item getItem() {
        return this.item;
    }

    /**
     * @param item
     */
    @Column(name = "item_count")
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return
     */
    public Integer getItemCount() {
        return this.itemCount;
    }

    /**
     * @param itemCount
     */
    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemCountPair)) return false;
        if (!super.equals(o)) return false;
        ItemCountPair that = (ItemCountPair) o;
        return Objects.equals(itemCount, that.itemCount);
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), itemCount);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItemCountPair{");
        sb.append("itemCount=").append(itemCount);
        sb.append('}');
        return sb.toString();
    }
}


