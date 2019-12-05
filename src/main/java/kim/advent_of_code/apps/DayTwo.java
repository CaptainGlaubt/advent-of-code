package kim.advent_of_code.apps;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DayTwo extends AbstractDay {
	private static final String INPUT_FILE = "/input/day_two.input";

	private enum Instruction {
		ADD(1), MULTIPLY(2), TERMINATE(99);

		private int instruction;

		private Instruction(int code) {
			this.instruction = code;
		}

		public static Instruction parse(int instructionValue) {
			for (Instruction instruction : Instruction.values()) {
				if (instruction.instruction == instructionValue) {
					return instruction;
				}
			}
			throw new IllegalArgumentException(String.format("Unknown instruction %s", instructionValue));
		}
	}

	private static class Result {
		private Integer result = 0;
		private Integer noun = 0;
		private Integer verb = 0;

		public Integer getResult() {
			return result;
		}

		public void setResult(Integer result) {
			this.result = result;
		}

		public Integer getNoun() {
			return noun;
		}

		public void setNoun(Integer noun) {
			this.noun = noun;
		}

		public Integer getVerb() {
			return verb;
		}

		public void setVerb(Integer verb) {
			this.verb = verb;
		}
	}

	public static void main(String[] args) {
		String input = loadInput(INPUT_FILE,
				(stream) -> stream.findFirst().orElseThrow(() -> new IllegalStateException("No input!")));
		Result result = calculate("IntComputerCalc", () -> runCalculation(input, 19690720));

		System.out.println(String.format("IntComputer result: %s; noun: %s; verb: %s", result.getResult(),
				result.getNoun(), result.getVerb()));
	}

	private static Result runCalculation(String input, Integer expectedResult) {
		for (int noun = 0; noun < 100; noun++) {
			for (int verb = 0; verb < 100; verb++) {
				List<Integer> opCodes = StreamSupport.stream(Arrays.spliterator(input.split(",")), false)
						.map(Integer::parseInt).collect(Collectors.toList());
				opCodes.set(1, noun);
				opCodes.set(2, verb);
				runProgram(0, opCodes);
				Integer calcResult = opCodes.get(0);
				if (calcResult.intValue() == expectedResult.intValue()) {
					Result result = new Result();
					result.setResult(100 * noun + verb);
					result.setNoun(noun);
					result.setVerb(verb);
					return result;
				}
			}
		}
		throw new IllegalArgumentException(String.format("Unable to find expected result %s", expectedResult));
	}

	private static void runProgram(Integer instructionPointer, List<Integer> opCodes) {
		Instruction opCode = Instruction.parse(opCodes.get(instructionPointer));
		if (opCode != Instruction.TERMINATE) {
			Integer parameter1 = opCodes.get(instructionPointer + 1);
			Integer arg1 = opCodes.get(parameter1);
			Integer parameter2 = opCodes.get(instructionPointer + 2);
			Integer arg2 = opCodes.get(parameter2);
			Integer parameter3 = opCodes.get(instructionPointer + 3);
			switch (opCode) {
			case ADD:
				opCodes.set(parameter3, arg1 + arg2);
				break;
			case MULTIPLY:
				opCodes.set(parameter3, arg1 * arg2);
				break;
			default:
				throw new IllegalStateException(String.format("Unknown opCode %s", opCode));
			}
			Integer newPosition = instructionPointer + 4;
			runProgram(newPosition, opCodes);
		}
	}
}
