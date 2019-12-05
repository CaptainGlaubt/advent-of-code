package kim.advent_of_code.apps;

import java.util.stream.Collectors;

public class DayOne extends AbstractDay {
	private static final String INPUT_FILE = "/input/day_one.input";

	public static void main(String[] args) {
		long fuelForModulesSum = calculate("FuelForModueCalc",
				() -> loadInputStream(INPUT_FILE).map(Long::parseLong)
						.map((mass) -> DayOne.calculateFuelForMass(0, mass))
						.collect(Collectors.summarizingLong(Long::longValue)).getSum());
		System.out.println(String.format("Sum of fuel: %s", fuelForModulesSum));
	}

	private static long calculateFuelForMass(long sum, long mass) {
		long fuelForMass = Math.floorDiv(mass, 3) - 2;
		return fuelForMass < 0 ? sum : calculateFuelForMass(sum + fuelForMass, fuelForMass);
	}
}
