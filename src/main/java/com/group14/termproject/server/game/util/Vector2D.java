package com.group14.termproject.server.game.util;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public static Vector2D up() {
        return new Vector2D(0, -1);
    }

    public static Vector2D down() {
        return new Vector2D(0, 1);
    }

    public static Vector2D zero() {
        return new Vector2D(0, 0);
    }

    public double getDistance(Vector2D vector) {
        double xv = this.x - vector.x;
        double yv = this.y - vector.y;
        return Math.sqrt(xv * xv + yv * yv);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        if (x == 0 && y == 0) return;
        double magnitude = Math.sqrt(x * x + y * y);
        x /= magnitude;
        y /= magnitude;
    }

    public Vector2D normalized() {
        Vector2D v = new Vector2D(this);
        v.normalize();
        return v;
    }

    public Vector2D getAdded(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }

    public void add(Vector2D vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public Vector2D getSubtracted(Vector2D vector) {
        return new Vector2D(this.x - vector.x, this.y - vector.y);
    }

    public void subtract(Vector2D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
    }

    public Vector2D getMultiplied(double factor) {
        return new Vector2D(this.x * factor, this.y * factor);
    }

    public void multiply(double factor) {
        this.x *= factor;
        this.y *= factor;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector2D) {
            Vector2D v = (Vector2D) o;
            return this.x == v.x && this.y == v.y;
        }
        return false;
    }
}
