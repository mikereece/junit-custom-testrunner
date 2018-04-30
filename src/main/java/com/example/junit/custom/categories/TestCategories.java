package com.example.junit.custom.categories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD })
public @interface TestCategories {

	public enum Priority {
		LOW, MEDIUM, HIGH
	};

	Priority[] priority();

	public enum Component {
		CORE, SERVICE, CRUD, INTERFACE, REPORTS
	};

	Component[] component();

	public enum Type {
		UNIT, SYSTEM_INTEGRATION, COMPONENT_INTEGRATION, END_TO_END, ACCEPTANCE
	};

	Type[] type();

}