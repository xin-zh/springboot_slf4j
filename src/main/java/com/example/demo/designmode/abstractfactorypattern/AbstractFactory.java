package com.example.demo.designmode.abstractfactorypattern;

import com.example.demo.designmode.common.Color;
import com.example.demo.designmode.common.Shape;

public interface AbstractFactory {
    Shape getShape(String shape);

    Color getColor(String color);
}
