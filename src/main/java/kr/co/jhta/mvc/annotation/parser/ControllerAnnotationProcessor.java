package kr.co.jhta.mvc.annotation.parser;

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import kr.co.jhta.mvc.annotation.Controller;

public class ControllerAnnotationProcessor {

	public static Set<Class<?>> scanClass(String packageName) {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forPackage(packageName))
				.setScanners(new ResourcesScanner(), new TypeAnnotationsScanner(), new SubTypesScanner(false)));
		
		return reflections.getTypesAnnotatedWith(Controller.class);
	}
}
