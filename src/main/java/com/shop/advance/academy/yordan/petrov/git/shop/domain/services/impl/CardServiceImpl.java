package com.shop.advance.academy.yordan.petrov.git.shop.domain.services.impl;

import com.shop.advance.academy.yordan.petrov.git.shop.data.dao.CardRepository;
import com.shop.advance.academy.yordan.petrov.git.shop.data.entities.Card;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.models.CardServiceModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.models.CardServiceViewModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.services.CardService;
import com.shop.advance.academy.yordan.petrov.git.shop.exeption.InvalidEntityException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public CardServiceViewModel createCard(CardServiceModel cardServiceModel) {

        Card card = this.modelMapper.map(cardServiceModel, Card.class);

        this.cardRepository.findByNumber(cardServiceModel.getNumber()).ifPresent(c -> {
            throw new InvalidEntityException(String.format("Card with number '%s' already exists.", cardServiceModel.getNumber()));
        });


        card.setDateIssued(LocalDateTime.now());
        card.setPinCode(this.bCryptPasswordEncoder.encode(cardServiceModel.getPinCode()));
        return this.modelMapper.map(this.cardRepository.saveAndFlush(card), CardServiceViewModel.class);

    }

    @Override
    @Transactional
    public CardServiceViewModel updateCard(CardServiceModel cardServiceModel) {

        Card card = this.modelMapper.map(cardServiceModel, Card.class);

        this.cardRepository.findById(cardServiceModel.getId())
                .orElseThrow(() -> new InvalidEntityException(String.format("Card with id '%d' not found .", cardServiceModel.getId())));

        return this.modelMapper.map(this.cardRepository.saveAndFlush(card), CardServiceViewModel.class);

    }

    @Override
    public CardServiceViewModel getCardById(long id) {

        return this.modelMapper
                .map(this.cardRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(String.format("Card  with ID %s not found.", id))), CardServiceViewModel.class);

    }

    @Override
    public List<CardServiceViewModel> getAllCards() {

        this.cardRepository.findAll()
                .stream()
                .findAny()
                .orElseThrow(() -> new InvalidEntityException("No Cards were found"));

        List<Card> cards = cardRepository.findAll();

        return modelMapper.map(cards, new TypeToken<List<CardServiceViewModel>>() {
        }.getType());

    }

    @Override
    public CardServiceViewModel deleteCardById(long id) {

        CardServiceViewModel cardServiceViewModel = this.getCardById(id);

        this.cardRepository.deleteById(id);

        return cardServiceViewModel;

    }
}
