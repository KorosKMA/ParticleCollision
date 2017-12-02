package complex;

import java.awt.Color;

import princeton.lib.StdDraw;

public class CollisionSystem {

	private static final double VEL_B = 0.1; // velocity border
	private static final double RAD_LO_B = 0.0125; // radius lower border
	private static final double RAD_HI_B = RAD_LO_B + 0.0125; // radius higher border

	private MinPQ<Event> pq = new MinPQ<Event>(10);
	private double t = 0.0;
	private Particle[] particles;
	private int numOfParticles;

	private class Event implements Comparable<Event> {
		private double time; // time of event
		private Particle a, b; // particles involved in event
		private int countA, countB; // collision counts for a and b

		public Event(double t, Particle a, Particle b) {
			time = t;
			this.a = a;
			this.b = b;
			if (a != null)
				countA = a.getCount();
			if (b != null)
				countB = b.getCount();
		}

		public int compareTo(Event that) {
			if (this.time > that.time)
				return -1;
			if (this.time < that.time)
				return 1;
			return 0;
		}

		public boolean isValid() {
			// unfinished
			if (a != null)
				if (b != null)
					return (a.timeToHit(b) <= 0 && (a.getCount() == countA && b.getCount() == countB));
				else
					return (a.timeToHitVerticalWall() <= 0 && (a.getCount() == countA));
			else if (b != null)
				return (b.timeToHitHorizontalWall() <= 0 && (countB == b.getCount()));
			else
				return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Event [time=" + time + ", a=" + a + ", b=" + b + ", isValid()=" + isValid() + "]";
		}

	}

	public CollisionSystem(Particle[] particles) {
		this.particles = particles;
		numOfParticles = particles.length;
	}

	private void predict(Particle a) {
		if (a == null)
			return;
		for (int i = 0; i < numOfParticles; i++) {
			double dTime = a.timeToHit(particles[i]);
			pq.insert(new Event((t + dTime), a, particles[i]));
		}
		pq.insert(new Event(t + a.timeToHitVerticalWall(), a, null));
		pq.insert(new Event(t + a.timeToHitHorizontalWall(), null, a));

	}

	private void redraw() {
		StdDraw.clear();
		for (int i = 0; i < numOfParticles; i++) {
			particles[i].move(0.1);
			particles[i].draw();
		}
		StdDraw.show(10);
		for (int i = 0; i < numOfParticles; i++)
			predict(particles[i]);
	}

	public void simulate() {
		pq.insert(new Event(0, null, null));
		while (!pq.isEmpty()) {
			Event event = pq.delMin();
			if (!event.isValid()) {
				continue;
			}
			Particle a = event.a;
			Particle b = event.b;
			for (int i = 0; i < particles.length; i++)
				particles[i].move(event.time - t);
			t = event.time;
			if (a != null && b != null)
				a.bounceOff(b);
			else if (a != null && b == null)
				a.bounceOffVerticalWall();
			else if (a == null && b != null)
				b.bounceOffHorizontalWall();
			else if (a == null && b == null)
				redraw();
			predict(a);
			predict(b);
		}
	}

	public static void main(String[] args) {
		int numOfParticles = 100;// Integer.parseInt(args[0]);
		Particle[] particles = new Particle[numOfParticles];
		StdDraw.setXscale(0, 1);
		StdDraw.setYscale(0, 1);
		double radiusAndMass;
		for (int i = 0; i < numOfParticles; i++) {
			radiusAndMass = Math.random() * (RAD_HI_B - RAD_LO_B) + RAD_LO_B;
			particles[i] = new Particle(Math.random(), Math.random(), VEL_B - Math.random() * 2 * VEL_B,
					VEL_B - Math.random() * 2 * VEL_B, radiusAndMass, radiusAndMass,
					new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
		}
		CollisionSystem simulation = new CollisionSystem(particles);
		while (true) {
			simulation.simulate();
		}
	}

}
