package com.example.junit.custom.categories.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import com.example.junit.custom.categories.TestCategories;
import com.example.junit.custom.categories.TestCategories.Component;
import com.example.junit.custom.categories.TestCategories.Priority;
import com.example.junit.custom.categories.TestCategories.Type;

public class TestCategoryUtil {

	public static boolean doesTestCategoriesMatch(TestCategories suiteCategories, TestCategories testCategories) {
		return doesComponentMatch(suiteCategories.component(), testCategories.component())
				&& doesPrioritiesMatch(suiteCategories.priority(), testCategories.priority()) && doesTypesMatch(suiteCategories.type(), testCategories.type());
	}

	private static boolean doesComponentMatch(Component[] suiteComponents, Component[] matchComponents) {
		Set<Component> set = new HashSet<Component>(Arrays.asList(matchComponents));
		return Arrays.stream(suiteComponents).anyMatch(set::contains);
	}

	private static boolean doesPrioritiesMatch(Priority[] suitePriorities, Priority[] matchPriorities) {
		Set<Priority> set = new HashSet<Priority>(Arrays.asList(matchPriorities));
		return Arrays.stream(suitePriorities).anyMatch(set::contains);
	}

	private static boolean doesTypesMatch(Type[] suiteTypes, Type[] matchTypes) {
		Set<Type> set = new HashSet<Type>(Arrays.asList(matchTypes));
		return Arrays.stream(suiteTypes).anyMatch(set::contains);
	}
}
