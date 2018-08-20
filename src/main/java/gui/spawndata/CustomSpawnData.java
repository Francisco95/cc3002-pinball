package gui.spawndata;

import com.almasb.fxgl.entity.SpawnData;
import gui.Config;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * Customization of {@link SpawnData} adding some parameters using the build pattern.
 * This customization is mainly used on the definition of additional walls.
 */
public class CustomSpawnData extends SpawnData {
    private double angle;
    private double length;
    private double height;
    private float restitution;
    private Color color;

    public static class Builder{
        private Point2D position = new Point2D(0, 0);
        private double angle = 0;
        private double length = Config.FLIPPER_WIDTH;
        private double height = 10;
        private float restitution = 0.5f;
        private Color color = Config.COLOR_WALL;

        public Builder(){}

        public Builder from(double x, double y){
            this.position = new Point2D(x, y);
            return this;
        }
        public Builder traslateToX(double newX){
            this.position = new Point2D(newX,this.position.getY());
            return this;
        }
        public Builder rotate(double angle){
            this.angle = angle;
            return this;
        }

        public Builder shape(double length, double height){
            this.length = length;
            this.height = height;
            return this;
        }

        public Builder paint(Color color){
            this.color = color;
            return this;
        }

        public Builder withRestitution(float restitution){
            this.restitution = restitution;
            return this;
        }

        public CustomSpawnData build(){
            CustomSpawnData data = new CustomSpawnData(this.position);
            data.angle = this.angle;
            data.restitution = this.restitution;
            data.length = this.length;
            data.height = this.height;
            data.color = this.color;
            return data;
        }
    }

    private CustomSpawnData(Point2D position){
        super(position);
    }

    public Color getColor() {
        return color;
    }

    public double getAngle() {
        return angle;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public float getRestitution() {
        return restitution;
    }
}
