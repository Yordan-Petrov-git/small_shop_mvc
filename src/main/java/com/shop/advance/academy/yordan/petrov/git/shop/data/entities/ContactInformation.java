package com.shop.advance.academy.yordan.petrov.git.shop.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "coontact_informations")
public class ContactInformation extends BaseEntity{

    private String email;
    private String phoneNumber;
    private String countryCode;


    public ContactInformation() {
    }
    @Column(name = "email")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Column(name = "country_code")
    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactInformation)) return false;
        if (!super.equals(o)) return false;
        ContactInformation that = (ContactInformation) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(countryCode, that.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, phoneNumber, countryCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContactInformation{");
        sb.append("email='").append(email).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", countryCode='").append(countryCode).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
