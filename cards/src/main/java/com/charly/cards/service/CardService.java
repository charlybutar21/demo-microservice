package com.charly.cards.service;

import com.charly.cards.dto.CardDto;

public interface CardService {

    void createCard(String mobileNumber);

    CardDto getCard(String mobileNumber);

    boolean updateCard(CardDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
