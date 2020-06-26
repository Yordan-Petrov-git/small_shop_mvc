package com.shop.advance.academy.yordan.petrov.git.shop.domain.models;

import com.shop.advance.academy.yordan.petrov.git.shop.data.entities.enums.UserType;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserServiceModel {

    private Long id;
    private String username;
    private String password;
    private UserType userType = UserType.REGULAR;
    private Instant dateRegistered;
    private Date dateOfBirth;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Set<AddressServiceModel> addresses = new HashSet<>();
    private Set<CardServiceModel> cards = new HashSet<>();
    private Set<ContactInformationServiceModel> contactInformation = new HashSet<>();
    private Set<RoleServiceModel> roles = new HashSet<>();
    private boolean isEnabled = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isAccountNonExpired = true;



    public UserServiceModel() {
    }

    public UserServiceModel(Long id, String username, String password, UserType userType,
                            Instant dateRegistered, Date dateOfBirth, String firstName,
                            String lastName, String phone, String email, Set<AddressServiceModel> addresses,
                            Set<CardServiceModel> cards, Set<ContactInformationServiceModel> contactInformation,
                            Set<RoleServiceModel> roles, boolean isEnabled, boolean isCredentialsNonExpired,
                            boolean isAccountNonLocked, boolean isAccountNonExpired) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.dateRegistered = dateRegistered;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.addresses = addresses;
        this.cards = cards;
        this.contactInformation = contactInformation;
        this.roles = roles;
        this.isEnabled = isEnabled;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isAccountNonExpired = isAccountNonExpired;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Instant getDateRegistered() {
        return this.dateRegistered;
    }

    public void setDateRegistered(Instant dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<AddressServiceModel> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<AddressServiceModel> addresses) {
        this.addresses = addresses;
    }

    public Set<CardServiceModel> getCards() {
        return this.cards;
    }

    public void setCards(Set<CardServiceModel> cards) {
        this.cards = cards;
    }

    public Set<ContactInformationServiceModel> getContactInformation() {
        return this.contactInformation;
    }

    public void setContactInformation(Set<ContactInformationServiceModel> contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Set<RoleServiceModel> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<RoleServiceModel> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserServiceModel)) return false;
        UserServiceModel that = (UserServiceModel) o;
        return isEnabled == that.isEnabled &&
                isCredentialsNonExpired == that.isCredentialsNonExpired &&
                isAccountNonLocked == that.isAccountNonLocked &&
                isAccountNonExpired == that.isAccountNonExpired &&
                Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                userType == that.userType &&
                Objects.equals(dateRegistered, that.dateRegistered) &&
                Objects.equals(dateOfBirth, that.dateOfBirth) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, userType, dateRegistered, dateOfBirth,
                firstName, lastName, phone, email, isEnabled, isCredentialsNonExpired,
                isAccountNonLocked, isAccountNonExpired);
    }

}
