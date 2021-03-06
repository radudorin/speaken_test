package com.springapp.hardware_store.dao;

import com.springapp.hardware_store.model.Member;
import com.springapp.hardware_store.model.ShoppingCart;

import java.util.List;

/**
 * Created by radud on 02/12/2015.
 */
public interface MemberDAO {

    public int save(Member entity);

    public Member delete(int id);

    public Member findById(int id);

    public List<Member> findAll();

    public Member getByField(String fieldName, String fieldValue);

}
