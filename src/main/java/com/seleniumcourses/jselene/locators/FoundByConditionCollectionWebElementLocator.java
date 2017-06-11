package com.seleniumcourses.jselene.locators;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.base.Function;
import com.seleniumcourses.jselene.ConditionNotMatchedException;
import com.seleniumcourses.jselene.Locator;
import com.seleniumcourses.jselene.SeleneCollection;
import com.seleniumcourses.jselene.SeleneElement;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static com.seleniumcourses.jselene.conditions.ConditionUtils.checkCondition;

/**
 * Created by yashaka on 3/30/17.
 */
public class FoundByConditionCollectionWebElementLocator implements Locator<WebElement> {
    private final SeleneCollection collection;
    private final Function<SeleneElement, WebElement> condition;

    public FoundByConditionCollectionWebElementLocator(SeleneCollection collection, Function<SeleneElement, WebElement> condition) {
        this.collection = collection;
        this.condition = condition;
    }

    @Override
    public WebElement find() {
        Optional<SeleneElement> result = this.collection.stream()
                .filter(it -> checkCondition(it, this.condition)).findFirst();
        if (result.isPresent()) {
            return result.get().getActualWebElement();
        } else {
            throw new ConditionNotMatchedException("no any collection element matched condition");
        }
    }

    @Override
    public String description() {
        return String.format("{%s}.findBy(%s)", this.collection, this.condition);
    }
}
