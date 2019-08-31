package net.douglashiura.picon.junit;

import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class JUnit4BuilderWithPicon extends RunnerBuilder {
	@Override
	public Runner runnerForClass(Class<?> testClass) throws Throwable {
		return new PiconRunner(testClass);
	}
}
