package com.example.demo.designmode.abstractfactorypattern;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.designmode.common.*;

public class ColorFactory implements AbstractFactory {

    @Override
    public Shape getShape(String shape) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        if (StringUtils.isEmpty(color)) {
            return null;
        }
        if (color.equalsIgnoreCase("RED")) {
            return new Red();
        }
        if (color.equalsIgnoreCase("GREEN")) {
            return new Green();
        }
        if (color.equalsIgnoreCase("BLUE")) {
            return new Blue();
        }
        return null;
    }
}
