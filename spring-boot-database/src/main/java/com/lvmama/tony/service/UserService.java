package com.lvmama.tony.service;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/25
 */
public interface UserService {

    /**
     * 新增一个用户
     *
     * @param username
     * @param age
     */
    void create(String username, Integer age);

    /**
     * 根据name删除一个用户高
     *
     * @param username
     */
    void deleteByUserName(String username);

}
