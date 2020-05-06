package com.group14.termproject.server.game.util;

import java.security.InvalidParameterException;

public class Rectangle {

    public double width;
    public double height;
    public Vector2D center;

    public Rectangle(double width, double height, Vector2D center) throws InvalidParameterException {
        if (width <= 0 || height <= 0 || center == null)
            throw new InvalidParameterException();
        this.width = width;
        this.height = height;
        this.center = center;
    }

    public void reposition(Vector2D center) {
        this.center = new Vector2D(center);
    }

    private Vector2D getTopLeft() {
        Vector2D v = new Vector2D(-width / 2, height / 2);
        return center.getAdded(v);
    }

    private Vector2D getBottomRight() {
        Vector2D v = new Vector2D(width / 2, -height / 2);
        return center.getAdded(v);
    }

    public boolean isOverlapping(Rectangle other) {
        // One rectangle is on the left side of the other
        boolean isAbove =
                this.getTopLeft().x > other.getBottomRight().x || other.getTopLeft().x > this.getBottomRight().x;
        boolean isRight =
                this.getTopLeft().y < other.getBottomRight().y || other.getTopLeft().y < this.getBottomRight().y;
        return !isAbove && !isRight;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Rectangle) {
            Rectangle r = (Rectangle) o;
            return this.width == r.width && this.height == r.height && this.center.equals(r.center);
        }
        return false;
    }
}
