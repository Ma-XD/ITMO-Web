package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostFormDTO;
import ru.itmo.wp.repository.PostRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostService {
    private final TagService tagService;
    private final PostRepository postRepository;

    public PostService(TagService tagService, PostRepository postRepository) {
        this.tagService = tagService;
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public void writeComment(Post post, User user, Comment comment) {
        post.addComment(comment);
        comment.setPost(post);
        comment.setUser(user);
        postRepository.save(post);
    }

    public Post getPostFromDTO(PostFormDTO postFormDTO) {
        Post post = new Post();
        post.setTitle(postFormDTO.getTitle());
        post.setText(postFormDTO.getText());

        if (!postFormDTO.getTagsLine().trim().isEmpty()) {
            String[] tagNames = postFormDTO.getTagsLine().trim().split("\\s+");
            Set<Tag> tags = new HashSet<>();
            for (String tagName : tagNames) {
                Tag tag = new Tag();
                tag.setName(tagName);
                tags.add(tag);
            }

            tagService.save(tags);
            post.setTags(tags);
        }
        return post;
    }
}
