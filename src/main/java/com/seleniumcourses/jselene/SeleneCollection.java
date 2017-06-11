package com.seleniumcourses.jselene;

import com.google.common.base.Function;
import com.seleniumcourses.jselene.locators.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yashaka on 3/30/17.
 */
public class SeleneCollection implements Collection<SeleneElement> {
    private final Locator<List<WebElement>> locator;

    public SeleneCollection(By by, WebDriver webdriver) {
        this(new ByWebElementsListLocator(by, webdriver));
    }

    public SeleneCollection(Locator<List<WebElement>> locator) {
        this.locator = locator;
    }

    public List<WebElement> getActualWebElements() {
        return this.locator.find();
    }

    public SeleneCollection should(Function<SeleneCollection, List<WebElement>> condition) {
        Wait.until(this, condition);
        return this;
    }

    @Override
    public String toString() {
        return this.locator.description();
    }

    public SeleneElement elementBy(Function<SeleneElement, WebElement> condition) {
        return new SeleneElement(new FoundByConditionCollectionWebElementLocator(this, condition));
    }

    public SeleneCollection filteredBy(Function<SeleneElement, WebElement> condition) {
        return new SeleneCollection(new FilteredByConditionWebElementsListLocator(this, condition));
    }

    public SeleneCollection excludedBy(Function<SeleneElement, WebElement> condition) {
        // todo: refactor to use "negated condition" not non-DRY ExcludedBlaBlaLocator ...
        return new SeleneCollection(new ExcludedByConditionWebElementsListLocator(this, condition));
    }

    @Override
    public int size() {
        return this.getActualWebElements().size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
//        return false;
    }

    @Override
    public Iterator<SeleneElement> iterator() {
        List<WebElement> webElements = this.getActualWebElements();
        SeleneCollection originalCollection = this;
        return new Iterator<SeleneElement>() {
            protected int index = 0;

            @Override
            public boolean hasNext() {
                return webElements.size() > this.index;
            }

            @Override
            public SeleneElement next() {
                SeleneElement indexedElement = new SeleneElement(new CachedWebElementLocator(webElements.get(index),
                        String.format("(%s)[%s]", originalCollection, index)));
                this.index += 1;
                return indexedElement;
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new NotImplementedException();
//        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new NotImplementedException();
//        return null;
    }

    @Override
    public boolean add(SeleneElement seleneElement) {
        throw new NotImplementedException();
//        return false;
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
//        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
//        return false;
    }

    @Override
    public boolean addAll(Collection<? extends SeleneElement> c) {
        throw new NotImplementedException();
//        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
//        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
//        return false;
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }
}