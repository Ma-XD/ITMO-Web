package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostFormDTO;

@Component
public class PostFormDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostFormDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostFormDTO postFormDTO = (PostFormDTO) target;
            if (postFormDTO.getTagsLine().trim().isEmpty()) {
                return;
            }
            String[] tags = postFormDTO.getTagsLine().trim().split("\\s+");
            for(String tag : tags) {
                if (tag.isEmpty() || !tag.matches("[a-z]+")) {
                    errors.rejectValue("tagsLine", "tagsLine.invalid-tags", "invalid tags");
                    return;
                }
            }
        }
    }
}
