package simple;
import java.awt.Color;

import princeton.lib.StdDraw;

public class Ball {

	private static final int BORDER = BouncingBalls.POS_B;
	//StdDraw creates 10% border after setting x and y scale. This is compensation
	private static final int B_COMPENSATION = (int) (BORDER*(1-Math.sqrt(0.9)));

	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius
	private Color color;

	public Ball(double rx, double ry, double vx, double vy, double radius, Color color) {
		this.rx = rx;
		this.ry = ry;
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
		this.color = color;
	}

	public void move(double dt) {
		if ((vx <= 0 && rx + vx * dt < radius - B_COMPENSATION) || (vx >= 0 && rx + vx * dt > BORDER + B_COMPENSATION - radius)) {
			vx = -vx;
			this.color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
		}
		if ((vy <= 0 && ry + vy * dt < radius - B_COMPENSATION) || (vy >= 0 && ry + vy * dt > BORDER + B_COMPENSATION - radius)) {
			vy = -vy;
			this.color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
		}
		rx = rx + vx * dt;
		ry = ry + vy * dt;
	}

	public void draw() {
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, radius);
	}
}
