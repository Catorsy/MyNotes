package com.example.mynotes;
//интерфейс отдает карточку по позиции и знает размер
public interface CardSourse {
    CardData getCard(int position);
    int size();
}
