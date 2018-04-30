package com.example.junit.custom.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import com.example.junit.custom.categories.TestCategories;
import com.example.junit.custom.categories.util.TestCategoryUtil;

public class TestRunner extends BlockJUnit4ClassRunner {

	private static final String TEST_POSTFIX = "Test";

	private final TestClass testSuiteClass;
	private List<FrameworkMethod> validTestMethods = new ArrayList<FrameworkMethod>();

	@SuppressWarnings("rawtypes")
	public TestRunner(Class clazz) throws InitializationError {
		super(clazz);
		Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
		Set<Class<?>> testClasses = reflections.getTypesAnnotatedWith(TestCategories.class);

		testSuiteClass = new TestClass(clazz);
		TestCategories testSuiteCategories = testSuiteClass.getAnnotation(TestCategories.class);

		for (Class<?> testClass : testClasses) {
			TestCategories classCategories = testClass.getAnnotation(TestCategories.class);

			if (!testSuiteClass.getName().equals(testClass.getName())) {
				if (testClass.getName().endsWith(TEST_POSTFIX)) {

					Method[] classMethods = testClass.getDeclaredMethods();
					for (int i = 0; i < classMethods.length; i++) {
						Method classMethod = classMethods[i];
						FrameworkMethod frameworkMethod = new FrameworkMethod(classMethod);
						TestCategories methodCategories = frameworkMethod.getAnnotation(TestCategories.class);

						addValidTestMethods(testSuiteCategories, classCategories, methodCategories, frameworkMethod);
					}
				}
			}
		}
	}

	@Override
	public void run(RunNotifier runNotifier) {
		// Description runnerDescription = Description.createSuiteDescription(testSuiteClass.getJavaClass());
		for (int i = 0; i < validTestMethods.size(); i++) {
			FrameworkMethod method = validTestMethods.get(i);
			Description suiteDescription = Description.createSuiteDescription(method.getDeclaringClass());
			// runnerDescription.addChild(suiteDescription);
			Description testDescription = Description.createTestDescription(method.getDeclaringClass(), method.getName());
			suiteDescription.addChild(testDescription);
			runNotifier.addListener(new RunListener());
			runNotifier.fireTestStarted(testDescription);
			// FIXME something needs to actually run the test here?
			try {
				Statement statement = classBlock(runNotifier);
				statement.evaluate();
				// runNotifier.fireTestFinished(testDescription);
			} catch (AssumptionViolatedException e) {
				runNotifier.fireTestIgnored(testDescription);
			} catch (StoppedByUserException e) {
				throw e;
			} catch (Throwable e) {
				runNotifier.fireTestFailure(new Failure(testDescription, e));
			}
		}
	}

	private void addValidTestMethods(TestCategories testSuiteCategories, TestCategories classCategories, TestCategories methodCategories,
			FrameworkMethod classMethod) {
		if (testSuiteCategories == null) {
			validTestMethods.add(classMethod);
		} else {
			if (classCategories != null) {
				if (methodCategories == null) {
					if (TestCategoryUtil.doesTestCategoriesMatch(testSuiteCategories, classCategories)) {
						validTestMethods.add(classMethod);
					}
				} else {
					if (TestCategoryUtil.doesTestCategoriesMatch(testSuiteCategories, methodCategories)) {
						validTestMethods.add(classMethod);
					}
				}
			} else {
				if (methodCategories != null) {
					if (TestCategoryUtil.doesTestCategoriesMatch(testSuiteCategories, methodCategories)) {
						validTestMethods.add(classMethod);
					}
				}
			}
		}
	}

	@Override
	public Description getDescription() {
		Description spec = Description.createSuiteDescription(this.testSuiteClass.getName(), this.testSuiteClass.getJavaClass().getAnnotations());
		return spec;
	}
}