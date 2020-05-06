package com.group14.termproject.server.game.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Vector2DTests {

    @Test
    public void testEquals() {
        Vector2D v1 = new Vector2D(5, 10);
        Vector2D v2 = new Vector2D(5, 10);
        Vector2D v3 = new Vector2D(10, 5);
        Assert.assertEquals(v1, v2);
        Assert.assertNotEquals(v1, v3);
    }

    @Test
    public void testDistance() {
        Vector2D v1 = new Vector2D(3, 5);
        Vector2D v2 = new Vector2D(7, 2);
        // When there are two different vectors.
        Assert.assertEquals(5.0, v1.getDistance(v2), 0.0);
        // Distance two self should be zero
        Assert.assertEquals(0.0, v1.getDistance(v1), 0.0);
    }

    @Test
    public void testNormalize() {
        Vector2D v = new Vector2D(3, 4);
        Vector2D expected = new Vector2D(0.6, 0.8);
        v.normalize();
        Assert.assertEquals(expected, v);
    }

    @Test
    public void testNormalized() {
        Vector2D v = new Vector2D(3, 4);
        Vector2D copied = new Vector2D(v);
        Vector2D normalized = v.normalized();

        Vector2D expected = new Vector2D(0.6, 0.8);
        Assert.assertEquals(expected, normalized);
        // Check if other vector's x and y are not changed.
        Assert.assertEquals(copied, v);
    }

    @Test
    public void testSubtraction() {
        Vector2D v1 = new Vector2D(10, 15);
        Vector2D v2 = new Vector2D(3, 5);
        Vector2D v1Copy = new Vector2D(v1);
        Vector2D v2Copy = new Vector2D(v2);
        Vector2D subtracted = v1.getSubtracted(v2);
        Vector2D expected = new Vector2D(7, 10);
        Assert.assertEquals(expected, subtracted);
        // Check if two vectors are not affected.
        Assert.assertEquals(v1Copy, v1);
        Assert.assertEquals(v2Copy, v2);

        v1.subtract(v2);
        Assert.assertEquals(expected, v1);
    }

    @Test
    public void testAddition() {
        Vector2D v1 = new Vector2D(20, 15);
        Vector2D v2 = new Vector2D(-5, 5);
        Vector2D v1Copy = new Vector2D(v1);
        Vector2D v2Copy = new Vector2D(v2);
        Vector2D subtracted = v1.getAdded(v2);
        Vector2D expected = new Vector2D(15, 20);
        Assert.assertEquals(expected, subtracted);
        // Check if two vectors are not affected.
        Assert.assertEquals(v1Copy, v1);
        Assert.assertEquals(v2Copy, v2);

        v1.add(v2);
        Assert.assertEquals(expected, v1);
    }

    @Test
    public void testMultiply() {
        Vector2D v = new Vector2D(5, 15);
        Vector2D vCopy = new Vector2D(v);
        double factor = 3;
        Vector2D expected = new Vector2D(15, 45);
        Vector2D result = v.getMultiplied(factor);
        Assert.assertEquals(expected, result);
        // Check if original vector is not affected.
        Assert.assertEquals(vCopy, v);

        v.multiply(factor);
        Assert.assertEquals(expected, v);
    }
}