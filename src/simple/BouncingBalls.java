package simple;
import java.awt.Color;
import java.util.Random;

import princeton.lib.StdDraw;

public class BouncingBalls {

	private static final Random rand = new Random();

	static final int POS_B = 32768;

	private static final int VEL_B = 1000;
	private static final int RAD_LO_B = 100;
	private static final int RAD_HI_B = 500;

	public static void main(String[] args) {
		int N = 2000;// Integer.parseInt(args[0]);
		StdDraw.setXscale(0, POS_B);
		StdDraw.setYscale(0, POS_B);
		Ball[] balls = new Ball[N];
		int radius;
		for (int i = 0; i < N; i++) {
			radius = (RAD_LO_B + rand.nextInt(RAD_HI_B - RAD_LO_B));
			balls[i] = new Ball(radius + rand.nextInt(POS_B - radius), radius + rand.nextInt(POS_B - radius),
					VEL_B - rand.nextInt(2 * VEL_B), VEL_B - rand.nextInt(2 * VEL_B), radius,
					new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
		}
		while (true) {
			StdDraw.clear();
			for (int i = 0; i < N; i++) {
				balls[i].move(0.5);
				balls[i].draw();
			}
			StdDraw.show(10);
		}
	}
}
