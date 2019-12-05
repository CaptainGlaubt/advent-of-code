package kim.advent_of_code.apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbstractDay {

	protected static <T> T loadInput(String inputFile, Function<Stream<String>, T> inputFunc) {
		return inputFunc.apply(loadInputStream(inputFile));
	}

	protected static Stream<String> loadInputStream(String inputFile) {
		try (InputStream in = DayOne.class.getResourceAsStream(inputFile);
				InputStreamReader inReader = new InputStreamReader(in);
				BufferedReader buffReader = new BufferedReader(inReader)) {
			return buffReader.lines().collect(Collectors.toList()).stream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected static <T> T calculate(String calcName, Supplier<T> supplier) {
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
