package complex;

import java.awt.Color;

import princeton.lib.StdDraw;

public class Particle {

	private static final double BORDER = 1;
	// StdDraw creates 10% border after setting x and y scale. This is
	// compensation
	private static final double B_COMPENSATION = BORDER * (1 - Math.sqrt(0.9));

	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius
	private final double mass; // mass
	private Color color; // color
	private int count; // number of collisions
	boolean hasCollided = false;

	public Particle(double rx, double ry, double vx, double vy, double radius, double mass, Color color) {
		this.rx = rx;
		this.ry = ry;
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
		this.mass = mass;
		this.color = color;
	}

	public void move(double dt) {
		// unfinished
		rx = rx + vx * dt;
		ry = ry + vy * dt;
	}

	public void draw() {
		// unfinished
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, radius);
	}

	public double timeToHit(Particle that) {
		if (this == that)
			return Double.POSITIVE_INFINITY;
		double dx = that.rx - this.rx, dy = that.ry - this.ry;
		double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
		double dvdr = dx * dvx + dy * dvy;
		if (dvdr > 0)
			return Double.POSITIVE_INFINITY;
		double dvdv = dvx * dvx + dvy * dvy;
		double drdr = dx * dx + dy * dy;
		double sigma = this.radius + that.radius;
		double d = (dvdr * dvdr) - dvdv * (drdr - sigma * sigma);
		if (d < 0)
			return Double.POSITIVE_INFINITY;
		return -(dvdr + Math.sqrt(d)) / dvdv;

	}

	public double timeToHitVerticalWall() {
		// unfinished
		return vx < 0 ? (B_COMPENSATION + rx - radius) / (-vx) : (BORDER + B_COMPENSATION - rx - radius) / vx;
	}

	public double timeToHitHorizontalWall() {
		// unfinished
		return vy < 0 ? (B_COMPENSATION + ry - radius) / (-vy) : (BORDER + B_COMPENSATION - ry - radius) / vy;
	}

	public void bounceOff(Particle that) {
		double dx = that.rx - this.rx, dy = that.ry - this.ry;
		double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
		double dvdr = dx * dvx + dy * dvy;
		double dist = this.radius + that.radius;
		double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
		double Jx = J * dx / dist;
		double Jy = J * dy / dist;
		this.vx += Jx / this.mass;
		this.vy += Jy / this.mass;
		that.vx -= Jx / that.mass;
		that.vy -= Jy / that.mass;
		this.count++;
		that.count++;
		this.color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
		that.color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
	}

	public void bounceOffVerticalWall() {
		// unfinished
		this.color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
		vx = -vx;
		count++;
	}

	public void bounceOffHorizontalWall() {
		// unfinished
		this.color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
		vy = -vy;
		count++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[x=" + rx + ", y=" + ry + "]";
	}

	public int getCount() {
		return count;
	}

}