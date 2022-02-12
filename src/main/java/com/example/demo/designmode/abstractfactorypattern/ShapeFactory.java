package com.example.demo.designmode.abstractfactorypattern;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.designmode.common.*;

public class ShapeFactory implements AbstractFactory {
    @Override
    public Shape getShape(String shapeType) {
        if (StringUtils.isEmpty(shapeType)) {

            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        }
        if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        }
        if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }

    @Override
    public Color getColor(String color) {
        return null;
    }

}
