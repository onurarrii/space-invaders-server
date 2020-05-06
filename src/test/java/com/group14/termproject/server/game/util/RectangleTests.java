package com.group14.termproject.server.game.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RectangleTests {

    @Test
    public void testInvalidInitialization() {
        double validSizeValue = 5.0;
        double invalidSizeValue = -5.0;
        Vector2D validCenter = new Vector2D(0, 0);
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        // When width is invalid
        Assert.assertThrows(expected, () -> new Rectangle(invalidSizeValue, validSizeValue, validCenter));
        // When height is invalid
        Assert.assertThrows(expected, () -> new Rectangle(validSizeValue, invalidSizeValue, validCenter));
        // When center is invalid
        Assert.assertThrows(expected, () -> new Rectangle(validSizeValue, validSizeValue, null));
        // When multiple invalid parameters are supplied
        Assert.assertThrows(expected, () -> new Rectangle(invalidSizeValue, validSizeValue, null));
        Assert.assertThrows(expected, () -> new Rectangle(invalidSizeValue, invalidSizeValue, null));

        // Valid initialization, should not throw exception
        new Rectangle(validSizeValue, validSizeValue, validCenter);
    }

    @Test
    public void testReposition() {
        Rectangle r = new Rectangle(100, 100, new Vector2D(0, 0));
        Vector2D newCenter = new Vector2D(10, 10);
        r.reposition(newCenter);
        Rectangle expected = new Rectangle(100, 100, newCenter);
        Assert.assertEquals(expected, r);
    }

    @Test
    public void testValidOverlappingCases() {
        // When overlapping only on horizontal axis
        Rectangle r1 = new Rectangle(400, 400, new Vector2D(300, 100));
        Rectangle r2 = new Rectangle(200, 50, new Vector2D(50, 100));
        Assert.assertTrue(r1.isOverlapping(r2));
        Assert.assertTrue(r2.isOverlapping(r1));

        // When overlapping only on vertical axis
        r1 = new Rectangle(400, 400, new Vector2D(100, 300));
        r2 = new Rectangle(50, 200, new Vector2D(100, 50));
        Assert.assertTrue(r1.isOverlapping(r2));
        Assert.assertTrue(r2.isOverlapping(r1));

        // When overlapping only both horizontal and vertical axis
        r1 = new Rectangle(150, 150, new Vector2D(100, 100));
        r2 = new Rectangle(150, 150, new Vector2D(0, 0));
        Assert.assertTrue(r1.isOverlapping(r2));
        Assert.assertTrue(r2.isOverlapping(r1));
    }

    @Test
    public void testNotOverlappingCases() {
        // When one is on the left side of the other
        Rectangle r1 = new Rectangle(400, 200, new Vector2D(300, 300));
        Rectangle r2 = new Rectangle(200, 100, new Vector2D(75, 75));
        Assert.assertFalse(r1.isOverlapping(r2));
        Assert.assertFalse(r2.isOverlapping(r1));

        // When one is above
        r1 = new Rectangle(200, 400, new Vector2D(300, 300));
        r2 = new Rectangle(100, 200, new Vector2D(75, 75));
        Assert.assertFalse(r1.isOverlapping(r2));
        Assert.assertFalse(r2.isOverlapping(r1));
    }

    @Test
    public void testEdgeOverlappingCases() {
        Vector2D center = new Vector2D(0, 0);
        // Each rectangle should overlap with itself
        Rectangle rectangle = new Rectangle(100, 100, center);
        Assert.assertTrue(rectangle.isOverlapping(rectangle));

        // When one rectangle is inside another
        Rectangle insider = new Rectangle(50, 50, center);
        Assert.assertTrue(rectangle.isOverlapping(insider));
        // Overlapping is commutative
        Assert.assertTrue(insider.isOverlapping(rectangle));
    }
}