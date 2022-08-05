package io.fozz101.ypm.repositories;

import io.fozz101.ypm.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findUserByUsername(String username);
    User getUserById(Long id);

}
