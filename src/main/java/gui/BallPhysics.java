package gui;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

/**
 * Class that define the whole physics of the ball and how interact (collide) with other objects
 *
 */
public class BallPhysics {


    public static double estimateGradientOfPlane(Point2D position1, Point2D position2){
        return (position2.getY() - position1.getY()) / (position2.getX() - position1.getX());
    }

    public static double gradientOfPerpendicularPlane(double gradientOfPlane){
        return 1 / gradientOfPlane;
    }

    public static Point2D vectorOnRotatedPlane(Point2D vec, double angleOfRotation){
        double angleOfRotationRadians = Math.toRadians(angleOfRotation);
        double xTilde = Math.cos(angleOfRotationRadians) * vec.getX()
                - Math.sin(angleOfRotationRadians) * vec.getY();
        double yTilde = Math.sin(angleOfRotationRadians) * vec.getX()
                + Math.cos(angleOfRotationRadians) * vec.getY();
        return new Point2D(xTilde, yTilde);
    }

    public static Point2D vectorFromRotatedPlane(Point2D vec, double angleOfRotation){
//        yTilde *= -1;
        double angleOfRotationRadians = Math.toRadians(angleOfRotation);
        double x = Math.cos(angleOfRotationRadians) * vec.getX() + Math.sin(angleOfRotationRadians) * vec.getY();
        double y = - Math.sin(angleOfRotationRadians) * vec.getX() + Math.cos(angleOfRotationRadians) * vec.getY();
        return new Point2D(x, y);
    }

    public static Point2D reflectVectorOnRotatedPlane(Point2D vec, double gradient){
        Point2D gradientToPlane = new Point2D(1, gradient);
        double angleBetweenPlanes = gradientToPlane.angle(new Point2D(0, 1));
        Point2D vecTilde = vectorOnRotatedPlane(vec, angleBetweenPlanes);
        System.out.println(vecTilde);
        System.out.println((-2*vecTilde.getY()));
        vecTilde = new Point2D(vecTilde.getX(), -vecTilde.getY());
        System.out.println(vecTilde);
        return vectorFromRotatedPlane(vecTilde, angleBetweenPlanes);
    }

    public static void ballReflectedWhenCollideToEntity(Entity ball, Entity anEntity){
        Point2D positionTarget = anEntity.getPosition();
        Point2D positionBall = ball.getPosition();
        double gradient =estimateGradientOfPlane(positionTarget, positionBall);
        PhysicsComponent physics = ball.getComponent(PhysicsComponent.class);
        Point2D newVelocity = reflectVectorOnRotatedPlane(physics.getLinearVelocity(), gradient);
        physics.setLinearVelocity(newVelocity);
    }
}
