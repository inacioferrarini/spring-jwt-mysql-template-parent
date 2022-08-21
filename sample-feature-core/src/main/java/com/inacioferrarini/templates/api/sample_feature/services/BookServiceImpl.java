package com.inacioferrarini.templates.api.sample_feature.services;

import com.inacioferrarini.templates.api.sample_feature.models.dtos.BookRecord;
import com.inacioferrarini.templates.api.sample_feature.repositories.BookRepository;
import com.inacioferrarini.templates.api.security.errors.exceptions.InvalidUserCredentialsException;
import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import com.inacioferrarini.templates.api.security.repositories.UserRepository;
import com.inacioferrarini.templates.api.security.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public void create(BookRecord book) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(book.owner().getUsername());
        //userEntity.orElseThrow(InvalidUserCredentialsException::new);
        // Create Exception for user not found



//        UserEntity userEntity = userRepository.findById(book.owner().id);
//        $user = Auth::guard('api')->user();
//        $input['user_id'] = $user->id;
//        $input['fii_ticker'] = $input['ticker'];
//        unset($input['ticker']);

//        $broker_id = $input['broker_id'];
//        if (is_null(Broker::find($broker_id))) {
//            throw new BrokerNotFoundException($broker_id);
//        }


    }

}
