package com.shop.advance.academy.yordan.petrov.git.shop.domain.services.impl;

import com.shop.advance.academy.yordan.petrov.git.shop.data.dao.AddressRepository;
import com.shop.advance.academy.yordan.petrov.git.shop.data.dao.ContactInformationRepository;
import com.shop.advance.academy.yordan.petrov.git.shop.data.dao.RoleRepository;
import com.shop.advance.academy.yordan.petrov.git.shop.data.dao.UserRepository;
import com.shop.advance.academy.yordan.petrov.git.shop.data.entities.Role;
import com.shop.advance.academy.yordan.petrov.git.shop.data.entities.User;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.models.UserServiceModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.models.UserServiceViewModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.services.RoleService;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.services.UserService;
import com.shop.advance.academy.yordan.petrov.git.shop.exeption.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ContactInformationRepository contactInformationRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, ContactInformationRepository contactInformationRepository, ModelMapper modelMapper, RoleRepository roleRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.contactInformationRepository = contactInformationRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public UserServiceViewModel createUser(@Valid UserServiceModel userServiceModel) {

        User user = this.modelMapper.map(userServiceModel, User.class);

        this.userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new InvalidEntityException(String.format("User with username '%s' already exists.", user.getUsername()));
        });


        if (contactInformationRepository.count() != 0) {

            this.contactInformationRepository.findByEmail(userServiceModel.getContactInformation()
                    .stream()
                    .findAny()
                    .get().getEmail()).ifPresent(e -> {
                throw new InvalidEntityException(String.format("Email '%s' is  already registered.", e.getEmail()));
            });


            this.contactInformationRepository.findByPhoneNumber(userServiceModel.getContactInformation()
                    .stream()
                    .findAny()
                    .get().getPhoneNumber()).ifPresent(p -> {
                throw new InvalidEntityException(String.format("Phone number : '%s' is  already registered.", p.getPhoneNumber()));
            });
        }

        if (userRepository.count() == 0) {
            //Sets 1 st registered user as admin role
            this.roleService.seedRolesInDatabase();

            user.setAuthorities(this.roleService.findAllRoles()
                    .stream()
                    .map(r -> this.modelMapper.map(r, Role.class))
                    .collect(Collectors.toSet()));

        } else if (userRepository.count() > 0) {
            //Sets 2 and so on user  as user role
            user.setAuthorities(new LinkedHashSet<>());
            user.getAuthorities()
                    .add(this.modelMapper.map(this.roleRepository.findByAuthority("USER"), Role.class));
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);

        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceViewModel.class);
    }

    @Override
    @Transactional
    public UserServiceViewModel updateUser(@Valid UserServiceModel userServiceModel) {

        User user = this.modelMapper.map(userServiceModel, User.class);

        this.userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new InvalidEntityException(String.format("User with username '%s' already exists.", user.getUsername()));
        });
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setModified(LocalDateTime.now());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceViewModel.class);
    }

    @Override
    public UserServiceViewModel getUserById(long id) {

        return this.modelMapper
                .map(this.userRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(String.format("User with ID %s not found.", id))), UserServiceViewModel.class);
    }

    @Override
    public List<UserServiceViewModel> getAllUsers() {

        this.userRepository.findAll()
                .stream()
                .findAny()
                .orElseThrow(() -> new InvalidEntityException("No Users were found"));

        List<User> users = this.userRepository.findAll();

        return this.modelMapper.map(users, new TypeToken<List<UserServiceViewModel>>() {
        }.getType());

    }

    @Override
    @Transactional
    public UserServiceViewModel deleteUserById(long id) {

        UserServiceViewModel deletedUser = this.getUserById(id);

        this.userRepository.deleteById(id);

        return this.modelMapper.map(deletedUser, UserServiceViewModel.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository.findByUsername(username)
                .orElseThrow((InvalidEntityException::new));

    }
}
