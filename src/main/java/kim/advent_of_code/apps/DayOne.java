package kim.advent_of_code.apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayOne {
	public static void main(String[] args) {
		long fuelForModulesSum = calculate("FuelForModueCalc",
				() -> loadInputStream().map(Long::parseLong).map((mass) -> DayOne.calculateFuelForMass(0, mass))
						.collect(Collectors.summarizingLong(Long::longValue)).getSum());
		System.out.println(String.format("Sum of fuel: %s", fuelForModulesSum));
	}

	private static Stream<String> loadInputStream() {
		try (InputStream in = DayOne.class.getResourceAsStream("/input/day_one.input");
				InputStreamReader inReader = new InputStreamReader(in);
				BufferedReader buffReader = new BufferedReader(inReader)) {
			return buffReader.lines().collect(Collectors.toList()).stream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static long calculateFuelForMass(long sum, long mass) {
		long fuelForMass = Math.floorDiv(mass, 3) - 2;
		return fuelForMass < 0 ? sum : calculateFuelForMass(sum + fuelForMass, fuelForMass);
	}

	private static <T> T calculate(String calcName, Supplier<T> supplier) {
		Instant calcStart = Instant.now();
		System.out.println(String.format("Calculation '%s' started; start time: %s", calcName, calcStart.toString()));
		T result = supplier.get();
		Instant calcEnd = Instant.now();
		long totalTimeMs = Duration.between(calcStart, calcEnd).toMillis();
		System.out.println(String.format("Calculation '%s' ended; end time: %s; total time; %s ms", calcName,
				calcEnd.toString(), totalTimeMs));
		return result;
	}
}
