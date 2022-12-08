package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void save(Set<Tag> tags) {
        Set<String> availableTags = new HashSet<>(tagRepository.findAllNames());
        for (Tag tag : tags) {
            if (!availableTags.contains(tag.getName())) {
                tagRepository.save(tag);
            } else {
                Tag tagInDB = tagRepository.findByName(tag.getName());
                tag.setId(tagInDB.getId());
                tag.setCreationTime(tagInDB.getCreationTime());
            }
        }
    }
}
