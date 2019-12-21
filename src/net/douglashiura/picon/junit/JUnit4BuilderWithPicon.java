package net.douglashiura.picon.junit;

<<<<<<< HEAD
=======
import net.douglashiura.picon.junit.PiconRunner;

>>>>>>> branch 'master' of https://github.com/douglashiura/picon.git
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class JUnit4BuilderWithPicon extends RunnerBuilder {
	@Override
	public Runner runnerForClass(Class<?> testClass) throws Throwable {
		return new PiconRunner(testClass);
	}
}
