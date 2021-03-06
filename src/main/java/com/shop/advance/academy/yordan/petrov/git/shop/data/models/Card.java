package com.shop.advance.academy.yordan.petrov.git.shop.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.enums.CardProviders;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.enums.CardType;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class model for .
 *
 * @author Yordan Petrov
 * @version 1.0.0.0
 * @since Jul 8, 2020.
 */
@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    private CardType cardType = CardType.NONE;
    private CardProviders cardProviders = CardProviders.NONE;
    private LocalDate expirationDate;
    private LocalDateTime dateIssued;
    private String cvvCode;
    private String pinCode;
    private String number;
    private BigDecimal balance;
    private Currency currency;
    private boolean isActive = true;
    private List<User> users = new ArrayList<>();

    /**
     * Constructor
     */
    public Card() {
    }

    /**
     * @return
     */
    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    public CardType getCardType() {
        return this.cardType;
    }

    /**
     * @param cardType
     */
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    /**
     * @return
     */
    @Column(name = "card_provider")
    @Enumerated(EnumType.STRING)
    public CardProviders getCardProviders() {
        return this.cardProviders;
    }

    /**
     * @param cardProviders
     */
    public void setCardProviders(CardProviders cardProviders) {
        this.cardProviders = cardProviders;
    }

    /**
     * @return
     */
    @Column(name = "expiration_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    /**
     * @param expirationDate
     */
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * @return
     */
    @Column(name = "issued_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getDateIssued() {
        return this.dateIssued;
    }

    /**
     * @param dateIssued
     */
    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    /**
     * @return
     */
    @Pattern(regexp = "^\\d{3}$", message = "cvv code is only 3 numbers")
    @Column(name = "cvv_code")
    public String getCvvCode() {
        return this.cvvCode;
    }

    /**
     * @param cvvCode
     */
    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    /**
     * @return
     */
    //TODO ADD PIN VALIDATION BEFORE B CRYPT Hash SOMEHOW
    @Column(name = "pin")
    //@Pattern(regexp = "^\\d{4}$|^\\d{8}$",message = "pin number must be either 4 or 8 numbers long")
    public String getPinCode() {
        return this.pinCode;
    }

    /**
     * @param pinCode
     */
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * @return
     */
    @Column(name = "number")
    public String getNumber() {
        return this.number;
    }

    /**
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return
     */
    @Column(name = "balance")
    public BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * @param balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    /**
     * @return
     */
    @ManyToOne(targetEntity = Currency.class
            , fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH})
    @JoinColumn(name = "currency_id"
            , referencedColumnName = "id")
    public Currency getCurrency() {
        return this.currency;
    }

    /**
     * @param currency
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * @return
     */
    @Column(name = "is_active")
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * @return
     */
    @OneToMany(targetEntity = User.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH})
    @JoinTable(name = "cards_user",
            joinColumns = @JoinColumn(name = "card_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    public List<User> getUsers() {
        return this.users;
    }

    /**
     * @param users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return isActive == card.isActive &&
                cardType == card.cardType &&
                cardProviders == card.cardProviders &&
                Objects.equals(expirationDate, card.expirationDate) &&
                Objects.equals(cvvCode, card.cvvCode) &&
                Objects.equals(pinCode, card.pinCode) &&
                Objects.equals(number, card.number) &&
                Objects.equals(balance, card.balance);
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardType, cardProviders, expirationDate, cvvCode, pinCode, number, balance, isActive);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("cardType=").append(cardType);
        sb.append(", cardProviders=").append(cardProviders);
        sb.append(", expirationDate=").append(expirationDate);
        sb.append(", cvvCode='").append(cvvCode).append('\'');
        sb.append(", pinCode='").append(pinCode).append('\'');
        sb.append(", number='").append(number).append('\'');
        sb.append(", balance=").append(balance);
        sb.append(", isActive=").append(isActive);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
