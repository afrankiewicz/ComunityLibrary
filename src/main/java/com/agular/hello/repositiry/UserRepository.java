package com.agular.hello.repositiry;

import com.agular.hello.entity.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, Long> {

    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);
}
