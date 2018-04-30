package com.example.junit.custom.suite;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.example.junit.custom.categories.TestCategories;
import com.example.junit.custom.categories.TestCategories.Component;
import com.example.junit.custom.categories.TestCategories.Priority;
import com.example.junit.custom.categories.TestCategories.Type;
import com.example.junit.custom.runner.TestRunner;

@RunWith(TestRunner.class)
@TestCategories(component = { Component.CORE }, priority = { Priority.HIGH }, type = { Type.UNIT })
public class RunAllUnitTests {

	@Test
	public void testRunnable() throws Exception {
		assertTrue(true);
	}
}
