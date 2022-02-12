package com.example.demo.designmode.factorypattern;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.designmode.common.Circle;
import com.example.demo.designmode.common.Rectangle;
import com.example.demo.designmode.common.Shape;
import com.example.demo.designmode.common.Square;

public class ShapeFactory {

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
        if (shapeType.equalsIgnoreCase("Square")) {
            return new Square();
        }
        return null;
    }
}
