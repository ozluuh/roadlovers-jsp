/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roadlovers.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ozluuh
 */
public interface BaseDao<E, K> {

	E findById(K id) throws SQLException;

	List<E> findAll() throws SQLException;
}
