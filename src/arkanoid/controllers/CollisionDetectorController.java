// Author: Walerij Hrul
//
package arkanoid.controllers;

import arkanoid.models.CircleShape;
import arkanoid.models.RectShape;

public class CollisionDetectorController {
    public static boolean circleHitsRectOnLeft(CircleShape circleShape, RectShape rectShape) {
        return isBetween(circleShape.getCenterY(), rectShape.getPosY(), rectShape.getPosY() + rectShape.getHeight())
                && circleShape.getCenterX() < rectShape.getPosX()
                && circleShape.getPosX() + circleShape.getDiameter() > rectShape.getPosX();
    }
    public static boolean circleHitsRectOnRight(CircleShape circleShape, RectShape rectShape) {
        return isBetween(circleShape.getCenterY(), rectShape.getPosY(), rectShape.getPosY() + rectShape.getHeight())
            && circleShape.getCenterX() > rectShape.getPosX() + rectShape.getWidth()
            && circleShape.getPosX() < rectShape.getPosX() + rectShape.getWidth();
    }
    public static boolean circleHitsRectOnUp(CircleShape circleShape, RectShape rectShape) {
        return isBetween(circleShape.getCenterX(), rectShape.getPosX(), rectShape.getPosX() + rectShape.getWidth())
                && circleShape.getCenterY() < rectShape.getPosY()
                && circleShape.getPosY() + circleShape.getDiameter() > rectShape.getPosY();
    }
    public static boolean circleHitsRectOnDown(CircleShape circleShape, RectShape rectShape) {
       return isBetween(circleShape.getCenterX(), rectShape.getPosX(), rectShape.getPosX() + rectShape.getWidth())
                && circleShape.getCenterY() > rectShape.getPosY() + rectShape.getHeight()
                && circleShape.getPosY() < rectShape.getPosY() + rectShape.getHeight();
    }
    public static boolean circleHitsRectOnLeftUpCorner(CircleShape circleShape, RectShape rectShape) {
        return circleShape.getCenterX() < rectShape.getPosX()
                && circleShape.getCenterY() < rectShape.getPosY()
                && distBetween(circleShape.getCenterX(), circleShape.getCenterY(), rectShape.getPosX(), rectShape.getPosY()) < circleShape.getRadius();
    }
    public static boolean circleHitsRectOnLeftDownCorner(CircleShape circleShape, RectShape rectShape) {
        return circleShape.getCenterX() < rectShape.getPosX()
                && circleShape.getCenterY() > rectShape.getPosY() + rectShape.getHeight()
                && distBetween(circleShape.getCenterX(), circleShape.getCenterY(), rectShape.getPosX(), rectShape.getPosY() + rectShape.getHeight()) < circleShape.getRadius();
    }
    public static boolean circleHitsRectOnRightUpCorner(CircleShape circleShape, RectShape rectShape) {
        return circleShape.getCenterX() > rectShape.getPosX() + rectShape.getWidth()
                && circleShape.getCenterY() < rectShape.getPosY()
                && distBetween(circleShape.getCenterX(), circleShape.getCenterY(), rectShape.getPosX() + rectShape.getWidth(), rectShape.getPosY()) < circleShape.getRadius();
    }
    public static boolean circleHitsRectOnRightDownCorner(CircleShape circleShape, RectShape rectShape) {
        return circleShape.getCenterX() > rectShape.getPosX() + rectShape.getWidth()
                && circleShape.getCenterY() > rectShape.getPosY() + rectShape.getHeight()
                && distBetween(circleShape.getCenterX(), circleShape.getCenterY(), rectShape.getPosX() + rectShape.getWidth(), rectShape.getPosY() + rectShape.getHeight()) < circleShape.getRadius();
    }
    public static boolean circleHitsRectOnCorner(CircleShape circleShape, RectShape rectShape) {
        return circleHitsRectOnLeftDownCorner(circleShape, rectShape)
                || circleHitsRectOnLeftUpCorner(circleShape, rectShape)
                || circleHitsRectOnRightDownCorner(circleShape, rectShape)
                || circleHitsRectOnRightUpCorner(circleShape, rectShape);
    }

    private static boolean isBetween(int a, int x1, int x2) {
        return a >= x1 && a <= x2;
    }
    private static int distBetween(int x1, int y1, int x2, int y2) {
        return (int)(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }
}
