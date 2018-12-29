package com.lvmama.tony.repository;


import com.lvmama.tony.model.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/26
 */
public interface AccountRepository extends ElasticsearchRepository<Account, String> {
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Account queryAccountById(String id);
}
