// Author: Walerij Hrul
//
package arkanoid.controllersTests;

import arkanoid.models.CircleShape;
import arkanoid.models.RectShape;
import arkanoid.controllers.CollisionDetectorController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionDetectorControllerTests {

    Rectangle rectangle = Rectangle.builder()
            .setPosX(100)
            .setPosY(100)
            .setWidth(100)
            .setHeight(50)
            .build();

    Circle circle = Circle.builder()
            .setRadius(20)
            .build();


    @Test
    void circleHitsRectOnLeftWorksProperly() {
       circle.setCenterY(rectangle.getPosY() + 1);
       circle.setCenterX(rectangle.getPosX() - circle.getRadius() + 1);

       boolean isHitted = CollisionDetectorController.circleHitsRectOnLeft(circle, rectangle);

        assertEquals(true, isHitted);
    }

    @Test
    void circleHitsRectOnRightWorksProperly() {
        circle.setCenterY(rectangle.getPosY() + 1);
        circle.setCenterX(rectangle.getPosX() + rectangle.getWidth() + circle.getRadius() - 1);

        boolean isHitted = CollisionDetectorController.circleHitsRectOnRight(circle, rectangle);

        assertEquals(true, isHitted);
    }

    @Test
    void circleHitsRectOnDownWorksProperly() {
        circle.setCenterY(rectangle.getPosY() + rectangle.getHeight() + circle.getRadius() - 1);
        circle.setCenterX(rectangle.getPosX() + 1);

        boolean isHitted = CollisionDetectorController.circleHitsRectOnDown(circle, rectangle);

        assertEquals(true, isHitted);
    }

    @Test
    void circleHitsRectOnUpWorksProperly() {
        circle.setCenterY(rectangle.getPosY() - circle.getRadius() + 1);
        circle.setCenterX(rectangle.getPosX() + 1);

        boolean isHitted = CollisionDetectorController.circleHitsRectOnUp(circle, rectangle);

        assertEquals(true, isHitted);
    }

    @Test
    void circleHitsRectOnLeftUpCornerWorksProperly() {
        circle.setCenterY(rectangle.getPosY() - 1);
        circle.setCenterX(rectangle.getPosX() - 1);

        boolean isHitted = CollisionDetectorController.circleHitsRectOnLeftUpCorner(circle, rectangle);

        assertEquals(true, isHitted);
    }

    @Test
    void circleHitsRectOnLeftDownCornerWorksProperly() {
        circle.setCenterY(rectangle.getPosY() + rectangle.getHeight() + 1);
        circle.setCenterX(rectangle.getPosX() - 1);

        boolean isHitted = CollisionDetectorController.circleHitsRectOnLeftDownCorner(circle, rectangle);

        assertEquals(true, isHitted);
    }

    @Test
    void circleHitsRectOnRightUpCornerWorksProperly() {
        circle.setCenterY(rectangle.getPosY() - 1);
        circle.setCenterX(rectangle.getPosX() + rectangle.getWidth() + 1);

        boolean isHitted = CollisionDetectorController.circleHitsRectOnRightUpCorner(circle, rectangle);

        assertEquals(true, isHitted);
    }

    @Test
    void circleHitsRectOnRightDownCornertWorksProperly() {
        circle.setCenterY(rectangle.getPosY() + rectangle.getHeight() + 1);
        circle.setCenterX(rectangle.getPosX() + rectangle.getWidth() + 1);

        boolean isHitted = CollisionDetectorController.circleHitsRectOnRightDownCorner(circle, rectangle);

        assertEquals(true, isHitted);
    }



}

class Circle implements CircleShape {
    private Circle() {};

    private int radius;
    private int posX;
    private int posY;

    public static class Builder {
        public Circle build() {
            return new Circle(this);
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setPosX(int posX) {
            this.posX = posX;
            return this;
        }

        public Builder setPosY(int posY) {
            this.posY = posY;
            return this;
        }

        private int radius = 1;
        private int posX = 0;
        private int posY = 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    private Circle(Builder builder) {
        this.radius = builder.radius;
        this.posX = builder.posX;
        this.posY = builder.posY;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getCenterX() {
        return this.posX + this.radius;
    }

    @Override
    public int getCenterY() {
        return this.posY + this.radius;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public int getDiameter() {
        return this.radius * 2;
    }

    public void setCenterX(int x) {
        this.setPosX(x - this.radius);
    }
    public void setCenterY(int y) {
        this.setPosY(y - this.radius);
    }
}

class Rectangle implements RectShape {
    private Rectangle() {};

    private int posX;
    private int posY;
    private int width;
    private int height;

    public static class Builder {
        public Rectangle build() {
            return new Rectangle(this);
        }

        public Builder setPosX(int posX) {
            this.posX = posX;
            return this;
        }

        public Builder setPosY(int posY) {
            this.posY = posY;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        private int posX = 0;
        private int posY = 0;
        private int width = 1;
        private int height = 1;
    }

    public static Builder builder() {
        return new Builder();
    }

    private Rectangle(Builder builder) {
        this.posX = builder.posX;
        this.posY = builder.posY;
        this.width = builder.width;
        this.height = builder.height;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
