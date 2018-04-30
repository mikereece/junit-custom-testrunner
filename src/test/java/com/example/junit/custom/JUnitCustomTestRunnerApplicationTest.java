package com.example.junit.custom;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import com.example.junit.custom.categories.TestCategories;
import com.example.junit.custom.categories.TestCategories.Component;
import com.example.junit.custom.categories.TestCategories.Priority;
import com.example.junit.custom.categories.TestCategories.Type;
import junit.framework.TestCase;

/**
 * Unit test for Category Types
 */
@RunWith(BlockJUnit4ClassRunner.class)
@TestCategories(component = { Component.CORE }, priority = { Priority.HIGH }, type = { Type.UNIT })
public class JUnitCustomTestRunnerApplicationTest extends TestCase {

	@Test
	public void testCore() {
		Assert.assertTrue(true);
	}

	@Test
	@TestCategories(component = { Component.CRUD }, priority = { Priority.HIGH }, type = { Type.UNIT })
	public void testCRUD() {
		Assert.assertTrue(true);
	}

	@Test
	@TestCategories(component = { Component.INTERFACE }, priority = { Priority.HIGH }, type = { Type.UNIT })
	public void testInterface() {
		Assert.assertTrue(true);
	}

	@Test
	@TestCategories(component = { Component.REPORTS }, priority = { Priority.HIGH }, type = { Type.UNIT })
	public void testReports() {
		Assert.assertTrue(true);
	}

	@Test
	@TestCategories(component = { Component.SERVICE }, priority = { Priority.HIGH }, type = { Type.UNIT })
	public void testService() {
		Assert.assertTrue(true);
	}
}
