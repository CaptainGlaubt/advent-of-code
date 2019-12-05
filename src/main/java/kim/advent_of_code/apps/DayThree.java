package kim.advent_of_code.apps;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

public class DayThree extends AbstractDay {
	private static final String INPUT_FILE = "/input/day_three.input";

	@Data
	private static class Point {
		private final int x;
		private final int y;
	}

	@Data
	private static class Wire {
		private final List<Point> points = new ArrayList<>();

		public void addPoint(Point point) {
			points.add(point);
		}
	}

	public static void main(String[] args) {
		int lowestManhattanDistance = calculate("ManhattanDistanceCalc", () -> {
			List<Wire> wires = loadInputStream(INPUT_FILE).map(DayThree::calculateWire).collect(Collectors.toList());
			List<Point> intersectingPoints = calculateIntersections(wires.get(0), wires.get(1));
			return calculateLowestManhattanDistance(intersectingPoints);
		});
		System.out.println(String.format("Lowest manhattan distance %s", lowestManhattanDistance));
	}

	private static Wire calculateWire(String input) {
		String[] paths = input.split(",");
		int x = 0;
		int y = 0;
		Wire wire = new Wire();
		for (String path : paths) {
			String direction = path.substring(0, 1);
			Integer distance = Integer.parseInt(path.substring(1));
			for (int i = 0; i < distance; i++) {
				Point point = calculateNextPoint(x, y, direction);
				x = point.getX();
				y = point.getY();
				wire.addPoint(point);
			}
		}
		return wire;
	}

	private static Point calculateNextPoint(int x, int y, String direction) {
		switch (direction) {
		case "R":
			x = x + 1;
			break;
		case "L":
			x = x - 1;
			break;
		case "U":
			y = y + 1;
			break;
		case "D":
			y = y - 1;
			break;
		default:
			throw new IllegalArgumentException(String.format("Unknown direction %s", direction));
		}
		return new Point(x, y);
	}

	private static List<Point> calculateIntersections(Wire wire1, Wire wire2) {
		List<Point> intersectingPoints = new ArrayList<>();
		List<Point> wire1Points = wire1.getPoints();
		List<Point> wire2Points = wire2.getPoints();
		for (Point point : wire1Points) {
			if (wire2Points.contains(point)) {
				intersectingPoints.add(point);
			}
		}
		return intersectingPoints;
	}

	private static int calculateLowestManhattanDistance(List<Point> points) {
		return points.stream().map(point -> Math.abs(point.getX()) + Math.abs(point.getY()))
				.collect(Collectors.summarizingInt(Integer::intValue)).getMin();
	}
}
