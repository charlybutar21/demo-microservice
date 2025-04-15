package com.charly.cards.service.impl;

import com.charly.cards.constant.CardConstant;
import com.charly.cards.dto.CardDto;
import com.charly.cards.entity.Card;
import com.charly.cards.exception.CardAlreadyExistsException;
import com.charly.cards.exception.ResourceNotFoundException;
import com.charly.cards.mapper.CardMapper;
import com.charly.cards.repository.CardRepository;
import com.charly.cards.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstant.CREDIT_CARD);
        newCard.setTotalLimit(CardConstant.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstant.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardDto getCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        return CardMapper.mapToCardsDto(card, new CardDto());
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Card cards = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(() -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
        CardMapper.mapToCards(cardDto, cards);
        cardRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card cards = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        cardRepository.deleteById(cards.getCardId());
        return true;
    }
}
