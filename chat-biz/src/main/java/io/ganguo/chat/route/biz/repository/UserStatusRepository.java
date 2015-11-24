package io.ganguo.chat.route.biz.repository;

import io.ganguo.chat.route.biz.entity.User;
import io.ganguo.chat.route.biz.entity.UserStatus;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * Created by James on 2015/11/12 0012.
 */

@Repository
public interface UserStatusRepository  extends CrudRepository<UserStatus, BigInteger> {

    UserStatus findByAccount(String account);

}
