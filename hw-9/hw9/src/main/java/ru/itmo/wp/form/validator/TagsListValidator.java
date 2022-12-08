package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.TagsSource;

@Component
public class TagsListValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TagsSource.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            TagsSource tagsSource = (TagsSource) target;
            String[] tags = tagsSource.getSource().split("\\s+");
            for(String tag : tags) {
                if (tag.isEmpty() || !tag.matches("[a-z]+")) {
                    errors.rejectValue("source", "source.invalid-tags", "invalid tags");
                    return;
                }
            }
        }
    }
}
