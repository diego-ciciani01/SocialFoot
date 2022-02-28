package com.socialfood.app.service;

import com.socialfood.app.model.Post;
import com.socialfood.app.model.Utente;
import com.socialfood.app.repository.PostCrudRepository;
import com.socialfood.app.repository.UtenteCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service("PostService")
@Transactional
public class PostService {
    @Autowired
    private UtenteCrudRepository utenteCrudRepository;
    @Autowired
    private PostCrudRepository postCrudRepository;

    public void creaPost(Post p) {
        postCrudRepository.save(p);
    }
    
    public List<Post> selectAll(Integer id){
        return postCrudRepository.findAllByUtente(new Utente(id));
    }
    
}